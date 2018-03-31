package com.ggdsn.algorithms.alg4work;

import java.util.Random;

public class RandomizedQueueTest {
    private static final int ADD_RATE = 10;

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            queue.enqueue(random.nextInt(100));
        }
        for (int i = 0; i < 100; i++) {
            int opera = random.nextInt(100);
            if (opera >= 0 && opera < ADD_RATE) {
                int val = random.nextInt(100);
                System.out.println("in=" + val);
                queue.enqueue(val);

            } else if (opera >= ADD_RATE && opera < 80) {
                if (!queue.isEmpty()) {
                    System.out.println("out=" + queue.dequeue());
                }

            } else if (opera >= 80) {
                for (int j : queue) {
                    System.out.print(j + " ");
                }
                System.out.println(" end");
            }
        }
    }
}
