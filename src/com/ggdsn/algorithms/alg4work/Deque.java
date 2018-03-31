package com.ggdsn.algorithms.alg4work;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Node(Item item) {
            this.item = item;
        }

        Item item;
        Node next;
        Node last;
    }

    private Node first, last;
    private int count;

    public Deque() {
    }                         // construct an empty deque

    public boolean isEmpty() {
        return first == null;
    }             // is the deque empty?

    public int size() {
        return count;
    }                     // return the number of items on the deque

    private void checkNotNull(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    public void addFirst(Item item) {
        checkNotNull(item);
        Node node = new Node(item);
        if (first == null) {
            first = node;
            last = node;
        } else {
            node.next = first;
            first.last = node;
            first = node;
        }
    }       // add the item to the front

    public void addLast(Item item) {
        checkNotNull(item);
        if (first == null) {
            addFirst(item);
            return;
        }
        Node node = new Node(item);
        node.last = last;
        last.next = node;
        last = node;
        count++;
    }       // add the item to the end

    public Item removeFirst() {
        if (first == null) throw new NoSuchElementException();
        Node node = first;
        first = first.next;
        if (first == null) {
            //为空说明原本就只有一个值了
            last = null;
        } else {
            first.last = null;
            node.next = null;
        }
        count--;
        return node.item;
    }     // remove and return the item from the front

    public Item removeLast() {
        if (last == null) throw new NoSuchElementException();
        Node node = last;
        last = node.last;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
            node.last = null;
        }
        count--;
        return node.item;
    }           // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (current == null) throw new NoSuchElementException();
                Node node = current;
                current = current.next;
                return node.item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }      // return an iterator over items in order from front to end

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int val = random.nextInt(100);
            int opera = random.nextInt(4);
            switch (opera) {
                case 0:
                    System.out.println("add first=" + val);
                    deque.addFirst(val);
                    break;
                case 1:
                    System.out.println("add last=" + val);
                    deque.addLast(val);
                    break;
                case 2:
                    if (!deque.isEmpty()) {
                        Integer remove = deque.removeFirst();
                        System.out.println("remove first=" + remove);
                    }
                    break;
                case 3:
                    if (!deque.isEmpty()) {
                        Integer last = deque.removeLast();
                        System.out.println("remove last=" + last);
                    }
                    break;
            }
        }
        for (int i : deque) {
            System.out.print(i + " ");
        }
        System.out.println();
    }   // unit testing (optional)
}