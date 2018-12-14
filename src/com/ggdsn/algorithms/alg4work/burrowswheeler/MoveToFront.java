package com.ggdsn.algorithms.alg4work.burrowswheeler;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static int[] ascii = new int[256];
    private static final int CHAR_BITS = 8;

    private static void initArray() {
        for (int i = 0; i < ascii.length; i++) {
            ascii[i] = i;
        }
    }

    private static void moveToFront(int idx) {
        if (idx == 0) return;
        int c = ascii[idx];
        System.arraycopy(ascii, 0, ascii, 1, idx);
        ascii[0] = c;
    }

    private static int find(int c) {
        for (int i = 0; i < ascii.length; i++) {
            if (c == ascii[i]) return i;
        }
        return -1;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        initArray();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int out = find(c);
            BinaryStdOut.write(out, CHAR_BITS);
            moveToFront(c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        initArray();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int intChar = c & 0xff;
            int out = find(intChar);
            moveToFront(out);
            BinaryStdOut.write(out, CHAR_BITS);
            System.out.println("c=" + c + " intChar=" + intChar + " idx=" + out + " hex=" +
                    Integer.toHexString(intChar)
            );
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
//        if (args.length < 2) return;
//        if (args[1].endsWith(".txt")) {
//            try {
//                FileInputStream in = new FileInputStream(Alg4.getFile("burrows", args[1]));
//                System.setIn(in);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                return;
//            }
//        }

        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}