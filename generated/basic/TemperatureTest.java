package bench;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TemperatureTest {

    private static final double DELTA = 1e-9;

    @Test
    public void freezingPoint() {
        assertEquals(32.0, Temperature.celsiusToFahrenheit(0.0), DELTA);
    }

    @Test
    public void boilingPoint() {
        assertEquals(212.0, Temperature.celsiusToFahrenheit(100.0), DELTA);
    }

    @Test
    public void bodyTemperature() {
        assertEquals(99.5, Temperature.celsiusToFahrenheit(37.5), DELTA);
        assertEquals(98.6, Temperature.celsiusToFahrenheit(37.0), DELTA);
    }

    @Test
    public void minusFortyIsSameInBothScales() {
        assertEquals(-40.0, Temperature.celsiusToFahrenheit(-40.0), DELTA);
    }

    @Test
    public void negativeTemperature() {
        assertEquals(14.0, Temperature.celsiusToFahrenheit(-10.0), DELTA);
    }

    @Test
    public void fractionalTemperature() {
        assertEquals(77.9, Temperature.celsiusToFahrenheit(25.5), DELTA);
    }

    @Test
    public void absoluteZeroAllowed() {
        assertEquals(-459.67, Temperature.celsiusToFahrenheit(-273.15), 1e-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void belowAbsoluteZeroThrows() {
        Temperature.celsiusToFahrenheit(-273.16);
    }

    @Test(expected = IllegalArgumentException.class)
    public void justBelowAbsoluteZeroThrows() {
        Temperature.celsiusToFahrenheit(Math.nextDown(-273.15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void farBelowAbsoluteZeroThrows() {
        Temperature.celsiusToFahrenheit(-1000.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeInfinityThrows() {
        Temperature.celsiusToFahrenheit(Double.NEGATIVE_INFINITY);
    }

    @Test
    public void largeTemperature() {
        assertEquals(1832.0, Temperature.celsiusToFahrenheit(1000.0), DELTA);
    }
}
