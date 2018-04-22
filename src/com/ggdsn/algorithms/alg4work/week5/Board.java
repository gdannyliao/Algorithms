package com.ggdsn.algorithms.alg4work.week5;

import java.util.ArrayList;

public class Board {
    private int[][] blocks;

    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();
        int length = blocks.length;
        this.blocks = new int[length][length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, length);
        }
        this.blocks = blocks;
    }         // construct a board from an n-by-n array of blocks

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return blocks.length;
    }              // board dimension n

    public int hamming() {
        int n = dimension();
        int dis = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = blocks[i][j];
                if (num == 0) continue;
                //求错误的块数量
                if (num != i * n + j + 1) dis++;
            }
        }
        return dis;
    }                // number of blocks out of place

    private int calManhattanDistance(int num, int x, int y) {
        int n = dimension();
        int targetX;
        int targetY;
        //右边界需要换到上一行
        if (num % n == 0) {
            targetX = num / n - 1;
            targetY = n - 1;
        } else {
            targetX = num / n;
            targetY = num % n - 1;
        }
        return Math.abs(x - targetX) + Math.abs(y - targetY);
    }

    public int manhattan() {
        int sum = 0;
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = blocks[i][j];
                if (num != 0) {
                    sum += calManhattanDistance(num, i, j);
                }
            }
        }
        return sum;
    }               // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        return hamming() == 0;
    }               // is this board the goal board?

    public Board twin() {
        int n = dimension();
        int[][] another = new int[n][n];
        int x = -1, y = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                another[i][j] = blocks[i][j];
                if (j < n - 1 && blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    x = i;
                    y = j;
                }
            }
        }
        if (x == -1) throw new IllegalArgumentException();
        exch(another, x, y, x, y + 1);
        return new Board(another);
    }                   // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;
        int n = dimension();
        if (that.dimension() != n) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }      // does this board equal y?

    public Iterable<Board> neighbors() {
        int n = dimension();
        int blankX = 0, blankY = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }

        /*
         * 1 2 4 8 分别代表左上右下
         */
        int calFlag = 0;
        int neighborCount = 0;
        if (blankX == 0) {
            if (blankY == 0) {
                calFlag = 12;
                neighborCount = 2;
            } else if (blankY == n - 1) {
                calFlag = 9;
                neighborCount = 2;
            } else {
                calFlag = 13;
                neighborCount = 3;
            }
        } else if (blankX == n - 1) {
            if (blankY == 0) {
                calFlag = 6;
                neighborCount = 2;
            } else if (blankY == n - 1) {
                calFlag = 3;
                neighborCount = 2;
            } else {
                calFlag = 7;
                neighborCount = 3;
            }
        } else {
            if (blankY == 0) {
                calFlag = 14;
                neighborCount = 3;
            } else if (blankY == n - 1) {
                calFlag = 11;
                neighborCount = 3;
            } else {
                calFlag = 15;
                neighborCount = 4;
            }
        }

        ArrayList<Board> boards = new ArrayList<>(neighborCount);
        for (int k = 0; k < neighborCount; k++) {
            int[][] neighbour = new int[n][n];
            for (int i = 0; i < n; i++) {
                System.arraycopy(blocks[i], 0, neighbour[i], 0, n);
            }
            //根据flag来判断上下左右是否可以交换
            switch (calFlag) {
                case 15:
                case 13:
                case 11:
                case 9:
                case 7:
                case 5:
                case 3:
                case 1:
                    exch(neighbour, blankX, blankY, blankX, blankY - 1);
                    calFlag -= 1;
                    break;
                case 14:
                case 10:
                case 6:
                case 2:
                    exch(neighbour, blankX, blankY, blankX - 1, blankY);
                    calFlag -= 2;
                    break;
                case 12:
                case 4:
                    exch(neighbour, blankX, blankY, blankX, blankY + 1);
                    calFlag -= 4;
                    break;
                case 8:
                    exch(neighbour, blankX, blankY, blankX + 1, blankY);
                    calFlag -= 8;
                    break;
            }
            boards.add(new Board(neighbour));
        }
        return boards;
    }    // all neighboring boards

    private void exch(int[][] board, int x1, int y1, int x2, int y2) {
        int tmp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = tmp;
    }

    private int bitCount(int num) {
        int count = 1;
        while (num / 10 > 0) {
            count++;
            num /= 10;
        }
        return count;
    }

    public String toString() {
        int n = dimension();
        StringBuilder sb = new StringBuilder(n + "\n");
        //这里进位了，所以减一
        int bitCount = bitCount(n * n - 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = blocks[i][j];
                int bitDelta = bitCount - bitCount(num);
                for (int k = 0; k < bitDelta; k++) {
                    sb.append(' ');
                }
                sb.append(num).append(' ');
            }
            sb.append("\n");
        }
        return sb.toString();
    }             // string representation of this board (in the output format specified below)
}