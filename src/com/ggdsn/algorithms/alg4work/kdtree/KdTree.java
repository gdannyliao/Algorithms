package com.ggdsn.algorithms.alg4work.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;

    public KdTree() {
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return size() == 0;
    }                      // is the set empty?

    public int size() {
        return root == null ? 0 : root.size;
    }                         // number of points in the set

    public void insert(Point2D p) {
        root = insert(root, p, Node.VERTICAL);
    }              // add the point to the set (if it is not already in the set)

    private int count = 0;

    private Node insert(Node node, Point2D p, boolean orient) {
        if (node == null) return new Node(p, orient, count++);
        else {
            Point2D current = node.value;
            if (current.x() == p.x() && current.y() == p.y())
                return node;
        }
        double compare;
        if (orient == Node.VERTICAL)
            compare = p.x() - node.value.x();
        else compare = p.y() - node.value.y();

        if (compare > 0f) {
            node.right = insert(node.right, p, !orient);
        } else {
            node.left = insert(node.left, p, !orient);
        }

        node.size = (node.left == null ? 0 : node.left.size) + (node.right == null ? 0 : node.right.size) + 1;
        return node;
    }

    public boolean contains(Point2D p) {
        return has(root, p);
    }            // does the set contain point p?

    private boolean has(Node node, Point2D p) {
        if (node == null) return false;
        if (node.value.equals(p)) return true;

        double compare;
        if (node.orientation == Node.VERTICAL) {
            compare = p.x() - node.value.x();
        } else compare = p.y() - node.value.y();

        if (compare < 0f) return has(node.left, p);
        else return has(node.right, p);
    }

    public void draw() {
        draw(root, 0.0, 0.0, 1.0, 1.0);
    }                         // draw all points to standard draw


    private void draw(Node node, double x1, double y1, double x2, double y2) {
        if (node == null) return;
        Point2D point = node.value;
        StdDraw.setPenColor(StdDraw.ORANGE);
        StdDraw.circle(point.x(), point.y(), 0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(point.x(), point.y(), String.valueOf(node.num));

        if (node.orientation == Node.VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(point.x(), y1, point.x(), y2);
            draw(node.left, x1, y1, point.x(), y2);
            draw(node.right, point.x(), y1, x2, y2);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x1, point.y(), x2, point.y());
            draw(node.left, x1, y1, x2, point.y());
            draw(node.right, x1, point.y(), x2, y2);
        }
    }

    private RectHV targetRect;
    private List<Point2D> rangeResults = new ArrayList<>();

    public Iterable<Point2D> range(RectHV rect) {
        targetRect = rect;
        rangeResults.clear();
        range(root, 0, 1, 1, 0);
        targetRect = null;
        return rangeResults;
    }             // all points that are inside the rectangle (or on the boundary)

    private void range(Node node, double left, double top, double right, double bottom) {
        if (node == null) return;
        if (targetRect.contains(node.value)) {
            rangeResults.add(node.value);
        }

        Point2D p = node.value;
        if (node.orientation == Node.VERTICAL) {
            if (intersects(targetRect, left, top, p.x(), bottom))
                range(node.left, left, top, p.x(), bottom);
            if (intersects(targetRect, p.x(), top, right, bottom))
                range(node.right, p.x(), top, right, bottom);
        } else {
            if (intersects(targetRect, left, p.y(), right, bottom))
                range(node.left, left, p.y(), right, bottom);
            if (intersects(targetRect, left, top, right, p.y()))
                range(node.right, left, top, right, p.y());
        }
    }

    private boolean intersects(RectHV that, double left, double top, double right, double bottom) {
        return right >= that.xmin() && top >= that.ymin()
                && that.xmax() >= left && that.ymax() >= bottom;
    }

    public Point2D nearest(Point2D p) {
        minDistance = Double.MAX_VALUE;
        nearest = null;
        target = p;
        findNearest(root, 0, 1.0, 1.0, 0);
        target = null;
        return nearest;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    private double distanceSquaredTo(Point2D p, double left, double top, double right, double bottom) {
        double dx = 0.0, dy = 0.0;
        if (p.x() < left) dx = p.x() - left;
        else if (p.x() > right) dx = p.x() - right;
        if (p.y() < bottom) dy = p.y() - bottom;
        else if (p.y() > top) dy = p.y() - top;
        return dx * dx + dy * dy;
    }

    private double minDistance = 0;
    private Point2D nearest;
    private Point2D target;

    /**
     * 情况1：整体被分为两半，目标点处于其中一边时。比较目标点所在区域内的最近距离M和目标点与另一边之间的距离N。如果M比N要大，另外一边可能有更近的点
     */
    private void findNearest(Node node, double left, double top, double right, double bottom) {
        if (node == null) return;

        Point2D point = node.value;

        double distance = Math.hypot(target.x() - point.x(), target.y() - point.y());
        if (distance < minDistance) {
            nearest = point;
            minDistance = distance;
        }

        if (node.orientation == Node.VERTICAL) {
            double leftDist = distanceSquaredTo(target, left, top, point.x(), bottom);
            double rightDist = distanceSquaredTo(target, point.x(), top, right, bottom);
            if (leftDist < rightDist) {
                findNearest(node.left, left, top, point.x(), bottom);
                if (rightDist < minDistance)
                    findNearest(node.right, point.x(), top, right, bottom);
            } else {
                findNearest(node.right, point.x(), top, right, bottom);
                if (leftDist < minDistance)
                    findNearest(node.left, left, top, point.x(), bottom);
            }
        } else {
            double topDist = distanceSquaredTo(target, left, top, right, point.y());
            double bottomDist = distanceSquaredTo(target, left, point.y(), right, bottom);
            if (bottomDist < topDist) {
                findNearest(node.left, left, point.y(), right, bottom);
                if (topDist < minDistance)
                    findNearest(node.right, left, top, right, point.y());
            } else {
                findNearest(node.right, left, top, right, point.y());
                if (bottomDist < minDistance)
                    findNearest(node.left, left, point.y(), right, bottom);
            }
        }
    }

    private static class Node {
        /**
         * 指的是分割线朝向
         */
        boolean orientation;
        Point2D value;
        Node left;
        Node right;
        int size = 1;
        int num;

        public Node(Point2D value, boolean orientation, int count) {
            this.orientation = orientation;
            this.value = value;
            num = count;
        }

        @Override
        public String toString() {
            return value.toString();
        }

        public static final boolean HORIZONTAL = false;
        public static final boolean VERTICAL = true;
    }
//    public static void main(String[] args) throws IOException {
//
//        // create initial board from file
//        com.ggdsn.algorithms.alg4work.kdtree.KdTree kdTree = new com.ggdsn.algorithms.alg4work.kdtree.KdTree();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Alg4.DATA_DIR + "/kdtree/" + args[0])));
//        String line;
//        do {
//            line = reader.readLine();
//            if (line == null || line.isEmpty())
//                break;
//            String[] strings = line.split(" ");
//            kdTree.insert(new Point2D(Double.valueOf(strings[0]), Double.valueOf(strings[1])));
//        } while (true);
//        System.out.println("kdTree " + kdTree.size() + " contains " + kdTree.contains(new Point2D(0.75,0.0)));
//
//    }     // unit testing of the methods (optional)
}
