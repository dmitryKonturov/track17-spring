package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public class QueueTest {

    @Test
    public void testEnqueue() {
        Queue queue = new MyLinkedList();
        Assert.assertTrue(queue.isEmpty());
        queue.enqueue(10);
        Assert.assertFalse(queue.isEmpty());
    }

    @Test
    public void testDequeue() {
        Queue queue = new MyLinkedList();
        Assert.assertTrue(queue.isEmpty());
        queue.enqueue(10);
        Assert.assertFalse(queue.isEmpty());
        int value = queue.dequeue();
        Assert.assertTrue(queue.isEmpty());
        Assert.assertTrue(value == 10);
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueOnEmpty() {
        Queue queue = new MyLinkedList();
        queue.dequeue();
    }

    @Test
    public void testQueueProperty() {
        Queue queue = new MyLinkedList();
        for (int i = 0; i < 100; ++i) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 100; ++i) {
            int value = queue.dequeue();
            Assert.assertTrue(value == i);
        }
        Assert.assertTrue(queue.isEmpty());
    }

}
