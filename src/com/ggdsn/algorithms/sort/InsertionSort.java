package com.ggdsn.algorithms.sort;



public class InsertionSort extends Sort{

    
	@Override
	public void sort(Comparable[] arr) {
		for (int i=1; i<arr.length; i++) {
			for (int j=i; j>0 && Sort.less(arr[j], arr[j-1]); j--) {
				Sort.exch(arr, j, j-1);
			}
		}
	}
}
