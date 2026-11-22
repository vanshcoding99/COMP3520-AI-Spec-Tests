package bench;

public class Temperature {
    public static final double ABSOLUTE_ZERO_C = -273.15;

    public static double celsiusToFahrenheit(double celsius) {
        if (celsius < ABSOLUTE_ZERO_C) {
            throw new IllegalArgumentException("below absolute zero");
        }
        return celsius * 9.0 / 5.0 + 32.0;
    }
}
