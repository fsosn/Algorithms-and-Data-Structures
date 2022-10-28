package pl.edu.pw.ee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;

public class HashListChainingPerformanceTest {

    private List<String> words;
    private final List<Double> loadFactorsFirstTable = new ArrayList<>();
    private final List<Double> loadFactorsSecondTable = new ArrayList<>();
    private final String inputFileName = "src/test/java/pl/edu/pw/ee/TestData/example_words.txt";
    private final String outputFileName = "src/test/java/pl/edu/pw/ee/TestData/results.txt";

    private void wordsFromFileToList() {
        try {
            words = new ArrayList<>();
            Scanner scanner = new Scanner(new File(this.inputFileName));
            while (scanner.hasNext()) {
                words.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private long measureAverageTime(int size) {
        HashListChaining hashListChaining = new HashListChaining(size);
        long[] values = new long[30];

        for (int i = 0; i < this.words.size(); i++) {
            hashListChaining.add(this.words.get(i));
        }

        if (size % 4 == 0) {
            loadFactorsFirstTable.add(hashListChaining.countLoadFactor());
        } else {
            loadFactorsSecondTable.add(hashListChaining.countLoadFactor());
        }

        for (int j = 0; j < 30; j++) {
            long startTime = System.nanoTime();
            for (int i = 0; i < this.words.size(); i++) {
                hashListChaining.get(this.words.get(i));
            }
            long estimatedTime = System.nanoTime() - startTime;

            values[j] = estimatedTime;
        }

        Arrays.sort(values);

        long totalTime = 0L;
        for (int i = 10; i < 20; i++) {
            totalTime += values[i];
        }

        return totalTime / 10L;
    }

    public void resultsToOutputFile() {
        long[] rowValuesFirstTable = new long[7];
        long[] rowValuesSecondTable = new long[7];

        int[] sizesFirstTable = {4096, 8192, 16384, 32768, 65536, 131072, 262144};
        int[] sizesSecondTable = {4093, 8353, 16567, 32983, 65831, 131441, 262147};

        int n = rowValuesFirstTable.length;

        try ( Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.outputFileName), StandardCharsets.UTF_8))) {

            writer.write("First table:\n\nSize:\t\t\tAvg time [ns]:\t\t\tLoad factor:\n");
            for (int i = 0; i < n; i++) {
                rowValuesFirstTable[i] = measureAverageTime(sizesFirstTable[i]);
                writer.write(sizesFirstTable[i] + "\t\t\t" + rowValuesFirstTable[i] + "\t\t\t");
                if (rowValuesFirstTable[i] < 10_000_000) {
                    writer.write("\t");
                }
                writer.write(loadFactorsFirstTable.get(i) + "\n");
            }

            writer.write("\n\nSecond table:\n\nSize:\t\t\tAvg time [ns]:\t\t\tLoad factor:\n");
            for (int i = 0; i < n; i++) {
                rowValuesSecondTable[i] = measureAverageTime(sizesSecondTable[i]);
                writer.write(sizesSecondTable[i] + "\t\t\t" + rowValuesSecondTable[i] + "\t\t\t");
                if (rowValuesSecondTable[i] < 10_000_000) {
                    writer.write("\t");
                }
                writer.write(loadFactorsSecondTable.get(i) + "");

                if (i != 6) {
                    writer.write("\n");
                }
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void performanceTest() {
        wordsFromFileToList();
        resultsToOutputFile();
    }
}
