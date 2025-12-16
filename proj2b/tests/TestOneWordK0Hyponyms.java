import Wordnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import edu.princeton.cs.algs4.In;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    // ngrams files
    public static final String VERY_SHORT_WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    private static final String SMALL_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    private static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";
    private static final String Small_words11 = "data/ngrams/Small_words11.csv";

    // wordnet Files
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets11.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms11.txt";
    public static final String SMALL_SYNSET_FILE1 = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE1 = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    private static final String HYPONYMS_FILE_SUBSET = "data/wordnet/hyponyms1000-subgraph.txt";
    private static final String SYNSETS_FILE_SUBSET = "data/wordnet/synsets1000-subgraph.txt";

    // EECS files
    private static final String FREQUENCY_EECS_FILE = "data/ngrams/frequency-EECS.csv";
    private static final String HYPONYMS_EECS_FILE = "data/wordnet/hyponyms-EECS.txt";
    private static final String SYNSETS_EECS_FILE = "data/wordnet/synsets-EECS.txt";

//    @Test
//    public void testActK0() {
//        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
//                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
//        List<String> words = new ArrayList<>();
//        words.add("act");
//
//        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
//        String actual = studentHandler.handle(nq);
//        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
//        assertThat(actual).isEqualTo(expected);
//    }



    //k = 0, single word, single query per test
    @Test
    public void testHyponymsSimple(){
//        System.out.println("Working directory = " + System.getProperty("user.dir"));
//        System.out.println(new File("data/wordnet/synsets11.txt").exists());


        WordNet wn = new WordNet(SMALL_SYNSET_FILE,SMALL_HYPONYM_FILE);
        List<String> words = new ArrayList<>();
        words.add("descent");

        String actual = wn.HyponymsOf(words);
        String expected = "[descent, jump, parachuting]";
        assertThat(actual).isEqualTo(expected);
    }


    //k = 0, single word, multiple queries per test
    @Test
    public void testHyponymsSimple1(){
//        System.out.println("Working directory = " + System.getProperty("user.dir"));
//        System.out.println(new File("data/wordnet/synsets11.txt").exists());

        //case 1
        WordNet wn = new WordNet(SMALL_SYNSET_FILE,SMALL_HYPONYM_FILE);
        List<String> words = new ArrayList<>();
        words.add("descent");

        String actual = wn.HyponymsOf(words);
        String expected = "[descent, jump, parachuting]";
        assertThat(actual).isEqualTo(expected);

        //case 2
        words.clear();
        words.add("action");

        actual = wn.HyponymsOf(words);
        expected = "[action, change, demotion]";
        assertThat(actual).isEqualTo(expected);

        //case 3
        words.clear();
        words.add("increase");

        actual = wn.HyponymsOf(words);
        expected = "[augmentation, increase, jump, leap]";
        assertThat(actual).isEqualTo(expected);

        //case 4
        words.clear();
        words.add("antihistamine");

        actual = wn.HyponymsOf(words);
        expected = "[actifed, antihistamine]";
        assertThat(actual).isEqualTo(expected);

        //case 5
        words.clear();
        words.add("nasal_decongestant");

        actual = wn.HyponymsOf(words);
        expected = "[actifed, nasal_decongestant]";
        assertThat(actual).isEqualTo(expected);

    }


    //k = 0, single word, single query per test
    @Test
    public void testHyponymsSimple2(){
//        System.out.println("Working directory = " + System.getProperty("user.dir"));
//        System.out.println(new File("data/wordnet/synsets11.txt").exists());


        WordNet wn = new WordNet(SMALL_SYNSET_FILE1,SMALL_HYPONYM_FILE1);
        List<String> words = new ArrayList<>();
        words.add("change");

        String actual = wn.HyponymsOf(words);
        String expected = "[alteration, change, demotion, increase, jump, leap, modification, saltation, transition, variation]";
        assertThat(actual).isEqualTo(expected);
    }



    //k = 0, multiple words, single query per test
    @Test
    public void testHyponymsSimple3(){
//        System.out.println("Working directory = " + System.getProperty("user.dir"));
//        System.out.println(new File("data/wordnet/synsets11.txt").exists());


        WordNet wn = new WordNet(SMALL_SYNSET_FILE1,SMALL_HYPONYM_FILE1);
        List<String> words = new ArrayList<>();
        words.add("change");
        words.add("occurrence");

        String actual = wn.HyponymsOf(words);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    //k = 0, multiple words, multiple queries per test
    @Test
    public void testHyponymsSimple4(){
//        System.out.println("Working directory = " + System.getProperty("user.dir"));
//        System.out.println(new File("data/wordnet/synsets11.txt").exists());

        //case 1
        WordNet wn = new WordNet(LARGE_SYNSET_FILE,LARGE_HYPONYM_FILE);
        List<String> words = new ArrayList<>();
        words.add("video");
        words.add("recording");

        String actual = wn.HyponymsOf(words);
        String expected = "[video, video_recording, videocassette, videotape]";
        assertThat(actual).isEqualTo(expected);

        //case 2
        words.clear();
        words.add("pastry");
        words.add("tart");

        actual = wn.HyponymsOf(words);
        expected = "[apple_tart, lobster_tart, quiche, quiche_Lorraine, tart, tartlet]";
        assertThat(actual).isEqualTo(expected);

    }


    // my solution is actually correct, but it takes 5 mins and 33 sec to finish.
    @Test
    public void testHyponymsSimple5(){
    //        System.out.println("Working directory = " + System.getProperty("user.dir"));
    //        System.out.println(new File("data/wordnet/synsets11.txt").exists());


//        WordNet wn = new WordNet(SMALL_SYNSET_FILE,SMALL_HYPONYM_FILE);
//        NGramMap map = new NGramMap(Small_words11, TOTAL_COUNTS_FILE);
//
//        List<String> words = new ArrayList<>();
//        words.add("action");
//
//        String actual = wn.HyponymsOf(words,1950, 1990, 1, map);
//        String expected = "[demotion]";
//        assertThat(actual).isEqualTo(expected);



        WordNet wn = new WordNet(LARGE_SYNSET_FILE,LARGE_HYPONYM_FILE);
        NGramMap map = new NGramMap(SMALL_WORDS_FILE, TOTAL_COUNTS_FILE);

        List<String> words = new ArrayList<>();
        words.add("food");
        words.add("cake");

        String actual = wn.HyponymsOf(words,1950, 1990, 5, map);
        String expected = "[cake, cookie, kiss, snap, wafer]";
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void testHyponymsSimple6(){
        //        System.out.println("Working directory = " + System.getProperty("user.dir"));
        //        System.out.println(new File("data/wordnet/synsets11.txt").exists());



        WordNet wn = new WordNet(SYNSETS_EECS_FILE,HYPONYMS_EECS_FILE);
        NGramMap map = new NGramMap(FREQUENCY_EECS_FILE, TOTAL_COUNTS_FILE);

        List<String> words = new ArrayList<>();
        words.add("CS61A");

        String actual = wn.HyponymsOf(words,2010, 2020, 4, map);
        String expected = "[CS170, CS61A, CS61B, CS61C]";
        assertThat(actual).isEqualTo(expected);
    }


}
