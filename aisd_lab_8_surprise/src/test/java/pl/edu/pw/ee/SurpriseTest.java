package pl.edu.pw.ee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SurpriseTest {

    @Test
    public void test() {
        int[] array = new int[]{3, -2, -1, -1, -1, -2, -4, -10, -10, -10, -8, 10, 3, -2, -1, -1, -1, -2, -4, -10, -10, -10, -8, 10};

        Surprise surprise = new Surprise();

        int value = surprise.countMaxSum(array);

        assertEquals(value, 22);
    }

}
