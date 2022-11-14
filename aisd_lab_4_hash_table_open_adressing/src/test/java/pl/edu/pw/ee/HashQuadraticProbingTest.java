package pl.edu.pw.ee;

import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class HashQuadraticProbingTest {

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
        HashTable<Double> unusedHash = new HashQuadraticProbing<>(initialSize, 0.5, 0.5);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArgumentAIsEqualZero() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> unusedHash = new HashQuadraticProbing<>(initialSize, 0, 0.5);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArgumentBIsEqualZero() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> unusedHash = new HashQuadraticProbing<>(initialSize, 0.5, 0);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPutMethodArgumentIsNull() {
        // given
        HashTable<Double> hash = new HashQuadraticProbing<>(1, 0.5, 0.5);

        // when
        hash.put(null);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenGetMethodArgumentIsNull() {
        // given
        HashTable<Double> hash = new HashQuadraticProbing<>(1, 0.5, 0.5);

        // when
        hash.get(null);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenDeleteMethodArgumentIsNull() {
        // given
        HashTable<Double> hash = new HashQuadraticProbing<>(1, 0.5, 0.5);

        // when
        hash.delete(null);

        // then
        assert false;
    }

    @Test
    public void shouldCorrectlyAddNewElemsWhenNotExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashQuadraticProbing<>();
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
        HashTable<Integer> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
        String output;

        // when
        output = hash.get("abcd");

        // then
        assertEquals(null, output);
    }

    @Test
    public void shouldNotThrowExceptionWhenDeleteElemWhichIsNotInHash() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();

        // when
        hash.delete("abcd");

        // then
        assert true;
    }

    @Test
    public void shouldCorrectlyReturnNumberOfElemsWhenDeleteFromHash() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();
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
        HashTable<Integer> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
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
        HashTable<String> hash = new HashQuadraticProbing<>();
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

    @Test
    public void shouldNotGoIntoInfiniteLoop() {
        // given
        HashTable<Integer> hash = new HashQuadraticProbing<>(9, 2, 2);

        hash.put(0);
        hash.put(1);
        hash.put(2);
        hash.put(3);
        hash.put(4);
        hash.put(5);
        hash.put(7);

        hash.delete(0);
        hash.delete(1);
        hash.delete(2);
        hash.delete(3);
        hash.delete(4);
        hash.delete(5);
        hash.delete(7);

        //when
        hash.delete(7);
        Integer output = hash.get(7);

        // then
        assert true;
        assertEquals(null, output);
    }

}
