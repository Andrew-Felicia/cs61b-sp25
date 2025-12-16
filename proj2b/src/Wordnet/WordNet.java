package Wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

import ngrams.TimeSeries;
import ngrams.NGramMap;

public class WordNet {

    In synsets;
    In hyponyms;
    String synsetsFilename;
    String hyponymsFilename;
    Map<Integer, Set<String>> idToSynsets = new HashMap<>();// store the synsets relationships
    Digraph g;

    public WordNet(String synsetsFilename, String hyponymsFilename) {
        this.synsetsFilename = synsetsFilename;
        this.hyponymsFilename = hyponymsFilename;
        synsets = new In(synsetsFilename);
        hyponyms = new In(hyponymsFilename);
        BuildDirectedGraph();
    }

//ChatGPT wrote this code.And i did some modify.
    /*
    this method using the input files synsets and hyponyms to build two things:
    Map<Integer, Set<String>> idToSynsets:  store the id and the correspond set of strings
    Digragh g: store the hyponyms relationships based on id.
     */
    public void BuildDirectedGraph() {
        int lines = 0;

        // Read synsets
        while (this.synsets.hasNextLine()) {
            String nextLine = synsets.readLine();
            lines++;

            String[] parts = nextLine.split(",", 3);
            int id = Integer.parseInt(parts[0]);

            Set<String> synsetWords =
                    new HashSet<>(Arrays.asList(parts[1].split(" ")));

            idToSynsets.put(id, synsetWords);
        }
        this.synsets.close();
        this.synsets = new In(this.synsetsFilename);//if don't do this line, user can only enter once.

        // Build graph
        this.g = new Digraph(lines);

        while (this.hyponyms.hasNextLine()) {
            String nextLine = hyponyms.readLine();
            String[] parts = nextLine.split(",");

            int parent = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                g.addEdge(parent, Integer.parseInt(parts[i]));
            }
        }
        this.hyponyms.close();
        this.hyponyms = new In(this.hyponymsFilename);//if don't do this line, user can only enter once.

    }

    //compute the result of many strings, and filter by startYear,endYear,k, and map.
    public String HyponymsOf(List<String> words, int startYear, int endYear, int k, NGramMap map) {

        // If only one word → simple case
        if (words.size() == 1) {
            List<String> sorted = new ArrayList<>(hyponymsOfSingleWord(words.get(0)));
            Collections.sort(sorted);

            //handle with k != 0.
            return handleK(sorted, startYear, endYear, k, map).toString();
        }

        // Start with hyponyms of the first word
        Set<String> intersection = hyponymsOfSingleWord(words.get(0));

        // Intersect with hyponyms of each remaining word
        for (int i = 1; i < words.size(); i++) {
            Set<String> next = hyponymsOfSingleWord(words.get(i));
            intersection.retainAll(next);   // <--- THIS DOES THE INTERSECTION
        }

        // Sort alphabetically
        List<String> sorted = new ArrayList<>(intersection);
        Collections.sort(sorted);

        //handle with k != 0
        return handleK(sorted, startYear, endYear, k, map).toString();
    }


    public String HyponymsOf(List<String> words) {

        // If only one word → simple case
        if (words.size() == 1) {
            List<String> sorted = new ArrayList<>(hyponymsOfSingleWord(words.get(0)));
            Collections.sort(sorted);

            return sorted.toString();
        }

        // Start with hyponyms of the first word
        Set<String> intersection = hyponymsOfSingleWord(words.get(0));

        // Intersect with hyponyms of each remaining word
        for (int i = 1; i < words.size(); i++) {
            Set<String> next = hyponymsOfSingleWord(words.get(i));
            intersection.retainAll(next);   // <--- THIS DOES THE INTERSECTION
        }

        // Sort alphabetically
        List<String> sorted = new ArrayList<>(intersection);
        Collections.sort(sorted);

        return sorted.toString();
    }




    //helper function,  compute the result of one single word
    private Set<String> hyponymsOfSingleWord(String word) {
        Set<String> result = new HashSet<>();

        // Step 1: find all synset IDs containing the word
        List<Integer> roots = new ArrayList<>();
        for (Map.Entry<Integer, Set<String>> e : idToSynsets.entrySet()) {
            if (e.getValue().contains(word)) {
                roots.add(e.getKey());
                result.addAll(e.getValue());
            }
        }

        // Step 2: BFS/DFS over the digraph to collect hyponyms
        Queue<Integer> q = new LinkedList<>(roots);
        Set<Integer> visited = new HashSet<>(roots);

        while (!q.isEmpty()) {
            int id = q.poll();
            for (int child : this.g.adj(id)) {
                if (!visited.contains(child)) {
                    visited.add(child);
                    q.add(child);
                    result.addAll(idToSynsets.get(child));
                }
            }
        }

        return result;
    }

    //helper function, handle with k != 0, and startYear and endYear.
    public List<String> handleK(List<String> sorted, int startYear, int endYear,
                                int k,  NGramMap map) {
        double tmp = 0.0;
        Map<String, Double> tmp1 = new HashMap<>();

        for(String s : sorted) {
            for(Double value : map.countHistory(s, startYear, endYear).data()) {
                tmp += value;
            }
            tmp1.put(s, tmp);
            tmp = 0.0;
        }

        if (k <= 0 || tmp1 == null || tmp1.isEmpty()) {
            return Collections.emptyList();
        }

        return tmp1.entrySet()
                   .stream()
                   .filter(e -> e.getValue() != null && e.getValue() != 0.0)
                   .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                   .limit(k)
                   .map(Map.Entry::getKey)
                   .sorted(String::compareTo) // make it to be in alphabet order.
                   .collect(Collectors.toList());
    }


}




