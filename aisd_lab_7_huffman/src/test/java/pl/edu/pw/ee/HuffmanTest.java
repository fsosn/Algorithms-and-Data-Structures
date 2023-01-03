package pl.edu.pw.ee;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import static java.util.logging.Level.SEVERE;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HuffmanTest {

    private Huffman huffman;
    private static final String PATH_TO_ROOT_DIR = "src/main/resources";
    private static final Logger LOG = Logger.getLogger(HuffmanTest.class.getName());

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToFileIsNull() {
        //given
        String invalidPathToFile = null;

        //when
        FileOperation.checkPathToFile(invalidPathToFile);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToFileIsEmpty() {
        //given
        String invalidPathToFile = "";

        //when
        FileOperation.checkPathToFile(invalidPathToFile);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToFileDoesNotExist() {
        //given
        String invalidPathToFile = "src/main/resources/doesnotexist.txt";

        //when
        FileOperation.checkPathToFile(invalidPathToFile);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirIsNull() {
        //given
        huffman = new Huffman();
        String invalidPathString = null;

        //when
        huffman.huffman(invalidPathString, true);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirIsEmpty() {
        //given
        huffman = new Huffman();
        String invalidPathString = "";

        //when
        huffman.huffman(invalidPathString, true);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirDoesNotExist() {
        //given
        huffman = new Huffman();
        String invalidPathString = "src/path/does/not/exist/";

        //when
        huffman.huffman(invalidPathString, true);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirIsNotDir() {
        //given
        huffman = new Huffman();
        String invalidPathString = "src/main/resources" + FileOperation.INPUT_FILE;

        //when
        huffman.huffman(invalidPathString, true);

        //then
        assert false;
    }

    @Test
    public void valuesShouldBeAssignedCorrectlyWhenConstructingChildNode() {
        //given
        Node node;
        char givenCharacter = 'a';
        int givenFrequency = 1;

        //when
        node = new Node(givenCharacter, givenFrequency);
        char retrievedCharacter = node.getCharacter();
        int retrievedFrequency = node.getFrequency();
        Node leftChild = node.getLeftChild();
        Node rightChild = node.getRightChild();
        boolean isLeaf = node.isLeaf();

        //then
        assertEquals(givenCharacter, retrievedCharacter);
        assertEquals(givenFrequency, retrievedFrequency);
        assertEquals(null, leftChild);
        assertEquals(null, rightChild);
        assertEquals(true, isLeaf);
    }

    @Test
    public void valuesShouldBeAssignedCorrectlyWhenConstructingParentNode() {
        //given
        Node parent;
        Node leftChild;
        Node rightChild;

        char givenCharacterLeftChild = 'a';
        char givenCharacterRightChild = 'b';
        char expectedCharacterParent = '\0';

        int givenFrequencyLeftChild = 1;
        int givenFrequencyRightChild = 2;
        int expectedFrequencyParent = givenFrequencyLeftChild + givenFrequencyRightChild;

        //when
        leftChild = new Node(givenCharacterLeftChild, givenFrequencyLeftChild);
        rightChild = new Node(givenCharacterRightChild, givenFrequencyRightChild);
        parent = new Node(leftChild, rightChild);

        char retrievedCharacter = parent.getCharacter();
        int retrievedFrequency = parent.getFrequency();
        boolean isLeaf = parent.isLeaf();

        Node retrievedLeftChild = parent.getLeftChild();
        Node retrievedRightChild = parent.getRightChild();

        char retrievedCharacterLeftChild = parent.getLeftChild().getCharacter();
        char retrievedCharacterRightChild = parent.getRightChild().getCharacter();

        int retrievedFrequencyLeftChild = parent.getLeftChild().getFrequency();
        int retrievedFrequencyRightChild = parent.getRightChild().getFrequency();

        //then
        assertEquals(expectedCharacterParent, retrievedCharacter);
        assertEquals(expectedFrequencyParent, retrievedFrequency);
        assertEquals(false, isLeaf);
        assertEquals(leftChild, retrievedLeftChild);
        assertEquals(rightChild, retrievedRightChild);
        assertEquals(givenCharacterLeftChild, retrievedCharacterLeftChild);
        assertEquals(givenCharacterRightChild, retrievedCharacterRightChild);
        assertEquals(givenFrequencyLeftChild, retrievedFrequencyLeftChild);
        assertEquals(givenFrequencyRightChild, retrievedFrequencyRightChild);
    }

    @Test
    public void valuesShouldBeAssignedCorrectlyWhenMakingParentNodesChildrenOfAnotherNode() {
        //given
        Node parent;
        Node leftChild;
        Node rightChild;
        int[] freq = {1, 2, 3, 4};
        int expectedFrequency = freq[0] + freq[1] + freq[2] + freq[3];

        //when
        leftChild = new Node(new Node('a', freq[0]), new Node('b', freq[1]));
        rightChild = new Node(new Node('c', freq[2]), new Node('d', freq[3]));
        parent = new Node(leftChild, rightChild);
        char retrievedCharacter = parent.getCharacter();
        int retrievedFrequency = parent.getFrequency();
        Node retrievedLeftChild = parent.getLeftChild();
        Node retrievedRightChild = parent.getRightChild();

        //then
        assertEquals('\0', retrievedCharacter);
        assertEquals(expectedFrequency, retrievedFrequency);
        assertEquals(false, parent.isLeaf());
        assertEquals(leftChild, retrievedLeftChild);
        assertEquals(rightChild, retrievedRightChild);
    }

    @Test
    public void charFrequencyMapSizeShouldBeEqualToNumOfDiffCharsInTextFile() {
        //given
        FileRead read = new FileRead(PATH_TO_ROOT_DIR);
        int actualMapSize;
        int expectedMapSize = countNumOfDifferentCharsInInputFile();

        //when
        actualMapSize = read.charFrequenciesMap().size();

        //then
        assertEquals(expectedMapSize, actualMapSize);
    }

    @Test
    public void charFrequencyValuesInMapShouldBeCorrect() {
        //given
        FileRead read = new FileRead(PATH_TO_ROOT_DIR);
        char firstChar = 'a';
        char secondChar = ' ';
        char thirdChar = '\n';
        List<Integer> list = countFrequenciesOfThreeCharsInInputFile(firstChar, secondChar, thirdChar);
        Map<Character, Integer> charFrequencies;

        //when
        charFrequencies = read.charFrequenciesMap();
        int retrievedFrequencyForA = charFrequencies.get(firstChar);
        int retrievedFrequencyForSpace = charFrequencies.get(secondChar);
        int retrievedFrequencyForNewline = charFrequencies.get(thirdChar);

        //then
        assertEquals(list.get(0), retrievedFrequencyForA);
        assertEquals(list.get(1), retrievedFrequencyForSpace);
        assertEquals(list.get(2), retrievedFrequencyForNewline);
    }

    @Test
    public void shouldReturnCorrectNumOfBitsWhenCompress() {
        //given
        huffman = new Huffman();
        long expectedNumberOfBits = fileSizeInBits();

        //when
        int actualNumberOfBits = huffman.huffman(PATH_TO_ROOT_DIR, true);

        //then
        assertEquals((int) expectedNumberOfBits, actualNumberOfBits);
    }

    @Test
    public void shouldReturnCorrectNumOfCharsWhenDecompress() {
        //given
        huffman = new Huffman();
        int expectedNumOfChars = countAllCharsInInputFile();

        //when
        int actualNumOfChars = huffman.huffman(PATH_TO_ROOT_DIR, false);

        //then
        assertEquals(expectedNumOfChars, actualNumOfChars);
    }
    
    @Test
    public void shouldReturnCorrectNumOfBitsWhenCompressAndCorrectNumOfCharsWhenDecompress() {
        //given
        huffman = new Huffman();
        long expectedNumberOfBits = fileSizeInBits();
        int expectedNumOfChars = countAllCharsInInputFile();

        //when
        int actualNumberOfBits = huffman.huffman(PATH_TO_ROOT_DIR, true);
        int actualNumOfChars = huffman.huffman(PATH_TO_ROOT_DIR, false);

        //then
        assertEquals((int) expectedNumberOfBits, actualNumberOfBits);
        assertEquals(expectedNumOfChars, actualNumOfChars);
    }
    

    private int countAllCharsInInputFile() {
        String filePath = PATH_TO_ROOT_DIR + FileOperation.INPUT_FILE;

        int numOfChars = 0;
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.read() != -1) {
                numOfChars++;
            }
            br.close();
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while counting chars in input file.", e);
        }

        return numOfChars;
    }

    private int countNumOfDifferentCharsInInputFile() {
        String filePath = PATH_TO_ROOT_DIR + FileOperation.INPUT_FILE;

        List<Integer> charList = new ArrayList<>();
        int character;
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((character = br.read()) != -1) {
                if (!charList.contains(character)) {
                    charList.add(character);
                }
            }
            br.close();
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while counting chars in input file.", e);
        }

        return charList.size();
    }

    private List countFrequenciesOfThreeCharsInInputFile(char firstChar, char secondChar, char thirdChar) {
        String filePath = PATH_TO_ROOT_DIR + FileOperation.INPUT_FILE;

        List<Integer> frequencies = new ArrayList<>(3);
        frequencies.add(0);
        frequencies.add(0);
        frequencies.add(0);

        int character;
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((character = br.read()) != -1) {
                if (character == firstChar) {
                    frequencies.set(0, frequencies.get(0) + 1);
                } else if (character == secondChar) {
                    frequencies.set(1, frequencies.get(1) + 1);
                } else if (character == thirdChar) {
                    frequencies.set(2, frequencies.get(2) + 1);
                }
            }
            br.close();
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while counting chars in input file.", e);
        }

        return frequencies;
    }

    private long fileSizeInBits() {
        Path filePath = Paths.get(PATH_TO_ROOT_DIR + FileOperation.COMPRESSED_FILE);

        long numOfBytes = 0;
        try {
            numOfBytes = Files.size(filePath);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while getting file size (in bits) from compressed file.", e);
        }

        return numOfBytes * 8;
    }
}
