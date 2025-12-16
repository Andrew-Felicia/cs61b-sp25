package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import Wordnet.WordNet;

import java.util.List;
import ngrams.TimeSeries;
import ngrams.NGramMap;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordNet net;
    NGramMap map;

    public HyponymsHandler(WordNet net, NGramMap map) {
        this.net = net;
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        //return "Hello";
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        return net.HyponymsOf(words, startYear, endYear, k, map);
    }
}
