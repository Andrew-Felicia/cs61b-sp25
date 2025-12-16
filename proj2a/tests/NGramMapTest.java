import ngrams.NGramMap;
import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Utils.*;
import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the NGramMap class.
 *  @author Josh Hug
 */
public class NGramMapTest {
    @Test
    public void testCountHistory() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(2005);
        expectedYears.add(2006);
        expectedYears.add(2007);
        expectedYears.add(2008);

        List<Double> expectedCounts = new ArrayList<>();
        expectedCounts.add(646179.0);
        expectedCounts.add(677820.0);
        expectedCounts.add(697645.0);
        expectedCounts.add(795265.0);

        TimeSeries request2005to2008 = ngm.countHistory("request");
        assertThat(request2005to2008.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2005to2008.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }

//        String result = request2005to2008.toString();
//        System.out.println(result);


        expectedYears = new ArrayList<>();
        expectedYears.add(2006);
        expectedYears.add(2007);
        expectedCounts = new ArrayList<>();
        expectedCounts.add(677820.0);
        expectedCounts.add(697645.0);


        TimeSeries request2006to2007 = ngm.countHistory("request", 2006, 2007);

        assertThat(request2006to2007.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2006to2007.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }
    }

    @Test
    public void testOnShortFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE,
                TOTAL_COUNTS_FILE);

        // returns the count of the number of occurrences of economically per year between 2006 and 2008.
        TimeSeries econCount = ngm.countHistory("wandered", 2006, 2008);
        assertThat(econCount.get(2006)).isWithin(1E-10).of(87688.0);
        assertThat(econCount.get(2007)).isWithin(1E-10).of(108634.0);
        assertThat(econCount.get(2008)).isWithin(1E-10).of(171015.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1999)).isWithin(1E-10).of(22668397698.0);

        // returns the relative weight of the word academic in each year between 2007 and 2008.
        TimeSeries academicWeight = ngm.weightHistory("airport", 2007, 2008);
        assertThat(academicWeight.get(2007)).isWithin(1E-7).of(175702.0 / 28307904288.0);
        assertThat(academicWeight.get(2008)).isWithin(1E-7).of(173294.0 / 28752030034.0);
    }
    @Test
    public void testOnLargeFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap(TOP_14337_WORDS_FILE,
                TOTAL_COUNTS_FILE);

        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries fishCount = ngm.countHistory("fish", 1850, 1933);
        assertThat(fishCount.get(1865)).isWithin(1E-10).of(136497.0);
        assertThat(fishCount.get(1922)).isWithin(1E-10).of(444924.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("fish", 1850, 1933);
        assertThat(fishWeight.get(1865)).isWithin(1E-7).of(136497.0/2563919231.0);

        TimeSeries dogCount = ngm.countHistory("dog", 1850, 1876);
        assertThat(dogCount.get(1865)).isWithin(1E-10).of(75819.0);

        List<String> fishAndDog = new ArrayList<>();
        fishAndDog.add("fish");
        fishAndDog.add("dog");
        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 1865, 1866);

        double expectedFishPlusDogWeight1865 = (136497.0 + 75819.0) / 2563919231.0;
        assertThat(fishPlusDogWeight.get(1865)).isWithin(1E-10).of(expectedFishPlusDogWeight1865);
    }


    @Test
    public void testOnShortFile1() {
        // creates an NGramMap from a large dataset
        //"./data/ngrams/Small_words11.csv"
        NGramMap ngm = new NGramMap("./data/ngrams/Small_words11.csv",
                TOTAL_COUNTS_FILE);

        // returns the count of the number of occurrences of economically per year between 2006 and 2008.
        TimeSeries econCount = ngm.countHistory("action", 1950, 1990);
        assertThat(econCount.get(1950)).isWithin(1E-10).of(1.0);
        assertThat(econCount.get(1987)).isWithin(1E-10).of(2.0);
//        TimeSeries econCount = ngm.countHistory("airport", 2007, 2010);
//        assertThat(econCount.get(2007)).isWithin(1E-10).of(175702.0);
//        assertThat(econCount.get(2008)).isWithin(1E-10).of(173294.0);

    }

}  