import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int openSitesCounter;
    private final WeightedQuickUnionUF weightedQ;
    private final int virtualTop, virtualBottom;
    private final int gridN;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("index must be positive");
        }
        weightedQ = new WeightedQuickUnionUF(
                n * n + 2);
        grid = new int[n][n];
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        gridN = n;
    }

    public void open(int rRow, int cCol) {
        int row = rRow - 1;
        int col = cCol - 1;
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("index must be positive");
        }
        if (rRow > gridN || cCol > gridN) {
            throw new IllegalArgumentException("Index must be within the grid length");
        }
        if (!isOpen(rRow, cCol)) {
            grid[row][col] = 1;
            openSitesCounter++;
            if (row == 0) {
                weightedQ.union(virtualTop, (row + col + row * (gridN - 1)));
                if (isOpen((rRow + 1), cCol)) {
                    weightedQ.union((row + 1) + col + (row + 1) * (gridN - 1),
                                    row + col + row * (gridN - 1));
                }
                if (col != (gridN - 1)) {
                    if (isOpen(rRow, (cCol + 1))) {
                        weightedQ.union((row + (col + 1) + row * (gridN - 1)),
                                        (row + col + row * (gridN - 1)));
                    }
                }
                else if (col != 0) {
                    if (isOpen(rRow, (cCol - 1))) {
                        weightedQ.union((row + (col - 1) + row * (gridN - 1)),
                                        (row + col + row * (gridN - 1)));
                    }
                }
            }
            else if (row == (gridN - 1)) {
                weightedQ.union(virtualBottom, (row + col + row * (gridN - 1)));
                if (isOpen((rRow - 1), cCol)) {
                    weightedQ.union((row - 1) + col + (row - 1) * (gridN - 1),
                                    row + col + row * (gridN - 1));
                }
                if (col != 0) {
                    if (isOpen(rRow, (cCol - 1))) {
                        weightedQ.union((row + (col - 1) + row * (gridN - 1)),
                                        (row + col + row * (gridN - 1)));
                    }
                }
                if (col != (gridN - 1)) {
                    if (isOpen(rRow, (cCol + 1))) {
                        weightedQ.union((row + (col + 1) + row * (gridN - 1)),
                                        (row + col + row * (gridN - 1)));
                    }
                }
            }
            else {
                if (isOpen((rRow + 1), cCol)) {
                    weightedQ.union(((row + 1) + col + (row + 1) * (gridN - 1)),
                                    (row + col + row * (gridN - 1)));
                }
                if (isOpen((rRow - 1), cCol)) {
                    weightedQ.union(((row - 1) + col + (row - 1) * (gridN - 1)),
                                    (row + col + row * (gridN - 1)));

                }
                if (col != 0) {
                    if (isOpen(rRow, (cCol - 1)))
                        weightedQ.union((row + (col - 1) + row * (gridN - 1)),
                                        (row + col + row * (gridN - 1)));
                }
                if (col != (gridN - 1)) {
                    if (isOpen(rRow, (cCol + 1))) {
                        weightedQ.union((row + (col + 1) + row * (gridN - 1)),
                                        (row + col + row * (gridN - 1)));
                    }
                }
            }
        }
    }

    public boolean isOpen(int rRow, int cCol) {
        if ((rRow-1) < 0 || (cCol-1) < 0) {
            throw new IllegalArgumentException("index must be positive");
        }
        if ((rRow) > gridN || (cCol) > gridN) {
            throw new IllegalArgumentException("Index must be within the grid length");
        }
        return grid[rRow - 1][cCol - 1] == 1;
    }

    public boolean isFull(int rRow, int cCol) {
        if (rRow <= 0 || cCol <= 0) {
            throw new IllegalArgumentException("index must be positive");
        }
        if (rRow > gridN || cCol > gridN) {
            throw new IllegalArgumentException("Index must be within the grid length");
        }
        return weightedQ.find(virtualTop) == weightedQ
                .find((rRow - 1) + (cCol - 1) + (rRow - 1) * (gridN - 1));
    }

    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    public boolean percolates() {
        return weightedQ.find(virtualTop) == weightedQ.find(virtualBottom);
    }
}
