package bench;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LeapYearTest {

    @Test(expected = IllegalArgumentException.class)
    public void zeroThrows() {
        LeapYear.isLeap(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeThrows() {
        LeapYear.isLeap(-4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minIntThrows() {
        LeapYear.isLeap(Integer.MIN_VALUE);
    }

    @Test
    public void yearOneIsValidAndNotLeap() {
        assertFalse(LeapYear.isLeap(1));
    }

    @Test
    public void divisibleBy400IsLeap() {
        assertTrue(LeapYear.isLeap(400));
        assertTrue(LeapYear.isLeap(1600));
        assertTrue(LeapYear.isLeap(2000));
        assertTrue(LeapYear.isLeap(2400));
    }

    @Test
    public void divisibleBy100ButNot400IsNotLeap() {
        assertFalse(LeapYear.isLeap(100));
        assertFalse(LeapYear.isLeap(1700));
        assertFalse(LeapYear.isLeap(1800));
        assertFalse(LeapYear.isLeap(1900));
        assertFalse(LeapYear.isLeap(2100));
    }

    @Test
    public void divisibleBy4ButNot100IsLeap() {
        assertTrue(LeapYear.isLeap(4));
        assertTrue(LeapYear.isLeap(1996));
        assertTrue(LeapYear.isLeap(2004));
        assertTrue(LeapYear.isLeap(2024));
    }

    @Test
    public void notDivisibleBy4IsNotLeap() {
        assertFalse(LeapYear.isLeap(2));
        assertFalse(LeapYear.isLeap(3));
        assertFalse(LeapYear.isLeap(1999));
        assertFalse(LeapYear.isLeap(2023));
        assertFalse(LeapYear.isLeap(2026));
    }
}
