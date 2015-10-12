import com.danny.algorithms.bit.Print1ToN;
import com.danny.algorithms.sort.SelectionSort;
import com.danny.algorithms.sort.Sort;

import edu.princeton.cs.algs4.Counter;
import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class Launcher {

	public static void main(String[] args) {
		Print1ToN.test();
//		Try.test();
//		AccrossAccess.test();
//		VariableTest.test();
//		IPStringToInt.test();
//		LargestIncreasingSubArrayLength.test();
//		new Leaf();
//		LongestCommonSubsequence.test();
//		ThreadTest.test();
	}

	static void testInsertionSort() {
		StdDraw.setCanvasSize(180, 640);
		StdDraw.setXscale(-1, Sort.chars.length);
		StdDraw.setPenRadius(.003);
		double[] a = new double[Sort.chars.length];
		for (int i = 0; i < Sort.chars.length; i++)
			a[i] = Math.random();
		SelectionSort.sort(a);
	}

	static void testHint() {
		double xlo = 0.2;
		double xhi = 0.5;
		double ylo = 0.5;
		double yhi = 0.6;

		int T = 10000;
		Interval1D xinter = new Interval1D(xlo, xhi);
		Interval1D yinter = new Interval1D(ylo, yhi);
		Interval2D box = new Interval2D(xinter, yinter);
		box.draw();

		Counter c = new Counter("hits");
		for (int t = 0; t < T; t++) {
			double x = Math.random();
			double y = Math.random();
			Point2D p = new Point2D(x, y);
			if (box.contains(p))
				c.increment();
			else
				p.draw();
		}
		StdOut.println(c);
		StdOut.println(box.area());
	}

}

class View {
	int i = 0;

	void newView(View v) {
		v = new View();
		v.i = 1;
	}
}