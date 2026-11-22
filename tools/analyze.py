#!/usr/bin/env python3
"""Secondary analysis: equivalent-mutant classification, kill redundancy, greedy minimal
suites, and simple static test-smell indicators. Writes results/analysis.json."""
import csv, json, os, re
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
R = lambda *p: os.path.join(ROOT, *p)
# Manually classified equivalent mutants (see README for the reasoning per mutant).
EQUIVALENT = {
 "ArrayMax-M02": "length <= 0 same as == 0 (length is never negative)",
 "ArrayMax-M11": "i != length same as i < length (i starts at 1, length >= 1)",
 "ArrayMax-M14": ">= selects an equal value, same maximum returned",
 "ArrayMax-M20": "starting at 0 compares values[0] with itself",
 "BinarySearch-M12": "branch only reached when sorted[mid] != key",
 "BinarySearch-M32": "mid = lo + (hi-lo)%2 stays in [lo,hi]: slower but correct",
 "BinarySearch-M44": "dividing by 3 keeps mid in [lo,hi]: correct",
 "BinarySearch-M45": "dividing by 1 gives mid = hi: slower but correct",
 "BoundedStack-M08": "size never exceeds capacity, so == equals >=",
 "BoundedStack-M13": "size is never negative", "BoundedStack-M19": "size is never negative",
 "BoundedStack-M25": "size is never negative",
 "FizzBuzz-M08": "n >= 1 so remainder is never negative", "FizzBuzz-M20": "same",
 "FizzBuzz-M32": "same",
 "Gcd-M13": "a, b already checked non-negative", "Gcd-M18": "same",
 "Gcd-M29": "b is never negative so b > 0 equals b != 0",
 "LeapYear-M08": "year >= 1 so remainder is never negative", "LeapYear-M13": "same",
 "LeapYear-M18": "same",
 "Palindrome-M02": "when i == j a character is compared with itself",
}
out = {}
for cond in ("basic", "structured"):
    rows = [r for r in csv.DictReader(open(R("results", "mutants_raw_results.csv"))) if r["condition"] == cond]
    hand = [r for r in csv.DictReader(open(R("results", "raw_results.csv"))) if r["condition"] == cond]
    nonequiv = [r for r in rows if r["fault"] not in EQUIVALENT]
    killers = {}
    for r in nonequiv + hand:
        tests = [t for t in r["killing_tests"].split(",") if t and t != "timeout"]
        killers[(r["unit"], r["fault"])] = set(tests) if tests else ({"<timeout>"} if r["detected"] == "1" else set())
    # redundancy and greedy minimal suite per unit
    units = sorted({u for u, _ in killers})
    min_total, redundancy = 0, []
    for u in units:
        faults = {f: k for (uu, f), k in killers.items() if uu == u and k}
        redundancy += [len(k) for k in faults.values()]
        left, chosen = set(faults), 0
        while left:
            counts = {}
            for f in left:
                for t in faults[f]:
                    counts[t] = counts.get(t, 0) + 1
            best = max(counts, key=counts.get)
            left = {f for f in left if best not in faults[f]}
            chosen += 1
        min_total += chosen
    # static smells
    files = [R("generated", cond, f) for f in sorted(os.listdir(R("generated", cond)))]
    asserts = no_assert = assert_bool = methods = 0
    for p in files:
        s = open(p).read()
        bodies = re.split(r"@Test\b", s)[1:]
        methods += len(bodies)
        for b in bodies:
            n = len(re.findall(r"\bassert\w+\s*\(", b)) + len(re.findall(r"expected\s*=", b.split("{")[0]))
            asserts += n
            no_assert += (n == 0)
            assert_bool += len(re.findall(r"\bassert(True|False)\s*\(\s*\w+\.\w+\(.*==", b))
    lines = sum(len(open(p).read().splitlines()) for p in files)
    out[cond] = {
        "test_methods": methods, "test_lines": lines, "asserts": asserts,
        "methods_without_assert": no_assert,
        "nonequivalent_mutants": len(nonequiv),
        "nonequivalent_killed": sum(int(r["detected"]) for r in nonequiv),
        "mutation_score_nonequiv": round(sum(int(r["detected"]) for r in nonequiv) / len(nonequiv), 4),
        "mean_killing_tests_per_fault": round(sum(redundancy) / len(redundancy), 2),
        "greedy_min_tests_for_same_kills": min_total,
    }
out["equivalent_mutants"] = len(EQUIVALENT)
json.dump(out, open(R("results", "analysis.json"), "w"), indent=2)
print(json.dumps(out, indent=2))
