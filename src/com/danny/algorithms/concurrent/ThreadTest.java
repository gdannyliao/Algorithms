package com.danny.algorithms.concurrent;

public class ThreadTest {
	private static boolean bShouldMain = false;

	public static void test() {
		/*
		 * new Thread(){ public void run() { for(int i=0;i<50;i++) { for(int
		 * j=0;j<10;j++) { System.out.println("i="+ i + ",j=" + j); ￼} } }
		 * }.start();
		 */
		// final String str = newString("");
		
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					synchronized (ThreadTest.class) {
						if (bShouldMain) {
							try {
								ThreadTest.class.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						for (int j = 0; j < 10; j++) {
							System.out.println(Thread.currentThread().getName()
									+ "i=" + i + ",j=" + j);
						}
						bShouldMain = true;
						ThreadTest.class.notify();
					}
				}
			}
		}).start();

		for (int i = 0; i < 50; i++) {
			synchronized (ThreadTest.class) {
				if (!bShouldMain) {
					try {
						ThreadTest.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 0; j < 5; j++) {
					System.out.println(Thread.currentThread().getName() + "i="
							+ i + ",j=" + j);
				}
				bShouldMain = false;
				ThreadTest.class.notify();
			}
		}
	}
}