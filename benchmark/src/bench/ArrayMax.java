package bench;

public class ArrayMax {
    public static int max(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("values must be non-empty");
        }
        int best = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > best) {
                best = values[i];
            }
        }
        return best;
    }
}
