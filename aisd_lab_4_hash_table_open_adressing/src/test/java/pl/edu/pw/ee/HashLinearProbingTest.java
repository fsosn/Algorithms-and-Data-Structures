package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class HashLinearProbingTest {

    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInitialSizeIsLowerThanOne() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> unusedHash = new HashLinearProbing<>(initialSize);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPutMethodArgumentIsNull() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>(1);

        // when
        hash.put(null);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenGetMethodArgumentIsNull() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>(1);

        // when
        hash.get(null);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenDeleteMethodArgumentIsNull() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>(1);

        // when
        hash.delete(null);

        // then
        assert false;
    }

    @Test
    public void shouldCorrectlyAddNewElemsWhenNotExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashLinearProbing<>(5);
        String newElem = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newElem);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void shouldCorrectlyAddMultipleNewElemsWhenNotExistInHashTable() {
        // given
        HashTable<Integer> hash = new HashLinearProbing<>();
        Integer newElem;
        int numOfElemsToBePut = 1000;

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);

        for (int i = 0; i < numOfElemsToBePut; i++) {
            newElem = i;
            hash.put(newElem);
        }

        int nOfElemsAfterPut = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(numOfElemsToBePut, nOfElemsAfterPut);
    }

    @Test
    public void shouldCorrectlyReturnNumOfElemsAfterPuttingIdenticalElems() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String firstElem = "nothing special";
        String secondElem = "nothing special";
        String thirdElem = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);
        hash.put(firstElem);
        hash.put(secondElem);
        hash.put(thirdElem);
        int nOfElemsAfterPut = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void shouldCorrectlyReturnNumOfElemsAfterDeletingSameValueMultipleTimes() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String elem = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);
        hash.put(elem);
        int nOfElemsAfterPut = getNumOfElems(hash);
        for (int i = 0; i < 10; i++) {
            hash.delete("nothing special");
        }
        int nOfElemsAfterDelete = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
        assertEquals(0, nOfElemsAfterDelete);
    }

    @Test
    public void getShouldCorrectlyReturnElemFromHash() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String input = "abcd";
        String output;

        // when
        int nOfElemsBeforePutAndGet = getNumOfElems(hash);
        hash.put(input);
        output = hash.get("abcd");
        int nOfElemsAfterPutAndGet = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBeforePutAndGet);
        assertEquals(1, nOfElemsAfterPutAndGet);
        assertEquals(input, output);
    }

    @Test
    public void getShouldReturnNullWhenElemIsNotInHash() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String output;

        // when
        output = hash.get("abcd");

        // then
        assertEquals(null, output);
    }

    @Test
    public void shouldNotThrowExceptionWhenDeleteElemWhichIsNotInHash() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();

        // when
        hash.delete("abcd");

        // then
        assert true;
    }

    @Test
    public void shouldCorrectlyReturnNumberOfElemsWhenDeleteFromHash() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String input = "to_be_deleted";

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);
        hash.put(input);
        int nOfElemsAfterPut = getNumOfElems(hash);

        hash.delete(input);
        int nOfElemsAfterDelete = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
        assertEquals(0, nOfElemsAfterDelete);
    }

    @Test
    public void shouldCorrectlyReturnNumberOfElemsWhenDeleteMultipleElemsFromHash() {
        // given
        HashTable<Integer> hash = new HashLinearProbing<>();
        int numOfElemsToBePut = 9_876;
        int numOfElemsToBeDeleted = 5_432;

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);

        for (int i = 0; i < numOfElemsToBePut; i++) {
            hash.put(i);
        }

        int nOfElemsAfterPut = getNumOfElems(hash);

        for (int i = 0; i < numOfElemsToBeDeleted; i++) {
            hash.delete(i);
        }

        int nOfElemsAfterDelete = getNumOfElems(hash);
        int expectedNOfElemsAfterDelete = nOfElemsAfterPut - numOfElemsToBeDeleted;

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(numOfElemsToBePut, nOfElemsAfterPut);
        assertEquals(expectedNOfElemsAfterDelete, nOfElemsAfterDelete);
    }

    @Test
    public void getShouldCorrectlyReturnEachOfTheElemsWithIdenticalHashCode() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String firstInput = "AaAaAa";
        String secondInput = "AaAaBB";
        String thirdInput = "AaBBAa";
        String firstOutput, secondOutput, thirdOutput;

        // when
        int nOfElemsBeforePutAndGet = getNumOfElems(hash);

        hash.put(firstInput);
        hash.put(secondInput);
        hash.put(thirdInput);

        firstOutput = hash.get(firstInput);
        secondOutput = hash.get(secondInput);
        thirdOutput = hash.get(thirdInput);

        int nOfElemsAfterPutAndGet = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBeforePutAndGet);
        assertEquals(3, nOfElemsAfterPutAndGet);
        assertEquals(firstInput, firstOutput);
        assertEquals(secondInput, secondOutput);
        assertEquals(thirdInput, thirdOutput);
    }

    @Test
    public void shouldCorrectlyDeleteFirstElemAndThenGetOtherElems() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String firstInput = "AaAaAa";
        String secondInput = "AaAaBB";
        String thirdInput = "AaBBAa";
        String firstOutput, secondOutput, thirdOutput;

        // when
        int nOfElemsBefore = getNumOfElems(hash);

        hash.put(firstInput);
        hash.put(secondInput);
        hash.put(thirdInput);

        hash.delete(firstInput);
        firstOutput = hash.get(firstInput);
        secondOutput = hash.get(secondInput);
        thirdOutput = hash.get(thirdInput);

        int nOfElemsAfter = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBefore);
        assertEquals(2, nOfElemsAfter);
        assertEquals(null, firstOutput);
        assertEquals(secondInput, secondOutput);
        assertEquals(thirdInput, thirdOutput);
    }

    @Test
    public void shouldCorrectlyDeleteSecondElemAndThenGetOtherElems() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String firstInput = "AaAaAa";
        String secondInput = "AaAaBB";
        String thirdInput = "AaBBAa";
        String firstOutput, secondOutput, thirdOutput;

        // when
        int nOfElemsBefore = getNumOfElems(hash);

        hash.put(firstInput);
        hash.put(secondInput);
        hash.put(thirdInput);

        hash.delete(secondInput);
        firstOutput = hash.get(firstInput);
        secondOutput = hash.get(secondInput);
        thirdOutput = hash.get(thirdInput);

        int nOfElemsAfter = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBefore);
        assertEquals(2, nOfElemsAfter);
        assertEquals(firstInput, firstOutput);
        assertEquals(null, secondOutput);
        assertEquals(thirdInput, thirdOutput);
    }

    @Test
    public void shouldCorrectlyDeleteThirdElemAndThenGetOtherElems() {
        // given
        HashTable<String> hash = new HashLinearProbing<>(10);
        String firstInput = "AaAaAa";
        String secondInput = "AaAaBB";
        String thirdInput = "AaBBAa";
        String firstOutput, secondOutput, thirdOutput;

        // when
        int nOfElemsBefore = getNumOfElems(hash);

        hash.put(firstInput);
        hash.put(secondInput);
        hash.put(thirdInput);

        hash.delete(thirdInput);
        firstOutput = hash.get(firstInput);
        secondOutput = hash.get(secondInput);
        thirdOutput = hash.get(thirdInput);

        int nOfElemsAfter = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBefore);
        assertEquals(2, nOfElemsAfter);
        assertEquals(firstInput, firstOutput);
        assertEquals(secondInput, secondOutput);
        assertEquals(null, thirdOutput);
    }

}
