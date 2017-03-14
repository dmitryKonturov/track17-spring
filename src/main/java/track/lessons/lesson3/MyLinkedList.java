package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List {

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
    private int size;


    public MyLinkedList() {
        head = null;
        size = 0;
    }

    @Override
    void add(int item) {
        Node toAdd = new Node(null, head, item);
        if (head != null) {
            head.prev = toAdd;
        }
        ++size;
        head = toAdd;
    }

    private Node getNodeByIndex(int idxFromEnd) throws NoSuchElementException {
        if (idxFromEnd < 0 || idxFromEnd >= size) {
            throw new NoSuchElementException();
        }
        Node current = head;
        while (idxFromEnd-- > 0) {
            current = current.next;
        }
        return current;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        Node toDelete = getNodeByIndex(size - idx);
        if (head == toDelete) {
            head = head.next;
        }
        if (toDelete.next != null) {
            toDelete.next.prev = toDelete.prev;
        }
        if (toDelete.prev != null) {
            toDelete.prev.next = toDelete.next;
        }
        --size;
        return toDelete.val;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        Node result = getNodeByIndex(size - idx);
        return result.val;
    }

    @Override
    int size() {
        return size;
    }
}
