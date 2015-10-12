
public class Try {
	public int TryCatch() {
		try {
			System.out.println("1");
			return 1;
		} catch (Exception e) {
			System.out.println(4);
		} finally {
			System.out.println("3");
			return 3;
		}
	}
	
	public int TryCatched() {
		int x = 0;
		try {
			x = 1;
			return x;
		} catch (IllegalArgumentException e) {
			x = 2;
			return x;
		} finally {
			x = 3;
		}
	}
	public static void test() {
		Try t = new Try();
		int i = t.TryCatch();
		System.out.println(i);
		System.out.println();
		
		System.out.println(t.TryCatched());
	}
}
