package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BoundedStackTest {

    @Test(expected = IllegalArgumentException.class)
    public void zeroCapacityThrows() {
        new BoundedStack(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeCapacityThrows() {
        new BoundedStack(-1);
    }

    @Test
    public void newStackIsEmpty() {
        BoundedStack s = new BoundedStack(3);
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void pushIncreasesSize() {
        BoundedStack s = new BoundedStack(3);
        s.push(10);
        assertFalse(s.isEmpty());
        assertEquals(1, s.size());
        s.push(20);
        assertEquals(2, s.size());
    }

    @Test
    public void peekReturnsTopWithoutRemoving() {
        BoundedStack s = new BoundedStack(3);
        s.push(1);
        s.push(2);
        assertEquals(2, s.peek());
        assertEquals(2, s.peek());
        assertEquals(2, s.size());
    }

    @Test
    public void popReturnsTopAndRemoves() {
        BoundedStack s = new BoundedStack(3);
        s.push(1);
        s.push(2);
        assertEquals(2, s.pop());
        assertEquals(1, s.size());
        assertEquals(1, s.peek());
    }

    @Test
    public void lifoOrder() {
        BoundedStack s = new BoundedStack(5);
        s.push(1);
        s.push(2);
        s.push(3);
        assertEquals(3, s.pop());
        assertEquals(2, s.pop());
        assertEquals(1, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    public void negativeAndZeroValues() {
        BoundedStack s = new BoundedStack(3);
        s.push(-5);
        s.push(0);
        assertEquals(0, s.pop());
        assertEquals(-5, s.pop());
    }

    @Test(expected = IllegalStateException.class)
    public void popOnEmptyThrows() {
        new BoundedStack(2).pop();
    }

    @Test(expected = IllegalStateException.class)
    public void peekOnEmptyThrows() {
        new BoundedStack(2).peek();
    }

    @Test(expected = IllegalStateException.class)
    public void pushBeyondCapacityThrows() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.push(2);
        s.push(3);
    }

    @Test
    public void capacityOneWorks() {
        BoundedStack s = new BoundedStack(1);
        s.push(42);
        assertEquals(1, s.size());
        try {
            s.push(43);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // ok
        }
        assertEquals(1, s.size());
        assertEquals(42, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    public void failedPushLeavesStackUnchanged() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.push(2);
        try {
            s.push(3);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // ok
        }
        assertEquals(2, s.size());
        assertEquals(2, s.peek());
    }

    @Test
    public void canPushAgainAfterPopWhenFull() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.push(2);
        s.pop();
        s.push(3);
        assertEquals(2, s.size());
        assertEquals(3, s.pop());
        assertEquals(1, s.pop());
    }

    @Test
    public void popAfterEmptiedThrows() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.pop();
        assertTrue(s.isEmpty());
        try {
            s.pop();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // ok
        }
        try {
            s.peek();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // ok
        }
        assertEquals(0, s.size());
    }

    @Test
    public void fillToCapacity() {
        BoundedStack s = new BoundedStack(100);
        for (int i = 0; i < 100; i++) {
            s.push(i);
        }
        assertEquals(100, s.size());
        for (int i = 99; i >= 0; i--) {
            assertEquals(i, s.pop());
        }
        assertTrue(s.isEmpty());
    }
}
