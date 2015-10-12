/**
 * 超类先完成构造
 * @author jkl
 *
 */
public class Root {
	static {
		System.out.println(1);
	}
	{
		System.out.println(2);
	}
	public Root() {
		System.out.println(3);
	}
}

class Leaf extends Root {
	static {
		System.out.println(4);
	}
	{
		System.out.println(5);
	}
	public Leaf() {
		System.out.println(6);
	}
}
