#!/usr/bin/env python3
"""Evaluation harness for spec-only AI-generated JUnit tests.

For every condition (generated/<condition>/) and every benchmark unit it:
  1. compiles the generated test against the correct implementation,
  2. runs it and records which test methods fail on correct code (false alarms),
  3. applies each seeded fault from benchmark/faults.json, recompiles and reruns,
  4. counts a fault as detected only if at least one test method that PASSED on the
     correct implementation fails (or the run times out) on the faulty version.

Usage: python3 tools/evaluate.py [condition ...]
Writes results/raw_results.csv, results/unit_summary.csv and results/summary.json.
"""
import csv
import json
import os
import re
import shutil
import subprocess
import sys
import tempfile
from concurrent.futures import ThreadPoolExecutor

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = os.path.join(ROOT, "benchmark", "src", "bench")
FAULT_SET = os.environ.get("FAULT_SET", "faults")  # "faults" (hand-seeded) or "mutants"
FAULTS = json.load(open(os.path.join(ROOT, "benchmark", FAULT_SET + ".json")))
PREFIX = "" if FAULT_SET == "faults" else FAULT_SET + "_"
LIB = os.path.join(ROOT, "lib")
CP_LIBS = os.pathsep.join(
    os.path.join(LIB, j) for j in ("junit-4.13.2.jar", "hamcrest-core-1.3.jar"))
TIMEOUT_S = 20
UNITS = sorted({f["unit"] for f in FAULTS})

FAIL_RE = re.compile(r"^\d+\) (\S+)\(bench\.\w+\)", re.M)
OK_RE = re.compile(r"^OK \((\d+) tests?\)", re.M)
RUN_RE = re.compile(r"^Tests run: (\d+),\s+Failures: (\d+)", re.M)


def compile_and_run(unit, unit_source, test_path):
    """Return dict(compiled, timeout, total, failed:set, log)."""
    work = tempfile.mkdtemp(prefix="eval_")
    try:
        pkg = os.path.join(work, "src", "bench")
        os.makedirs(pkg)
        with open(os.path.join(pkg, unit + ".java"), "w") as fh:
            fh.write(unit_source)
        shutil.copy(test_path, os.path.join(pkg, unit + "Test.java"))
        out = os.path.join(work, "out")
        os.makedirs(out)
        c = subprocess.run(
            ["javac", "-nowarn", "-cp", CP_LIBS, "-d", out]
            + [os.path.join(pkg, f) for f in os.listdir(pkg)],
            capture_output=True, text=True)
        if c.returncode != 0:
            return {"compiled": False, "timeout": False, "total": 0,
                    "failed": set(), "log": c.stderr}
        try:
            r = subprocess.run(
                ["java", "-cp", out + os.pathsep + CP_LIBS,
                 "org.junit.runner.JUnitCore", "bench." + unit + "Test"],
                capture_output=True, text=True, timeout=TIMEOUT_S)
        except subprocess.TimeoutExpired:
            return {"compiled": True, "timeout": True, "total": 0,
                    "failed": set(), "log": "timeout"}
        text = r.stdout
        m = OK_RE.search(text)
        if m:
            return {"compiled": True, "timeout": False, "total": int(m.group(1)),
                    "failed": set(), "log": ""}
        m = RUN_RE.search(text)
        total = int(m.group(1)) if m else 0
        return {"compiled": True, "timeout": False, "total": total,
                "failed": set(FAIL_RE.findall(text)), "log": text[-3000:]}
    finally:
        shutil.rmtree(work, ignore_errors=True)


def count_test_methods(path):
    return len(re.findall(r"@Test\b", open(path).read()))


