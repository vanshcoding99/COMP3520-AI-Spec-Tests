package bench;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PalindromeTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullThrows() {
        Palindrome.isPalindrome(null);
    }

    @Test
    public void emptyStringIsPalindrome() {
        assertTrue(Palindrome.isPalindrome(""));
    }

    @Test
    public void noLettersOrDigitsIsPalindrome() {
        assertTrue(Palindrome.isPalindrome("   "));
        assertTrue(Palindrome.isPalindrome("!?.,-"));
    }

    @Test
    public void singleCharacterIsPalindrome() {
        assertTrue(Palindrome.isPalindrome("a"));
        assertTrue(Palindrome.isPalindrome("7"));
    }

    @Test
    public void simplePalindromes() {
        assertTrue(Palindrome.isPalindrome("racecar"));
        assertTrue(Palindrome.isPalindrome("abba"));
    }

    @Test
    public void simpleNonPalindromes() {
        assertFalse(Palindrome.isPalindrome("ab"));
        assertFalse(Palindrome.isPalindrome("hello"));
        assertFalse(Palindrome.isPalindrome("abca"));
    }

    @Test
    public void ignoresCase() {
        assertTrue(Palindrome.isPalindrome("RaceCar"));
        assertTrue(Palindrome.isPalindrome("Aa"));
    }

    @Test
    public void ignoresPunctuationAndSpaces() {
        assertTrue(Palindrome.isPalindrome("A man, a plan, a canal: Panama"));
        assertTrue(Palindrome.isPalindrome("No 'x' in Nixon"));
        assertTrue(Palindrome.isPalindrome("Was it a car or a cat I saw?"));
    }

    @Test
    public void digitsAreConsidered() {
        assertTrue(Palindrome.isPalindrome("12321"));
        assertTrue(Palindrome.isPalindrome("1a2-2A1"));
        assertFalse(Palindrome.isPalindrome("123"));
        assertFalse(Palindrome.isPalindrome("a1b2"));
    }

    @Test
    public void punctuationDoesNotMakeNonPalindromeTrue() {
        assertFalse(Palindrome.isPalindrome("race a car"));
    }

    @Test
    public void nonAlphanumericOnlyAroundSingleChar() {
        assertTrue(Palindrome.isPalindrome("!!a??"));
    }
}
