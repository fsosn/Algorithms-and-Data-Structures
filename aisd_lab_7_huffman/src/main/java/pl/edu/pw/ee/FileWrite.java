package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.Map;
import java.util.logging.Logger;
import static java.util.logging.Level.SEVERE;

public class FileWrite extends FileOperation {

    private final String pathToRootDir;

    private static final Logger LOG = Logger.getLogger(FileWrite.class.getName());

    public FileWrite(String pathToRootDir) {
        this.pathToRootDir = pathToRootDir;
    }

    public int encodeText(Map<Character, String> charCodes) {
        String inputFilePath = this.pathToRootDir + INPUT_FILE;
        String compressedFilePath = this.pathToRootDir + COMPRESSED_FILE;

        FileOperation.checkPathToFile(inputFilePath);

        BitSet bitSet = new BitSet();
        int bitIndex = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
            Path path = Paths.get(compressedFilePath);

            int character;
            while ((character = br.read()) != -1) {
                if (character >= 128 || character < 0) {
                    throw new IllegalArgumentException("Text file contains characters, which are not present in the 128 code-point ASCII table.");
                }

                String code = charCodes.get((char) character);
                char[] codeBits = code.toCharArray();

                for (char codeBit : codeBits) {
                    if (codeBit == '1') {
                        bitSet.set(bitIndex);
                    }
                    bitIndex++;
                }
            }
            bitSet.set(bitIndex);

            Files.write(path, bitSet.toByteArray());
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during writing encoded text to output file.", e);
            throw new RuntimeException("Failed to find file during writing encoded text to output file.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during writing encoded text to output file.", e);
            throw new RuntimeException("Failed to write encoded text to output file.", e);
        }

        return bitSet.toByteArray().length * 8;
    }

    public int decodeText(Node root) {
        int numOfCharacters = 0;
        Node current = root;
        BitSet bitSet = getBitSetFromCompressed();

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(this.pathToRootDir + DECOMPRESSED_FILE));) {
            for (int i = 0; i < bitSet.length() - 1; i++) {
                if (bitSet.get(i)) {
                    if (current == null) {
                        throw new NullPointerException("Header or compressed file might have been changed or encoded incorrectly.");
                    }
                    current = current.getRightChild();

                    if (current.isLeaf() && current.getCharacter() != '\0') {
                        writer.write(current.getCharacter());
                        numOfCharacters++;
                        current = root;
                    }
                } else {
                    if (current == null) {
                        throw new NullPointerException("Header or compressed file might have been changed or encoded incorrectly.");
                    }
                    current = current.getLeftChild();

                    if (current.isLeaf() && current.getCharacter() != '\0') {
                        writer.write(current.getCharacter());
                        numOfCharacters++;
                        current = root;
                    }
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during decoding compressed text.", e);
            throw new RuntimeException("Failed to find file during decoding compressed text.");
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during decoding compressed text.", e);
            throw new RuntimeException("Failed during writing decoded compressed text.", e);
        }

        return numOfCharacters;
    }

    public void headerInformation(Node node) {
        StringBuilder header = new StringBuilder("");
        postOrderTraversal(node, header);

        BitSet bitSet = new BitSet();
        int bitIndex = 0;

        for (char c : header.toString().toCharArray()) {
            if (c == '1') {
                bitSet.set(bitIndex);
            }
            bitIndex++;
        }
        bitSet.set(bitIndex);

        try {
            Path path = Paths.get(this.pathToRootDir + HEADER_INFORMATION_FILE);
            Files.write(path, bitSet.toByteArray());
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during writing header information.", e);
            throw new RuntimeException("Failed to find file during writing header information.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during writing header information.", e);
            throw new RuntimeException("Failed to write header information.", e);
        }
    }

    private void postOrderTraversal(Node node, StringBuilder header) {
        if (node == null) {
            return;
        }

        postOrderTraversal(node.getLeftChild(), header);
        postOrderTraversal(node.getRightChild(), header);

        if (node.isLeaf()) {
            header.append('1');
            String characterBinary = Integer.toBinaryString(node.getCharacter());
            for (int i = 0; i < 7 - characterBinary.length(); i++) {
                header.append('0');
            }
            header.append(characterBinary);
        } else {
            header.append('0');
        }
    }

    private BitSet getBitSetFromCompressed() {
        String filePath = this.pathToRootDir + COMPRESSED_FILE;
        FileOperation.checkPathToFile(filePath);

        BitSet bitSet = new BitSet();
        try {
            byte[] byteArray = Files.readAllBytes(Paths.get(filePath));
            bitSet = BitSet.valueOf(byteArray);
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during reading bit set from compressed file.", e);
            throw new RuntimeException("Failed to find file during reading bit set from compressed file.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Reading bit set from compressed file failed.", e);
            throw new RuntimeException("Failed to read bit set from compressed file.", e);
        }
        return bitSet;
    }
}
