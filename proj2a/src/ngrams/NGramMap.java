package ngrams;

import java.sql.Time;
import java.util.Collection;
import edu.princeton.cs.algs4.In;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    In inWords;
    In inCounts;
    String wordsFilename;
    String countsFilename;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordsFilename = wordsFilename;
        this.countsFilename = countsFilename;
        inWords = new In(wordsFilename);
        inCounts = new In(countsFilename);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries tmp = new TimeSeries();
        while(this.inWords.hasNextLine()) {
            String nextLine = this.inWords.readLine();
            //String [] splitLine = nextLine.split("\t");
            String [] splitLine = nextLine.split("\\s+");
            if(splitLine[0].equals(word)){
                tmp.put(Integer.valueOf(splitLine[1]), Double.valueOf(splitLine[2]));
            }
        }
        this.inWords.close();
        this.inWords = new In(this.wordsFilename);
        return new TimeSeries(tmp, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries result = new TimeSeries();
        while(this.inWords.hasNextLine()) {
            String nextLine = this.inWords.readLine();
            String [] splitLine = nextLine.split("\t");
            if(splitLine[0].equals(word)){
                result.put(Integer.valueOf(splitLine[1]), Double.valueOf(splitLine[2]));
            }
        }
        this.inWords.close();
        this.inWords = new In(this.wordsFilename);
        return result;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries result = new TimeSeries();
        while(this.inCounts.hasNextLine()) {
            String nextLine = this.inCounts.readLine();
            String [] splitLine = nextLine.split(",");
            result.put(Integer.valueOf(splitLine[0]), Double.valueOf(splitLine[1]));
        }
        this.inCounts.close();
        this.inCounts = new In(this.countsFilename);
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries tmp1 = countHistory(word, startYear, endYear);
        TimeSeries tmp2 = totalCountHistory();
        return tmp1.dividedBy(tmp2);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries tmp1 = countHistory(word);
        TimeSeries tmp2 = totalCountHistory();
        return tmp1.dividedBy(tmp2);

    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for(String i : words) {
            result = result.plus(weightHistory(i, startYear, endYear));
            /*
            if you write this: result.plus(weightHistory(i, startYear, endYear));
            you didn't use the value returned by plus method, and you got the error:
            java.lang.NullPointerException: actual value cannot be null.
            it's a very subtle error.
             */
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries result = new TimeSeries();
        for(String i : words) {
            result = result.plus(weightHistory(i));
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
