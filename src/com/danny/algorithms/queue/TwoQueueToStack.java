package com.danny.algorithms.queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class TwoQueueToStack<T> {
	Queue<T> que1;
	Queue<T> que2;
	
	public TwoQueueToStack() {
		que1 = new ArrayDeque<T>();
		que2 = new ArrayDeque<T>();
	}
	
	public void push(T obj) {
		if (obj == null)
			return;
		
		if (que1.isEmpty() && que2.isEmpty())
			que1.add(obj);
		else if (que1.isEmpty() && !que2.isEmpty())
			que2.add(obj);
		else if (que2.isEmpty() && !que1.isEmpty())
			que1.add(obj);
	}
	
	public T pull() {
		if (que1.isEmpty() && que2.isEmpty())
			return null;
		
		if (que1.isEmpty() && !que2.isEmpty()) {
			int len = que2.size()-1;
			for (int i=0; i<len; i++)
				que1.add(que2.poll());
			return que2.poll();
		}
		
		if (que2.isEmpty() && !que1.isEmpty()) {
			int len = que1.size()-1;
			for (int i=0; i<len; i++)
				que2.add(que1.poll());
			return que1.poll();
		}
		return null;
	}
	
	public static void testTwoQueueToStack() {
		TwoQueueToStack<Integer> stack = new TwoQueueToStack<Integer>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		System.out.println(stack.pull());
		stack.push(4);
		System.out.println(stack.pull());
		stack.push(5);
		System.out.println(stack.pull());
		System.out.println(stack.pull());
		System.out.println(stack.pull());
		System.out.println(stack.pull());
		stack.push(6);
		System.out.println(stack.pull());
		stack.push(null);
		System.out.println(stack.pull());

	}
	
}
