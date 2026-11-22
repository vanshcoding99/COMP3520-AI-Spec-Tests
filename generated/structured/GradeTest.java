package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class GradeTest {

    // Requirement: throws IllegalArgumentException if score < 0 (closest invalid)
    @Test
    public void scoreMinusOneThrows() {
        assertThrows(IllegalArgumentException.class, () -> Grade.letter(-1));
    }

    // Requirement: throws IllegalArgumentException if score > 100 (closest invalid)
    @Test
    public void score101Throws() {
        assertThrows(IllegalArgumentException.class, () -> Grade.letter(101));
    }

    // Requirement: throws IllegalArgumentException for very negative score
    @Test
    public void minIntScoreThrows() {
        assertThrows(IllegalArgumentException.class, () -> Grade.letter(Integer.MIN_VALUE));
    }

    // Requirement: throws IllegalArgumentException for very large score
    @Test
    public void maxIntScoreThrows() {
        assertThrows(IllegalArgumentException.class, () -> Grade.letter(Integer.MAX_VALUE));
    }

    // Requirement: 90 to 100 -> 'A' (upper boundary)
    @Test
    public void score100IsA() {
        assertEquals('A', Grade.letter(100));
    }

    // Requirement: 90 to 100 -> 'A' (just below upper boundary)
    @Test
    public void score99IsA() {
        assertEquals('A', Grade.letter(99));
    }

    // Requirement: 90 to 100 -> 'A' (typical)
    @Test
    public void score95IsA() {
        assertEquals('A', Grade.letter(95));
    }

    // Requirement: 90 to 100 -> 'A' (just above lower boundary)
    @Test
    public void score91IsA() {
        assertEquals('A', Grade.letter(91));
    }

    // Requirement: 90 to 100 -> 'A' (lower boundary)
    @Test
    public void score90IsA() {
        assertEquals('A', Grade.letter(90));
    }

    // Requirement: 80 to 89 -> 'B' (upper boundary)
    @Test
    public void score89IsB() {
        assertEquals('B', Grade.letter(89));
    }

    // Requirement: 80 to 89 -> 'B' (typical)
    @Test
    public void score85IsB() {
        assertEquals('B', Grade.letter(85));
    }

    // Requirement: 80 to 89 -> 'B' (just above lower boundary)
    @Test
    public void score81IsB() {
        assertEquals('B', Grade.letter(81));
    }

    // Requirement: 80 to 89 -> 'B' (lower boundary)
    @Test
    public void score80IsB() {
        assertEquals('B', Grade.letter(80));
    }

    // Requirement: 70 to 79 -> 'C' (upper boundary)
    @Test
    public void score79IsC() {
        assertEquals('C', Grade.letter(79));
    }

    // Requirement: 70 to 79 -> 'C' (typical)
    @Test
    public void score75IsC() {
        assertEquals('C', Grade.letter(75));
    }

    // Requirement: 70 to 79 -> 'C' (just above lower boundary)
    @Test
    public void score71IsC() {
        assertEquals('C', Grade.letter(71));
    }

    // Requirement: 70 to 79 -> 'C' (lower boundary)
    @Test
    public void score70IsC() {
        assertEquals('C', Grade.letter(70));
    }

    // Requirement: 60 to 69 -> 'D' (upper boundary)
    @Test
    public void score69IsD() {
        assertEquals('D', Grade.letter(69));
    }

    // Requirement: 60 to 69 -> 'D' (typical)
    @Test
    public void score65IsD() {
        assertEquals('D', Grade.letter(65));
    }

    // Requirement: 60 to 69 -> 'D' (just above lower boundary)
    @Test
    public void score61IsD() {
        assertEquals('D', Grade.letter(61));
    }

    // Requirement: 60 to 69 -> 'D' (lower boundary)
    @Test
    public void score60IsD() {
        assertEquals('D', Grade.letter(60));
    }

    // Requirement: 0 to 59 -> 'F' (upper boundary)
    @Test
    public void score59IsF() {
        assertEquals('F', Grade.letter(59));
    }

    // Requirement: 0 to 59 -> 'F' (typical)
    @Test
    public void score30IsF() {
        assertEquals('F', Grade.letter(30));
    }

    // Requirement: 0 to 59 -> 'F' (just above lower boundary)
    @Test
    public void score1IsF() {
        assertEquals('F', Grade.letter(1));
    }

    // Requirement: 0 to 59 -> 'F' (lower boundary, zero)
    @Test
    public void score0IsF() {
        assertEquals('F', Grade.letter(0));
    }
}
