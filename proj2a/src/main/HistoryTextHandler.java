package main;

import ngrams.NGramMap;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;


public class HistoryTextHandler extends NgordnetQueryHandler{

    NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }



    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();


        StringBuilder responds = new StringBuilder();
        for(String i : words){
            responds.append(i).append(": ").append(map.weightHistory(i, startYear, endYear).toString()).append("\n");
        }
        return responds.toString();
    }
}
