package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class GcdTest {

    // Requirement: throws IllegalArgumentException if a < 0 (closest invalid)
    @Test
    public void negativeAThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gcd.gcd(-1, 5));
    }

    // Requirement: throws IllegalArgumentException if b < 0 (closest invalid)
    @Test
    public void negativeBThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gcd.gcd(5, -1));
    }

    // Requirement: throws IllegalArgumentException if both negative
    @Test
    public void bothNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gcd.gcd(-4, -6));
    }

    // Requirement: throws IllegalArgumentException if a < 0 even when b is 0
    @Test
    public void negativeAWithZeroBThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gcd.gcd(-1, 0));
    }

    // Requirement: throws IllegalArgumentException if b < 0 even when a is 0
    @Test
    public void zeroAWithNegativeBThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gcd.gcd(0, -1));
    }

    // Requirement: throws IllegalArgumentException for Integer.MIN_VALUE
    @Test
    public void minIntThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gcd.gcd(Integer.MIN_VALUE, 2));
    }

    // Requirement: throws IllegalArgumentException if both a and b are 0
    @Test
    public void bothZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> Gcd.gcd(0, 0));
    }

    // Requirement: returns greatest common divisor (typical)
    @Test
    public void gcdOf12And18Is6() {
        assertEquals(6, Gcd.gcd(12, 18));
    }

    // Requirement: argument order does not matter for gcd
    @Test
    public void gcdOf18And12Is6() {
        assertEquals(6, Gcd.gcd(18, 12));
    }

    // Requirement: coprime numbers have gcd 1
    @Test
    public void coprimeNumbersGcdIsOne() {
        assertEquals(1, Gcd.gcd(17, 5));
    }

    // Requirement: equal numbers have gcd equal to themselves
    @Test
    public void equalNumbersGcdIsSelf() {
        assertEquals(7, Gcd.gcd(7, 7));
    }

    // Requirement: one divides the other
    @Test
    public void divisorIsGcd() {
        assertEquals(5, Gcd.gcd(5, 25));
    }

    // Requirement: gcd(x, 0) returns x
    @Test
    public void gcdWithZeroSecondReturnsFirst() {
        assertEquals(9, Gcd.gcd(9, 0));
    }

    // Requirement: gcd(0, x) returns x
    @Test
    public void gcdWithZeroFirstReturnsSecond() {
        assertEquals(9, Gcd.gcd(0, 9));
    }

    // Requirement: gcd(1, 0) returns 1 (smallest valid non-zero next to the both-zero case)
    @Test
    public void gcdOneAndZeroIsOne() {
        assertEquals(1, Gcd.gcd(1, 0));
    }

    // Requirement: gcd(0, 1) returns 1
    @Test
    public void gcdZeroAndOneIsOne() {
        assertEquals(1, Gcd.gcd(0, 1));
    }

    // Requirement: gcd with 1 is 1
    @Test
    public void gcdWithOneIsOne() {
        assertEquals(1, Gcd.gcd(1, 100));
    }

    // Requirement: gcd(x, 0) returns x for large x
    @Test
    public void gcdMaxIntAndZero() {
        assertEquals(Integer.MAX_VALUE, Gcd.gcd(Integer.MAX_VALUE, 0));
    }
}
