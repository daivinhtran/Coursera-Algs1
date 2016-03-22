import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
public class Board {
    private final int N; // width or height of the N-by-N Board
    private final int[][] tiles;
    // location of blank square
    private int blankI;
    private int blankJ;

    /**
     * Construct a board from an N-by-N array of blocks
     * blocks[i][j] is block in row i, column j
     */
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException("invalid input points");

        N = blocks.length;
        tiles = new int[N][N];

        blankI = -1;
        blankJ = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                }
                tiles[i][j] = blocks[i][j];
            }
        }
    }

    /**
     * Return the board dimension
     * @return the board dimension
     */
    public int dimension() {
        return N;
    }

    /**
     * Compute and return the number of blocks in the wrong position
     * @return the number of blocks out of place
     */
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != (i*N + j + 1))
                    ham++;
            }
        }
        return ham;
    }

    /**
     * Return the sum of Manhattan distanes between blocks and goal
     * @return the sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int man = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != 0) {
                    int goalX = (tiles[i][j] - 1) % N;
                    int goalY = (tiles[i][j] - 1) / N;
                    man += (Math.abs(goalX - j) + Math.abs(goalY - i));
                }
            }
        }
        return man;
    }

    /**
     * Whether the board is the goal board
     * @return whether the board is the goal board
     */
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != (N - 1) && j != (N - 1)) {
                    if (tiles[i][j] != (i * N + j + 1))
                    return false;
                } else if (i == (N - 1) && j == (N - 1)) {
                    return tiles[i][j] == 0;
                }
            }
        }
        return true;
    }

    /**
     * Return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        int i1, j1;
        int i2, j2;
        int[][] twinBlocks = copyTiles();

        do {
            i1 = StdRandom.uniform(N);
            j1 = StdRandom.uniform(N);
        } while (tiles[i1][j1] == 0);

        do {
            i2 = StdRandom.uniform(N);
            j2 = StdRandom.uniform(N);
        } while ((i1 == i2 && j1 == j2) || tiles[i2][j2] == 0);

        twinBlocks[i1][j1] = tiles[i2][j2];
        twinBlocks[i2][j2] = tiles[i1][j1];

        return new Board(twinBlocks);
    }

    /**
     * Return whether this board is equal y
     @ @param y the board y
     * @return true if the board is equal to y; false otherwise
     */
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y instanceof Board) {
            Board that = (Board) y;
            if (that.dimension() != this.dimension())
                return false;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (this.tiles[i][j] != that.tiles[i][j])
                        return false;
                }
            }
            return true;
        } else {
            return false;
        }         
    }

    /**
     * Return all neighboring boards
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Queue<Board> nbQueue = new Queue<Board>();
        int[][] newTiles;

        // left
        if (blankJ - 1 >= 0) {
            newTiles = copyTiles();
            newTiles[blankI][blankJ - 1] = 0;
            newTiles[blankI][blankJ] = tiles[blankI][blankJ - 1];
            nbQueue.enqueue(new Board(newTiles));
        }

        // right
        if (blankJ + 1 < N) {
            newTiles = copyTiles();
            newTiles[blankI][blankJ + 1] = 0;
            newTiles[blankI][blankJ] = tiles[blankI][blankJ + 1];
            nbQueue.enqueue(new Board(newTiles));
        }

        // top
        if (blankI - 1 >= 0) {
            newTiles = copyTiles();
            newTiles[blankI - 1][blankJ] = 0;
            newTiles[blankI][blankJ] = tiles[blankI - 1][blankJ];
            nbQueue.enqueue(new Board(newTiles));
        }

        // bottom
        if (blankI + 1 < N) {
            newTiles = copyTiles();
            newTiles[blankI + 1][blankJ] = 0;
            newTiles[blankI][blankJ] = tiles[blankI + 1][blankJ];
            nbQueue.enqueue(new Board(newTiles));
        }

        return nbQueue;
    }

    /**
     * Print the board in N-by-N grid format
     * @return the string presentation of this board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // return a copy of the current tiles array
    private int[][] copyTiles() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    /**
     * Unit test
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        System.out.println("initial: " + initial);
        System.out.println("twin: " + initial.twin());
    }
}
