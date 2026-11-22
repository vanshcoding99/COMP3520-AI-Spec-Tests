package bench;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FizzBuzzTest {

    @Test(expected = IllegalArgumentException.class)
    public void zeroThrows() {
        FizzBuzz.convert(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeThrows() {
        FizzBuzz.convert(-3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeFifteenThrows() {
        FizzBuzz.convert(-15);
    }

    @Test
    public void oneReturnsNumber() {
        assertEquals("1", FizzBuzz.convert(1));
    }

    @Test
    public void plainNumbers() {
        assertEquals("2", FizzBuzz.convert(2));
        assertEquals("7", FizzBuzz.convert(7));
        assertEquals("98", FizzBuzz.convert(98));
        assertEquals("101", FizzBuzz.convert(101));
    }

    @Test
    public void multiplesOfThree() {
        assertEquals("Fizz", FizzBuzz.convert(3));
        assertEquals("Fizz", FizzBuzz.convert(6));
        assertEquals("Fizz", FizzBuzz.convert(9));
        assertEquals("Fizz", FizzBuzz.convert(99));
    }

    @Test
    public void multiplesOfFive() {
        assertEquals("Buzz", FizzBuzz.convert(5));
        assertEquals("Buzz", FizzBuzz.convert(10));
        assertEquals("Buzz", FizzBuzz.convert(20));
        assertEquals("Buzz", FizzBuzz.convert(100));
    }

    @Test
    public void multiplesOfFifteen() {
        assertEquals("FizzBuzz", FizzBuzz.convert(15));
        assertEquals("FizzBuzz", FizzBuzz.convert(30));
        assertEquals("FizzBuzz", FizzBuzz.convert(45));
        assertEquals("FizzBuzz", FizzBuzz.convert(90));
    }

    @Test
    public void largeValue() {
        assertEquals(String.valueOf(Integer.MAX_VALUE), FizzBuzz.convert(Integer.MAX_VALUE));
    }
}
