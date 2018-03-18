import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.File;
import java.util.Arrays;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] states;
    private int n;
    private int openCount;

    public Percolation(int n) {
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n);
        states = new boolean[n * n];
    }            // create n-by-n grid, with all sites blocked

    int toIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    private boolean isInMatrix(int i) {
        return i >= 0 && i < n * n;
    }

    public void open(int row, int col) {
        int i = toIndex(row, col);
        if (!isInMatrix(i)) return;
        if (states[i]) return;
        states[i] = true;
        openCount++;

        int top = toIndex(row - 1, col);
        if (isInMatrix(top) && states[top]) {
            uf.union(top, i);
        }


        int bottom = toIndex(row + 1, col);
        if (isInMatrix(bottom) && states[bottom]) {
            uf.union(i, bottom);
        }

        //由于uf是连续的一维数组，在左边界如果执行union会导致连接到了右上一格
        if (col != 1) {
            int left = toIndex(row, col - 1);
            if (isInMatrix(left) && states[left]) {
                uf.union(left, i);
            }
        }

        //右边界同理
        if (col != n) {
            int right = toIndex(row, col + 1);
            if (isInMatrix(right) && states[right]) {
                uf.union(i, right);
            }
        }
    }  // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        int i = toIndex(row, col);
        return isInMatrix(i) && states[i];
    } // is site (row, col) open?

    public boolean isFull(int row, int col) {
        int i = toIndex(row, col);
        if (!isInMatrix(i)) return false;
        if (!states[i]) return false;
        for (int j = 1; j <= n; j++) {
            int topIndex = toIndex(1, j);
            if (states[topIndex] && uf.connected(topIndex, i)) return true;
        }
        return false;
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return openCount;
    }    // number of open sites

    public boolean percolates() {
        for (int i = 1; i <= n; i++) {
            if (isFull(n, i)) return true;
        }
        return false;
    }             // does the system percolate?

    public static void main(String[] args) {
        System.out.println("args=" + Arrays.toString(args));
        if (args.length == 0 || args[0].equals("*")) {
            File dir = new File(PREFIX);
            if (dir.exists() && dir.isDirectory()) {
                String[] files = dir.list();
                if (files == null) return;
                for (String s : files) {
                    if (s.endsWith(".txt")) {
                        run(s);
                    }
                }
            }
        } else run(args[0]);
    }

    private static void run(String fileName) {
        In in = new In(PREFIX + fileName);

        int n = in.readInt();
        Percolation percolation = new Percolation(n);
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            percolation.open(x, y);
        }
        System.out.println(fileName + " is percolation=" + percolation.percolates());
        in.close();
    }

    public static String PREFIX = "data/algs4-data/percolation/";
}