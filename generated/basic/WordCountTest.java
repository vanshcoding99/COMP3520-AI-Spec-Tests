package bench;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WordCountTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullThrows() {
        WordCount.countWords(null);
    }

    @Test
    public void emptyStringIsZero() {
        assertEquals(0, WordCount.countWords(""));
    }

    @Test
    public void whitespaceOnlyIsZero() {
        assertEquals(0, WordCount.countWords(" "));
        assertEquals(0, WordCount.countWords("   "));
        assertEquals(0, WordCount.countWords("\t\n \r\n"));
    }

    @Test
    public void singleWord() {
        assertEquals(1, WordCount.countWords("hello"));
        assertEquals(1, WordCount.countWords("a"));
    }

    @Test
    public void multipleWordsSingleSpaces() {
        assertEquals(3, WordCount.countWords("the quick fox"));
    }

    @Test
    public void multipleSpacesBetweenWords() {
        assertEquals(2, WordCount.countWords("hello     world"));
    }

    @Test
    public void leadingAndTrailingWhitespaceIgnored() {
        assertEquals(2, WordCount.countWords("   hello world   "));
        assertEquals(1, WordCount.countWords("\n\tword\t\n"));
    }

    @Test
    public void tabsAndNewlinesSeparateWords() {
        assertEquals(3, WordCount.countWords("one\ttwo\nthree"));
        assertEquals(4, WordCount.countWords("a \t\n b\r\nc  \t d"));
    }

    @Test
    public void punctuationIsPartOfWord() {
        assertEquals(3, WordCount.countWords("hello, world! ok?"));
        assertEquals(1, WordCount.countWords("don't-stop"));
    }

    @Test
    public void nonLetterTokensCount() {
        assertEquals(3, WordCount.countWords("123 !@# x"));
    }

    @Test
    public void multiLineText() {
        assertEquals(6, WordCount.countWords("line one\nline two\n\nline three\n"));
    }
}
