package pl.edu.pw.ee;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RedBlackTreeTest {

    @Test(expected = IllegalArgumentException.class)
    public void putShouldThrowExceptionWhenKeyIsNull() {
        //given
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

        //when
        tree.put(null, 1);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void putShouldThrowExceptionWhenValueIsNull() {
        //given
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

        //when
        tree.put(1, null);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowExceptionWhenKeyIsNull() {
        //given
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

        //when
        tree.get(null);

        //then
        assert false;
    }

    @Test
    public void putCorrectValueShouldNotThrowException() {
        //given
        RedBlackTree<String, String> tree = new RedBlackTree<>();

        //when
        tree.put("A", "A");

        //then
        assert true;
    }

    @Test
    public void getShouldReturnNullIfTreeIsEmpty() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();

        //when
        Integer result = tree.get("a");

        //then
        assertEquals(result, null);
    }

    @Test
    public void getShouldReturnCorrectValueIfTreeIsNotEmpty() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("a", 1);

        //when
        Integer result = tree.get("a");

        //then
        assert result == 1;
    }

    @Test
    public void getShouldReturnCorrectValueWhenMultipleElemsInTree() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("b", 1);
        tree.put("a", 3);
        tree.put("c", 2);
        tree.put("e", 21);
        tree.put("d", 7);

        //when
        Integer result = tree.get("e");

        //then
        assert result == 21;
    }

    @Test
    public void putSameKeyOnTree() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("a", 1);
        tree.put("a", 2);

        //when
        Integer result = tree.get("a");

        //then
        assert result == 2;
    }

    @Test
    public void putValueOnTree() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("a", 1);
        tree.put("b", 1);

        //when
        Integer result1 = tree.get("a");
        Integer result2 = tree.get("b");

        //then
        assert result1 == 1;
        assert result2 == 1;
    }

    @Test
    public void shouldRemoveDeleteMax() {
        //given
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

        int max = 100;
        for (int i = 0; i <= max; i++) {
            tree.put(i, i);
        }
        //when
        tree.deleteMax();

        assertEquals(null, tree.get(max));
    }

    @Test
    public void shouldNotThrowExceptionIfDeleteMaxWhenTreeIsEmpty() {
        //given
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

        //when
        tree.deleteMax();

        assert true;
    }

    @Test
    public void putMultipleValuesOnTreeAndGetValue() {
        //given
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

        //when
        int max = 1000;
        for (int i = 0; i <= max; i++) {
            tree.put(i, i);
        }

        int result = tree.get(500);

        //then
        assert result == 500;
    }

    @Test
    public void preOrderShouldReturnCorrectResult() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("A", 1);
        tree.put("B", 1);
        tree.put("C", 1);
        tree.put("D", 1);
        tree.put("E", 1);

        //when
        String result = tree.getPreOrder();

        assertEquals("D:1 B:1 A:1 C:1 E:1", result);
    }

    @Test
    public void inOrderShouldReturnCorrectResult() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("A", 1);
        tree.put("B", 1);
        tree.put("C", 1);
        tree.put("D", 1);
        tree.put("E", 1);

        //when
        String result = tree.getInOrder();

        //then
        assertEquals("A:1 B:1 C:1 D:1 E:1", result);
    }

    @Test
    public void postOrderShouldReturnCorrectResult() {
        //given
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("A", 1);
        tree.put("B", 1);
        tree.put("C", 1);
        tree.put("D", 1);
        tree.put("E", 1);

        //when
        String result = tree.getPostOrder();

        //then
        assertEquals("A:1 C:1 B:1 E:1 D:1", result);
    }

    @Test
    public void randomDataShouldNotThrowAnyException() {
        //given
        Random random = new Random(1);

        //when
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        for (int i = 0; i < 1000; i++) {
            tree.put(i, random.nextInt());
        }
        tree.get(500);

        //then
        assert true;
    }
}
