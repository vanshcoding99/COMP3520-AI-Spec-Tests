package bench;

public class LeapYear {
    public static boolean isLeap(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("year must be >= 1");
        }
        if (year % 400 == 0) {
            return true;
        }
        if (year % 100 == 0) {
            return false;
        }
        return year % 4 == 0;
    }
}
