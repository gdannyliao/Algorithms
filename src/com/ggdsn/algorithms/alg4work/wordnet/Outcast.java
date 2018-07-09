package com.ggdsn.algorithms.alg4work.wordnet;

import com.ggdsn.algorithms.alg4work.Alg4;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }       // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        int minDis = Integer.MAX_VALUE;
        //FIXME 2018/7/9 得到的结果全不对
        String minNoun = null;
        for (String noun : nouns) {
            int di = 0;
            for (String noun1 : nouns) {
                di += wordNet.distance(noun, noun1);
            }
            if (di < minDis) {
                minDis = di;
                minNoun = noun;
            }
        }
        return minNoun;
    } // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        String path = Alg4.DATA_DIR + File.separator + "wordnet" + File.separator;
        WordNet wordnet = new WordNet(path + args[0], path + args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(path + args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}