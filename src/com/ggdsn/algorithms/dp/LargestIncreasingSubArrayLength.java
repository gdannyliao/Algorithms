package com.ggdsn.algorithms.dp;
/**
 * 
 最大递增子序列长度
 
 对于每个值i，判断它是不是比前一个(j)大，如果是，则它的d(i) = d(j) +1
    如果不是，则它的d(i) = 1
 
 通过依次比较，求出长度最大的那个
 */

public class LargestIncreasingSubArrayLength {
	public static int getLargest(int[] arr) {
		if (arr == null || arr.length == 0)
			return 0;
		int len = 1;
		int[] d = new int[arr.length];
		d[0] = 1;
		
		for (int i=1; i<arr.length; ++i) {
			//默认d[i]置为1
			d[i] = 1;
			for (int j=0; j<i; j++) {
				int a = arr[i];
				int b = arr[j];
				//找到升序的，而且之前的最大值+1大于当前数的最大值的(max(1, d[j]+1})
				//这样可以忽略部分下降的情况
				if (a >= b && d[j]+1 > d[i])
					d[i] = d[j] + 1;
				if (d[i] > len)
					len = d[i];
			}
		}
		
		return len;
	}
	
	private static void print(int[] arr) {
		int len = getLargest(arr);
		System.out.println("array:" + arr + " lis is:" + len);
	}
	
	public static void test() {
		print(new int[]{4,5,0,7,-1,8,3,2});
	}
}
