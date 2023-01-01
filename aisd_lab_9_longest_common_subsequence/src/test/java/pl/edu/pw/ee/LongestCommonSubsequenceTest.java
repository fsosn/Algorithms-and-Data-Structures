package pl.edu.pw.ee;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongestCommonSubsequenceTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream initialOutput = System.out;
    private final String nl = System.lineSeparator();

    @Before
    public void setNewStream() {
        System.setOut(new PrintStream(output));
    }

    @After
    public void restoreInitialStream() {
        System.setOut(initialOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTopStringIsNull() {
        //given
        LongestCommonSubsequence lcs;

        //when
        lcs = new LongestCommonSubsequence(null, "test");
        lcs.findLCS();

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTopStringIsEmpty() {
        //given
        LongestCommonSubsequence lcs;

        //when
        lcs = new LongestCommonSubsequence("", "test");
        lcs.findLCS();

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLeftStringIsNull() {
        //given
        LongestCommonSubsequence lcs;

        //when
        lcs = new LongestCommonSubsequence("test", null);
        lcs.findLCS();

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLeftStringIsEmpty() {
        //given
        LongestCommonSubsequence lcs;

        //when
        lcs = new LongestCommonSubsequence("test", "");
        lcs.findLCS();

        //then
        assert false;
    }

    @Test
    public void shouldReturnCorrectValueDirectionAndVisitedStatusFromMatrixElement() {
        //given
        MatrixElement matrixElement;
        int givenValue = 47;
        char givenDirection = MatrixElement.DIRECTION_SYMBOL_UP;
        boolean givenVisitedStatus = true;

        //when
        matrixElement = new MatrixElement(givenValue, givenDirection);
        matrixElement.setVisited(givenVisitedStatus);
        int retrievedValue = matrixElement.getValue();
        char retrievedDirection = matrixElement.getDirection();
        boolean retrievedVisitedStatus = matrixElement.getVisited();

        //then
        assertEquals(givenValue, retrievedValue);
        assertEquals(givenDirection, retrievedDirection);
        assertEquals(givenVisitedStatus, retrievedVisitedStatus);
    }

    @Test
    public void shouldReturnCorrectResultWhenStringsDoHaveCommonSubsequence() {
        //given
        LongestCommonSubsequence lcs;
        String topStr = "FRANCISZKAŃSKA";
        String leftStr = "WATYKAŃSKI";
        String expectedResult = "AKAŃSK";

        //when
        lcs = new LongestCommonSubsequence(topStr, leftStr);
        String actualResult = lcs.findLCS();

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnCorrectResultWhenStringsDoNotHaveCommonSubsequence() {
        //given
        LongestCommonSubsequence lcs;
        String topStr = "ABC";
        String leftStr = "XYZ";

        //when
        lcs = new LongestCommonSubsequence(topStr, leftStr);
        String result = lcs.findLCS();

        //then
        assertEquals("", result);
    }

    @Test
    public void shouldReturnCorrectResultWhenStringsAreEqual() {
        //given
        LongestCommonSubsequence lcs;
        String topStr = "ABC";
        String leftStr = topStr;
        String expectedResult = topStr;

        //when
        lcs = new LongestCommonSubsequence(topStr, leftStr);
        String actualResult = lcs.findLCS();

        //then
        assertEquals(topStr, leftStr);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldCorrectlyDisplayMatrixWhenStringsDoesNotContainEscapeSequences() {
        //given
        LongestCommonSubsequence lcs;
        String topStr = "abcdefg";
        String leftStr = "bdeg";
        String expectedOutput
                = "+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "|     |     |  a  |  b  |  c  |  d  |  e  |  f  |  g  |" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "|     |  0  |  0  |  0  |  0  |  0  |  0  |  0  |  0  |" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |\\    |     |     |     |     |     |" + nl
                + "|  b  |  0  |  0  |  1  |< 1  |  1  |  1  |  1  |  1  |" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |\\    |     |     |     |" + nl
                + "|  d  |  0  |  0  |  1  |  1  |  2  |  2  |  2  |  2  |" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |\\    |     |     |" + nl
                + "|  e  |  0  |  0  |  1  |  1  |  2  |  3  |< 3  |  3  |" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |\\    |" + nl
                + "|  g  |  0  |  0  |  1  |  1  |  2  |  3  |  3  |  4  |" + nl
                + "|     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl;

        //when
        lcs = new LongestCommonSubsequence(topStr, leftStr);
        lcs.display();

        //then
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldCorrectlyDisplayMatrixWhenStringsContainEscapeSequences() {
        //given
        LongestCommonSubsequence lcs;
        String topStr = "\rA\nste\trix";
        String leftStr = "\r\nObel\tix";
        String expectedOutput
                = "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "|     |     | \\r  |  A  | \\n  |  s  |  t  |  e  | \\t  |  r  |  i  |  x  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "|     |  0  |  0  |  0  |  0  |  0  |  0  |  0  |  0  |  0  |  0  |  0  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |\\    |     |     |     |     |     |     |     |     |     |" + nl
                + "| \\r  |  0  |  1  |< 1  |  1  |  1  |  1  |  1  |  1  |  1  |  1  |  1  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |\\    |     |     |     |     |     |     |     |" + nl
                + "| \\n  |  0  |  1  |  1  |  2  |< 2  |< 2  |  2  |  2  |  2  |  2  |  2  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |  ^  |     |     |     |     |     |" + nl
                + "|  O  |  0  |  1  |  1  |  2  |  2  |  2  |  2  |  2  |  2  |  2  |  2  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |  ^  |     |     |     |     |     |" + nl
                + "|  b  |  0  |  1  |  1  |  2  |  2  |  2  |  2  |  2  |  2  |  2  |  2  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |\\    |     |     |     |     |" + nl
                + "|  e  |  0  |  1  |  1  |  2  |  2  |  2  |  3  |  3  |  3  |  3  |  3  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |  ^  |     |     |     |     |" + nl
                + "|  l  |  0  |  1  |  1  |  2  |  2  |  2  |  3  |  3  |  3  |  3  |  3  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |\\    |     |     |     |" + nl
                + "| \\t  |  0  |  1  |  1  |  2  |  2  |  2  |  3  |  4  |< 4  |  4  |  4  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |     |     |\\    |     |" + nl
                + "|  i  |  0  |  1  |  1  |  2  |  2  |  2  |  3  |  4  |  4  |  5  |  5  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |\\    |" + nl
                + "|  x  |  0  |  1  |  1  |  2  |  2  |  2  |  3  |  4  |  4  |  5  |  6  |" + nl
                + "|     |     |     |     |     |     |     |     |     |     |     |     |" + nl
                + "+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" + nl;

        //when
        lcs = new LongestCommonSubsequence(topStr, leftStr);
        lcs.display();

        //then
        assertEquals(expectedOutput, output.toString());
    }
}
