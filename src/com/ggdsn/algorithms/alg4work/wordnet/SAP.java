package com.ggdsn.algorithms.alg4work.wordnet;

import com.ggdsn.algorithms.alg4work.Alg4;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SAP {
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null || G.V() == 0) throw new IllegalArgumentException();
        this.graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        init();
        calDistance(v, w, new HashMap<>(), 0);
        return isSet ? minDistance : -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        length(v, w);
        return isSet ? sca : -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        length(v, w);
        return sca;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();

        init();
        for (Integer n1 : v) {
            for (Integer n2 : w) {
                calDistance(n1, n2, new HashMap<>(), 0);
            }
        }
        return isSet ? minDistance : -1;
    }

    private void calDistance(Integer a, Integer b, Map<Integer, Integer> path, int distance) {
        //由于不保证是无环图，所以如何即保证遍历，又不重复？
        //TODO 解决环的问题
        Iterable<Integer> adj = graph.adj(a);
        path.put(a, distance++);
        if (!adj.iterator().hasNext()) {
            findCross(b, path, 0);
        } else {
            for (Integer next : adj) {
                calDistance(next, b, path, distance);
            }
        }
        path.remove(a);
    }

    private int minDistance = Integer.MAX_VALUE;
    private boolean isSet = false;
    private int sca;

    private void init() {
        minDistance = Integer.MAX_VALUE;
        isSet = false;
        sca = -1;
    }

    private void findCross(Integer v, Map<Integer, Integer> path, int distance) {
        if (path.containsKey(v)) {
            isSet = true;
            int dis = path.get(v) + distance;
            if (dis < minDistance) {
                minDistance = dis;
                sca = v;
            }
            return;
        }
        for (Integer i : graph.adj(v)) {
            findCross(i, path, distance + 1);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String path = Alg4.DATA_DIR + File.separator + "wordnet" + File.separator;

        In in = new In(path + args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
