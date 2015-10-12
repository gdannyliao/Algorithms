package com.danny.algorithms.list;


public class Node {
	public Node(int v) {
		this.v = v;
	}
	int v;
	Node next;
	@Override
	public String toString() {
		return String.valueOf(v);
	}
	public static void print(Node node) {
		System.out.print("linked list:");
		if (node == null || node.next == null)
			System.out.println(node);
		while (node != null) {
			System.out.print(node.v);
			node = node.next;
		}
		System.out.println();
	}
}