def evaluate(condition):
    rows, unit_rows = [], []
    gen_dir = os.path.join(ROOT, "generated", condition)
    for unit in UNITS:
        test_path = os.path.join(gen_dir, unit + "Test.java")
        correct_src = open(os.path.join(SRC, unit + ".java")).read()
        faults = [f for f in FAULTS if f["unit"] == unit]
        if not os.path.exists(test_path):
            unit_rows.append({"condition": condition, "unit": unit, "present": 0,
                              "compiled": 0, "test_methods": 0, "false_alarms": 0,
                              "faults": len(faults), "detected": 0})
            continue
        base = compile_and_run(unit, correct_src, test_path)
        n_methods = count_test_methods(test_path)
        detected = 0

        def apply(f):
            if "offset" in f:
                o = f["offset"]
                return correct_src[:o] + f["new"] + correct_src[o + len(f["old"]):]
            return correct_src.replace(f["find"], f["replace"], 1)

        with ThreadPoolExecutor(max_workers=8) as ex:
            results = list(ex.map(
                lambda f: compile_and_run(unit, apply(f), test_path) if base["compiled"] else None,
                faults))
        for f, res in zip(faults, results):
            if res is None:
                killed, reason = False, "test did not compile"
            elif res["timeout"]:
                killed, reason = True, "timeout"
            else:
                new_fails = res["failed"] - base["failed"]
                killed = bool(new_fails)
                reason = ",".join(sorted(new_fails)) if killed else ""
            detected += int(killed)
            rows.append({"condition": condition, "unit": unit, "fault": f["id"],
                         "fault_desc": f["desc"], "detected": int(killed),
                         "killing_tests": reason})
        unit_rows.append({"condition": condition, "unit": unit, "present": 1,
                          "compiled": int(base["compiled"]),
                          "test_methods": n_methods,
                          "false_alarms": len(base["failed"]),
                          "false_alarm_tests": ",".join(sorted(base["failed"])),
                          "faults": len(faults), "detected": detected})
        print(f"{condition:11s} {unit:13s} compiled={base['compiled']!s:5s} "
              f"methods={n_methods:3d} false_alarms={len(base['failed'])} "
              f"detected={detected}/{len(faults)}", flush=True)
    return rows, unit_rows


def main():
    conditions = sys.argv[1:] or sorted(
        d for d in os.listdir(os.path.join(ROOT, "generated"))
        if os.path.isdir(os.path.join(ROOT, "generated", d)))
    all_rows, all_units = [], []
    for c in conditions:
        r, u = evaluate(c)
        all_rows += r
        all_units += u
    os.makedirs(os.path.join(ROOT, "results"), exist_ok=True)
    with open(os.path.join(ROOT, "results", PREFIX + "raw_results.csv"), "w", newline="") as fh:
        w = csv.DictWriter(fh, fieldnames=list(all_rows[0].keys()))
        w.writeheader()
        w.writerows(all_rows)
    keys = ["condition", "unit", "present", "compiled", "test_methods",
            "false_alarms", "false_alarm_tests", "faults", "detected"]
    with open(os.path.join(ROOT, "results", PREFIX + "unit_summary.csv"), "w", newline="") as fh:
        w = csv.DictWriter(fh, fieldnames=keys, extrasaction="ignore")
        w.writeheader()
        for u in all_units:
            w.writerow({k: u.get(k, "") for k in keys})
    summary = {}
    for c in conditions:
        us = [u for u in all_units if u["condition"] == c]
        summary[c] = {
            "units": len(us),
            "compiled_units": sum(u["compiled"] for u in us),
            "test_methods": sum(u["test_methods"] for u in us),
            "units_with_false_alarms": sum(1 for u in us if u["false_alarms"] > 0),
            "false_alarm_methods": sum(u["false_alarms"] for u in us),
            "faults": sum(u["faults"] for u in us),
            "faults_detected": sum(u["detected"] for u in us),
        }
    json.dump(summary, open(os.path.join(ROOT, "results", PREFIX + "summary.json"), "w"), indent=2)
    print(json.dumps(summary, indent=2))


if __name__ == "__main__":
    main()
