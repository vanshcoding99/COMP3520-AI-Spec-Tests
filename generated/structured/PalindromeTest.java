package bench;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PalindromeTest {

    // Requirement: throws IllegalArgumentException if s is null
    @Test
    public void nullThrows() {
        assertThrows(IllegalArgumentException.class, () -> Palindrome.isPalindrome(null));
    }

    // Requirement: empty string returns true
    @Test
    public void emptyStringIsPalindrome() {
        assertTrue(Palindrome.isPalindrome(""));
    }

    // Requirement: string with no letters or digits returns true (whitespace only)
    @Test
    public void whitespaceOnlyIsPalindrome() {
        assertTrue(Palindrome.isPalindrome("   \t\n "));
    }

    // Requirement: string with no letters or digits returns true (punctuation only)
    @Test
    public void punctuationOnlyIsPalindrome() {
        assertTrue(Palindrome.isPalindrome("!?.,;-"));
    }

    // Requirement: single letter reads the same both ways
    @Test
    public void singleLetterIsPalindrome() {
        assertTrue(Palindrome.isPalindrome("a"));
    }

    // Requirement: single digit reads the same both ways
    @Test
    public void singleDigitIsPalindrome() {
        assertTrue(Palindrome.isPalindrome("7"));
    }

    // Requirement: returns true for a simple palindrome (odd length)
    @Test
    public void simpleOddLengthPalindrome() {
        assertTrue(Palindrome.isPalindrome("racecar"));
    }

    // Requirement: returns true for a simple palindrome (even length)
    @Test
    public void simpleEvenLengthPalindrome() {
        assertTrue(Palindrome.isPalindrome("abba"));
    }

    // Requirement: returns false when characters do not read the same
    @Test
    public void nonPalindromeReturnsFalse() {
        assertFalse(Palindrome.isPalindrome("hello"));
    }

    // Requirement: two different letters is not a palindrome
    @Test
    public void twoDifferentLettersIsNotPalindrome() {
        assertFalse(Palindrome.isPalindrome("ab"));
    }

    // Requirement: ignores letter case
    @Test
    public void ignoresCase() {
        assertTrue(Palindrome.isPalindrome("RaceCar"));
    }

    // Requirement: ignores non-alphanumeric characters and case
    @Test
    public void ignoresPunctuationSpacesAndCase() {
        assertTrue(Palindrome.isPalindrome("A man, a plan, a canal: Panama"));
    }

    // Requirement: digits are kept and compared
    @Test
    public void digitPalindrome() {
        assertTrue(Palindrome.isPalindrome("12321"));
    }

    // Requirement: digits are kept and compared (non-palindrome)
    @Test
    public void digitNonPalindrome() {
        assertFalse(Palindrome.isPalindrome("123"));
    }

    // Requirement: mixed letters and digits, ignoring separators
    @Test
    public void mixedLettersAndDigitsPalindrome() {
        assertTrue(Palindrome.isPalindrome("1a-2-A1"));
    }

    // Requirement: non-alphanumeric characters are ignored, so result depends only on letters
    @Test
    public void ignoredCharactersDoNotMakePalindrome() {
        assertFalse(Palindrome.isPalindrome("a!b"));
    }

    // Requirement: a single letter surrounded by ignored characters returns true
    @Test
    public void singleLetterWithPunctuationIsPalindrome() {
        assertTrue(Palindrome.isPalindrome("  ?x!  "));
    }
}
