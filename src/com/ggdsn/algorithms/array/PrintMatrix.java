package com.ggdsn.algorithms.array;

/**
 * 按圈打印矩阵，按 → ⬆ ⬇ ⬅ 的方向
 * 
 * 二维数组，int[][] 第一个代表x, 第二个代表y 表示x行y列。如x=[2,3]表示 0 0 0 0 0 0 0 0 0 0 0 0 0 x 0 0
 * 0 0 0 0
 * 
 * @author jkl
 * 
 */
public class PrintMatrix {
	public void print(int[][] matrix) {
		if (matrix == null || matrix.length < 1 || matrix[0].length < 1)
			return;
		//算出停止的地方，即中轴。为短边长的一半
		int border = matrix.length < matrix[0].length ? matrix.length
				: matrix[0].length;
		border >>= 1;
		int offset = 0;
		//每次循环打印一圈
		while (offset <= border) {
			printCircle(matrix, offset);
			offset++;
		}
	}

	private void printCircle(int[][] matrix, int offset) {
		int start = offset;
		int endx = matrix.length - offset - 1;
		int endy = matrix[0].length - offset - 1;
		int x = start;
		int y = start;
		while (y <= endy) {
			System.out.print(matrix[x][y]);
			y++;
		}
		y--;
		x++;
		while (x <= endx) {
			System.out.println(matrix[x][y]);
			x++;
		}
		x--;
		y--;
		while (y >= start && x > start) {
			System.out.print(matrix[x][y]);
			y--;
		}
		y++;
		x--;
		while (x >= start+1 && y > start) {
			System.out.println(matrix[x][y]);
			x--;
		}
	}

	public static void test() {
		int[][] matrix = new int[3][4];
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				matrix[i][j] = i+j;
			}
		}
		PrintMatrix p = new PrintMatrix();
		p.print(matrix);
	}
}
