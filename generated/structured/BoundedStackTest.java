package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoundedStackTest {

    // Requirement: constructor throws IllegalArgumentException if capacity <= 0 (zero)
    @Test
    public void zeroCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new BoundedStack(0));
    }

    // Requirement: constructor throws IllegalArgumentException if capacity <= 0 (negative)
    @Test
    public void negativeCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new BoundedStack(-1));
    }

    // Requirement: capacity 1 is valid (boundary); new stack is empty
    @Test
    public void capacityOneCreatesEmptyStack() {
        BoundedStack s = new BoundedStack(1);
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    // Requirement: new stack is empty with size 0
    @Test
    public void newStackIsEmpty() {
        BoundedStack s = new BoundedStack(5);
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    // Requirement: push increases size and stack is no longer empty
    @Test
    public void pushIncreasesSize() {
        BoundedStack s = new BoundedStack(3);
        s.push(10);
        assertEquals(1, s.size());
        assertFalse(s.isEmpty());
    }

    // Requirement: peek returns top item without removing it
    @Test
    public void peekReturnsTopWithoutRemoving() {
        BoundedStack s = new BoundedStack(3);
        s.push(1);
        s.push(2);
        assertEquals(2, s.peek());
        assertEquals(2, s.size());
    }

    // Requirement: peek repeated returns same value
    @Test
    public void repeatedPeekReturnsSameValue() {
        BoundedStack s = new BoundedStack(3);
        s.push(7);
        assertEquals(7, s.peek());
        assertEquals(7, s.peek());
        assertEquals(1, s.size());
    }

    // Requirement: pop removes and returns the top item
    @Test
    public void popReturnsAndRemovesTop() {
        BoundedStack s = new BoundedStack(3);
        s.push(1);
        s.push(2);
        assertEquals(2, s.pop());
        assertEquals(1, s.size());
        assertEquals(1, s.peek());
    }

    // Requirement: items come out in LIFO order
    @Test
    public void itemsComeOutLifo() {
        BoundedStack s = new BoundedStack(3);
        s.push(1);
        s.push(2);
        s.push(3);
        assertEquals(3, s.pop());
        assertEquals(2, s.pop());
        assertEquals(1, s.pop());
        assertTrue(s.isEmpty());
    }

    // Requirement: popping the last item makes the stack empty
    @Test
    public void popLastItemMakesEmpty() {
        BoundedStack s = new BoundedStack(2);
        s.push(5);
        s.pop();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    // Requirement: pop throws IllegalStateException if empty
    @Test
    public void popOnEmptyThrows() {
        BoundedStack s = new BoundedStack(2);
        assertThrows(IllegalStateException.class, s::pop);
    }

    // Requirement: peek throws IllegalStateException if empty
    @Test
    public void peekOnEmptyThrows() {
        BoundedStack s = new BoundedStack(2);
        assertThrows(IllegalStateException.class, s::peek);
    }

    // Requirement: failed pop leaves stack empty
    @Test
    public void failedPopLeavesStateUnchanged() {
        BoundedStack s = new BoundedStack(2);
        assertThrows(IllegalStateException.class, s::pop);
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    // Requirement: pop after all items removed throws IllegalStateException
    @Test
    public void popAfterDrainingThrows() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.pop();
        assertThrows(IllegalStateException.class, s::pop);
    }

    // Requirement: push up to exactly capacity is allowed
    @Test
    public void pushToExactCapacityAllowed() {
        BoundedStack s = new BoundedStack(3);
        s.push(1);
        s.push(2);
        s.push(3);
        assertEquals(3, s.size());
        assertEquals(3, s.peek());
    }

    // Requirement: push throws IllegalStateException when stack already holds capacity items
    @Test
    public void pushBeyondCapacityThrows() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.push(2);
        assertThrows(IllegalStateException.class, () -> s.push(3));
    }

    // Requirement: failed push leaves size and top unchanged
    @Test
    public void failedPushLeavesStateUnchanged() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.push(2);
        assertThrows(IllegalStateException.class, () -> s.push(3));
        assertEquals(2, s.size());
        assertEquals(2, s.peek());
    }

    // Requirement: failed push leaves contents unchanged (LIFO order preserved)
    @Test
    public void failedPushPreservesContents() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.push(2);
        assertThrows(IllegalStateException.class, () -> s.push(3));
        assertEquals(2, s.pop());
        assertEquals(1, s.pop());
    }

    // Requirement: capacity 1 stack is full after one push
    @Test
    public void capacityOneFullAfterOnePush() {
        BoundedStack s = new BoundedStack(1);
        s.push(9);
        assertThrows(IllegalStateException.class, () -> s.push(10));
    }

    // Requirement: after popping from a full stack, push is allowed again
    @Test
    public void pushAllowedAfterPopFromFull() {
        BoundedStack s = new BoundedStack(2);
        s.push(1);
        s.push(2);
        s.pop();
        s.push(3);
        assertEquals(2, s.size());
        assertEquals(3, s.peek());
    }

    // Requirement: holds int values including negative and zero
    @Test
    public void holdsNegativeAndZeroValues() {
        BoundedStack s = new BoundedStack(2);
        s.push(0);
        s.push(-5);
        assertEquals(-5, s.pop());
        assertEquals(0, s.pop());
    }
}
