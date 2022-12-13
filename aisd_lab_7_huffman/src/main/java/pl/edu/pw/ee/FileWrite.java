package pl.edu.pw.ee;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.Map;
import java.util.Scanner;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

public class FileWrite {

    private static final Logger LOG = Logger.getLogger(FileWrite.class.getName());
    private final String COMPRESSED_TEXT_FILEPATH = "src/main/resources/compressed.bin";
    private final String HEADER_INFORMATION_FILEPATH = "src/main/resources/header_information.bin";

    public int encodeText(Map<Character, String> charCodes, String pathToRootDir) {
        BitSet bitSet = new BitSet();
        int bitIndex = 0;

        try {
            FileInputStream file = new FileInputStream(pathToRootDir);
            Scanner scanner = new Scanner(file);
            Path path = Paths.get(COMPRESSED_TEXT_FILEPATH);

            while (scanner.hasNextLine()) {
                char[] charsInLine = scanner.nextLine().toCharArray();

                for (char c : charsInLine) {
                    if (c >= 128) {
                        throw new IllegalArgumentException("Text file contains characters, which are not present in the 128 code-point ASCII table.");
                    }
                    String code = charCodes.get(c);
                    char[] codeBits = code.toCharArray();

                    for (char codeBit : codeBits) {
                        if (codeBit == '1') {
                            bitSet.set(bitIndex);
                        }
                        bitIndex++;
                    }
                }
            }
            Files.write(path, bitSet.toByteArray());
        } catch (IOException e) {
            LOG.log(SEVERE, "[Error] An error occurred during writing encoded text to output file.", e);
        }

        return bitSet.size();
    }

    public void headerInformation(Node node) {
        StringBuilder header = new StringBuilder("");
        postOrderTraversal(node, header);

        BitSet bitSet = new BitSet(header.length());
        int bitIndex = 0;

        for (char c : header.toString().toCharArray()) {
            if (c == '1') {
                bitSet.set(bitIndex);
            }
            bitIndex++;
        }

        try {
            Path path = Paths.get(HEADER_INFORMATION_FILEPATH);
            Files.write(path, bitSet.toByteArray());
        } catch (IOException e) {
            LOG.log(SEVERE, "[Error] An error occurred during writing header information.", e);
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
            for (int i = 0; i < 8 - characterBinary.length(); i++) {
                header.append('0');
            }
            header.append(characterBinary);
        } else {
            header.append('0');
        }
    }
}
