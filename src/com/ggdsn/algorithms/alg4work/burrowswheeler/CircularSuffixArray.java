package com.ggdsn.algorithms.alg4work.burrowswheeler;

import java.util.Arrays;

public class CircularSuffixArray {
    private int length = 0;
    private int[] idx;
    private static final int R = 256;

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        length = s.length();

        idx = new int[length];
        int[] aux = new int[length];

        for (int i = 0; i < length; i++) {
            idx[i] = i;
        }
        //j是当前计算的列，offset是当前行的偏移量，也是行号。
        //当j+offset>=length时，对应字母的原始列是j+offset-length。否则就是j+offset
        for (int j = length - 1; j >= 0; j--) {
            int[] count = new int[R + 1];
            for (int i = 0; i < length; i++) {
                count[s.charAt(i) + 1]++;
            }

            for (int i = 0; i < R; i++) {
                //计算每个字符的起始位置
                count[i + 1] += count[i];
            }

            for (int offset = 0; offset < length; offset++) {
                int actualI = offset;
                //找到实际处理的行的位置, 但这样会破坏稳定性
                for (int i = 0; i < length; i++) {
                    if (idx[i] == offset) {
                        actualI = i;
                        break;
                    }
                }

                int rawCol = j + actualI;
                if (rawCol >= length)
                    rawCol -= length;
//                System.out.println("j=" + j + " offset=" + offset + " rawCol=" + rawCol + " char=" + s.charAt(rawCol));
                aux[count[s.charAt(rawCol)]++] = actualI;
            }
//            System.out.println(Arrays.toString(aux));
            System.arraycopy(aux, 0, idx, 0, length);
            //TODO 2018.12.14 这个算法不是稳定的，所以解压时无法还原
        }
        System.out.println("idx=" + Arrays.toString(idx));
    }   // circular suffix array of s

    public int length() {
        return length;
    } // length of s

    public int index(int i) {
        return idx[i];
    }                // returns index of ith sorted suffix

    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i));
        }
    } // unit testing (required)
}