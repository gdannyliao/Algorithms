package com.ggdsn.algorithms.alg4work.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set = new TreeSet<>();

    public PointSET() {
    }                          // construct an empty set of points

    public boolean isEmpty() {
        return set.isEmpty();
    }// is the set empty?

    public int size() {
        return set.size();
    }                 // number of points in the set

    public void insert(Point2D p) {
        set.add(p);
    }// add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return set.contains(p);
    }            // does the set contain point p?

    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }                      // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> list = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }             // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D point) {
        double minDis = Double.MAX_VALUE;
        Point2D min = null;
        for (Point2D p : set) {
            double distance = point.distanceTo(p);
            if (distance < minDis) {
                minDis = distance;
                min = p;
            }
        }
        return min;
    }            // a nearest neighbor in the set to point p; null if the set is empty

}