//I wrote this code below, but something is wrong.
//
//    public String HyponymsOf(List<String> words) {
//        int lines = 0;
//
//        while (this.synsets.hasNextLine()) {
//            lines += 1;
//
//            String nextLine = synsets.readLine();
//
//            String[] parts = nextLine.split(",", 3);
//            int id = Integer.parseInt(parts[0]);
//
//            // synset words separated by space
//            String[] field2 = parts[1].split(" ");
//            Set<String> synsetWords = new HashSet<>(Arrays.asList(field2));
//
//            idToSynsets.put(id, synsetWords);
//        }
//        this.synsets.close();
//        this.synsets = new In(this.synsetsFilename);
//
//        Digraph tmp = new Digraph(lines); // directed graph
//
//        while (this.hyponyms.hasNextLine()) {
//            String nextLine = hyponyms.readLine();
//
//            String[] parts = nextLine.split(",");
//            int first = Integer.parseInt(parts[0]);
//            for(int i = 1; i < parts.length; i++) {
//                tmp.addEdge(first, Integer.parseInt(parts[i]));
//            }
//        }
//        this.hyponyms.close();
//        this.hyponyms = new In(this.hyponymsFilename);
//
//
//
//
//
//        Set<String> tmp1 = new HashSet<String>(); // store the result
//        List<Integer> tmp2 = new ArrayList<>();  // store the Integer which paired with the desired words
//        for(String i : words) {
//            for(Map.Entry<Integer, Set<String>> elem : idToSynsets.entrySet()) {
//                if(elem.getValue().contains(i)) {
//                    tmp2.add(elem.getKey());
//                    tmp1.addAll(elem.getValue());
//                }
//            }
//        }
//
//        List<Integer> tmp3 = new ArrayList<>();// store the Integer of the desired words' children
//        for(Integer i : tmp2) {
//            //tmp3.addAll((Collection<? extends Integer>) tmp.adj(i));
//            for (int child : tmp.adj(i)) {
//                tmp3.add(child);
//            }
//
//        }
//
//        //iterate tmp3 to get all the children of the desired words
//        while(!tmp3.isEmpty()) {
//            for(Integer i : tmp3) {
//                tmp1.addAll(idToSynsets.get(i));
//                if(tmp.adj(i) == null) {
//                    tmp3.remove(i);
//                }
//                else {
//                    //tmp3.addAll((Collection<? extends Integer>) tmp.adj(i));
//                    for (int child : tmp.adj(i)) {
//                        tmp3.add(child);
//                    }
//
//                    tmp3.remove(i);
//                }
//            }
//        }
//
//
//        return tmp1.toString();
//
//    }


//ChatGPT did some modify based on my code,and it's woking well for first part.

//    public String HyponymsOf(List<String> words) {
//
//        int lines = 0;
//
//        // Read synsets
//        while (this.synsets.hasNextLine()) {
//            String nextLine = synsets.readLine();
//            lines++;
//
//            String[] parts = nextLine.split(",", 3);
//            int id = Integer.parseInt(parts[0]);
//
//            Set<String> synsetWords =
//                    new HashSet<>(Arrays.asList(parts[1].split(" ")));
//
//            idToSynsets.put(id, synsetWords);
//        }
//        this.synsets.close();
//
//        // Build graph
//        Digraph g = new Digraph(lines);
//
//        while (this.hyponyms.hasNextLine()) {
//            String nextLine = hyponyms.readLine();
//            String[] parts = nextLine.split(",");
//
//            int parent = Integer.parseInt(parts[0]);
//            for (int i = 1; i < parts.length; i++) {
//                g.addEdge(parent, Integer.parseInt(parts[i]));
//            }
//        }
//        this.hyponyms.close();
//
//        // Step 1: find all synset IDs containing the input words
//        Set<Integer> startIDs = new HashSet<>();
//        Set<String> result = new HashSet<>();
//
//        for (String w : words) {
//            for (Map.Entry<Integer, Set<String>> e : idToSynsets.entrySet()) {
//                if (e.getValue().contains(w)) {
//                    startIDs.add(e.getKey());
//                    result.addAll(e.getValue());
//                }
//            }
//        }
//
//        // Step 2: BFS to find all descendants
//        Queue<Integer> queue = new LinkedList<>(startIDs);
//        Set<Integer> visited = new HashSet<>(startIDs);
//
//        while (!queue.isEmpty()) {
//            int cur = queue.remove();
//
//            for (int child : g.adj(cur)) {
//                if (!visited.contains(child)) {
//                    visited.add(child);
//                    queue.add(child);
//
//                    // Add child's words to result
//                    result.addAll(idToSynsets.get(child));
//                }
//            }
//        }
//
//        List<String> sortedResult = new ArrayList<>(result);
//        Collections.sort(sortedResult);
//
//       // Return as string
//        return sortedResult.toString();
//    }