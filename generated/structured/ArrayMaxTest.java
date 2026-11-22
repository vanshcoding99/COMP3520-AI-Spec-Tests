package bench;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class ArrayMaxTest {

    // Requirement: throws IllegalArgumentException if values is null
    @Test
    public void nullArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> ArrayMax.max(null));
    }

    // Requirement: throws IllegalArgumentException if values has length 0
    @Test
    public void emptyArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> ArrayMax.max(new int[0]));
    }

    // Requirement: returns the largest element (single element, smallest valid length)
    @Test
    public void singleElementReturnsThatElement() {
        assertEquals(42, ArrayMax.max(new int[] {42}));
    }

    // Requirement: returns the largest element (typical)
    @Test
    public void returnsLargestOfSeveral() {
        assertEquals(9, ArrayMax.max(new int[] {3, 9, 1, 7}));
    }

    // Requirement: largest element at first position
    @Test
    public void largestAtStart() {
        assertEquals(10, ArrayMax.max(new int[] {10, 2, 3}));
    }

    // Requirement: largest element at last position
    @Test
    public void largestAtEnd() {
        assertEquals(10, ArrayMax.max(new int[] {1, 2, 10}));
    }

    // Requirement: values may be negative (all negative)
    @Test
    public void allNegativeReturnsLeastNegative() {
        assertEquals(-2, ArrayMax.max(new int[] {-5, -2, -9}));
    }

    // Requirement: values may be negative (mixed signs)
    @Test
    public void mixedSignsReturnsLargest() {
        assertEquals(3, ArrayMax.max(new int[] {-10, 3, 0, -1}));
    }

    // Requirement: zero as the largest element
    @Test
    public void zeroIsLargest() {
        assertEquals(0, ArrayMax.max(new int[] {-3, 0, -1}));
    }

    // Requirement: duplicate maximum values
    @Test
    public void duplicateMaximum() {
        assertEquals(5, ArrayMax.max(new int[] {5, 1, 5}));
    }

    // Requirement: extreme int values
    @Test
    public void extremeValues() {
        assertEquals(Integer.MAX_VALUE, ArrayMax.max(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, 0}));
    }

    // Requirement: single negative extreme element
    @Test
    public void singleMinValue() {
        assertEquals(Integer.MIN_VALUE, ArrayMax.max(new int[] {Integer.MIN_VALUE}));
    }

    // Requirement: the array is not modified
    @Test
    public void arrayIsNotModified() {
        int[] values = {4, -1, 8, 3, 8, 0};
        int[] copy = values.clone();
        ArrayMax.max(values);
        assertArrayEquals(copy, values);
    }
}
