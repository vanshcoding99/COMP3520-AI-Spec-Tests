package bench;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GcdTest {

    @Test(expected = IllegalArgumentException.class)
    public void negativeAThrows() {
        Gcd.gcd(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeBThrows() {
        Gcd.gcd(5, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bothNegativeThrows() {
        Gcd.gcd(-4, -6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeAWithZeroBThrows() {
        Gcd.gcd(-3, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bothZeroThrows() {
        Gcd.gcd(0, 0);
    }

    @Test
    public void zeroSecondReturnsFirst() {
        assertEquals(7, Gcd.gcd(7, 0));
        assertEquals(1, Gcd.gcd(1, 0));
    }

    @Test
    public void zeroFirstReturnsSecond() {
        assertEquals(9, Gcd.gcd(0, 9));
        assertEquals(1, Gcd.gcd(0, 1));
    }

    @Test
    public void typicalCases() {
        assertEquals(6, Gcd.gcd(12, 18));
        assertEquals(6, Gcd.gcd(18, 12));
        assertEquals(4, Gcd.gcd(8, 12));
        assertEquals(21, Gcd.gcd(1071, 462));
    }

    @Test
    public void coprimeReturnsOne() {
        assertEquals(1, Gcd.gcd(13, 17));
        assertEquals(1, Gcd.gcd(8, 9));
    }

    @Test
    public void equalNumbers() {
        assertEquals(5, Gcd.gcd(5, 5));
    }

    @Test
    public void oneDividesOther() {
        assertEquals(5, Gcd.gcd(5, 25));
        assertEquals(5, Gcd.gcd(25, 5));
    }

    @Test
    public void withOne() {
        assertEquals(1, Gcd.gcd(1, 100));
    }

    @Test
    public void largeValues() {
        assertEquals(Integer.MAX_VALUE, Gcd.gcd(Integer.MAX_VALUE, 0));
        assertEquals(Integer.MAX_VALUE, Gcd.gcd(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(1, Gcd.gcd(Integer.MAX_VALUE, Integer.MAX_VALUE - 1));
    }
}
