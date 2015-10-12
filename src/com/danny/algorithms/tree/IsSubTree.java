package com.danny.algorithms.tree;


public class IsSubTree {
	public boolean isSub(Tree node, Tree x) {
		if (node == null && x == null)
			return true;
		if (node == null)
			return false;
		if (x == null)
			return false;
		
		if (x.equals(node)) {
			boolean isSub = true;
			if (x.getLeft() != null) {
				isSub = isSub(node.getLeft(), x.getLeft());
			}
			else if (x.getRight() != null) {
				isSub(node.getRight(), x.getRight());
			}
		} else return isSub(node.getLeft(), x) || isSub(node.getRight(), x);
		return false;
	}
	
	public static void test() {
		//直链
		Tree<Character> tree = new Tree<Character>('a');
		for (int i = 1; i < 10; i++) {
			tree.add(new Tree<Character>((char) ('a' + i)));
		}
		
		Tree<Character> sub1 = new Tree<Character>('b');
		sub1.add(new Tree<Character>('c'));
		sub1.add(new Tree<Character>('d'));
		
		IsSubTree is = new IsSubTree();
		System.out.println("is sub tree:" + is.isSub(tree, sub1));
		//
		Tree<Character> tree1 = new Tree<Character>('g');
		tree1.add(new Tree<Character>('d'));
		tree1.add(new Tree<Character>('j'));
		tree1.add(new Tree<Character>('e'));
		tree1.add(new Tree<Character>('t'));
		tree1.add(new Tree<Character>('a'));
	}
}
