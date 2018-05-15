package com.ggdsn.algorithms.tree;

public class Tree<T extends Comparable> {
	T value;
	private Tree<T> right;
	private Tree<T> left;

	public Tree(T value) {
		this.value = value;
	}

	public void add(Tree<T> node) {
		if (node == null) {
			return;
		}
		int result = node.value.compareTo(value);
		if (result > 0) {
			if (right == null)
				right = node;
			else
				right.add(node);
		} else if (result < 0) {
			if (left == null)
				left = node;
			else
				left.add(node);
		}
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public Tree<T> getRight() {
		return right;
	}

	public Tree<T> getLeft() {
		return left;
	}
	public boolean hasLeft() {
		return left != null;
	}
	public boolean hasRight() {
		return right != null;
	}

	public void setLeft(Tree<T> left) {
		this.left = left;
	}
	public void setRight(Tree<T> right) {
		this.right = right;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Tree)
				return value.equals(((Tree<T>) obj).value);
		}
		return false;
	}
}
