package com.ggdsn.algorithms.array;

/**
 * 二维数组中查找
 * @author jkl
 *
 */
public class FindInMatrix {
	public static int[] find(int[][] matrix, int num) {
		if (matrix == null || matrix.length < 1 || matrix[0] == null || matrix[0].length < 1)
			return null;
		
		int rowLen = matrix[0].length;
		int columnLen = matrix.length;
		
		int x, y;
		x = 0;
		y = rowLen - 1;
		
		while (x < columnLen && y >= 0) {
			int cur = matrix[x][y];
			if (matrix[x][y] == num) {
				return new int[]{x, y};
			} else if (matrix[x][y] > num) {
				--y;
			} else if (matrix[x][y] < num) {
				++x;
			}
		}
		
		return null;
	}
	
	public void testFindNormal(int[][] matrix, int num) {

		int[] find = find(matrix, num);
		if (find != null)
			System.out.printf("find x:%d, y:%d\n", find[0], find[1]);
		else System.out.println("find nothing");
	}
	public static void testFind() {
		FindInMatrix find = new FindInMatrix();
		int[][] matrix = new int[4][];
		matrix[0] = new int[] {1,2,8,9};
		matrix[1] = new int[] {2,4,9,12};
		matrix[2] = new int[] {4,7,10,13};
		matrix[3] = new int[] {6,8,11,15};
		
		find.testFindNormal(matrix, 15);
		find.testFindNormal(matrix, 2);
		find.testFindNormal(matrix, 0);
		find.testFindNormal(matrix, -1);
		find.testFindNormal(matrix, 14);
		
		find.testFindNormal(null, 2);
		find.testFindNormal(new int[5][], 0);
		
	}
}
