import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
public class Solver {
	private SearchNode initial;
	MinPQ<SearchNode> priorityQueue = new MinPQ<SearchNode>();
	private class SearchNode implements Comparable<SearchNode> {
		private SearchNode previous;
		private Board board;
		private int moves;

		public SearchNode(Board board) {
			this.board = board;
			this.moves = 0;
			this.previous = null;
		}

		public SearchNode(Board board, SearchNode previous) {
			this.board = board;
			this.moves = previous.moves + 1;
			this.previous = previous;
		}

		public int compareTo(SearchNode that) {
			int thisPriority = this.board.manhattan() + this.moves;
			int thatPriority = that.board.manhattan() + that.moves;
			if (thisPriority != thatPriority) {
				return thisPriority - thatPriority;
			} else if (this.board.manhattan() != that.board.manhattan()) {
				return this.board.manhattan() - that.board.manhattan();
			} else {
				return that.board.hamming() - this.board.hamming();
			}
		}
	}

	/**
	 * Find a solution to the initial board using the A* algorithm
	 */
    public Solver(Board initial) {
    	this.initial = new SearchNode(initial);
    }

    // /**
    //  * Whether the intial board is solvable
    //  * @return true if the initial board is solvable. Otherwise, return false
    //  */
    // public boolean isSolvable() {

    // }

    // /**
    //  * Compute and return the minimum number of moves to solve initial board
    //  * @return the minimum number of moves to solve initial board; -1 if unsovable
    //  */
    public int moves() {
    	return initial.board.manhattan();
    }

    /**
     * Return a sequence of boards in the shortest solution; null if unsolvable
     * @return a sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
    	Queue<Board> solutionQueue = new Queue<Board>();

    	priorityQueue.insert(initial);
    	SearchNode newNode = priorityQueue.delMin();
    	Board newBoard = newNode.board;

    	do {
    		newNode = priorityQueue.delMin();
    		newBoard = newNode.board;
    		solutionQueue.enqueue(newBoard);

    		for (Board board : newBoard.neighbors()) {
    			if (newNode.previous != null) {
    				if (!newBoard.equals(newNode.previous.board)) {
    					priorityQueue.insert(new SearchNode(board, newNode));
    				}
    			} else {
    				priorityQueue.insert(new SearchNode(board, newNode));
    			}
    		}    		
    	} while (!newBoard.isGoal());


    	while (!newBoard.isGoal()) {
    		
    	}
    	return solutionQueue;
    }

    // solve a slider puzzle
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    System.out.println("Minimum number of moves = " + solver.moves());

	    int i = 0;
	    for (Board board : solver.solution()) {
	    	System.out.println(board);
	    	System.out.println(i++);
	    }

	    // solver.solution();

	    // // print solution to standard output
	    // if (!solver.isSolvable())
	    //     StdOut.println("No solution possible");
	    // else {
	    //     StdOut.println("Minimum number of moves = " + solver.moves());
	    //     for (Board board : solver.solution())
	    //         StdOut.println(board);
	    // }
	}
}