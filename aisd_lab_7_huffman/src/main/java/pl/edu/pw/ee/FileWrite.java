package pl.edu.pw.ee;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
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
    private final String COMPRESSED_TEXT_FILEPATH = "src/main/resources/compressed.txt";

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
                        if ((int) codeBit - 48 == 1) {
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

        try ( BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/header_information.txt"));) {
            bw.write(header.toString());
            bw.close();
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
            header.append("1").append(node.getCharacter());
        } else {
            header.append("0");
        }
    }
}
