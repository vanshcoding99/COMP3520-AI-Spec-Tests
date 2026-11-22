package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class WordCountTest {

    // Requirement: throws IllegalArgumentException if text is null
    @Test
    public void nullThrows() {
        assertThrows(IllegalArgumentException.class, () -> WordCount.countWords(null));
    }

    // Requirement: returns 0 for an empty string
    @Test
    public void emptyStringReturnsZero() {
        assertEquals(0, WordCount.countWords(""));
    }

    // Requirement: returns 0 for a whitespace-only string (spaces)
    @Test
    public void spacesOnlyReturnsZero() {
        assertEquals(0, WordCount.countWords("     "));
    }

    // Requirement: returns 0 for whitespace-only string (tabs and newlines)
    @Test
    public void mixedWhitespaceOnlyReturnsZero() {
        assertEquals(0, WordCount.countWords(" \t\n\t \n"));
    }

    // Requirement: single word
    @Test
    public void singleWord() {
        assertEquals(1, WordCount.countWords("hello"));
    }

    // Requirement: single character word
    @Test
    public void singleCharacterWord() {
        assertEquals(1, WordCount.countWords("a"));
    }

    // Requirement: words separated by single spaces
    @Test
    public void wordsSeparatedBySingleSpaces() {
        assertEquals(3, WordCount.countWords("the quick fox"));
    }

    // Requirement: words separated by multiple spaces
    @Test
    public void wordsSeparatedByMultipleSpaces() {
        assertEquals(3, WordCount.countWords("the   quick     fox"));
    }

    // Requirement: tabs separate words
    @Test
    public void tabsSeparateWords() {
        assertEquals(2, WordCount.countWords("one\ttwo"));
    }

    // Requirement: newlines separate words
    @Test
    public void newlinesSeparateWords() {
        assertEquals(2, WordCount.countWords("one\ntwo"));
    }

    // Requirement: mixed whitespace separators
    @Test
    public void mixedWhitespaceSeparators() {
        assertEquals(4, WordCount.countWords("a \t b\n\nc \n\t d"));
    }

    // Requirement: leading whitespace is ignored
    @Test
    public void leadingWhitespaceIgnored() {
        assertEquals(2, WordCount.countWords("   hello world"));
    }

    // Requirement: trailing whitespace is ignored
    @Test
    public void trailingWhitespaceIgnored() {
        assertEquals(2, WordCount.countWords("hello world   "));
    }

    // Requirement: leading and trailing whitespace ignored together
    @Test
    public void leadingAndTrailingWhitespaceIgnored() {
        assertEquals(1, WordCount.countWords("\n\t  word  \t\n"));
    }

    // Requirement: a word is a maximal run of non-whitespace (punctuation counts as part of word)
    @Test
    public void punctuationIsPartOfWord() {
        assertEquals(2, WordCount.countWords("hello, world!"));
    }

    // Requirement: non-whitespace run without letters still counts as a word
    @Test
    public void symbolsOnlyRunIsAWord() {
        assertEquals(3, WordCount.countWords("--- 123 ?!"));
    }
}
