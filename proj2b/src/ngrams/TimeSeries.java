package ngrams;

import java.sql.Time;
import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for(Integer year : ts.keySet()) {
            if(year >= startYear && year <= endYear){
                this.put(year, ts.get(year));
            }
        }
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
//        List<Integer> result = new ArrayList<>();
//        Set<Integer> tmp = this.descendingKeySet();
//        for(Integer year : tmp) {
//            result.addFirst(year);
//        }
//        return result;
        return new ArrayList<>(this.keySet());
        /*
        ArrayList doesn’t have addFirst() — that’s a method from LinkedList.
        → This will not even compile.

       You are using descendingKeySet(), which gives keys in descending order,
       but you’re trying to re-reverse it manually with addFirst().
       → Overcomplicates it.
       TreeMap already keeps keys in ascending order,
        */
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
//        List<Integer> resultK = this.years();
//        List<Double> resultV = new ArrayList<>();
//        for(Integer year : resultK) {
//            resultV.addLast(this.get(year));
//        }
//        return resultV;
        List<Double> result = new ArrayList<>();
        for (Integer year : this.keySet()) {
            result.add(this.get(year));
        }
        return result;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries result = ts;
        for(Map.Entry<Integer, Double> entry : this.entrySet()) {
            if(result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), result.get(entry.getKey()) + entry.getValue());
            }
            else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries result = new TimeSeries();
        for(Map.Entry<Integer, Double> entry : this.entrySet()) {
            if(!ts.containsKey(entry.getKey())){
                throw new IllegalArgumentException("S is missing a year that exists in this TimeSeries");
            }
            if(ts.get(entry.getKey()) == 0.0){
                throw new IllegalArgumentException("can't divide by zero!");
            }
            result.put(entry.getKey(), entry.getValue() / ts.get(entry.getKey()));
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
