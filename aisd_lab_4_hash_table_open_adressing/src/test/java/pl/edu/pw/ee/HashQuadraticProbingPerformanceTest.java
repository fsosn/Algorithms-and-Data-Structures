package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import pl.edu.pw.ee.services.HashTable;

public class HashQuadraticProbingPerformanceTest {

    private static final String WORDS_FILEPATH = "src/main/resources/wordlist_100_000.txt";
    private static final Logger LOG = Logger.getLogger(HashQuadraticProbingPerformanceTest.class.getName());
    private final double a = 0.5;
    private final double b = 0.5;

    @Test
    public void measurePerfomanceOfHashes() {
        int nOfRepeats = 30;
        int[] initialSizes = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144};
        String[] words = prepareWords();

        HashTable<String> hash;
        List<Long> times;
        long startTime, measuredTime;

        for (int size : initialSizes) {
            times = new ArrayList<>();

            for (int i = 0; i < nOfRepeats; i++) {
                hash = new HashQuadraticProbing<>(size, this.a, this.b);

                startTime = System.nanoTime();
                putWordsIntoHash(hash, words);
                measuredTime = System.nanoTime() - startTime;

                times.add(measuredTime);
            }

            countAndPrintMinAvgTime(times, size);
        }

    }

    private void putWordsIntoHash(HashTable<String> hash, String[] words) {
        int n = words.length;

        for (int i = 0; i < n; i++) {
            hash.put(words[i]);
        }
    }

    private String[] prepareWords() {
        List<String> words = new ArrayList<>();
        String line;
        int counter = 0;
        int maxNumWords = 100_000;

        try ( FileReader fReader = new FileReader(WORDS_FILEPATH);  BufferedReader buffReader = new BufferedReader(fReader)) {

            while ((line = buffReader.readLine()) != null && counter < maxNumWords) {
                words.add(line);
                counter++;
            }

        } catch (IOException e) {
            LOG.log(SEVERE, "[Error] An error ocurred during preparing words.", e);
        }

        return words.toArray(new String[0]);
    }

    private void countAndPrintMinAvgTime(List<Long> timesAsList, int initSize) {
        int n = timesAsList.size();
        int startId = 10;
        int endId = 20;

        long sum = 0;
        long avgTime;

        timesAsList.sort(null);

        for (int i = startId; i < endId && i < n; i++) {
            sum += timesAsList.get(i);
        }

        avgTime = sum / (endId - startId);

        System.out.println(format("Init size: %10d | Time: %15d", initSize, avgTime));
    }

}
