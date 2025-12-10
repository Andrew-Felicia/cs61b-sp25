package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import Wordnet.WordNet;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordNet net;

    public HyponymsHandler(WordNet net) {
        this.net = net;
    }
    @Override
    public String handle(NgordnetQuery q) {
        //return "Hello";
        List<String> words = q.words();
        return net.HyponymsOf(words);
    }
}
