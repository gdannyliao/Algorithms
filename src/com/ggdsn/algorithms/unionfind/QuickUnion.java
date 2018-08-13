package com.ggdsn.algorithms.unionfind;

import java.util.HashSet;
import java.util.Set;

public class QuickUnion implements UnionFind {
    private int[] id;
    private int[] weight;

    public QuickUnion(int n) {
        id = new int[n];
        weight = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            weight[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        int weightP = weight[rootP];
        int weightQ = weight[rootQ];
        if (weightP > weightQ) {
            //将较小的树并入较大的树，以免整棵树过高。
            id[rootQ] = rootP;
            weight[rootP] += weightQ;
        } else {
            id[rootP] = rootQ;
            weight[rootQ] += weightP;
        }
    }

    @Override
    public int find(int p) {
        /*
        因为规定了树根部位int[p] = p，所以不等于的位置都不是根
         */
        while (id[p] != p) {
            //将这棵子树拔高一层，最终会拔高到根部
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
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