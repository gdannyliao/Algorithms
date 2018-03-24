package com.ggdsn.algorithms.alg4work;

import com.ggdsn.algorithms.unionfind.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Alg4 {
    public static void main(String[] args) {
        try {
            FileInputStream input = new FileInputStream("data/algs4-data/" + args[0]);
            System.setIn(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        int N = StdIn.readInt();
        UnionFind uf = new QuickFind(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}