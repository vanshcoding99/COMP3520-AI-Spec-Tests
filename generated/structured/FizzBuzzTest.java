package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class FizzBuzzTest {

    // Requirement: throws IllegalArgumentException if n < 1 (closest invalid)
    @Test
    public void zeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> FizzBuzz.convert(0));
    }

    // Requirement: throws IllegalArgumentException if n < 1 (negative)
    @Test
    public void negativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> FizzBuzz.convert(-15));
    }

    // Requirement: throws IllegalArgumentException for Integer.MIN_VALUE
    @Test
    public void minIntThrows() {
        assertThrows(IllegalArgumentException.class, () -> FizzBuzz.convert(Integer.MIN_VALUE));
    }

    // Requirement: n = 1 (boundary) returns decimal string
    @Test
    public void oneReturnsOne() {
        assertEquals("1", FizzBuzz.convert(1));
    }

    // Requirement: n = 2 returns decimal string
    @Test
    public void twoReturnsTwo() {
        assertEquals("2", FizzBuzz.convert(2));
    }

    // Requirement: otherwise returns decimal string, e.g. "7"
    @Test
    public void sevenReturnsSeven() {
        assertEquals("7", FizzBuzz.convert(7));
    }

    // Requirement: divisible by 3 only -> "Fizz"
    @Test
    public void threeReturnsFizz() {
        assertEquals("Fizz", FizzBuzz.convert(3));
    }

    // Requirement: divisible by 3 only -> "Fizz" (another multiple)
    @Test
    public void nineReturnsFizz() {
        assertEquals("Fizz", FizzBuzz.convert(9));
    }

    // Requirement: divisible by 5 only -> "Buzz"
    @Test
    public void fiveReturnsBuzz() {
        assertEquals("Buzz", FizzBuzz.convert(5));
    }

    // Requirement: divisible by 5 only -> "Buzz" (another multiple)
    @Test
    public void tenReturnsBuzz() {
        assertEquals("Buzz", FizzBuzz.convert(10));
    }

    // Requirement: divisible by both 3 and 5 -> "FizzBuzz"
    @Test
    public void fifteenReturnsFizzBuzz() {
        assertEquals("FizzBuzz", FizzBuzz.convert(15));
    }

    // Requirement: divisible by both 3 and 5 -> "FizzBuzz" (another multiple)
    @Test
    public void thirtyReturnsFizzBuzz() {
        assertEquals("FizzBuzz", FizzBuzz.convert(30));
    }

    // Requirement: neighbours of 15 follow the other rules
    @Test
    public void fourteenReturnsFourteen() {
        assertEquals("14", FizzBuzz.convert(14));
    }

    // Requirement: neighbours of 15 follow the other rules
    @Test
    public void sixteenReturnsSixteen() {
        assertEquals("16", FizzBuzz.convert(16));
    }

    // Requirement: multi-digit non-multiple returns decimal string
    @Test
    public void ninetySevenReturnsDecimalString() {
        assertEquals("97", FizzBuzz.convert(97));
    }

    // Requirement: large non-multiple returns decimal string (Integer.MAX_VALUE = 2147483647)
    @Test
    public void maxIntReturnsDecimalString() {
        assertEquals("2147483647", FizzBuzz.convert(Integer.MAX_VALUE));
    }
}
