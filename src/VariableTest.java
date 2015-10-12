interface AA {
	int x = 0;
}

class B {
	int x = 1;
}

public class VariableTest extends B implements AA {
	public void pX() {
		System.out.println(AA.x);
		System.out.println(super.x);
	}

	public static void test() {
		new VariableTest().pX();
	}
}