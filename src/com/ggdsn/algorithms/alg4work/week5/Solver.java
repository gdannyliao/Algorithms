package com.ggdsn.algorithms.alg4work.week5;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private int finalMove = Integer.MAX_VALUE;
    private List<Board> boards = new ArrayList<>();

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<MyBroad> pq = new MinPQ<>();
        pq.insert(new MyBroad(initial, null, 0));
        pq.insert(new MyBroad(initial.twin(), null, 0));
        while (!pq.isEmpty()) {
            MyBroad b = pq.delMin();
            Board board = b.board;
            if (board.isGoal()) {
                //TODO 2018.4.22 这里遇到第一个解就退出了，防止循环查找，但会导致solution()不正确
                finalMove = finalMove < b.move ? finalMove : b.move;
                boards.add(board);
                break;
            }
            Iterable<Board> neighbors = board.neighbors();
            for (Board n : neighbors) {
                if (n.equals(b.last)) continue;
                pq.insert(new MyBroad(n, board, b.move + 1));
            }
        }
    }       // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return finalMove != Integer.MAX_VALUE;
    }          // is the initial board solvable?

    public int moves() {
        return finalMove;
    }                   // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        return boards;
    }     // sequence of boards in a shortest solution; null if unsolvable

    private static class MyBroad implements Comparable<MyBroad> {
        Board board;
        int priority;
        int move;
        Board last;

        public MyBroad(Board board, Board last, int move) {
            this.board = board;
            this.last = last;
            this.move = move;
            priority = move + board.manhattan();
        }

        @Override
        public int compareTo(MyBroad o) {
            if (o == null)
                return 1;
            return priority - o.priority;
        }
    }
}