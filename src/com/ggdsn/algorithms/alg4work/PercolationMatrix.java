package com.ggdsn.algorithms.alg4work;

import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/**
 * 这个解法采用一个n*n的矩阵进行并查集计算。设置两个虚拟节点top, bottom来辅助进行连通性的计算。
 */
public class PercolationMatrix {
    /**
     * sites 0 : closed, 1 open
     */
    private Site[][] sites;

    private Site top = new Site(0, 0);
    private Site bottom;
    private int openCount;

    // create n-by-n grid, with all sites blocked
    public PercolationMatrix(int n) {
        //由于题目说index从1到n，所以这里直接在这里做偏移比较方便
        n++;
        sites = new Site[n][n];
        bottom = new Site(n, n);
        top.isOpen = true;
        bottom.isOpen = true;
        for (int j = 1; j < n; j++) sites[0][j] = top;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                sites[i][j] = new Site(i, j);
            }
        }
    }

    private Site find(int x, int y) {
        if (x == top.initX) return top;
        if (x == bottom.initX) return bottom;

        if (!isInMatrix(x, y)) return null;

        Site s = sites[x][y];
        if (s.rootX != x || s.rootY != y) {
            return find(s.rootX, s.rootY);
        }
        return s;
    }

    private void union(int x1, int y1, int x2, int y2) {
        Site root1 = find(x1, y1);
        Site root2 = find(x2, y2);
        if (root1 == root2) return;
        if (root1 == null || root2 == null) return;

        //仅打开的site可以连通
        if (!root1.isOpen || !root2.isOpen) return;

        //2018.3.17 FIXME 由于根节点可能被改动n次，所以很可能最终两个根节点并没有落在同一个节点上。
        if (root1.weight >= root2.weight) {
            root2.rootX = root1.rootX;
            root2.rootY = root1.rootY;
            root1.weight += root2.weight;
        } else {
            root1.rootX = root2.rootX;
            root1.rootY = root2.rootY;
            root2.weight += root1.weight;
        }
    }

    private boolean isInMatrix(int x, int y) {
        if (x < 1 || x >= sites.length) return false;
        if (y < 1 || y >= sites.length) return false;
        return true;
    }

    // open site (x, y) if it is not open already
    public void open(int x, int y) {
        if (!isInMatrix(x, y)) return;
        sites[x][y].isOpen = true;
        openCount++;
        //上
        union(x, y, x - 1, y);
        //左
        union(x, y, x, y - 1);
        //下
        union(x, y, x + 1, y);
        //右
        union(x, y, x, y + 1);
    }

    public boolean isOpen(int x, int y) {
        return sites[x][y].isOpen;
    }

    // is site (row, col) full?
    public boolean isFull(int x, int y) {
        if (!isInMatrix(x, y)) return false;
        Site site = sites[x][y];
        return site.rootX == top.rootX && site.rootY == top.rootY;
    }

    public int numberOfOpenSites() {
        return openCount;
    }    // number of open sites

    public boolean percolates() {
        //TODO 如何判断它们已经贯通了？ 如果采用判断底部是否指向顶部的方式，则底部如何连向多个节点是个要处理的问题。
        return bottom.rootX == top.rootX && bottom.rootY == top.rootY;
    }     // does the system percolate?

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
        PercolationMatrix percolation = new PercolationMatrix(n);
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

class Site {
    boolean isOpen;
    int rootX;
    int rootY;
    int weight = 1;

    int initX, initY;

    Site(int x, int y) {
        initX = x;
        initY = y;
        rootX = x;
        rootY = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return initX == site.initX &&
                initY == site.initY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(initX, initY);
    }

    @Override
    public String toString() {
        return "Site=[" + initX + "," + initY + "] open=" + isOpen + " root=[" + rootX + "," + rootY + "] weight=" + weight;
    }
}