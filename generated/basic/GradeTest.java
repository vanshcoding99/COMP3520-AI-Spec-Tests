package bench;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GradeTest {

    @Test(expected = IllegalArgumentException.class)
    public void negativeScoreThrows() {
        Grade.letter(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scoreAbove100Throws() {
        Grade.letter(101);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minIntThrows() {
        Grade.letter(Integer.MIN_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxIntThrows() {
        Grade.letter(Integer.MAX_VALUE);
    }

    @Test
    public void aRange() {
        assertEquals('A', Grade.letter(100));
        assertEquals('A', Grade.letter(95));
        assertEquals('A', Grade.letter(90));
    }

    @Test
    public void bRange() {
        assertEquals('B', Grade.letter(89));
        assertEquals('B', Grade.letter(85));
        assertEquals('B', Grade.letter(80));
    }

    @Test
    public void cRange() {
        assertEquals('C', Grade.letter(79));
        assertEquals('C', Grade.letter(75));
        assertEquals('C', Grade.letter(70));
    }

    @Test
    public void dRange() {
        assertEquals('D', Grade.letter(69));
        assertEquals('D', Grade.letter(65));
        assertEquals('D', Grade.letter(60));
    }

    @Test
    public void fRange() {
        assertEquals('F', Grade.letter(59));
        assertEquals('F', Grade.letter(30));
        assertEquals('F', Grade.letter(1));
        assertEquals('F', Grade.letter(0));
    }
}
