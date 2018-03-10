package com.ggdsn.algorithms.sort;


import edu.princeton.cs.algs4.StdDraw;

public class SelectionSort extends Sort {

	@Override
	public void sort(Comparable[] arr) {
		/*
		 * 1选出数组中最小值
		 * 2使之与第一个交换
		 * 3对于剩下的数组重复上面两步
		 */
		for (int i=1; i<arr.length; i++) {
			int min = i;
			for (int j=i+1; j<arr.length; j++) {
				if (arr[min].compareTo(arr[j]) > 0) {
					min = j;
				}
			}
			Comparable temp = arr[i];
			arr[i] = arr[min];
			arr[min] = temp;
		}
		
	}
	
    public static void sort(double[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) { 
            int min = i;
            for (int j = i+1; j < N; j++)
                if (less(a[j], a[min])) min = j;
            show(a, i, min);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            exch(a, i, min);
        }
    }

    private static void show(double[] a, int i, int min) {
        StdDraw.setYscale(-a.length + i + 1, i);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for (int k = 0; k < i; k++)
            StdDraw.line(k, 0, k, a[k]*.6);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int k = i; k < a.length; k++)
            StdDraw.line(k, 0, k, a[k]*.6);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(min, 0, min, a[min]*.6);
    }

    private static boolean less(double v, double w) {
        return v < w;
    }

    private static void exch(double[] a, int i, int j) {
        double t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
