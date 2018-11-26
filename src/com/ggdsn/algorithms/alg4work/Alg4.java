package com.ggdsn.algorithms.alg4work;

import com.ggdsn.algorithms.unionfind.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Alg4 {
    public static void main(String[] args) {
        try {
            FileInputStream input = new FileInputStream(DATA_DIR + File.pathSeparator + args[0]);
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

    public static final String DATA_DIR = "data/algs4-data";

    public static String getFile(String name) {
        return Alg4.DATA_DIR + File.separator + name;
    }

    public static String getFile(String dirName, String fileName) {
        return Alg4.DATA_DIR + File.separator + dirName + File.separator + fileName;
    }
}