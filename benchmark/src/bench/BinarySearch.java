package bench;

public class BinarySearch {
    public static int indexOf(int[] sorted, int key) {
        if (sorted == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        int lo = 0;
        int hi = sorted.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (sorted[mid] == key) {
                return mid;
            } else if (sorted[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }
}
