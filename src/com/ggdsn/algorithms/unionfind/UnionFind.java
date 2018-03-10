package com.ggdsn.algorithms.unionfind;

public interface UnionFind {
    /**
     * 连接p, q
     */
    void union(int p, int q);
    /**
     * p的index
     */
    int find(int p);
    /**
     * 两点之间是否已连接
     */
    boolean connected(int p, int q);
    /**
     * 连通分量的数量
     */
    int count();
}