package bench;

public class FizzBuzz {
    public static String convert(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be >= 1");
        }
        if (n % 15 == 0) return "FizzBuzz";
        if (n % 3 == 0) return "Fizz";
        if (n % 5 == 0) return "Buzz";
        return String.valueOf(n);
    }
}
