package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RbtMapTest {

    @Test(expected = IllegalArgumentException.class)
    public void setValueShouldThrowExceptionWhenKeyIsNull() {
        //given
        RbtMap tree = new <Integer, Integer> RbtMap();

        //when
        tree.setValue(null, 1);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValueShouldThrowExceptionWhenValueIsNull() {
        //given
        RbtMap tree = new <String, Integer> RbtMap();

        //when
        tree.setValue("test", null);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueShouldThrowExceptionWhenKeyIsNull() {
        //given
        RbtMap tree = new <String, Integer> RbtMap();

        //when
        tree.getValue(null);

        //then
        assert false;
    }

    @Test
    public void getValueShouldReturnCorrectValueWhenTreeIsNotEmpty() {
        //given
        RbtMap tree = new <String, Integer> RbtMap();

        //when
        tree.setValue("test", 1);

        //then
        assertEquals(1, tree.getValue("test"));
    }

    @Test
    public void getValueShouldReturnNullWhenTreeIsEmpty() {
        //given
        RbtMap tree = new <String, Integer> RbtMap();

        //when
        //then
        assertEquals(null, tree.getValue("test"));
    }
}
