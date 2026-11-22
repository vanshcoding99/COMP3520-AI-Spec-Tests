#!/usr/bin/env python3
"""Systematic mutant generator (secondary, tool-generated fault set).

Applies standard mutation operators at every applicable location in each correct unit:
  ROR  relational operator replacement  (<, <=, >, >=, ==, !=  -> each other operator)
  AOR  arithmetic operator replacement  (+, -, *, /, %  -> each other operator, binary only)
  CRP  integer literal +1 and -1
Writes benchmark/mutants.json in the same find/replace format as faults.json, except that
each entry carries a character offset so the exact occurrence is mutated.
Mutants that do not compile are dropped. Mutants killed by no test in any condition are
reported separately as "possibly equivalent" (not checked by hand).
"""
import json
import os
import re
import subprocess
import tempfile
import shutil

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = os.path.join(ROOT, "benchmark", "src", "bench")
REL = ["<=", ">=", "==", "!=", "<", ">"]
ARI = ["+", "-", "*", "/", "%"]


def code_spans(src):
    """Yield (start, end) of code regions outside string/char literals and comments."""
    spans, i, start, n = [], 0, 0, len(src)
    while i < n:
        if src.startswith("//", i):
            spans.append((start, i)); j = src.find("\n", i); i = n if j < 0 else j; start = i
        elif src.startswith("/*", i):
            spans.append((start, i)); j = src.find("*/", i); i = n if j < 0 else j + 2; start = i
        elif src[i] in "\"'":
            spans.append((start, i)); q = src[i]; i += 1
            while i < n and src[i] != q:
                i += 2 if src[i] == "\\" else 1
            i += 1; start = i
        else:
            i += 1
    spans.append((start, n))
    return spans


def candidates(unit, src):
    out = []
    lines_skip = set()
    for (a, b) in code_spans(src):
        seg = src[a:b]
        for m in re.finditer(r"<=|>=|==|!=|(?<![<>=!-])<(?![<=])|(?<![<>=-])>(?![>=])", seg):
            pos = a + m.start()
            line = src[:pos].count("\n") + 1
            if "class " in src.splitlines()[line - 1] or "<" in seg[m.start():m.start()+1] and "int[]" in seg[max(0,m.start()-5):m.start()]:
                continue
            for r in REL:
                if r != m.group(0):
                    out.append(("ROR", pos, m.group(0), r, line))
        for m in re.finditer(r"(?<=[\w\)\]] )([+\-*/%])(?= [\w\(])", seg):
            pos = a + m.start(1)
            line = src[:pos].count("\n") + 1
            for r in ARI:
                if r != m.group(1):
                    out.append(("AOR", pos, m.group(1), r, line))
        for m in re.finditer(r"(?<![\w.])(\d+)(?![\w.])", seg):
            pos = a + m.start(1)
            line = src[:pos].count("\n") + 1
            v = int(m.group(1))
            for r in (v + 1, v - 1):
                if r >= 0:
                    out.append(("CRP", pos, m.group(1), str(r), line))
    return out


def compiles(src, unit):
    d = tempfile.mkdtemp()
    try:
        p = os.path.join(d, unit + ".java")
        open(p, "w").write(src)
        r = subprocess.run(["javac", "-nowarn", "-d", d, p], capture_output=True)
        return r.returncode == 0
    finally:
        shutil.rmtree(d, ignore_errors=True)


def main():
    mutants = []
    for fn in sorted(os.listdir(SRC)):
        unit = fn[:-5]
        src = open(os.path.join(SRC, fn)).read()
        k = 0
        for op, pos, old, new, line in candidates(unit, src):
            msrc = src[:pos] + new + src[pos + len(old):]
            if not compiles(msrc, unit):
                continue
            k += 1
            mutants.append({"id": f"{unit}-M{k:02d}", "unit": unit, "op": op,
                            "offset": pos, "old": old, "new": new, "line": line,
                            "desc": f"{op} line {line}: '{old}' -> '{new}'"})
        print(unit, k, flush=True)
    json.dump(mutants, open(os.path.join(ROOT, "benchmark", "mutants.json"), "w"), indent=1)
    print("total", len(mutants))


if __name__ == "__main__":
    main()
