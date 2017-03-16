package track.lessons.lesson3;

import java.util.NoSuchElementException;

import static java.lang.System.arraycopy;


/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */

public class MyArrayList extends List {

    private int[] array;
    private int size;
    private int capacity = 8;

    public MyArrayList() {
        array = new int[capacity];
        size = 0;
    }

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        size = 0;
    }

    @Override
    public void add(int item) {
        if (size == capacity) {
            doReallocation();
        }
        array[size++] = item;
    }

    private void doReallocation() {
        int[] newArray = new int[2 * capacity];
        arraycopy(array, 0, newArray, 0, capacity);
        array = newArray;
        capacity *= 2;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        int toReturn = array[idx];
        arraycopy(array, idx + 1, array, idx, size - idx - 1);
        --size;
        return toReturn;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >= size) {
            throw new NoSuchElementException();
        }
        return array[idx];
    }

    @Override
    int size() {
        return size;
    }

}
