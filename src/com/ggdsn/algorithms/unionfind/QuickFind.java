package com.ggdsn.algorithms.unionfind;

import java.util.HashSet;
import java.util.Set;

public class QuickFind implements UnionFind {
    private int[] id;

    public QuickFind(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP = id[p];
        int rootQ = id[q];
        //将所有原本指向Q的节点指向P
        for (int i : id) {
            if (id[i] == rootQ)
                id[i] = rootP;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public int count() {
        Set<Integer> set = new HashSet<>();
        for (int i : id) {
            set.add(id[i]);
        }
        return set.size();
    }
}