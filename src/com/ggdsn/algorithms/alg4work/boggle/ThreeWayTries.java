package com.ggdsn.algorithms.alg4work.boggle;

public class ThreeWayTries {
    private Node root;

    public Object get(CharSequence key) {
        return find(root, key, 0, true);
    }

    public boolean hasSection(CharSequence section) {
        Object o = find(root, section, 0, false);
        return o != null && o.equals(0);
    }

    public boolean hasValue(CharSequence key) {
        return find(root, key, 0, true) != null;
    }

    private Object find(Node node, CharSequence key, int idx, boolean checkValue) {
        if (node == null) return null;
        char c = key.charAt(idx);
        if (c < node.c)
            return find(node.left, key, idx, checkValue);
        else if (c > node.c)
            return find(node.right, key, idx, checkValue);
        else {
            if (c == 'Q') {
                if (idx + 1 < key.length() && key.charAt(idx + 1) == 'U')
                    idx++;
            }
            if (idx < key.length() - 1) {
                return find(node.mid, key, idx + 1, checkValue);
            } else {
                if (checkValue)
                    return node.value;
                else return 0;
            }
        }
    }

    public void put(String key, Object value) {
        if (key == null) return;
        root = put(root, key, value, 0);
    }

    private Node put(Node node, String key, Object value, int idx) {
        if (idx >= key.length()) return node;
        char c = key.charAt(idx);
        if (node == null) {
            node = new Node();
            node.c = c;
        }
        if (c < node.c)
            node.left = put(node.left, key, value, idx);
        else if (c > node.c)
            node.right = put(node.right, key, value, idx);
        else {
            if (c == 'Q') {
                if (idx + 1 < key.length() && key.charAt(idx + 1) == 'U')
                    idx++;
            }
            if (idx < key.length() - 1) {
                node.mid = put(node.mid, key, value, idx + 1);
            } else node.value = value;
        }
        return node;
    }

    private static class Node {
        Node left, mid, right;
        char c;
        Object value;

        @Override
        public String toString() {
            String letter;
            if (c == 'Q') letter = "QU";
            else letter = Character.toString(c);
            return "Node{" +
                    "c=" + letter +
                    ", value=" + value +
                    '}';
        }
    }
}
