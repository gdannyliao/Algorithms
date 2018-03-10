package com.ggdsn.algorithms.list;


public class ReverseLinkedList {
	Node reverse(Node head) {
		if (head == null || head.next == null)
			return head;
		Node one = head;
		Node two = head.next;
		one.next = null;
		if (two.next == null) {
			two.next = one;
			return two;
		}
		else {
			Node three = two.next;
			while (three != null) {
				two.next = one;
				one = two;
				two = three;
				three = three.next;
			}
			two.next = one;
			return two;
		}
	}
	Node last;
	Node reverseRecursive(Node head) {
		if (head == null || head.next == null) {
			last = head;
			return head;
		}
		Node next = reverseRecursive(head.next);
		next.next = head;
		head.next = null;
		return head;
	}

	public static void test() {
		Node node, last, head;
		last = new Node(-1);
		head = last;
		for (int i=0; i<5; i++) {
			node = new Node(i);
			last.next = node;
			last = node;
		}
		
		ReverseLinkedList r = new ReverseLinkedList();
		Node headR = r.reverse(head);
		Node.print(headR);
		
		Node.print(r.reverse(null));
		Node.print(r.reverse(new Node(0)));
		
		r.reverseRecursive(headR);
		Node.print(r.last);
	}

}


