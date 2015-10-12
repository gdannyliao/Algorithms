package com.danny.algorithms.tree;

import edu.princeton.cs.algs4.Stack;

public class TraverseTree {
	Stack<Tree> stack = new Stack<Tree>();

	public void printFront(Tree node) {
		if (node == null)
			return;

		stack.push(node);
		while (!stack.isEmpty()) {
			node = stack.pop();
			System.out.print(node);
			if (node.getRight() != null) {
				stack.push(node.getRight());
			}
			if (node.getLeft() != null) {
				stack.push(node.getLeft());
			}
		}
		System.out.println();
	}

	public static void test() {
		//直链
		Tree<Character> tree = new Tree<Character>('a');
		for (int i = 1; i < 10; i++) {
			tree.add(new Tree<Character>((char) ('a' + i)));
		}

		TraverseTree traverse = new TraverseTree();
		traverse.printFront(tree);
		//
		Tree<Character> tree1 = new Tree<Character>('g');
		tree1.add(new Tree<Character>('d'));
		tree1.add(new Tree<Character>('j'));
		tree1.add(new Tree<Character>('e'));
		tree1.add(new Tree<Character>('t'));
		tree1.add(new Tree<Character>('a'));
		
		traverse.printFront(tree1);
	}
}

