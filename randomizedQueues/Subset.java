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
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        
        // fill in the Reservoir array
        for (int i = 0; i < k; i++) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        // replace elements with gradually decreasing probablity
        int index = k;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            int j = StdRandom.uniform(index + 1);
            if (j < k) {
                queue.dequeue();
                queue.enqueue(item);
            }
            index++;
        }
        for (String string : queue) {
            StdOut.println(string);
        }

        return;
    }
}