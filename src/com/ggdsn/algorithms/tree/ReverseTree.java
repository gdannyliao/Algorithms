package com.ggdsn.algorithms.tree;

import java.util.ArrayDeque;

public class ReverseTree {
	public Tree reverse(Tree node) {
		if (node == null || node.getLeft() == null && node.getRight() == null)
			return node;
		ArrayDeque<Tree> que = new ArrayDeque<Tree>();
		que.add(node);
		Tree cur, temp;
		while (!que.isEmpty()) {
			cur = que.remove();
			temp = cur.getRight();
			cur.setRight(cur.getLeft());
			cur.setLeft(temp);
			
			if (cur.hasLeft())
				que.add(cur.getLeft());
			if (cur.hasRight())
				que.add(cur.getRight());
				
		}
		return node;
	}
	
	public static void test() {
		//直链
		Tree<Character> tree = new Tree<Character>('a');
		for (int i = 1; i < 10; i++) {
			tree.add(new Tree<Character>((char) ('a' + i)));
		}
		
		ReverseTree r = new ReverseTree();
		r.reverse(tree);
		TraverseTree t = new TraverseTree();
		t.printFront(tree);
		//
		Tree<Character> tree1 = new Tree<Character>('g');
		tree1.add(new Tree<Character>('d'));
		tree1.add(new Tree<Character>('j'));
		tree1.add(new Tree<Character>('e'));
		tree1.add(new Tree<Character>('t'));
		tree1.add(new Tree<Character>('a'));
		r.reverse(tree1);
		t.printFront(tree1);
		
		r.reverse(null);
		r.reverse(new Tree<Character>('d'));
	}
}
