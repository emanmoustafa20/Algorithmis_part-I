import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] openSiteCounter;
    private final static double confidanceLevel = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("index must be positive");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials must be positive");
        }
        int t, b, q;
        double opened = 0;
        openSiteCounter = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            opened = 0;
            t = StdRandom.uniform((n), (n * n));
            while (!p.percolates()) {
                b = StdRandom.uniform(1, n + 1);
                q = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(b, q)) {
                    p.open(b, q);
                    opened++;
                }
            }
            openSiteCounter[i] = (opened / (n * n));
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSiteCounter);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSiteCounter);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (confidanceLevel * (stddev() / Math.sqrt(openSiteCounter.length))));
    }

    public double confidenceHi() {
        return (mean() + (confidanceLevel * (stddev() / Math.sqrt(openSiteCounter.length))));
    }

    public static void main(String[] args) {
        // left empty on purpose
        PercolationStats p = new PercolationStats(2, 10000);
        System.out.println(p.mean() + " " + p.stddev());
        System.out.println(p.confidenceHi() + " " + p.stddev());
    }
}
