package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HeapTest {

    @Test(expected = IllegalArgumentException.class)
    public void putNullParameterCheck() {
        //given
        List<Double> list = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        Heap heap = new Heap(list);

        //when
        heap.put(null);

        //then
        assert false;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void popFromEmptyHeap() {
        //given
        Heap emptyHeap = new Heap(Collections.emptyList());

        //when
        emptyHeap.pop();

        //then
        assert false;
    }

    @Test(expected = NullPointerException.class)
    public void nullListCheck() {
        //given
        List<String> nullList = null;

        //when
        Heap heap = new Heap(nullList);
        heap.build();

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullElementsInInputListConstructorTest() {
        //given
        List<String> listContainingNulls = new ArrayList<>(Arrays.asList("a", null, "b", null, "c"));

        //when
        Heap heap = new Heap(listContainingNulls);
        heap.build();

        //then
        assert false;
    }

    @Test
    public void buildTest() {
        //given
        List<Double> inputList = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        Heap heap = new Heap(inputList);

        //when
        heap.build();

        //then
        List<Double> expectedOutputList = new ArrayList<>(Arrays.asList(5.0, 4.0, 3.0, 1.0, 2.0));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void buildTestWithNegativeData() {
        //given
        List<Double> inputList = new ArrayList<>(Arrays.asList(-5.0, -3.0, -4.0, -1.0, -2.0));
        Heap heap = new Heap(inputList);

        //when
        heap.build();

        //then
        List<Double> expectedOutputList = new ArrayList<>(Arrays.asList(-1.0, -2.0, -4.0, -3.0, -5.0));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void buildTestWithConstantData() {
        //given
        List<Integer> inputList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        Heap heap = new Heap(inputList);

        //when
        heap.build();

        //then
        List<Integer> expectedOutputList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void buildTestWithStringData() {
        //given
        List<String> inputList = new ArrayList<>(Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee"));
        Heap heap = new Heap(inputList);

        //when
        heap.build();

        //then
        List<String> expectedOutputList = new ArrayList<>(Arrays.asList("eee", "ddd", "ccc", "aaa", "bbb"));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void buildTestWithLargerData() {
        //given
        List<Integer> inputList = new ArrayList<>(Arrays.asList(4, 7, 3, 2, 19, 22, 33, 43, 2, 6, 1, 60, 61, 50, 102, 32, 1, 3, 7));
        Heap heap = new Heap(inputList);

        //when
        heap.build();

        //then
        List<Integer> expectedOutputList = new ArrayList<>(Arrays.asList(102, 43, 61, 32, 19, 60, 50, 7, 7, 6, 1, 4, 22, 3, 33, 2, 1, 3, 2));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void heapifyTest() {
        //given
        List<Integer> inputList = new ArrayList<>(Arrays.asList(4, 7, 3, 2, 19, 22, 33, 43));
        Heap heap = new Heap(inputList);

        //when
        heap.heapify(2, 7);

        //then
        List<Integer> expectedOutputList = new ArrayList<>(Arrays.asList(4, 7, 33, 2, 19, 22, 3, 43));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void putWithBuildTest() {
        //given
        List<Double> inputList = new ArrayList<>(Arrays.asList(7.5, 5.2, 8.1, 10.9, 20.4));
        Heap heap = new Heap(inputList);
        heap.build();

        //when
        heap.put(30.0);

        //then
        List<Double> expectedOutputList = new ArrayList<>(Arrays.asList(30.0, 10.9, 20.4, 7.5, 5.2, 8.1));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void putWithoutBuildTest() {
        //given
        List<Double> inputList = new ArrayList<>(Arrays.asList(7.5, 5.2, 8.1, 10.9, 20.4));
        Heap heap = new Heap(inputList);

        //when
        heap.put(30.0);

        //then
        List<Double> expectedOutputList = new ArrayList<>(Arrays.asList(30.0, 10.9, 20.4, 7.5, 5.2, 8.1));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void putLargeAmountOfItemsOnHeapTest() {
        //given
        List<Integer> list = new ArrayList<>();
        Heap heap = new Heap(list);
        int expectedSize = 1_000_000;

        //when
        for (int i = 0; i < expectedSize; i++) {
            heap.put(i);
        }
        int actualSize = list.size();

        //then
        boolean isMaxHeap = true;
        for (int i = 1; i < list.size(); i++) {
            int parent = (i - 1) / 2;
            if (list.get(i).compareTo(list.get(parent)) > 0) {
                isMaxHeap = false;
                break;
            }
        }
        assertTrue(isMaxHeap && actualSize == expectedSize);
    }

    @Test
    public void checkIfPoppedIsMaxElementOnHeap() {
        //given
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Heap heap = new Heap(list);
        heap.build();
        Integer maxElement = Collections.max(list);

        //when
        Comparable poppedElement = heap.pop();

        //then
        assertEquals(maxElement, poppedElement);
    }

    @Test
    public void popWithBuildTest() {
        //given
        List<Double> inputList = new ArrayList<>(Arrays.asList(1.5, 2.6, 3.7, 4.8, 5.9, 6.0));
        Heap heap = new Heap(inputList);
        heap.build();

        //when
        heap.pop();

        //then
        List<Double> expectedOutputList = new ArrayList<>(Arrays.asList(5.9, 4.8, 3.7, 1.5, 2.6));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void popWithoutBuildTest() {
        //given
        List<Double> inputList = new ArrayList<>(Arrays.asList(1.5, 2.6, 3.7, 4.8, 5.9, 6.0));
        Heap heap = new Heap(inputList);

        //when
        heap.pop();

        //then
        List<Double> expectedOutputList = new ArrayList<>(Arrays.asList(5.9, 4.8, 3.7, 1.5, 2.6));
        assertEquals(inputList, expectedOutputList);
    }

    @Test
    public void popLargeAmountOfItemsFromHeap() {
        //given
        List<Integer> list = new ArrayList<>();
        int firstSize = 1_000_000;
        int secondSize = 500_000;

        Random random = new Random(1);
        for (int i = 0; i < firstSize; i++) {
            int randInt = random.nextInt();
            list.add(randInt);
        }

        Heap heap = new Heap(list);
        heap.build();

        //when
        for (int i = 0; i < secondSize; i++) {
            heap.pop();
        }
        int actualSize = list.size();

        //then
        int expectedSize = firstSize - secondSize;

        boolean isMaxHeap = true;
        for (int i = 1; i < list.size(); i++) {
            int parent = (i - 1) / 2;
            if (list.get(i).compareTo(list.get(parent)) > 0) {
                isMaxHeap = false;
                break;
            }
        }
        assertTrue(isMaxHeap && expectedSize == actualSize);
    }

}
