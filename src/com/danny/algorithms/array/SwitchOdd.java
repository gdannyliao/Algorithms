package com.danny.algorithms.array;

public class SwitchOdd {
	public int[] switchOdd(int[] arr) {
		if (arr == null || arr.length < 2)
			return arr;
		
		int font = 0, tail = arr.length - 1;
		
		while (font < tail) {
			if (isOdd(arr[font]) && !isOdd(arr[tail])) {
				switchNum(arr, font, tail);
				font++; tail--;
			} else if (!isOdd(arr[font]))
				font++;
			else if (isOdd(arr[tail]))
				tail--;
		}
		return arr;
	}
	void switchNum(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	boolean isOdd(int num) {
		return (num & 0x1) == 0 ? true : false;
	}
	
	public static void test() {
		SwitchOdd s = new SwitchOdd();
		System.out.println(s.switchOdd(null));
		int[] arr = {1};
		int[] result = s.switchOdd(arr);
		print(result);
		
		arr = new int[] {1,2};
		result = s.switchOdd(arr);
		print(result);
		
		arr = new int[] {2,1};
		print(s.switchOdd(arr));
		
		arr = new int[] {3,1,5,4,2,6};
		print(s.switchOdd(arr));
		arr = new int[] {2,4,6,1,3,5};
		print(s.switchOdd(arr));
	}
	
	public static void print(int[] result) {
		for (int i: result)
			System.out.print(i);
		System.out.println();
	}
}
