package com.ggdsn.algorithms.bit;

public class Power {
	public double power(double base, int exp) {
		if (base == 0.0) {
			if (exp < 0)
				throw new ArithmeticException("base :" + base + " exp:" + exp);
			else
				return 0;
			//FIXME exp为0 的情况包含在getPower中
		} else if (exp == 0)
			return 1;
		else if (exp > 0) {
			return getPower(base, exp);
		} else if (exp < 0) {
			double midResult = getPower(base, exp);
			return 1 / midResult;
		}
		return 0.0;
	}

	private double getPower(double base, int exp) {
		double result = 1.0;
		//FIXME 最小负数没有绝对值
		exp = abs(exp);
		for (int i = 0; i < exp; i++) {
			result *= base;
		}
		return result;
	}
	
	private int abs(int num) {
		//如果是大于0的值，绝对值就是它自己
		if (num >= 0)
			return num;
		//如果是32位补码能表示的最小值，则没有对应的绝对值，直接返回
		if (num == 0x80000000)
			return num;
		//对于普通负值，取反后加一即为所求绝对值
		int anti = ~num;
		return ++anti;
	}

	public static void test() {
		Power p = new Power();
		for (int i = -3; i < 3; i++)
			for (int j = -3; j < 3; j++) {
				try {
					System.out.println("base: " + i + " exp:" + j + " result:" + p.power(i, j));
				} catch (ArithmeticException e) {
					e.printStackTrace();
				}
			}
	}
}
