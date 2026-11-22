package bench;

public class Palindrome {
    public static boolean isPalindrome(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s must not be null");
        }
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            char a = s.charAt(i);
            char b = s.charAt(j);
            if (!Character.isLetterOrDigit(a)) { i++; continue; }
            if (!Character.isLetterOrDigit(b)) { j--; continue; }
            if (Character.toLowerCase(a) != Character.toLowerCase(b)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
