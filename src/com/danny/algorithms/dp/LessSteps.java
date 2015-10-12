package com.danny.algorithms.dp;

/**
 * 一只青蛙一次可以跳1阶或2阶，求跳到第n阶最小步为？
 * @author jkl
 *
 */
public class LessSteps {
	/**
	 * 求至少需要多少步
	 */
	public int getSteps(int n) {
		if (n < 0)
			return -1;
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		
		int lastD = 1;
		int lastLastD = 0;
		int curD = 0;
		for (int i=2; i<=n; i++) {
			if (lastD > lastLastD) {
				curD = lastLastD + 1;
			} else {
				curD = lastD + 1;
			}
			lastLastD = lastD;
			lastD = curD;
		}
		
		return curD;
	}
	
	/**
	 * 求总共需要的步数
	 * @param n
	 * @return
	 */
	public long steps(int n) {
		if (n < 0)
			return -1;
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		if (n == 2)
			return 2;
		long last = 2, lastLast = 1, cur = 3;
		for (int i=3; i<=n; i++) {
			cur = last + lastLast;
			lastLast = last;
			last = cur;
		}
		return cur;
	}
	public static void test() {
		LessSteps step = new LessSteps();
		for (int i=0; i<10; i++)
			System.out.println("jump to " + i + " need at least steps:" + step.getSteps(i));
		for (int i=0; i<10; i++)
			System.out.println("jump to " + i + " has steps:" + step.steps(i));
		System.out.println(step.steps(100));
	}
}
