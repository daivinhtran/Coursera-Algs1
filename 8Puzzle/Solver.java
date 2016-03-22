import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Objects;
import java.util.Collection;
public class Solver {

    private SearchNode origin;
    private SearchNode originTwin;
    private Collection<Board> solutionSeq;
    private MinPQ<SearchNode> open;

    /**
     * Find a solution to the initial board using the A* algorithm
     */
    public Solver(Board initial) {
        this.origin = new SearchNode(initial);
        this.originTwin = new SearchNode(initial.twin());
        open = new MinPQ<SearchNode>(new SearchNodeComparator());
        solutionSeq = Collections.emptyList();
        solve();
    }

    private class SearchNode {

        private final Board board;
        private final SearchNode previous;
        private final int moves;

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
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode left, SearchNode right) {
            int manhattanPriority = Integer.compare(left.board.manhattan() + left.moves,
                                                    right.board.manhattan() + right.moves);
            if (manhattanPriority == 0) {
                int manhattanDistance = Integer.compare(left.board.manhattan(), right.board.manhattan());
                if (manhattanDistance == 0) {
                    return Integer.compare(left.board.hamming(), right.board.hamming());
                }
                return manhattanDistance;
            }
            return manhattanPriority;
        }
    }

    /**
     * Whether the intial board is solvable
     * @return true if the initial board is solvable. Otherwise, return false
     */
    public boolean isSolvable() {
        return !solutionSeq.isEmpty();
    }

    /**
     * Compute and return the minimum number of moves to solve initial board
     * @return the minimum number of moves to solve initial board; -1 if unsovable
     */
    public int moves() {
        return solutionSeq.size() - 1;
    }

    /**
     * Return a sequence of boards in the shortest solution; null if unsolvable
     * @return a sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solutionSeq;
    }

    private void solve() {

        open.insert(origin);
        open.insert(originTwin);

        while (!open.isEmpty()) {
            SearchNode current = open.delMin();
            if (!current.board.isGoal()) {
                for (Board neighbor : current.board.neighbors()) {
                    SearchNode candidate = new SearchNode(neighbor, current);
                    if (Objects.isNull(current.previous) ||
                        !neighbor.equals(current.previous.board)) {
                        open.insert(candidate);
                    }
                }
            }
            else {
                LinkedList<Board> out = new LinkedList<>();
                do {
                    out.addFirst(current.board);
                    current = current.previous;
                } while (!Objects.isNull(current));
                if (!out.isEmpty() && out.getFirst().equals(origin.board)) {
                    solutionSeq = Collections.unmodifiableCollection(out);
                }
                return;
            }
        }
    }

    private boolean isLegitMove(Board closedBoard, Board previous) {
        System.out.println("last on solution: " + previous);
        System.out.println("closedBoard: " + closedBoard);
        for (Board neighbor : previous.neighbors()) {
            if (closedBoard.equals(neighbor)) {
                System.out.println("legit");
                return true;
            }
        }
        System.out.println("not legit");
        return false;
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

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}