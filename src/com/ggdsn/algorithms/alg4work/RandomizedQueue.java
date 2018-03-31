package com.ggdsn.algorithms.alg4work;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int MIN_SIZE = 8;
    @SuppressWarnings("unchecked")
    private Item[] array = (Item[]) new Object[MIN_SIZE];
    private int count = 0;
    /**
     * 此指针一定指向当前可插入的那个位
     */
    private int insertIdx = 0;

    public RandomizedQueue() {
    }            // construct an empty randomized queue

    public boolean isEmpty() {
        return count == 0;
    }// is the randomized queue empty?

    public int size() {
        return count;
    }                   // return the number of items on the randomized queue

    private void extend() {
        @SuppressWarnings("unchecked") Item[] newArr = (Item[]) new Object[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArr[i] = array[i];
            array[i] = null;
        }
        array = newArr;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (count == array.length) {
            //需要扩容
            extend();
        } else if (insertIdx == array.length) {
            //需要整理
            tidy();
        }

        array[insertIdx++] = item;
        count++;
    }          // add the item

    public Item dequeue() {
        int uni = uniform();
        Item item = array[uni];
        array[uni] = null;
        count--;
        if (count <= array.length / 4) {
            //当总数小于1/4时，应该缩表，但缩表前可能需要整理一下
            shrink();
        }
        return item;
    }                    // remove and return a random item

    private static int tidy(Object[] from, Object[] to) {
        int emptyIdx = 0;
        for (int i = 0; i < from.length; i++) {
            if (from[i] == null) continue;
            if (from[i] == to[emptyIdx]) {
                emptyIdx++;
                continue;
            }
            while (to[emptyIdx] != null) emptyIdx++;
            to[emptyIdx++] = from[i];
            from[i] = null;
        }
        return emptyIdx;
    }

    private void tidy() {
        insertIdx = tidy(array, array);
    }

    private void shrink() {
        if (array.length / 2 >= MIN_SIZE) {
            @SuppressWarnings("unchecked") Item[] newArr = (Item[]) new Object[array.length / 2];
            insertIdx = tidy(array, newArr);
            array = newArr;
        }
    }

    private int uniform() {
        if (count == 0) throw new NoSuchElementException();
        int uni;
        do {
            uni = StdRandom.uniform(array.length);
        } while (array[uni] == null);
        return uni;
    }

    public Item sample() {
        return array[uniform()];
    }                   // return a random item (but do not remove it)

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            /**
             * currentIdx始终指向第一个非空的item
             */
            private int currentIdx;

            @Override
            public boolean hasNext() {
                if (count < 1) return false;
                Item item = null;
                while (currentIdx < array.length && item == null) {
                    item = array[currentIdx++];
                }
                boolean has = item != null;
                //上面的循环中多判断了一次，这里要想办法补回
                if (has) currentIdx--;
                return item != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return array[currentIdx++];
            }
        };
    }         // return an independent iterator over items in random order
}
