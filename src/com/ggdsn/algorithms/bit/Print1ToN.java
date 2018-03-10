package com.ggdsn.algorithms.bit;


public class Print1ToN {
	public void print(int n) {
		if (n < 1) {
			return;
		}
		
		short[] number = new short[n];
		for (int i=0; i<number.length; i++)
			number[i] = 0;
		
		while (increase(number)) {
			printNumber(number);
		}
	}

	private void printNumber(short[] number) {
		boolean isHead = true;
		for (short c : number) {
			if (c != 0)
				isHead = false;
			if (!isHead)
				System.out.print(c);
		}
		System.out.println();
	}

	private boolean increase(short[] number) {
		if (number == null || number.length == 0)
			return false;
		int last = number.length - 1;
		while (last >= 0) {
			number[last]++;
			if (number[last] == 10) {
				number[last] = 0;
				last--;
			} else break;
		}
		
		if (last < 0 && number[0] == 0)
			return false;
		else return true;
	}
	
	void print1ToMax(int n) {
		if (n <= 0)
			return;
		short[] nums = new short[n];
		//对于每一个十位，都要从0-9依次打印它的个位，以此类推
		for (short i=0; i<10; i++) {
			nums[0] = i;
			printRecursively(nums, n, 0);
		}
	}
	void printRecursively(short[] nums, int length, int index) {
		if (length-1 == index) {
			printNumber(nums);
			return;
		}
		for (short i=0; i<10; i++) {
			nums[index+1] = i;
			printRecursively(nums, length, index+1);
		}
	}
	public static void test() {
		Print1ToN p = new Print1ToN();
//		p.print(1);
		p.print1ToMax(3);
//		p.print(3);
	}
}
