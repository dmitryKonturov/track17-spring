package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Stack, Queue {

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        return remove(0);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() { }

    private Node getNodeByIndex(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        Node current = head;
        while (idx-- > 0) {
            current = current.next;
        }
        return current;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        Node toReturn = getNodeByIndex(idx);
        return toReturn.val;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        Node toDelete = getNodeByIndex(idx);
        if (toDelete.next != null) {
            toDelete.next.prev = toDelete.prev;
        }
        if (toDelete.prev != null) {
            toDelete.prev.next = toDelete.next;
        }
        if (toDelete == head) {
            head = head.next;
        }
        if (toDelete == tail) {
            tail = tail.prev;
        }
        --size;
        return toDelete.val;
    }

    @Override
    void add(int item) {
        ++size;
        Node newNode = new Node(tail, null, item);
        if (tail != null) {
            tail.next = newNode;
        }
        if (head == null) {
            head = newNode;
        }
        tail = newNode;
    }

    @Override
    int size() {
        return size;
    }
}
