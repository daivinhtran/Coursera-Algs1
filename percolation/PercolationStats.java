/*----------------------------------------------------------------
 *  Author:        Vinh Tran
 *  Written:       2/5/2016
 *  Last updated:  8/7/2006
 *
 *  Compilation:   javac-algs4 Percolation.java
 *  Execution:     java-algs4 Percolation
 *  Dependencies:  Percolation.java
 *  
 *  The program is to estimate the percolation threshold.
 *  Steps:
 *  1. Initial all sites to be blocked.
 *  2. Repeat the following until the system percolates:
 *        Choose a site (row i, column j) uniformly at random among 
 *        all blocked sites.
 *        Open the site (row i, column j).
 *  3. The fraction of sites that are opened when the system percolates 
 *  provides an estimate of the percolation threshold.
 *----------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {

    private double[] threshold;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        //throw exception if N or T is less than or equal to zero
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T is less than 0");
        }
        else {
            //Initialize threshold array and per array grid with N-by-N dimension
            this.threshold = new double[T];

            for (int z = 0; z < T; z++) {
                Percolation per =  new Percolation(N);
                int i;
                int j;
                int count = 0;

                while (!per.percolates()) {
                  i = StdRandom.uniform(N) + 1;
                  j = StdRandom.uniform(N) + 1;
                  if (per.isOpen(i, j)) {
                    continue;
                  }
                  else {
                    per.open(i, j);
                    count++;
                  }
                }
                threshold[z] = (double) count/(N*N);
                count = 0;
            }
        }

    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }         
    // sample standard deviation of percolation threshold             
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    // low  endpoint of 95% confidence interval                    
    public double confidenceLo() {
        return StdStats.mean(threshold)
            - 1.96*StdStats.stddev(threshold)/Math.sqrt((double) threshold.length);
    }
    // high endpoint of 95% confidence interval              
    public double confidenceHi() {
        return StdStats.mean(threshold)
            + 1.96*StdStats.stddev(threshold)/Math.sqrt((double) threshold.length);
    }              

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats perStats = new PercolationStats(Integer.parseInt(args[0]),
                                        Integer.parseInt(args[1]));

        //System.out.println(perStats.threshold[2]);
        System.out.println("mean \t\t\t= " + perStats.mean());
        System.out.println("stddev \t\t\t= " + perStats.stddev());
        System.out.println("95% confidence interval = " + perStats.confidenceLo() + ", " + perStats.confidenceHi());

    }  


}