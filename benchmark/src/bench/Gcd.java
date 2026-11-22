package bench;

public class Gcd {
    public static int gcd(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("arguments must be non-negative");
        }
        if (a == 0 && b == 0) {
            throw new IllegalArgumentException("gcd(0, 0) is undefined");
        }
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
