package bench;

public class WordCount {
    public static int countWords(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        return trimmed.split("\\s+").length;
    }
}
