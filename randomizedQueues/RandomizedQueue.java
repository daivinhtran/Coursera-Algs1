
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * A randomized queue is similar to a stack or queue, except the item removed
 * is chosen uniformly at random from items in the data structure
 * @author Vinh Tran
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;  // array of items
    private int size;
    /**
     * Initializes an empty stack
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        size = 0;
    }
    /**
     * Returns true if the queue is empty
     * @return <tt>true</tt> if this queue is empty; <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Returns the number of items on the queue
     * @return the number of items on the queue
     */
    public int size() {
        return size;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    /**
     * Adds the item
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) 
            throw new NullPointerException("null pointer not allowed");
        if (size == a.length) 
            resize(2 * a.length); // double size of array
        a[size++] = item;
    }
    /**
     * Remove and return a random item
     * @return the removed item
     */ 
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        int index = StdRandom.uniform(size);
        Item item = a[index];

        a[index] = a[size - 1];
        a[size - 1] = null;
        size--;
        // shrink size of array if neccessary
        if (size > 0 && size == a.length/4) resize(a.length / 2);
        return item;
    }
    /**
     * return (but do not remove) a random item
     * @return the an random item
     */ 
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = a[StdRandom.uniform(size)];
        return item;
    }
    /**
     * Return an interator over items in random order
     * @return an interator over items in random order
     */
    public Iterator<Item> iterator() {
        return new ReverseQueueIterator();
    }

    // an iterator
    private class ReverseQueueIterator implements Iterator<Item> {
        private int i;
        private int[] shuffledIndexArr;

        public ReverseQueueIterator() {
            shuffledIndexArr = new int[size];
            for (int j = 0; j < size; j++) {
                shuffledIndexArr[j] = j;
            }
            StdRandom.shuffle(shuffledIndexArr);
            i = 0;
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[shuffledIndexArr[i++]];
        }
    }
    /**
     * Unit tests the <tt>Queue</tt> datat type
     */
    public static void main(String[] args) {

    }
}













