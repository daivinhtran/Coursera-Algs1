

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Deque is a generalization of a stack and a queue that supports adding
 * and removing items from either front or the back of the data structure
 * @author Vinh Tran
 */

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }
    /**
     * Constructs an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
    /**
     * Returns true if the deque is empty
     * @return <tt>true</tt> if this deque is empty; <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return first == null && last == null;
    }
    /**
     * Returns the number of items on the deque
     * @return the number of items on the deque
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to the front of this deque
     * @param item the item to add
     */
    public void addFirst(Item item) {
        if (item == null) 
            throw new NullPointerException("null pointer not allowed");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) oldfirst.previous = first;
        if (last == null) { // first item of the deque
            last = first;
        }
        size++;
    }
    /**
     * Adds the item to the back of this deque
     * @param item the item to add
     */ 
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("null pointer not allowed");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (oldlast != null) oldlast.next = last;
        last.previous = oldlast;
        if (first ==  null) { // first item of the deque
            first = last;
        }
        size++;
    }
    /**
     * Remove the first item of the deque
     * @return the removed item
     */ 
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        if (first != null) first.previous = null;
        if (first == null) last = null;
        size--;  
        return item;
    }
    /**
     * Remove the last item of the deque
     * @return the removed item
     */  
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.previous;
        if (last != null) last.next = null;
        if (last == null) first = null;
        size--;
        return item;
    }
    /**
     * Return an interator over items in order from front to end
     * @return an interator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // DequeIterator is an instance of Iterator
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    /**
     * Unit tests the <tt>Deque</tt> datat type
     */
    public static void main(String[] args) {

    }
}













