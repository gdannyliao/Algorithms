import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.File;
import java.util.Arrays;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] openStates;
    private int n;
    private int openCount;
    private int virtualTopIdx = 0;
    private int virtualBottomIdx;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("n is illegal");
        this.n = n;
        int size = n * n + 2;
        virtualBottomIdx = size - 1;
        uf = new WeightedQuickUnionUF(size);
        openStates = new boolean[size];
        openStates[virtualTopIdx] = true;
        openStates[virtualBottomIdx] = true;
        //2018.3.18 FIXME  我觉得首尾两个虚拟点的方式还是有问题，假如有一个权重比较小的路径是连通的，但有一个权重比较大的路径是不连通的，那虚拟点肯定会连接到权重大的路径上去
    }            // create n-by-n grid, with all sites blocked

    private int toIndex(int row, int col) {
        int idx = (row - 1) * n + col;
        if (idx < virtualTopIdx) idx = virtualTopIdx;
        if (idx > virtualBottomIdx) idx = virtualBottomIdx;
        return idx;
    }

    private boolean inMatrix(int row, int col) {
        return 1 <= row && row <= n && 1 <= col && col <= n;
    }

    public void open(int row, int col) {
        if (!inMatrix(row, col)) throw new IllegalArgumentException();
        int i = toIndex(row, col);
        if (openStates[i]) return;
        openStates[i] = true;
        openCount++;

        int top = toIndex(row - 1, col);
        if (openStates[top]) {
            uf.union(top, i);
        }


        int bottom = toIndex(row + 1, col);
        if (openStates[bottom]) {
            uf.union(i, bottom);
        }

        //由于uf是连续的一维数组，在左边界如果执行union会导致连接到了右上一格
        if (col != 1) {
            int left = toIndex(row, col - 1);
            if (openStates[left]) {
                uf.union(left, i);
            }
        }

        //右边界同理
        if (col != n) {
            int right = toIndex(row, col + 1);
            if (openStates[right]) {
                uf.union(i, right);
            }
        }
    }  // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        if (!inMatrix(row, col)) throw new IllegalArgumentException();
        int i = toIndex(row, col);
        return openStates[i];
    } // is site (row, col) open?

    public boolean isFull(int row, int col) {
        if (!inMatrix(row, col)) throw new IllegalArgumentException();
        int i = toIndex(row, col);
        return openStates[i] && uf.connected(i, virtualTopIdx);
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return openCount;
    }    // number of open sites

    public boolean percolates() {
        return uf.connected(virtualBottomIdx, virtualTopIdx);
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