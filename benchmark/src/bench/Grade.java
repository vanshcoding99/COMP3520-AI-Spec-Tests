package bench;

public class Grade {
    public static char letter(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("score must be 0..100");
        }
        if (score >= 90) return 'A';
        if (score >= 80) return 'B';
        if (score >= 70) return 'C';
        if (score >= 60) return 'D';
        return 'F';
    }
}
