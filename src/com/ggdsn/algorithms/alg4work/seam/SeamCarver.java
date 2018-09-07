package com.ggdsn.algorithms.alg4work.seam;

import com.ggdsn.algorithms.alg4work.Alg4;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        if (picture.width() == 0 || picture.height() == 0) throw new IllegalArgumentException();
        energy = new double[picture.height()][picture.width()];
        this.picture = new Picture(picture);
    }           // create a seam carver object based on the given picture

    public Picture picture() {
        return picture;
    }                         // current picture

    public int width() {
        return picture.width();
    }                     // width of current picture

    public int height() {
        return picture.height();
    }                   // height of current picture

    public double energy(int col, int row) {
        if (col < 0 || col >= width() || row < 0 || row >= height()) throw new IllegalArgumentException();
        if (energy[row][col] == 0) {
            double e;
            if (col == 0 || row == 0 || col == lastIndex(false) || row == lastIndex(true)) e = 1000;
            else e = Math.sqrt(getSquare(picture.get(col + 1, row), picture.get(col - 1, row)) +
                    getSquare(picture.get(col, row + 1), picture.get(col, row - 1)));
            energy[row][col] = e;
        }
        return energy[row][col];
    }      // energy of pixel at column col and row row

    private double getSquare(Color one, Color two) {
        return Math.pow(one.getRed() - two.getRed(), 2) + Math.pow(one.getGreen() - two.getGreen(), 2) + Math.pow(one.getBlue() - two.getBlue(), 2);
    }

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "P{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private double[][] weightTo;
    private Point[][] pathTo;
    private double[][] energy;
    private LinkedList<Point> queue = new LinkedList<>();

    public int[] findHorizontalSeam() {
        init(false);
        for (int start = 0; start < height(); start++) {
            queue.add(new Point(start, 0));
            while (!queue.isEmpty()) {
                relax(queue.remove(), false);
            }
        }
        //找到最小的起点
        int[] minPath = new int[picture.width()];
        int start = findMinStartPoint(false);
        int lastIndex = lastIndex(false);
        minPath[lastIndex] = start;
        for (int j = lastIndex; j > 0; j--) {
            Point p = pathTo[start][j];
            minPath[j - 1] = p.x;
            start = p.x;
        }
        clear();
        return minPath;
    }             // sequence of indices for horizontal seam

    public int[] findVerticalSeam() {
        init(true);
        for (int start = 0; start < width(); start++) {
            queue.add(new Point(0, start));
            while (!queue.isEmpty()) {
                relax(queue.remove(), true);
            }
        }
        int[] minPath = new int[height()];
        int start = findMinStartPoint(true);
        int lastIndex = lastIndex(true);
        minPath[lastIndex] = start;
        for (int i = lastIndex; i > 0; i--) {
            Point p = pathTo[i][start];
            minPath[i - 1] = p.y;
            start = p.y;
        }
        clear();
        return minPath;
    }             // sequence of indices for vertical seam

    private void clear() {
        weightTo = null;
        pathTo = null;
        queue.clear();
    }

    private int lastIndex(boolean vertical) {
        if (vertical) return height() - 1;
        else return width() - 1;
    }

    private int findMinStartPoint(boolean vertical) {
        int start = 0;
        double min = Double.MAX_VALUE;
        if (vertical) {
            int lastX = picture.height() - 1;
            for (int j = 0; j < picture.width(); j++) {
                if (weightTo[lastX][j] < min) {
                    start = j;
                    min = weightTo[lastX][j];
                }
            }
        } else {
            int lastY = picture.width() - 1;
            for (int i = 0; i < picture.height(); i++) {
                if (weightTo[i][lastY] < min) {
                    start = i;
                    min = weightTo[i][lastY];
                }
            }
        }
        return start;
    }

    private void relax(Point from, boolean vertical) {
        Point[] adj = getAdjacent(from.x, from.y, vertical);
        for (Point to : adj) {
            if (to == null) continue;
            double energy = energy(to.y, to.x);
            if (energy + weightTo[from.x][from.y] < weightTo[to.x][to.y]) {
                double weight = energy + weightTo[from.x][from.y];
                weightTo[to.x][to.y] = weight;
                pathTo[to.x][to.y] = from;
                queue.add(to);
            }
        }
    }

    private void init(boolean vertical) {
        weightTo = new double[picture.height()][picture.width()];
        pathTo = new Point[picture.height()][picture.width()];
        if (vertical) {
            for (int i = 1; i < weightTo.length; i++) {
                for (int j = 0; j < weightTo[0].length; j++) {
                    weightTo[i][j] = Double.MAX_VALUE;
                    pathTo[i][j] = null;
                }
            }
            for (int j = 0; j < weightTo[0].length; j++) {
                weightTo[0][j] = 1000;
                pathTo[0][j] = null;
            }
        } else {
            for (int j = 1; j < weightTo[0].length; j++) {
                for (int i = 0; i < weightTo.length; i++) {
                    weightTo[i][j] = Double.MAX_VALUE;
                    pathTo[i][j] = null;
                }
            }
            for (int i = 0; i < weightTo.length; i++) {
                weightTo[i][0] = 1000;
                pathTo[i][0] = null;
            }
        }
    }

    private Point[] getAdjacent(int i, int j, boolean vertical) {
        Point[] points = new Point[3];
        if (vertical) {
            if (i >= picture.height() - 1) return points;
            if (j == 0)
                points[0] = null;
            else
                points[0] = new Point(i + 1, j - 1);
            points[1] = new Point(i + 1, j);
            if (j >= picture.width() - 1)
                points[2] = null;
            else
                points[2] = new Point(i + 1, j + 1);
        } else {
            if (j >= picture.width() - 1) return points;
            if (i == 0)
                points[0] = null;
            else
                points[0] = new Point(i - 1, j + 1);
            points[1] = new Point(i, j + 1);
            if (i >= picture.height() - 1)
                points[2] = null;
            else
                points[2] = new Point(i + 1, j + 1);
        }
        return points;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height() < 1) throw new IllegalArgumentException();
        Picture res = new Picture(width(), height() - 1);
        for (int j = 0; j < width(); j++) {
            boolean crossLine = false;
            for (int i = 0; i < res.height(); i++) {
                if (seam[j] == i) crossLine = true;
                if (crossLine) res.setRGB(j, i, picture.getRGB(j, i + 1));
                else res.setRGB(j, i, picture.getRGB(j, i));
            }
        }
        picture = res;
    }// remove horizontal seam from current picture

    public void removeVerticalSeam(int[] seam) {
        if (seam == null || width() < 1) throw new IllegalArgumentException();
        Picture res = new Picture(width() - 1, height());
        for (int i = 0; i < height(); i++) {
            boolean crossLine = false;
            for (int j = 0; j < res.width(); j++) {
                if (seam[i] == j) crossLine = true;
                if (crossLine) res.setRGB(j, i, picture.getRGB(j + 1, i));
                else res.setRGB(j, i, picture.getRGB(j, i));
            }
        }
        picture = res;
    } // remove vertical seam from current picture

    public static void main(String[] args) {
//        String[] files = {"3x4.png", "3x7.png", "4x6.png", "5x6.png", "6x5.png", "7x3.png", "7x10.png", "8x1.png", "10x10.png"
//                , "10x12.png", "12x10.png", "diagonals.png", "stripes.png", "1x1.png", "1x8.png"};
        String[] files = {"4x6.png"};
        for (String f : files) {
            Picture picture = new Picture(Alg4.getFile("seam" + File.separator + f));
            SeamCarver carver = new SeamCarver(picture);
            System.out.println(f + ", v:" + Arrays.toString(carver.findVerticalSeam()) + " h:" + Arrays.toString(carver.findHorizontalSeam()));
            System.out.println(carver.picture());
//            carver.removeHorizontalSeam(carver.findHorizontalSeam());
            carver.removeVerticalSeam(carver.findVerticalSeam());
            System.out.println(carver.picture());
        }
    }
}