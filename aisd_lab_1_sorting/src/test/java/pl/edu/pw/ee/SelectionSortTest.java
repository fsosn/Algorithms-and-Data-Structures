package pl.edu.pw.ee;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class SelectionSortTest {

    private static final double EPS = 0;
    private SelectionSort selectionSort;

    @Before
    public void setup() {
        selectionSort = new SelectionSort();
    }

    @Test(expected = IllegalArgumentException.class)
    public void parameterNullCheck() {
        //given
        double[] nums = null;
        //when
        this.selectionSort.sort(nums);
        //then
        assert false;
    }

    @Test
    public void shouldSortCorreclyWhenInputArrayIsEmpty() {
        //given
        double[] nums = {};

        //when
        selectionSort.sort(nums);

        //then
        int expectedSize = 0;
        assertEquals(expectedSize, nums.length);
    }

    @Test
    public void shouldSortCorrectlyWhenOnlyOneElem() {
        //given
        double[] nums = {0};

        //when
        selectionSort.sort(nums);

        //then
        double[] expected = {0};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortCorrectlyWhenAllInputInAscOrder() {
        //given
        double[] nums = {1, 2, 3, 4, 5};

        //when
        selectionSort.sort(nums);

        //then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test

    public void shouldSortCorrectlyWhenAllInputInDescOrder() {
        //given
        double[] nums = {5, 4, 3, 2, 1};

        //when
        selectionSort.sort(nums);

        //then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, EPS);
    }
}
