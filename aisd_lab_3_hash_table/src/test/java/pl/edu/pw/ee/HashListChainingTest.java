package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HashListChainingTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenHashListSizeIsNegative() {
        //given
        int size = -1;

        //when
        HashListChaining hashListChaining = new HashListChaining(size);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenHashListSizeLowerThanOne() {
        //given
        int size = 0;

        //when
        HashListChaining hashListChaining = new HashListChaining(size);

        //then
        assert false;
    }

    @Test
    public void shouldNotThrowExceptionWhenHashListSizeIsPositive() {
        //given
        int size = 1;

        //when
        HashListChaining hashListChaining = new HashListChaining(size);

        //then
        assert true;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAttemptingToAddNullValue() {
        //given
        HashListChaining hashListChaining = new HashListChaining(1);

        //when
        hashListChaining.add(null);

        //then
        assert false;
    }

    @Test
    public void shouldNotThrowExceptionWhenAttemptingToAddNonNullValue() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(1);
        String string = "abc";

        //when
        hashListChaining.add(string);

        //then
        assert true && hashListChaining.getNumberOfElements() == 1;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAttemptingToGetNullValue() {
        //given
        HashListChaining hashListChaining = new HashListChaining(1);

        //when
        hashListChaining.get(null);

        //then
        assert false;
    }

    @Test
    public void shouldNotThrowExceptionWhenAttemptingToGetNullValue() {
        //given
        HashListChaining<Double> hashListChaining = new HashListChaining<>(1);

        //when
        hashListChaining.get(1.23);

        //then
        assert true;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAttemptingToDeleteNullValue() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(1);

        //when
        hashListChaining.delete(null);

        //then
        assert false;
    }

    @Test
    public void shouldNotThrowExceptionWhenAttemptingToDeleteNonNullValue() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(1);

        //when
        hashListChaining.delete("abc");

        //then
        assert true && hashListChaining.getNumberOfElements() == 0;
    }

    @Test
    public void getShouldReturnCorrectAddedValue() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(1);
        String input = "abc";
        String expected;
        hashListChaining.add(input);

        //when
        expected = hashListChaining.get(input);

        //then
        assertEquals(expected, input);
    }

    @Test
    public void numberOfElementsShouldBeCorrectAfterAddingValues() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(2);

        //when
        hashListChaining.add("abc");
        hashListChaining.add("cba");
        hashListChaining.add("bac");

        //then
        assertEquals(3, hashListChaining.getNumberOfElements());
    }

    @Test
    public void numberOfElementsShouldBeCorrectAfterAddingAndDeleting() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(5);

        //when
        hashListChaining.add("abc");
        hashListChaining.add("cba");
        hashListChaining.add("bac");
        hashListChaining.delete("abc");
        hashListChaining.delete("cba");

        //then
        assertEquals(1, hashListChaining.getNumberOfElements());
    }

    @Test
    public void shouldReturnCorrectLoadFactor() {
        //given
        int size = 7;
        HashListChaining<String> hashListChaining = new HashListChaining<>(size);
        hashListChaining.add("abc");
        hashListChaining.add("cba");
        hashListChaining.add("bac");
        double expectedLoadFactor = hashListChaining.getNumberOfElements() / (double) size;

        //when
        double actualLoadFactor = hashListChaining.countLoadFactor();

        //then
        assert expectedLoadFactor == actualLoadFactor;
    }

    @Test
    public void shouldNotAddElementWhichIsAlreadyInHashList() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(5);

        //when
        hashListChaining.add("abc");
        hashListChaining.add("abc");
        hashListChaining.add("abc");

        //then
        assertEquals(1, hashListChaining.getNumberOfElements());
    }

    @Test
    public void shouldDeleteFirstElementFromHashList() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(1);

        String firstElement = "aaa";
        String secondElement = "bbb";
        String thirdElement = "ccc";

        hashListChaining.add(firstElement);
        hashListChaining.add(secondElement);
        hashListChaining.add(thirdElement);

        //when
        hashListChaining.delete(firstElement);

        //then
        assertEquals(null, hashListChaining.get(firstElement));
        assertEquals(secondElement, hashListChaining.get(secondElement));
        assertEquals(thirdElement, hashListChaining.get(thirdElement));
        assertEquals(2, hashListChaining.getNumberOfElements());
    }

    @Test
    public void shouldDeleteMiddleElementFromHashList() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(1);

        String firstElement = "aaa";
        String secondElement = "bbb";
        String thirdElement = "ccc";

        hashListChaining.add(firstElement);
        hashListChaining.add(secondElement);
        hashListChaining.add(thirdElement);

        //when
        hashListChaining.delete(secondElement);

        //then
        assertEquals(firstElement, hashListChaining.get(firstElement));
        assertEquals(null, hashListChaining.get(secondElement));
        assertEquals(thirdElement, hashListChaining.get(thirdElement));
        assertEquals(2, hashListChaining.getNumberOfElements());
    }

    @Test
    public void shouldDeleteLastElementFromHashList() {
        //given
        HashListChaining<String> hashListChaining = new HashListChaining<>(1);

        String firstElement = "aaa";
        String secondElement = "bbb";
        String thirdElement = "ccc";

        hashListChaining.add(firstElement);
        hashListChaining.add(secondElement);
        hashListChaining.add(thirdElement);

        //when
        hashListChaining.delete(thirdElement);

        //then
        assertEquals(firstElement, hashListChaining.get(firstElement));
        assertEquals(secondElement, hashListChaining.get(secondElement));
        assertEquals(null, hashListChaining.get(thirdElement));
        assertEquals(2, hashListChaining.getNumberOfElements());
    }

}
