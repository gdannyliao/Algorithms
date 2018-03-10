package com.ggdsn.algorithms.concurrent;

public class AccrossAccess {
	private boolean isQuit;
	private Object lock = new Object();
	private boolean inMain = true;
	
	public void run() {
		int times = 0;
		int max = 5000;
		int curTime = 0;
		while (true) {
			while (!isQuit && times < max) {
				synchronized (lock) {
					if (!inMain) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					++times;
					++curTime;
					if (curTime == 99) {
						curTime = 0;
						System.out.println("main times: " + ++times);
						inMain = false;
						lock.notify();
					}
				}
			}
			break;
		}
	}
	public class Thread2 extends Thread {
		
		@Override
		public void run() {
			int times = 0;
			int max = 500;
			int curTime = 0;
			while (true) {
				while (times < max) {
					synchronized (lock) {
						if (inMain) {
							try {
								lock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						++times;
						++curTime;
						if (curTime == 9) {
							curTime = 0;
							inMain = true;
							System.out.println("thread 2 times:" + ++times);
							lock.notify();
						}
					}
				}
				break;
			}
		}
	}
	
	public static void test() {
		AccrossAccess a = new AccrossAccess();
		Thread2 t = a.new Thread2();
		t.start();
		a.run();
	}
	

}
