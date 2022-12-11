package pl.edu.pw.ee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HuffmanTest {

    Huffman hf = new Huffman();
    FileRead fr = new FileRead();

    @Test
    public void testtt() {
        int n = hf.huffman("src/main/resources/niemanie.txt", true);
        assertEquals(2173, n);
    }

    @Test
    public void test() {
        assertEquals(fr.numOfChars("src/main/resources/niemanie.txt"), 12323);
    }
}
