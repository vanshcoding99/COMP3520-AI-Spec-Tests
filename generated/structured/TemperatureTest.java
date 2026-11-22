package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TemperatureTest {

    private static final double DELTA = 1e-9;

    // Requirement: throws IllegalArgumentException if celsius < -273.15 (just below)
    @Test
    public void justBelowAbsoluteZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> Temperature.celsiusToFahrenheit(-273.16));
    }

    // Requirement: throws IllegalArgumentException for far below absolute zero
    @Test
    public void farBelowAbsoluteZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> Temperature.celsiusToFahrenheit(-1000.0));
    }

    // Requirement: exactly -273.15 is allowed and converts to -459.67
    @Test
    public void absoluteZeroAllowed() {
        assertEquals(-459.67, Temperature.celsiusToFahrenheit(-273.15), DELTA);
    }

    // Requirement: just above -273.15 converts by formula
    @Test
    public void justAboveAbsoluteZero() {
        assertEquals(-459.652, Temperature.celsiusToFahrenheit(-273.14), DELTA);
    }

    // Requirement: returns celsius * 9 / 5 + 32 (example 37.5 -> 99.5)
    @Test
    public void bodyTemperatureExample() {
        assertEquals(99.5, Temperature.celsiusToFahrenheit(37.5), DELTA);
    }

    // Requirement: zero celsius -> 32 F
    @Test
    public void zeroCelsius() {
        assertEquals(32.0, Temperature.celsiusToFahrenheit(0.0), DELTA);
    }

    // Requirement: 100 C -> 212 F
    @Test
    public void boilingPoint() {
        assertEquals(212.0, Temperature.celsiusToFahrenheit(100.0), DELTA);
    }

    // Requirement: negative valid celsius, -40 C -> -40 F
    @Test
    public void minusForty() {
        assertEquals(-40.0, Temperature.celsiusToFahrenheit(-40.0), DELTA);
    }

    // Requirement: result is a double (fractional output)
    @Test
    public void fractionalResult() {
        assertEquals(33.8, Temperature.celsiusToFahrenheit(1.0), DELTA);
    }

    // Requirement: negative zero is valid and converts to 32
    @Test
    public void negativeZero() {
        assertEquals(32.0, Temperature.celsiusToFahrenheit(-0.0), DELTA);
    }
}
