package com.danny.algorithms.search;

public class FindMinInRotatedArray {
	int find(int[] arr) {
		if (arr == null || arr.length == 0)
			throw new IllegalArgumentException();

		int left = 0;
		int right = arr.length - 1;

		int mid = 0;

		while (arr[left] >= arr[right]) {
			if (left + 1 == right)
				return arr[mid];
			mid = (left + right) / 2;
			if (arr[left] == arr[right] && arr[left] == arr[mid])
				return findInSame(arr);
			
			if (arr[left] <= arr[mid])
				left = mid;
			else if (arr[mid] <= arr[right])
				right = mid;
		}
		return arr[mid];
	}
	private int findInSame(int[] arr) {
		int min = arr[0];
		for (int i=1; i<arr.length; i++) {
			if (arr[i] < min)
				min = arr[i];
		}
		return min;
	}
	public static void test() {
		FindMinInRotatedArray findMin = new FindMinInRotatedArray();
		int[] arr = {3,4,5,1,2};
		System.out.println(findMin.find(arr));
		int[] arr2 = {1,0,1,1,1};
		System.out.println(findMin.find(arr2));
		int[] arr3 = {3,3,4,4,4,5,5,5,1,1,1,2,2,2,3};
		System.out.println(findMin.find(arr3));

	}
}