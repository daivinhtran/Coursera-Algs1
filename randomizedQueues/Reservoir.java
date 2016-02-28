/**
 * The program demonstrates reservoir sampling
 * https://en.wikipedia.org/wiki/Reservoir_sampling
 * @author Vinh Tran
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class Reservoir {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String[] reservoirArr = new String[k];

        // To store all inputs
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }

        System.out.println("Everthing is queue: ... ");
        for (String string : queue) {
            System.out.println(string);
        }

        Iterator<String> queueIterator = queue.iterator();

        System.out.println("Fill in the reservoir array: ...");
        // fill the reservoir array
        for (int i = 0; i < k; i++) {
            reservoirArr[i] = queueIterator.next();
        }

        for (String string : reservoirArr) {
            System.out.println(string);
        }

        for (int i = k; i < queue.size(); i++) {
            int j = StdRandom.uniform(i + 1); // [0, i] uniformly random
            if (j < k) {
                reservoirArr[j] = queueIterator.next();
            }
            else {
                queueIterator.next();
            }
        }

        System.out.println("final Reservoir array: ...");

        for (String string : reservoirArr) {
            System.out.println(string);
        }

    }
}