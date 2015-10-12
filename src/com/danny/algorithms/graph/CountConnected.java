package com.danny.algorithms.graph;

import edu.princeton.cs.introcs.In;

public class CountConnected {
	private boolean[] marked;
	private int[] id;
	private int count;

	public CountConnected(Graph g) {
		marked = new boolean[g.vertices];
		id = new int[g.vertices];
		for (int i=0;i<g.vertices; i++) {
			if (!marked[i]) {
				dfs(g, i);
				count++;
			}
		}
		System.out.println();
	}

	private void dfs(Graph g, int i) {
		marked[i] = true;
		id[i] = count;
		for (int w:g.adj.get(i)) {
			if (!marked[i])
				dfs(g, w);
		}
	}
	
	public boolean connected(int v, int w) {
		return id[v] == id[w];
	}
	public int id(int v) {
		return id[v];
	}
	
	public int count() {
		return count;
	}
	
	public static void testConnected(In in) {
		Graph g = new Graph(in);
		CountConnected cc = new CountConnected(g);
	}
}
