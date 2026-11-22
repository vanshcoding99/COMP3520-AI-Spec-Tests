package bench;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BinarySearchTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullThrows() {
        BinarySearch.indexOf(null, 5);
    }

    @Test
    public void emptyArrayReturnsMinusOne() {
        assertEquals(-1, BinarySearch.indexOf(new int[0], 5));
    }

    @Test
    public void singleElementFound() {
        assertEquals(0, BinarySearch.indexOf(new int[] {7}, 7));
    }

    @Test
    public void singleElementNotFound() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {7}, 3));
        assertEquals(-1, BinarySearch.indexOf(new int[] {7}, 9));
    }

    @Test
    public void findsEveryElement() {
        int[] a = {1, 3, 5, 7, 9, 11, 13};
        for (int i = 0; i < a.length; i++) {
            assertEquals(i, BinarySearch.indexOf(a, a[i]));
        }
    }

    @Test
    public void findsEveryElementEvenLength() {
        int[] a = {2, 4, 6, 8, 10, 12};
        for (int i = 0; i < a.length; i++) {
            assertEquals(i, BinarySearch.indexOf(a, a[i]));
        }
    }

    @Test
    public void findsFirstAndLast() {
        int[] a = {10, 20, 30, 40, 50};
        assertEquals(0, BinarySearch.indexOf(a, 10));
        assertEquals(4, BinarySearch.indexOf(a, 50));
    }

    @Test
    public void keyBelowRange() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {10, 20, 30}, 5));
    }

    @Test
    public void keyAboveRange() {
        assertEquals(-1, BinarySearch.indexOf(new int[] {10, 20, 30}, 35));
    }

    @Test
    public void keyInGap() {
        int[] a = {1, 3, 5, 7, 9};
        assertEquals(-1, BinarySearch.indexOf(a, 0));
        assertEquals(-1, BinarySearch.indexOf(a, 2));
        assertEquals(-1, BinarySearch.indexOf(a, 4));
        assertEquals(-1, BinarySearch.indexOf(a, 6));
        assertEquals(-1, BinarySearch.indexOf(a, 8));
        assertEquals(-1, BinarySearch.indexOf(a, 10));
    }

    @Test
    public void twoElements() {
        int[] a = {4, 8};
        assertEquals(0, BinarySearch.indexOf(a, 4));
        assertEquals(1, BinarySearch.indexOf(a, 8));
        assertEquals(-1, BinarySearch.indexOf(a, 6));
    }

    @Test
    public void negativeValues() {
        int[] a = {-10, -5, 0, 5, 10};
        assertEquals(0, BinarySearch.indexOf(a, -10));
        assertEquals(1, BinarySearch.indexOf(a, -5));
        assertEquals(2, BinarySearch.indexOf(a, 0));
        assertEquals(-1, BinarySearch.indexOf(a, -1));
    }

    @Test
    public void searchForMinusOneValue() {
        int[] a = {-3, -1, 2};
        assertEquals(1, BinarySearch.indexOf(a, -1));
    }

    @Test
    public void extremeValues() {
        int[] a = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertEquals(0, BinarySearch.indexOf(a, Integer.MIN_VALUE));
        assertEquals(2, BinarySearch.indexOf(a, Integer.MAX_VALUE));
        assertEquals(-1, BinarySearch.indexOf(a, 1));
    }

    @Test
    public void largeArray() {
        int[] a = new int[10001];
        for (int i = 0; i < a.length; i++) {
            a[i] = i * 2;
        }
        assertEquals(0, BinarySearch.indexOf(a, 0));
        assertEquals(5000, BinarySearch.indexOf(a, 10000));
        assertEquals(10000, BinarySearch.indexOf(a, 20000));
        assertEquals(-1, BinarySearch.indexOf(a, 9999));
        assertEquals(-1, BinarySearch.indexOf(a, 20002));
    }

    @Test
    public void arrayNotModified() {
        int[] a = {1, 2, 3, 4, 5};
        int[] copy = a.clone();
        BinarySearch.indexOf(a, 3);
        BinarySearch.indexOf(a, 6);
        assertArrayEquals(copy, a);
    }
}
