package bench;

public class BoundedStack {
    private final int[] items;
    private int size;

    public BoundedStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        items = new int[capacity];
        size = 0;
    }

    public void push(int value) {
        if (size >= items.length) {
            throw new IllegalStateException("stack is full");
        }
        items[size] = value;
        size++;
    }

    public int pop() {
        if (size == 0) {
            throw new IllegalStateException("stack is empty");
        }
        size--;
        return items[size];
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("stack is empty");
        }
        return items[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
