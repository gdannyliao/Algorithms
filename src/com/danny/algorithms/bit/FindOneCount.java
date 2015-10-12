package com.danny.algorithms.bit;

public class FindOneCount {
	public int getOneCount(int num) {
		int mask = 1, count = 0;
		boolean isNegative = false;
		if (num < 0) {
			num = ~num;
			isNegative = true;
		}
		while (num != 0) {
			int result = num&mask;
			if ((result) != 0) {
				count++;
			}
			num >>= 1;
		}
		if (isNegative)
			count = 32 - count;
		return count;
	}
	
	
	public int getOneCountLess(int num) {
		int count = 0;
		while (num != 0) {
			count++;
			num = (num-1) & num;
		}
		return count;
	}
	public static void test() {
		FindOneCount find = new FindOneCount();
		for (int i=-5; i<10; i++)
			System.out.println(i + " has " + find.getOneCountLess(i) + " one in it");
		System.out.println(Integer.MIN_VALUE + " has " + find.getOneCountLess(0x80000000) + " one in it");
		System.out.println(Integer.MAX_VALUE + " has " + find.getOneCountLess(0x7FFFFFFF) + " one in it");

	}
}
