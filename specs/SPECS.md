# Benchmark specifications

All classes are in package `bench`. Each section gives the public API and the required behaviour.
These specifications are the only information given to the test generator. Expected behaviour is
defined here, not by any implementation.

## 1. LeapYear
`public static boolean isLeap(int year)`
- Throws `IllegalArgumentException` if `year < 1`.
- Returns true if the year is divisible by 400.
- Otherwise returns false if the year is divisible by 100.
- Otherwise returns true if the year is divisible by 4, else false.

## 2. Grade
`public static char letter(int score)`
- Throws `IllegalArgumentException` if `score < 0` or `score > 100`.
- 90 to 100 returns 'A'; 80 to 89 returns 'B'; 70 to 79 returns 'C'; 60 to 69 returns 'D'; 0 to 59 returns 'F'.

## 3. Palindrome
`public static boolean isPalindrome(String s)`
- Throws `IllegalArgumentException` if `s` is null.
- Ignores every character that is not a letter or digit, and ignores letter case.
- Returns true if the remaining characters read the same forwards and backwards.
- An empty string, or a string with no letters or digits, returns true.

## 4. Gcd
`public static int gcd(int a, int b)`
- Throws `IllegalArgumentException` if `a < 0` or `b < 0`.
- Throws `IllegalArgumentException` if both `a` and `b` are 0.
- Otherwise returns the greatest common divisor of `a` and `b`. `gcd(x, 0)` and `gcd(0, x)` return `x`.

## 5. ArrayMax
`public static int max(int[] values)`
- Throws `IllegalArgumentException` if `values` is null or has length 0.
- Returns the largest element. Values may be negative. The array is not modified.

## 6. BankAccount
Instance class. `public BankAccount()` creates an account with balance 0.
- `public void deposit(long cents)`: throws `IllegalArgumentException` if `cents <= 0`; otherwise adds `cents` to the balance.
- `public void withdraw(long cents)`: throws `IllegalArgumentException` if `cents <= 0`; throws `IllegalStateException` if `cents` is greater than the current balance (balance unchanged); otherwise subtracts `cents`. Withdrawing exactly the full balance is allowed.
- `public long getBalance()`: returns the current balance in cents.

## 7. FizzBuzz
`public static String convert(int n)`
- Throws `IllegalArgumentException` if `n < 1`.
- Returns "FizzBuzz" if `n` is divisible by both 3 and 5; "Fizz" if divisible by 3 only; "Buzz" if divisible by 5 only; otherwise the decimal string of `n` (e.g. "7").

## 8. Temperature
`public static double celsiusToFahrenheit(double celsius)`
- Throws `IllegalArgumentException` if `celsius < -273.15`. Exactly -273.15 is allowed.
- Otherwise returns `celsius * 9 / 5 + 32` (as a double; e.g. 37.5 C returns 99.5 F).

## 9. Discount
`public static double finalPrice(double unitPrice, int quantity)`
- Throws `IllegalArgumentException` if `unitPrice < 0` or `quantity <= 0`.
- `subtotal = unitPrice * quantity`.
- If `quantity >= 10`, a 10% discount applies to the subtotal.
- Otherwise, if `subtotal >= 100.00`, a 5% discount applies.
- Otherwise no discount. Only one discount ever applies.
- The result is rounded to 2 decimal places (half up, via `Math.round(x * 100) / 100.0`).

## 10. BoundedStack
Instance class holding `int` values with a fixed capacity.
- `public BoundedStack(int capacity)`: throws `IllegalArgumentException` if `capacity <= 0`.
- `public void push(int value)`: throws `IllegalStateException` if the stack already holds `capacity` items.
- `public int pop()`: removes and returns the top item; throws `IllegalStateException` if empty.
- `public int peek()`: returns the top item without removing it; throws `IllegalStateException` if empty.
- `public int size()` and `public boolean isEmpty()`. Items come out in last-in, first-out order.

## 11. WordCount
`public static int countWords(String text)`
- Throws `IllegalArgumentException` if `text` is null.
- A word is a maximal run of non-whitespace characters. Words may be separated by any amount of whitespace (spaces, tabs, newlines). Leading and trailing whitespace is ignored.
- Returns 0 for an empty or whitespace-only string.

## 12. BinarySearch
`public static int indexOf(int[] sorted, int key)`
- Throws `IllegalArgumentException` if `sorted` is null.
- `sorted` is in ascending order with no duplicates (callers guarantee this).
- Returns the index of `key` if present, otherwise -1. An empty array returns -1.
