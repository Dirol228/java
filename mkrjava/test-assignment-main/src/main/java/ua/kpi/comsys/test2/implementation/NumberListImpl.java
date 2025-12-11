/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */

package ua.kpi.comsys.test2.implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import ua.kpi.comsys.test2.NumberList;

/**
 * Custom implementation of INumberList interface.
 * Has to be implemented by each student independently.
 *
 * Represents a number as a circular doubly linked list of digits.
 *
 * @author Андрій Шаповалов, group ІП-34, record book № 0014
 *
 */
public class NumberListImpl implements NumberList {

    // base for this student variant: main = 16 (hex), alternate = 2 (binary)
    private static final int MAIN_BASE = 16;
    private static final int ALT_BASE  = 2;

    /**
     * Node of circular doubly linked list.
     */
    private static class Node {
        byte value;
        Node next;
        Node prev;

        Node(byte value) {
            this.value = value;
        }
    }

    private Node head;     // first (most significant) digit
    private int size;      // number of digits
    private final int base; // current scale of notation (16 or 2)

    // ===================== CONSTRUCTORS =====================

    /**
     * Default constructor. Returns empty <tt>NumberListImpl</tt>
     */
    public NumberListImpl() {
        this.base = MAIN_BASE;
        this.head = null;
        this.size = 0;
    }

    /**
     * Internal constructor for given base.
     */
    private NumberListImpl(int base) {
        if (base != MAIN_BASE && base != ALT_BASE) {
            throw new IllegalArgumentException("Unsupported base: " + base);
        }
        this.base = base;
        this.head = null;
        this.size = 0;
    }

