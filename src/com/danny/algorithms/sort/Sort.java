package com.danny.algorithms.sort;

public abstract class Sort {
	public  abstract void sort(Comparable[] arr);
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	public static void exch(Comparable[] arr, int i, int j) {
		Comparable a = arr[i];
		arr[i] = arr[j];
		arr[j] = a;
	}
	
	public static void print(Comparable[] arr) {
		for (int i=0; i<arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.println();
	}
	
	public static boolean isSorted(Comparable[] arr) {
		for (int i=1; i<arr.length; i++) {
			if (less(arr[i], arr[i-1]))
				return false;
		}
		return true;
	}
	
	public static final Character[] chars = {0, 
		'S', 'O', 'R','T', 'E', 'X', 'A', 'M', 'P', 'L', 'E'
	};
}
