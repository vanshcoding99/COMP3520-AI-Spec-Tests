package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class BinarySearchTest {

    private static final int[] DATA = {-7, -2, 0, 3, 8, 15, 42};

    // Requirement: throws IllegalArgumentException if sorted is null
    @Test
    public void nullArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> BinarySearch.indexOf(null, 1));
    }

    // Requirement: empty array returns -1
    @Test
    public void emptyArrayReturnsMinusOne() {
        assertEquals(-1, BinarySearch.indexOf(new int[0], 5));
    }

    // Requirement: single element present returns index 0
    @Test
    public void singleElementFound() {
        assertEquals(0, BinarySearch.indexOf(new int[] {5}, 5));
    }

    // Requirement: single element, key smaller, returns -1
    @Test
    public void singleElementKeySmallerNotFound() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {5}, 4));
    }

    // Requirement: single element, key larger, returns -1
    @Test
    public void singleElementKeyLargerNotFound() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {5}, 6));
    }

    // Requirement: returns index of key if present (middle)
    @Test
    public void keyInMiddleFound() {
        assertEquals(3, BinarySearch.indexOf(DATA, 3));
    }

    // Requirement: returns index of key if present (first element)
    @Test
    public void firstElementFound() {
        assertEquals(0, BinarySearch.indexOf(DATA, -7));
    }

    // Requirement: returns index of key if present (last element)
    @Test
    public void lastElementFound() {
        assertEquals(6, BinarySearch.indexOf(DATA, 42));
    }

    // Requirement: every element is found at its index
    @Test
    public void everyElementFoundAtItsIndex() {
        for (int i = 0; i < DATA.length; i++) {
            assertEquals(i, BinarySearch.indexOf(DATA, DATA[i]));
        }
    }

    // Requirement: key smaller than all elements returns -1
    @Test
    public void keyBelowRangeNotFound() {
        assertEquals(-1, BinarySearch.indexOf(DATA, -8));
    }

    // Requirement: key larger than all elements returns -1
    @Test
    public void keyAboveRangeNotFound() {
        assertEquals(-1, BinarySearch.indexOf(DATA, 43));
    }

    // Requirement: key between elements returns -1
    @Test
    public void keyBetweenElementsNotFound() {
        assertEquals(-1, BinarySearch.indexOf(DATA, 4));
    }

    // Requirement: negative key present is found
    @Test
    public void negativeKeyFound() {
        assertEquals(1, BinarySearch.indexOf(DATA, -2));
    }

    // Requirement: zero key present is found
    @Test
    public void zeroKeyFound() {
        assertEquals(2, BinarySearch.indexOf(DATA, 0));
    }

    // Requirement: two-element array, both positions found
    @Test
    public void twoElementArray() {
        int[] a = {1, 2};
        assertEquals(0, BinarySearch.indexOf(a, 1));
        assertEquals(1, BinarySearch.indexOf(a, 2));
    }

    // Requirement: extreme int values are found
    @Test
    public void extremeValuesFound() {
        int[] a = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertEquals(0, BinarySearch.indexOf(a, Integer.MIN_VALUE));
        assertEquals(2, BinarySearch.indexOf(a, Integer.MAX_VALUE));
    }

    // Requirement: even-length array, key absent returns -1
    @Test
    public void evenLengthArrayKeyAbsent() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {2, 4, 6, 8}, 5));
    }
}
