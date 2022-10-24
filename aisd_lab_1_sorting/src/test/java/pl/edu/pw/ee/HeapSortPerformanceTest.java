package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;

public class HeapSortPerformanceTest {

    private final int measurementPoints = 100;
    private final int multiplier = 200;
    private final int numberOfMeasurements = 5;

    private final HeapSort heapSort = new HeapSort();

    private final List<Long> timeInNanoSecondsForOptimistic = new ArrayList<>();
    private final List<Long> timeInNanoSecondsForPessimistic = new ArrayList<>();
    private final List<Long> timeInNanoSecondsForRandom = new ArrayList<>();

    private final List<Integer> amountOfData = new ArrayList<>();

    private double[] generateOptimisticData(int n) {
        double[] array = new double[n];

        for (int i = 0; i < n; i++) {
            array[i] = 1.0;
        }

        return array;
    }

    private double[] generatePessimisticData(int n) {
        double[] array = new double[n];

        int currentIndex = 0;
        for (int i = n; i > 0; i--) {
            array[currentIndex++] = i;
        }

        return array;
    }

    private double[] generateRandomData(int n) {
        Random random = new Random(1);
        return random.doubles(n).toArray();
    }

    private long measureOptimisticDataTime(int n) {
        long totalTime = 0L;

        for (int i = 0; i < numberOfMeasurements; i++) {
            double[] nums = generateOptimisticData(n);

            long startTime = System.nanoTime();
            heapSort.sort(nums);
            long estimatedTime = System.nanoTime() - startTime;

            totalTime += estimatedTime;
        }

        return totalTime / Long.valueOf(numberOfMeasurements);
    }

    private long measurePessimisticDataTime(int n) {
        long totalTime = 0L;

        for (int i = 0; i < numberOfMeasurements; i++) {
            double[] nums = generatePessimisticData(n);

            long startTime = System.nanoTime();
            heapSort.sort(nums);
            long estimatedTime = System.nanoTime() - startTime;

            totalTime += estimatedTime;
        }

        return totalTime / Long.valueOf(numberOfMeasurements);
    }

    private long measureRandomDataTime(int n) {
        long totalTime = 0L;

        for (int i = 0; i < numberOfMeasurements; i++) {
            double[] nums = generateRandomData(n);

            long startTime = System.nanoTime();
            heapSort.sort(nums);
            long estimatedTime = System.nanoTime() - startTime;

            totalTime += estimatedTime;
        }

        return totalTime / Long.valueOf(numberOfMeasurements);
    }

    private void produceDataForLists() {
        timeInNanoSecondsForOptimistic.add(measureOptimisticDataTime(1));
        timeInNanoSecondsForPessimistic.add(measurePessimisticDataTime(1));
        timeInNanoSecondsForRandom.add(measureRandomDataTime(1));
        amountOfData.add(1);

        for (int i = 1; i <= this.measurementPoints; i++) {
            int amount = i * multiplier;

            timeInNanoSecondsForOptimistic.add(measureOptimisticDataTime(amount));
            timeInNanoSecondsForPessimistic.add(measurePessimisticDataTime(amount));
            timeInNanoSecondsForRandom.add(measureRandomDataTime(amount));

            amountOfData.add(amount);
        }
    }

    private void printTimeLists() {
        System.out.println("HeapSort Performance (time in ns)");
        System.out.println("Amount      O           P           R");

        for (int i = 0; i <= this.measurementPoints; i++) {
            System.out.print(amountOfData.get(i) + "        ");
            System.out.print(timeInNanoSecondsForOptimistic.get(i) + "          ");
            System.out.print(timeInNanoSecondsForPessimistic.get(i) + "         ");
            System.out.println(timeInNanoSecondsForRandom.get(i));
        }
    }

    @Test
    public void testQuickSortPerformance() {
        produceDataForLists();
        printTimeLists();
    }

}