    /**
     * Internal constructor from BigInteger and base.
     */
    private NumberListImpl(BigInteger value, int base) {
        this(base);
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Negative numbers are not supported");
        }
        if (value.equals(BigInteger.ZERO)) {
            // represent zero as single 0 digit
            linkLast((byte) 0);
            return;
        }
        String s = value.toString(base);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = Character.digit(c, base);
            if (d < 0) {
                throw new IllegalArgumentException("Invalid digit '" + c + "' for base " + base);
            }
            linkLast((byte) d);
        }
    }

    /**
     * Constructs new <tt>NumberListImpl</tt> by <b>decimal</b> number
     * from file, defined in string format.
     *
     * @param file - file where number is stored.
     */
    public NumberListImpl(File file) {
        this.base = MAIN_BASE;
        this.head = null;
        this.size = 0;

        if (file == null) {
            throw new NullPointerException("file is null");
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read number from file", e);
        }

        String value = sb.toString().trim();
        if (value.isEmpty()) {
            // treat empty file as zero
            linkLast((byte) 0);
        } else {
            initFromDecimalString(value);
        }
    }

    /**
     * Constructs new <tt>NumberListImpl</tt> by <b>decimal</b> number
     * in string notation.
     *
     * @param value - number in string notation.
     */
    public NumberListImpl(String value) {
        this.base = MAIN_BASE;
        this.head = null;
        this.size = 0;
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        initFromDecimalString(value.trim());
    }

    private void initFromDecimalString(String value) {
        if (value.isEmpty()) {
            linkLast((byte) 0);
            return;
        }
        BigInteger bi = new BigInteger(value); // decimal string
        if (bi.signum() < 0) {
            throw new IllegalArgumentException("Negative numbers are not supported");
        }
        NumberListImpl tmp = new NumberListImpl(bi, MAIN_BASE);
        this.head = tmp.head;
        this.size = tmp.size;
    }

    /**
     * Saves the number, stored in the list, into specified file
     * in <b>decimal</b> scale of notation.
     *
     * @param file - file where number has to be stored.
     */
    public void saveList(File file) {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
        String decimal = toDecimalString();
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.print(decimal);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save list to file", e);
        }
    }

    /**
     * Returns student's record book number, which has 4 decimal digits.
     *
     * @return student's record book number.
     */
    public static int getRecordBookNumber() {
        // here you should return your real record book number;
        // if it has 4 digits, for example 1234 - return 1234.
        // In assignment text they also allow using ordinal number in group
        // if record book is not available.
        return 14;
    }

    /**
     * Returns new <tt>NumberListImpl</tt> which represents the same number
     * in other scale of notation, defined by personal test assignment.<p>
     *
     * For this variant: main base 16 <-> alternate base 2.
     *
     * Does not impact the original list.
     *
     * @return <tt>NumberListImpl</tt> in other scale of notation.
     */
    public NumberListImpl changeScale() {
        BigInteger value = toBigInteger();
        int targetBase = (this.base == MAIN_BASE ? ALT_BASE : MAIN_BASE);
        return new NumberListImpl(value, targetBase);
    }

    /**
     * Returns new <tt>NumberListImpl</tt> which represents the result of
     * additional operation, defined by personal test assignment.<p>
     *
     * For this variant additional operation is addition of two numbers.
     *
     * Does not impact the original list.
     *
     * @param arg - second argument of additional operation
     *
     * @return result of additional operation.
     */
    public NumberListImpl additionalOperation(NumberList arg) {
        if (arg == null) {
            throw new NullPointerException("arg is null");
        }
        BigInteger a = toBigInteger();
        BigInteger b = toBigInteger(arg);
        BigInteger sum = a.add(b);
        return new NumberListImpl(sum, this.base);
    }

    /**
     * Returns string representation of number, stored in the list
     * in <b>decimal</b> scale of notation.
     *
     * @return string representation in <b>decimal</b> scale.
     */
    public String toDecimalString() {
        BigInteger value = toBigInteger();
        return value.toString(10);
    }

    @Override
    public String toString() {
        if (size == 0 || head == null) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        Node current = head;
        for (int i = 0; i < size; i++) {
            int d = current.value;
            char c;
            if (d >= 0 && d <= 9) {
                c = (char) ('0' + d);
            } else {
                c = (char) ('A' + (d - 10));
            }
            sb.append(c);
            current = current.next;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof List)) return false;
        List<?> other = (List<?>) o;
        if (other.size() != this.size) return false;
        int idx = 0;
        for (Object obj : other) {
            if (!(obj instanceof Byte)) return false;
            Byte val = (Byte) obj;
            if (!val.equals(get(idx))) return false;
            idx++;
        }
        return true;
    }

    // ===================== INTERNAL HELPERS =====================

    private void checkElementNotNull(Byte e) {
        if (e == null) {
            throw new NullPointerException("Null elements are not allowed");
        }
    }

    private void checkDigitRange(int digit) {
        if (digit < 0 || digit >= base) {
            throw new IllegalArgumentException("Digit " + digit + " is out of range for base " + base);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
    }

    private Node nodeAt(int index) {
        checkIndex(index);
        if (index < (size / 2)) {
            Node x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node x = head.prev; // tail
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void linkLast(byte value) {
        checkDigitRange(value);
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
    }

    private void linkBefore(byte value, Node succ) {
        checkDigitRange(value);
        Node newNode = new Node(value);
        Node pred = succ.prev;
        newNode.next = succ;
        newNode.prev = pred;
        pred.next = newNode;
        succ.prev = newNode;
        if (succ == head) {
            head = newNode;
        }
        size++;
    }

    private byte unlink(Node node) {
        if (size == 0 || node == null) {
            throw new IllegalStateException("List is empty");
        }
        byte oldVal = node.value;
        if (size == 1) {
            head = null;
            size = 0;
            return oldVal;
        }
        Node pred = node.prev;
        Node succ = node.next;
        pred.next = succ;
        succ.prev = pred;
        if (node == head) {
            head = succ;
        }
        size--;
        return oldVal;
    }

    private BigInteger toBigInteger() {
        if (size == 0 || head == null) {
            return BigInteger.ZERO;
        }
        BigInteger result = BigInteger.ZERO;
        BigInteger b = BigInteger.valueOf(base);
        Node current = head;
        for (int i = 0; i < size; i++) {
            int d = current.value;
            result = result.multiply(b).add(BigInteger.valueOf(d));
            current = current.next;
        }
        return result;
    }

    private static BigInteger toBigInteger(NumberList list) {
        if (list instanceof NumberListImpl) {
            return ((NumberListImpl) list).toBigInteger();
        }
        // Default assumption: digits in MAIN_BASE
        BigInteger result = BigInteger.ZERO;
        BigInteger b = BigInteger.valueOf(MAIN_BASE);
        for (Byte digit : list) {
            if (digit == null) {
                throw new NullPointerException("Null digit in list");
            }
            int d = digit;
            if (d < 0) d += 256;
            if (d >= MAIN_BASE) {
                throw new IllegalArgumentException("Digit " + d + " out of range for base " + MAIN_BASE);
            }
            result = result.multiply(b).add(BigInteger.valueOf(d));
        }
        return result;
    }

    // ===================== List IMPLEMENTATION =====================

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
        if (!(o instanceof Byte)) return false;
        Byte val = (Byte) o;
        if (val == null || size == 0 || head == null) return false;
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (val.equals(current.value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            private Node current = head;
            private int visited = 0;

            @Override
            public boolean hasNext() {
                return visited < size;
            }

            @Override
            public Byte next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                byte val = current.value;
                current = current.next;
                visited++;
                return val;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove not supported in iterator");
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node current = head;
        for (int i = 0; i < size; i++) {
            arr[i] = current.value;
            current = current.next;
        }
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // As per assignment this method is not required to be implemented.
        throw new UnsupportedOperationException("toArray(T[] a) is not implemented");
    }

    @Override
    public boolean add(Byte e) {
        checkElementNotNull(e);
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Byte)) {
            return false;
        }
        Byte val = (Byte) o;
        if (val == null || size == 0 || head == null) {
            return false;
        }
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (val.equals(current.value)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("collection is null");
        }
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Byte> c) {
        if (c == null) {
            throw new NullPointerException("collection is null");
        }
        boolean modified = false;
        for (Byte e : c) {
            add(e);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Byte> c) {
        if (c == null) {
            throw new NullPointerException("collection is null");
        }
        checkPositionIndex(index);
        if (c.isEmpty()) {
            return false;
        }
        int idx = index;
        for (Byte e : c) {
            add(idx++, e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("collection is null");
        }
        if (isEmpty()) return false;
        boolean modified = false;
        int originalSize = size;
        Node current = head;
        for (int i = 0; i < originalSize; i++) {
            Node next = current.next;
            if (c.contains(current.value)) {
                unlink(current);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("collection is null");
        }
        if (isEmpty()) return false;
        boolean modified = false;
        int originalSize = size;
        Node current = head;
        for (int i = 0; i < originalSize; i++) {
            Node next = current.next;
            if (!c.contains(current.value)) {
                unlink(current);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public Byte get(int index) {
        Node node = nodeAt(index);
        return node.value;
    }

    @Override
    public Byte set(int index, Byte element) {
        checkElementNotNull(element);
        Node node = nodeAt(index);
        byte old = node.value;
        checkDigitRange(element);
        node.value = element;
        return old;
    }

    @Override
    public void add(int index, Byte element) {
        checkElementNotNull(element);
        checkPositionIndex(index);
        if (index == size) {
            linkLast(element);
        } else {
            Node succ = nodeAt(index);
            linkBefore(element, succ);
        }
    }

    @Override
    public Byte remove(int index) {
        Node node = nodeAt(index);
        return unlink(node);
    }

    @Override
    public int indexOf(Object o) {
        if (!(o instanceof Byte)) return -1;
        Byte val = (Byte) o;
        if (val == null || size == 0 || head == null) return -1;
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (val.equals(current.value)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (!(o instanceof Byte)) return -1;
        Byte val = (Byte) o;
        if (val == null || size == 0 || head == null) return -1;
        Node current = head.prev; // tail
        for (int i = size - 1; i >= 0; i--) {
            if (val.equals(current.value)) {
                return i;
            }
            current = current.prev;
        }
        return -1;
    }

    @Override
    public ListIterator<Byte> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Byte> listIterator(int index) {
        checkPositionIndex(index);
        return new ListIterator<Byte>() {
            private int cursor = index;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public Byte next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Byte val = get(cursor);
                lastRet = cursor;
                cursor++;
                return val;
            }

            @Override
            public boolean hasPrevious() {
                return cursor > 0;
            }

            @Override
            public Byte previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                cursor--;
                Byte val = get(cursor);
                lastRet = cursor;
                return val;
            }

            @Override
            public int nextIndex() {
                return cursor;
            }

            @Override
            public int previousIndex() {
                return cursor - 1;
            }

            @Override
            public void remove() {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                NumberListImpl.this.remove(lastRet);
                if (lastRet < cursor) {
                    cursor--;
                }
                lastRet = -1;
            }

            @Override
            public void set(Byte e) {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                NumberListImpl.this.set(lastRet, e);
            }

            @Override
            public void add(Byte e) {
                NumberListImpl.this.add(cursor, e);
                cursor++;
                lastRet = -1;
            }
        };
    }

    @Override
    public List<Byte> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        NumberListImpl result = new NumberListImpl(this.base);
        for (int i = fromIndex; i < toIndex; i++) {
            result.add(get(i));
        }
        return result;
    }

    // =============== Additional operations from NumberList ===============

    @Override
    public boolean swap(int index1, int index2) {
        if (index1 == index2) return true;
        checkIndex(index1);
        checkIndex(index2);
        Node n1 = nodeAt(index1);
        Node n2 = nodeAt(index2);
        byte tmp = n1.value;
        n1.value = n2.value;
        n2.value = tmp;
        return true;
    }

    @Override
    public void sortAscending() {
        if (size <= 1) return;
        for (int i = 1; i < size; i++) {
            byte key = get(i);
            int j = i - 1;
            while (j >= 0 && get(j) > key) {
                set(j + 1, get(j));
                j--;
            }
            set(j + 1, key);
        }
    }

    @Override
    public void sortDescending() {
        if (size <= 1) return;
        for (int i = 1; i < size; i++) {
            byte key = get(i);
            int j = i - 1;
            while (j >= 0 && get(j) < key) {
                set(j + 1, get(j));
                j--;
            }
            set(j + 1, key);
        }
    }

    @Override
    public void shiftLeft() {
        if (size <= 1 || head == null) return;
        // cyclic left shift: first becomes last
        head = head.next;
    }

    @Override
    public void shiftRight() {
        if (size <= 1 || head == null) return;
        // cyclic right shift: last becomes first
        head = head.prev;
    }
}
