package bench;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArrayMaxTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullThrows() {
        ArrayMax.max(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyThrows() {
        ArrayMax.max(new int[0]);
    }

    @Test
    public void singleElement() {
        assertEquals(42, ArrayMax.max(new int[] {42}));
    }

    @Test
    public void maxAtStart() {
        assertEquals(9, ArrayMax.max(new int[] {9, 1, 2, 3}));
    }

    @Test
    public void maxInMiddle() {
        assertEquals(9, ArrayMax.max(new int[] {1, 9, 2, 3}));
    }

    @Test
    public void maxAtEnd() {
        assertEquals(9, ArrayMax.max(new int[] {1, 2, 3, 9}));
    }

    @Test
    public void allNegative() {
        assertEquals(-1, ArrayMax.max(new int[] {-5, -1, -10, -3}));
    }

    @Test
    public void mixedSigns() {
        assertEquals(0, ArrayMax.max(new int[] {-5, 0, -3}));
        assertEquals(4, ArrayMax.max(new int[] {-5, 4, -3}));
    }

    @Test
    public void duplicateMax() {
        assertEquals(7, ArrayMax.max(new int[] {7, 3, 7, 1}));
    }

    @Test
    public void allEqual() {
        assertEquals(2, ArrayMax.max(new int[] {2, 2, 2}));
    }

    @Test
    public void extremeValues() {
        assertEquals(Integer.MAX_VALUE, ArrayMax.max(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, 0}));
        assertEquals(Integer.MIN_VALUE, ArrayMax.max(new int[] {Integer.MIN_VALUE}));
        assertEquals(Integer.MIN_VALUE, ArrayMax.max(new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE}));
    }

    @Test
    public void arrayNotModified() {
        int[] values = {3, -1, 8, 2};
        int[] copy = values.clone();
        ArrayMax.max(values);
        assertArrayEquals(copy, values);
    }
}
