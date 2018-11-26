package com.ggdsn.algorithms.alg4work.boggle;

import java.util.Collection;
import java.util.HashSet;

/**
 * 对字典构建单词查找树，然后遍历所有格子，看看单词查找树中有没有对应的单词即可吧？
 */
public class BoggleSolver {
    private ThreeWayTries tries;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        tries = new ThreeWayTries();
        for (String s : dictionary) {
            tries.put(s, s);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Collection<String> res = new HashSet<>();
        boolean[][] usedMatrix = new boolean[board.rows()][board.cols()];
        StringBuilder prefix = new StringBuilder();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                find(board, i, j, prefix, output, usedMatrix, res);
            }
        }
        return res;
    }

    /**
     * 这个方法仅执行递归操作，不做起点判断。
     */
    private void find(BoggleBoard board, int x, int y, StringBuilder compareString,
                      StringBuilder outputString, boolean[][] usedMatrix, Collection<String> result) {
        if (!inBoard(board, x, y)) return;
        if (usedMatrix[x][y]) return;

        char letter = board.getLetter(x, y);
        compareString.append(letter);
        outputString.append(letter);
        if (letter == 'Q')
            outputString.append('U');
        if (tries.hasSection(compareString)) {
            if (tries.hasValue(compareString) && outputString.length() > 2) {
                result.add(outputString.toString());
            }
            usedMatrix[x][y] = true;
            find(board, x - 1, y, compareString, outputString, usedMatrix, result);
            find(board, x - 1, y - 1, compareString, outputString, usedMatrix, result);
            find(board, x - 1, y + 1, compareString, outputString, usedMatrix, result);
            find(board, x, y - 1, compareString, outputString, usedMatrix, result);
            find(board, x, y + 1, compareString, outputString, usedMatrix, result);
            find(board, x + 1, y, compareString, outputString, usedMatrix, result);
            find(board, x + 1, y - 1, compareString, outputString, usedMatrix, result);
            find(board, x + 1, y + 1, compareString, outputString, usedMatrix, result);
            usedMatrix[x][y] = false;
        }
        compareString.deleteCharAt(compareString.length() - 1);
        outputString.deleteCharAt(outputString.length() - 1);
        if (letter == 'Q')
            outputString.deleteCharAt(outputString.length() - 1);
    }

    private boolean inBoard(BoggleBoard board, int x, int y) {
        return x >= 0 && y >= 0 && x < board.rows() && y < board.cols();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!tries.hasValue(word)) return 0;
        int length = word.length();
        if (word.contains("QU")) length--;
        if (length < 3) return 0;
        else if (length < 5) return 1;
        else if (length < 6) return 2;
        else if (length < 7) return 3;
        else if (length < 8) return 5;
        else return 11;
    }

//    public static void main(String[] args) {
//        String dirName = "boggle";
//        In in = new In(Alg4.getFile(dirName, args[0]));
//        String[] dictionary = in.readAllStrings();
//        com.ggdsn.algorithms.alg4work.boggle.BoggleSolver solver = new com.ggdsn.algorithms.alg4work.boggle.BoggleSolver(dictionary);
//        BoggleBoard board = new BoggleBoard(Alg4.getFile(dirName, args[1]));
//        int score = 0;
//        for (String word : solver.getAllValidWords(board)) {
//            StdOut.println(word);
//            score += solver.scoreOf(word);
//        }
//        StdOut.println("Score = " + score);
//    }
}
