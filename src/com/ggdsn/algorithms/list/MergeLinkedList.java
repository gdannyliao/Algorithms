package com.ggdsn.algorithms.list;

public class MergeLinkedList {
	public Node merge(Node one, Node two) {
		if (one == null && two == null)
			return null;
		if (one == null)
			return two;
		if (two == null)
			return one;
		
		Node temp, head;
		if (one.v > two.v) {
			head = two;
			two = two.next;
		} else {
			head = one;
			one = one.next;
		}
		temp = head;
		while (one != null || two != null) {
			if (one == null) {
				temp.next = two;
				temp = two;
				two = two.next;
			} else if (two == null) {
				temp.next = one;
				temp = one;
				one = one.next;
			} else if (one.v > two.v) {
				temp.next = two;
				temp = two;
				two = two.next;
			} else {
				temp.next = one;
				temp = one;
				one = one.next;
			}
		}
		return head;
	}
	
	public static void test() {
		Node list1 = new Node(0);
		Node temp = list1;
		for (int i=0; i<10; i += 2) {
			temp.next = new Node(i);
			temp = temp.next;
		}
		
		Node list2 = new Node(0);
		temp = list2;
		for (int i=1;i<7; i+=2) {
			temp.next = new Node(i);
			temp = temp.next;
		}
		MergeLinkedList m = new MergeLinkedList();
		temp = m.merge(list1, list2);
		Node.print(temp);
		Node.print(m.merge(null, null));
		Node.print(m.merge(null, list2));
		Node.print(m.merge(new Node(1), null));
	}
}
