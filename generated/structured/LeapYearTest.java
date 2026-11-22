package bench;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LeapYearTest {

    // Requirement: throws IllegalArgumentException if year < 1 (closest invalid value)
    @Test
    public void yearZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> LeapYear.isLeap(0));
    }

    // Requirement: throws IllegalArgumentException if year < 1 (negative)
    @Test
    public void negativeYearThrows() {
        assertThrows(IllegalArgumentException.class, () -> LeapYear.isLeap(-4));
    }

    // Requirement: throws IllegalArgumentException if year < 1 (extreme negative)
    @Test
    public void minIntYearThrows() {
        assertThrows(IllegalArgumentException.class, () -> LeapYear.isLeap(Integer.MIN_VALUE));
    }

    // Requirement: year 1 is valid (boundary) and not divisible by 4 -> false
    @Test
    public void yearOneIsNotLeap() {
        assertFalse(LeapYear.isLeap(1));
    }

    // Requirement: year 2 is valid (just above boundary), not divisible by 4 -> false
    @Test
    public void yearTwoIsNotLeap() {
        assertFalse(LeapYear.isLeap(2));
    }

    // Requirement: divisible by 4 (smallest valid) -> true
    @Test
    public void yearFourIsLeap() {
        assertTrue(LeapYear.isLeap(4));
    }

    // Requirement: divisible by 400 -> true
    @Test
    public void year2000IsLeap() {
        assertTrue(LeapYear.isLeap(2000));
    }

    // Requirement: divisible by 400 -> true (smallest such year)
    @Test
    public void year400IsLeap() {
        assertTrue(LeapYear.isLeap(400));
    }

    // Requirement: divisible by 100 but not 400 -> false
    @Test
    public void year1900IsNotLeap() {
        assertFalse(LeapYear.isLeap(1900));
    }

    // Requirement: divisible by 100 but not 400 -> false (smallest such year)
    @Test
    public void year100IsNotLeap() {
        assertFalse(LeapYear.isLeap(100));
    }

    // Requirement: divisible by 4 but not 100 -> true
    @Test
    public void year2024IsLeap() {
        assertTrue(LeapYear.isLeap(2024));
    }

    // Requirement: not divisible by 4 -> false
    @Test
    public void year2023IsNotLeap() {
        assertFalse(LeapYear.isLeap(2023));
    }

    // Requirement: neighbours of a 400-multiple are handled by the other rules
    @Test
    public void year2001IsNotLeap() {
        assertFalse(LeapYear.isLeap(2001));
    }

    // Requirement: divisible by 4 (just below a 400-multiple) -> true
    @Test
    public void year1996IsLeap() {
        assertTrue(LeapYear.isLeap(1996));
    }

    // Requirement: divisible by 100 but not 400 -> false (2100)
    @Test
    public void year2100IsNotLeap() {
        assertFalse(LeapYear.isLeap(2100));
    }
}
