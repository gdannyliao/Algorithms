package com.danny.algorithms.graph;


import java.util.ArrayList;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Graph {
	protected int vertices;
	protected int edges;

	protected ArrayList<ArrayList<Integer>> adj;
	protected boolean[] marked;
	//存储着每个连通点回到起点的路径，是一棵树,存储着父节点的位置
	protected int[] edgeTo;
	protected int begin;
	
	public Graph(int verCount) {
		vertices = verCount;

		edges = 0;
		adj = new ArrayList<ArrayList<Integer>>(vertices);
		for (int i = 0; i < vertices; i++) {
			adj.add(new ArrayList<Integer>());
		}
	}

	public void setBegin(int v) {
		begin = v;
		edgeTo = new int[vertices];
		marked = new boolean[vertices];
		dfs(v);
	}
	public int getBegin() {
		return begin;
	}
	public boolean hasPathTo(int v) {
		if (marked == null)
			return false;
		
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		if (!hasPathTo(v))
			return null;
		
		Stack<Integer> path = new Stack<Integer>();
		
		for (int x=v; x!=begin; x=edgeTo[x])
			path.push(x);
		path.push(begin);
		
		return path;
	}
	
	
	//深度优先搜索，循环实现
	private void dfsByLoop(int v) {
		Stack<Integer> stack = new Stack<Integer>();

		stack.push(v);
		Integer cur = null;
		while ((cur = stack.pop()) != null) {
			ArrayList<Integer> list = adj.get(cur);
			marked[cur] = true;
			for (int w:list) {
				if (!marked[w]) {
					edgeTo[w] = cur;
					stack.push(w);
				}
			}
		}
	}
	//深度优先搜索，递归实现
	private void dfs(int v) {
		ArrayList<Integer> adjv = adj.get(v);
		marked[v] = true;
		for (int w : adjv) {
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(w);
			}
		}
	}
	public Graph(In in) {
		this(in.readInt());
		int edges = in.readInt();

		int v, w;
		for (int i = 0; i < edges; i++) {
			v = in.readInt();
			w = in.readInt();
			addEdge(v, w);
		}
	}

	public int getVertexCount() {
		return vertices;
	}

	public int getEdgeCount() {
		return edges;
	}

	public void addEdge(int v, int w) {
		adj.get(v).add(w);
		adj.get(w).add(v);
		edges++;
	}

	public ArrayList<Integer> getAdjTable(int v) {
		if (v > -1 || v < adj.size() - 1) {
			return adj.get(v);
		}
		return null;
	}

	public String toString() {
		String s = vertices + " vertices, " + edges + " edges\n";
		for (int i = 0; i < vertices; ++i) {
			s += i + ": ";
			for (Integer w : adj.get(i))
				s += w + " ";
			s += "\n";
		}
		return s;
	}
	
	public void printPath() {
        for (int v = 0; v < getVertexCount(); v++) {
            if (hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", begin, v);
                for (int x : pathTo(v)) {
                    if (x == begin) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", begin, v);
            }

        }
	}
}
