package com.danny.algorithms.dp;

/**
 * 求两个字符串之间的最长公共子序列
 * @author jkl
 *
 */
public class LongestCommonSubsequence {
	/**
	 * 根据状态转移方程，构造c[][]导向数组
	 * @param a
	 * @param b
	 * @return
	 */
	int[][] getRotationMatrix(String a, String b) {
		int[][] mat = new int[a.length() + 1][b.length() + 1];
		int i = 1;
		for (; i < mat.length; i++) {
			for (int j=1; j < mat[0].length; ++j) {
				char ca = a.charAt(i - 1);
				char cb = b.charAt(j - 1);
				//根据状态转移方程，if x == y ,则 c[i][j] = c[i-1][j-1]
				if (ca == cb) {
					mat[i][j] = mat[i-1][j-1]+1;
				} else if (mat[i-1][j] > mat[i][j-1]) {
					//若 x!=y, 则c[i][j] = max{c[i-1][j], c[i][j-1]}
					mat[i][j] = mat[i-1][j];
				} else {
					mat[i][j] = mat[i][j-1];
				}
			}
		}
		return mat;
	}
	/**
	 * 根据所求的导向数组，求得公共子序列。
	 * @param a
	 * @param b
	 * @return
	 */
	char[] lcs(String a, String b) {
		if (a == null || b == null || a.length() < 1 || b.length() < 1)
			return null;

		int[][] mat = getRotationMatrix(a, b);
		//求得最长子序列的长度，最长子序列的长度为最后一个值的长度
		int leng = mat[mat.length - 1][mat[0].length - 1];

		char[] result = new char[leng];
		int i = mat.length - 1;
		int j = mat[0].length - 1;
		int current = leng - 1;

		while (i > 0) {
			while (j > 0) {
				//如果左，上，左上三个值相同，说明此时所指是一个公共值，添加到返回结果中。同时向左上走一步
				if (mat[i - 1][j - 1] == mat[i - 1][j]
						&& mat[i - 1][j - 1] == mat[i][j - 1]) {
					result[current] = a.charAt(i-1);
					--i;--j;
					--current;
				} else {
					//若结果不同，则往大的值方向走
					if (mat[i-1][j] > mat[i][j-1]) {
						--i;
					} else {
						//若左和上的值一致，优先往左走
						--j;
					}
				}
				if (current < 0)
					return result;
			}
		}
		return result;
	}
	public static void test() {
		LongestCommonSubsequence l = new LongestCommonSubsequence();
		char[] lcs = l.lcs("abcbdab", "bdcaba");
		System.out.println(lcs);
	}
}
