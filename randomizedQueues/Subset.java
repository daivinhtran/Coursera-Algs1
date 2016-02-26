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
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.enqueue(item);
            else if (!s.isEmpty()) StdOut.print(s.dequeue() + " ");
        }
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(s.dequeue());
        }
    }
}