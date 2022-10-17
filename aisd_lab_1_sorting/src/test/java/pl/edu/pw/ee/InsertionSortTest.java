package pl.edu.pw.ee;

import java.util.Random;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class InsertionSortTest {

    private static final double EPS = 0;
    private InsertionSort insertionSort;
    private Random random;

    @Before
    public void setup() {
        insertionSort = new InsertionSort();
    }

    @Test(expected = IllegalArgumentException.class)
    public void parameterNullCheck() {
        //given
        double[] nums = null;

        //when
        insertionSort.sort(nums);

        //then
        assert false;
    }

    @Test
    public void shouldSortCorrectlyWhenInputArrayIsEmpty() {
        //given
        double[] nums = {};

        //when
        insertionSort.sort(nums);

        //then
        int expectedSize = 0;
        assertEquals(expectedSize, nums.length);
    }

    @Test
    public void shouldSortCorrectlyWhenOnlyOneElem() {
        //given
        double[] nums = {0};

        //when
        insertionSort.sort(nums);

        //then
        double[] expected = {0};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortCorrectlyWhenAllInputInAscOrder() {
        //given
        double[] nums = {1, 2, 3, 4, 5};

        //when
        insertionSort.sort(nums);

        //then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortCorrectlyWhenAllInputInDescOrder() {
        //given
        double[] nums = {5, 4, 3, 2, 1};

        //when
        insertionSort.sort(nums);

        //then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortCorrectlyWhenInputInNoOrder() {
        //given
        double[] nums = {2, 5, 1, 3, 4};

        //when
        insertionSort.sort(nums);

        //then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortCorrectlyWhenNegativeInput() {
        //given
        double[] nums = {-2, -5, -1, -3, -4};

        //when
        insertionSort.sort(nums);

        //then
        double[] expected = {-5, -4, -3, -2, -1};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortCorrectlyWhenZeroInput() {
        //given
        double[] nums = {0, 0, 0, 0, 0};

        //when
        insertionSort.sort(nums);

        //then
        double[] expected = {0, 0, 0, 0, 0};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortCorrectlyWhenInputRandomlyGenerated() {
        //given
        random = new Random(100);
        double[] nums = random.doubles(100000).toArray();

        //when
        insertionSort.sort(nums);

        //then
        boolean sorted = true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                sorted = false;
                break;
            }
        }
        assertTrue(sorted);
    }
}
