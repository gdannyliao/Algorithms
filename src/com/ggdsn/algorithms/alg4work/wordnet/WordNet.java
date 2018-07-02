package com.ggdsn.algorithms.alg4work.wordnet;

import com.ggdsn.algorithms.alg4work.Alg4;
import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.*;

public class WordNet {
    private HashMap<Integer, Word> intWord = new HashMap<>();
    private HashMap<String, List<Integer>> wordInt = new HashMap<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In synsetIn = new In(synsets);
        while (synsetIn.hasNextLine()) {
            String line = synsetIn.readLine();
            String[] split = line.split(",");
            if (split.length < 2) continue;

            Word word = new Word(Integer.parseInt(split[0]));
            String[] words = split[1].split(" ");
            for (String s : words) {
                word.words.add(s);
                if (wordInt.containsKey(s)) {
                    wordInt.get(s).add(word.id);
                } else {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(word.id);
                    wordInt.put(s, list);
                }
            }
            intWord.put(word.id, word);
        }
        synsetIn.close();

        In hypernymsIn = new In(hypernyms);
        while (hypernymsIn.hasNextLine()) {
            String line = hypernymsIn.readLine();
            String[] split = line.split(",");
            if (split.length < 2) continue;
            Word word = intWord.get(Integer.parseInt(split[0]));
            if (word == null) continue;
            for (int i = 1; i < split.length; i++) {
                word.ancestors.add(Integer.parseInt(split[i]));
            }
        }
        hypernymsIn.close();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordInt.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return wordInt.containsKey(word);
    }

    // distance between nounA and nounB (defined below)

    /**
     * 这个算法的思路是：对于任意两个点，先算A点到根节点的所有点，设为路径A。再判断B点及其父节点中第一个与路径A产生交集的点即为最近公共祖先。
     * 如果不存在交集，则返回-1
     */
    public int distance(String nounA, String nounB) {
        List<Integer> idA = wordInt.get(nounA);
        if (idA == null) throw new IllegalArgumentException("nounA is not noun");
        List<Integer> idB = wordInt.get(nounB);
        if (idB == null) throw new IllegalArgumentException("nounB is not noun");

        int minDis = Integer.MAX_VALUE;
        for (Integer i : idA) {
            for (Integer j : idB) {
                int d = calDistance(i, j, new HashMap<>(), 0);
                if (d > -1 && d < minDis)
                    minDis = d;
            }
        }
        return minDis;
    }

    /**
     * 如果两者不存在联系，则返回-1
     */
    private int calDistance(Integer a, Integer b, Map<Integer, Integer> path, int distance) {
        Word word = intWord.get(a);
        if (word == null) return -1;
        path.put(a, distance++);
        if (word.ancestors.isEmpty()) {
            return findCross(b, path, 0);
        } else {
            int minDis = Integer.MAX_VALUE;
            for (Integer anc : word.ancestors) {
                int d = calDistance(anc, b, path, distance);
                if (d != -1 && d < minDis)
                    minDis = d;
            }
            return minDis;
        }
    }

    private int findCross(Integer v, Map<Integer, Integer> otherPath, int distance) {
        Word word = intWord.get(v);
        if (word == null) return -1;
        if (otherPath.containsKey(v)) return otherPath.get(v) + distance;
        int minDis = Integer.MAX_VALUE;
        for (Integer i : word.ancestors) {
            int d = findCross(i, otherPath, distance + 1);
            if (d != -1 && d < minDis)
                minDis = d;
        }
        return minDis;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)

    /**
     * 最近公共祖先
     */
//    public String sap(String nounA, String nounB) {
//
//    }

    // do unit testing of this class
    public static void main(String[] args) {
        String path = Alg4.DATA_DIR + File.separator + "wordnet" + File.separator;
        WordNet wordNet = new WordNet(path + args[0], path + args[1]);
        System.out.println("distance =" + wordNet.distance("Alpena", "houndstooth_check"));
    }
}

class Word {
    public final List<String> words = new ArrayList<>();
    public final List<Integer> ancestors = new ArrayList<>();
    public final int id;

    public Word(int id) {
        this.id = id;
    }
}