# Can AI Write the Tests First?

Specification-only JUnit test generation with a basic prompt versus a structured checklist prompt.
COMP 3520 Software Engineering, Thompson Rivers University (Assignment 4, research and implementation).

**Research question.** When an LLM writes JUnit tests from a written specification without seeing the
implementation (as in test-driven development), does a structured requirement-to-test checklist prompt
improve compilation, false alarms on correct code, and detection of faults, compared with a basic prompt?

## Results at a glance

| Measure | Basic | Checklist |
|---|---|---|
| Test classes compiling | 12/12 | 12/12 |
| Test methods | 151 | 208 |
| False-alarm test methods | 0 | 0 |
| Hand-seeded faults detected | 36/36 | 36/36 |
| Non-equivalent mutants detected | 345/345 | 344/345 |

The checklist did not improve effectiveness on this benchmark and produced 38% more tests.
See the research paper for the full discussion.

## Repository layout

| Path | Contents |
|---|---|
| `specs/SPECS.md` | The 12 specifications. The only description of behaviour given to the model. |
| `prompts/basic.txt`, `prompts/structured.txt` | The two prompt templates (the compared conditions). |
| `benchmark/src/bench/` | Correct reference implementations of the 12 units. |
| `benchmark/faults.json` | 36 hand-seeded faults (exact find/replace patches), written before tests were generated. |
| `benchmark/mutants.json` | 367 compilable mutants from `tools/mutate.py` (ROR, AOR, constant +/-1). |
| `generated/basic/`, `generated/structured/` | Unedited AI-generated JUnit 4 test classes. |
| `tools/evaluate.py` | Harness: compile, run on correct code, run on each faulty version. |
| `tools/mutate.py` | Systematic mutant generator. |
| `tools/analyze.py` | Equivalent-mutant classification, redundancy, greedy minimal suites, assertion counts. |
| `results/` | Raw and summary results (CSV and JSON). |
| `lib/` | JUnit 4.13.2 and Hamcrest 1.3 jars (EPL 1.0 and BSD licences) so the harness runs offline. |

## Requirements

- Java 17 or newer (`javac`, `java` on PATH). Tested with OpenJDK 21.
- Python 3.9+ (standard library only).
- Optional: Maven 3.8+ to build the units with `pom.xml` (the harness does not need Maven).

## Reproducing the results

```bash
# 1. Primary evaluation: 36 hand-seeded faults, both conditions
python3 tools/evaluate.py basic structured

# 2. (Optional) regenerate the mutant set; the committed mutants.json is what the paper used
python3 tools/mutate.py

# 3. Secondary evaluation: 367 mutants, both conditions (about 10 minutes on 2 cores)
FAULT_SET=mutants python3 tools/evaluate.py basic structured

# 4. Secondary analysis
python3 tools/analyze.py
```

On Windows PowerShell use `$env:FAULT_SET="mutants"; python tools/evaluate.py basic structured`.

Outputs: `results/raw_results.csv`, `results/unit_summary.csv`, `results/summary.json`
(and the same with a `mutants_` prefix), plus `results/analysis.json`.

### How detection is counted

A fault is detected only if a test method that **passed** on the correct implementation fails on the
faulty version, or the run exceeds 20 seconds (infinite loop). Tests that fail on correct code are false
alarms and are excluded from detection, so a broken test cannot "detect" every fault.

### How the tests were generated

Model: Claude Opus 5.5 (Anthropic), November 2026, one generation per unit per condition. Each condition
was run by a separate agent that received only the prompt template plus the specifications, had no file
read access, could only write its output files, and never saw `benchmark/`. Outputs were not edited.

## Hand-seeded faults

| Fault | Change and behaviour that reveals it |
|---|---|
| LeapYear-F1 | Missing divisible-by-400 rule (2000 treated as non-leap) |
| LeapYear-F2 | Missing divisible-by-100 rule (1900 treated as leap) |
| LeapYear-F3 | Off-by-one validation: year 0 accepted |
| Grade-F1 | Boundary: 90 graded B |
| Grade-F2 | Upper bound not validated: 101 accepted |
| Grade-F3 | Wrong threshold: 50-59 graded D |
| Palindrome-F1 | Case-sensitive comparison |
| Palindrome-F2 | Punctuation and spaces not ignored |
| Palindrome-F3 | Null returns false instead of throwing |
| Gcd-F1 | Euclid update order wrong: returns the second argument after one step (gcd(12,8) gives 8) |
| Gcd-F2 | Negative b not rejected |
| Gcd-F3 | gcd(0,0) returns 0 instead of throwing |
| ArrayMax-F1 | Initial max is 0: wrong for all-negative arrays |
| ArrayMax-F2 | Loop skips the last element |
| ArrayMax-F3 | Empty array throws ArrayIndexOutOfBoundsException, not IllegalArgumentException |
| BankAccount-F1 | Withdrawing exact full balance rejected |
| BankAccount-F2 | Zero deposit accepted |
| BankAccount-F3 | Insufficient funds throws wrong exception type |
| FizzBuzz-F1 | Check order wrong: 15 returns Fizz |
| FizzBuzz-F2 | Zero accepted (returns FizzBuzz) |
| FizzBuzz-F3 | Multiples of 5 return Fizz |
| Temperature-F1 | Integer division 9/5 == 1 |
| Temperature-F2 | Exactly absolute zero rejected |
| Temperature-F3 | Subtracts 32 instead of adding |
| Discount-F1 | Boundary: quantity 10 gets no bulk discount |
| Discount-F2 | Boundary: subtotal exactly 100 gets no discount |
| Discount-F3 | Quantity 0 accepted |
| BoundedStack-F1 | Full check off by one: ArrayIndexOutOfBoundsException instead of IllegalStateException |
| BoundedStack-F2 | pop does not remove the item |
| BoundedStack-F3 | Capacity 0 accepted |
| WordCount-F1 | Splits on single space only (multiple spaces, tabs miscounted) |
| WordCount-F2 | Empty or blank string returns 1 |
| WordCount-F3 | Leading/trailing whitespace not ignored |
| BinarySearch-F1 | Loop condition misses final candidate |
| BinarySearch-F2 | Upper bound off by one: out-of-bounds for key above max |
| BinarySearch-F3 | No progress: infinite loop for some missing keys |

## Equivalent mutants

22 of the 367 mutants were detected by neither condition and were classified by hand as equivalent
(no input allowed by the specification behaves differently). The reason for each is in `EQUIVALENT`
in `tools/analyze.py`. The single non-equivalent mutant missed by one condition is `Palindrome-M10`
(`!=` changed to `<`), missed by the checklist suite because all its negative examples had a smaller
left character than right character.

## AI use

AI tools were used throughout, as permitted by the assignment. See the "Disclosure of AI Use" section of
the paper.
