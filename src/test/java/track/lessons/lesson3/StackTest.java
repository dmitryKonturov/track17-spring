package track.lessons.lesson3;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class StackTest {

    @Test(expected = NoSuchElementException.class)
    public void popOnEmptyStack() {
        Stack stack = new MyLinkedList();
        stack.pop();
    }

    @Test(expected = NoSuchElementException.class)
    public void popOnEmptyStack2() {
        Stack stack = new MyLinkedList();
        for (int i = 0; i < 100; ++i) {
            stack.push(i);
        }
        for (int i = 0; i < 100; ++i) {
            stack.pop();
        }
        stack.pop();
    }

    @Test
    public void checkStackProperty() {
        Stack stack = new MyLinkedList();
        for (int i = 0; i <= 100; ++i) {
            stack.push(2 * i);
        }
        for (int i = 100; i >= 0; --i) {
            int value = stack.pop();
            Assert.assertTrue(value == 2 * i);
        }
    }

    @Test
    public void checkStackProperty2() {
        Stack stack = new MyLinkedList();
        for (int i = 0; i <= 100; ++i) {
            stack.push(2 * i);
        }
        Assert.assertFalse(stack.isEmpty());
        for (int i = 100; i >= 50; --i) {
            int value = stack.pop();
            Assert.assertTrue(value == 2 * i);
        }
        Assert.assertFalse(stack.isEmpty());
        for (int i = 0; i <= 100; ++i) {
            stack.push(2 * i);
        }
        Assert.assertFalse(stack.isEmpty());
        for (int i = 100; i >= 0; --i) {
            int value = stack.pop();
            Assert.assertTrue(value == 2 * i);
        }
        Assert.assertFalse(stack.isEmpty());
        for (int i = 49; i >= 0; --i) {
            int value = stack.pop();
            Assert.assertTrue(value == 2 * i);
        }
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void checkEmpty() {
        Stack stack = new MyLinkedList();
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void checkPush() {
        Stack stack = new MyLinkedList();
        Assert.assertTrue(stack.isEmpty());
        stack.push(50);
        Assert.assertFalse(stack.isEmpty());
    }

}
