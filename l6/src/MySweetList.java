import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Клас MySweetList реалізує інтерфейс List для елементів,
 * що наслідуються від Sweet, використовуючи двозв'язний список.
 */
public class MySweetList<T extends Sweet> implements List<T> {

    private static class Node<E> {
        E value;
        Node<E> prev;
        Node<E> next;

        Node(E value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MySweetList() {}

    public MySweetList(T item) {
        add(item);
    }

    public MySweetList(Collection<? extends T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Колекція не може бути порожньою.");
        }
        addAll(collection);
    }

    // -------- Допоміжні методи --------

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Індекс " + index + " недоступний.");
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Індекс " + index + " недоступний.");
    }

    private Node<T> nodeAt(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++)
                current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--)
                current = current.prev;
        }
        return current;
    }

    private void linkLast(T element) {
        Node<T> newNode = new Node<>(element);
        if (tail == null)
            head = tail = newNode;
        else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> node) {
        if (node == head) {
            Node<T> newNode = new Node<>(element);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(element);
        Node<T> prev = node.prev;

        newNode.prev = prev;
        newNode.next = node;

        prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null)
            head = next;
        else
            prev.next = next;

        if (next == null)
            tail = prev;
        else
            next.prev = prev;

        size--;
        return node.value;
    }

    // -------- Реалізація List --------

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> current = head;
            Node<T> lastReturned = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null)
                    throw new NoSuchElementException();
                lastReturned = current;
                current = current.next;
                return lastReturned.value;
            }

            @Override
            public void remove() {
                if (lastReturned == null)
                    throw new IllegalStateException();
                unlink(lastReturned);
                lastReturned = null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<T> n = head;
        int i = 0;
        while (n != null) {
            arr[i++] = n.value;
            n = n.next;
        }
        return arr;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        if (t == null) throw new IllegalArgumentException("Елемент не може бути null.");
        linkLast(t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkIndexForAdd(index);
        if (index == size)
            linkLast(element);
        else
            linkBefore(element, nodeAt(index));
    }

    @Override
    public boolean remove(Object o) {
        Node<T> n = head;
        while (n != null) {
            if (o.equals(n.value)) {
                unlink(n);
                return true;
            }
            n = n.next;
        }
        return false;
    }

    @Override
    public T remove(int index) {
        return unlink(nodeAt(index));
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T e : c) add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndexForAdd(index);
        for (T e : c) add(index++, e);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c)
            changed |= remove(o);
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node<T> n = head;
        boolean changed = false;
        while (n != null) {
            Node<T> next = n.next;
            if (!c.contains(n.value)) {
                unlink(n);
                changed = true;
            }
            n = next;
        }
        return changed;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        return nodeAt(index).value;
    }

    @Override
    public T set(int index, T element) {
        Node<T> n = nodeAt(index);
        T old = n.value;
        n.value = element;
        return old;
    }

    @Override
    public int indexOf(Object o) {
        int idx = 0;
        Node<T> n = head;
        while (n != null) {
            if (o.equals(n.value)) return idx;
            idx++;
            n = n.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int idx = size - 1;
        Node<T> n = tail;
        while (n != null) {
            if (o.equals(n.value)) return idx;
            idx--;
            n = n.prev;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
