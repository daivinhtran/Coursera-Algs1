
/*----------------------------------------------------------------
 *  Author:        Vinh Tran
 *  Written:       2/5/2016
 *  Last updated:  8/7/2006
 *
 *  Compilation:   javac-algs4 Percolation.java
 *  Execution:     java-algs4 Percolation
 *  
 *  The class models a percolation system using an N-by-N grid of sites
 *  Each site is either open or blocked.
 *  A full site is an open site that can be connected to an open site 
 *  in the top row via a chain of neighboring open sites.
 *  The system percolates if we fill all open sites connected to the
 *  top row and that process fills some open site on the bottom.
 *  Backwash issue is also solved.
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

  //declare percolation grid with gridSize-by-gridSize
  private boolean[][] grid;
  private int gridSize; //the height or the width of the grid

  //declare an empty union-find data structures
  private WeightedQuickUnionUF quickUnion1;
  private WeightedQuickUnionUF quickUnion2;

  // create N-by-N grid, with all sites blocked
  public Percolation(int N) {

    //throw exception if N is less than 0
    if (N <= 0) {
      throw new IllegalArgumentException("The input is less than 0");
    }

    gridSize = N;
    //initialize percolation grid with all the sites to be blocked
    this.grid = new boolean[gridSize + 1][gridSize + 1];
    for (int i = 1; i <= gridSize; i++)
      for (int j = 1; j <= gridSize; j++)
        grid[i][j] = false;

    //initialize weighted quick union data structure

    // has virtual top only
    this.quickUnion1 = new WeightedQuickUnionUF(gridSize * gridSize + 1);

    // has virtual bottom and top
    this.quickUnion2 = new WeightedQuickUnionUF(gridSize * gridSize + 2);
  }   

  // open site (row i, column j) if it is not open already            
  public void open(int i, int j) {
    //throw exception if out of bound
    validates(i, j);
    if (isOpen(i, j)) {
      return;
    }
    else {
      //open the site
      this.grid[i][j] = true;

      //Connect to top and bottom
      if (i == 1) {
        this.quickUnion1.union(0, xyTo1D(i, j));
        this.quickUnion2.union(0, xyTo1D(i, j));
      }
      if (i == gridSize) {
        this.quickUnion2.union(gridSize * gridSize + 1, xyTo1D(i, j));
      }

      //connect if the surroundings isOpen as well
      //TOP ROW
      if (i == 1 && (i + 1) <= gridSize && isOpen(i + 1, j)) {
        this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i + 1, j));
        this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i + 1, j));
      }
      //BOTTOM ROW
      else if (i == gridSize && (i -  1) > 0 && isOpen(i - 1, j)) {
        this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i - 1, j));
        this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i - 1, j)); 
      }
      else {
        if ((i -  1) > 0 && isOpen(i - 1, j)) {
          this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i - 1, j));
          this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i - 1, j));
        }
        if ((i + 1) <= gridSize && isOpen(i + 1, j)) {
          this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i + 1, j));
          this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i + 1, j));
        }
      }

      if (j == 1 && (j + 1) <= gridSize && isOpen(i, j + 1)) {
        this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i, j + 1));
        this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i, j + 1));
      }
      else if (j == gridSize && (j - 1) > 0 && isOpen(i, j - 1)) {
        this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i, j - 1));
        this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i, j - 1));  
      }
      else {
        if ((j -  1) > 0 && isOpen(i, j - 1)) {
          this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i, j - 1));
          this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i, j - 1));
        }
        if ((j + 1) <= gridSize && isOpen(i, j + 1)) {
          this.quickUnion1.union(xyTo1D(i, j), xyTo1D(i, j + 1));
          this.quickUnion2.union(xyTo1D(i, j), xyTo1D(i, j + 1));
        }
      }
    }
    return;
  }

  // is site (row i, column j) open?
  public boolean isOpen(int i, int j) {
    //throw exception if out of bound
    validates(i, j);
      
    return this.grid[i][j];
    
  }
  // is site (row i, column j) full?     
  public boolean isFull(int i, int j) {
    //throw exception if out of bound
    validates(i, j);
    //check if the site is connected with any site on the top row
    if (this.quickUnion1.connected(xyTo1D(i, j), 0))
      return true;

    return false;
  }  

  // does the system percolate?   
  public boolean percolates() {
    return this.quickUnion2.connected(0, gridSize * gridSize + 1);
  }
    
  // convert the location of the site responding to 
  // the Weighted Quick-Union data structure array
  private int xyTo1D(int row, int col) {
    return (row - 1) * gridSize + col;
  }           

  private void validates(int i, int j) {
    if (i < 1 || i > gridSize || j < 1 || j > gridSize) {
        throw new IndexOutOfBoundsException("index " + i + " or " + j 
          + " is not between 1 and " + gridSize);  
    }
    return;
  }


}