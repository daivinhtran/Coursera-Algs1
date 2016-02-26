/**
 * The client program takes command-line integer k;
 * reads in a sequence of N strings from standard input using
 * using StdIn.readString();
 * and print out exactly k of them, uniformly at random
 * Each item from the sequence can be printed out at most once
 * @author Vinh Tran
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");
        }
        
        Iterator<String> queueIterator = queue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(queueIterator.next());
        }
    }
}