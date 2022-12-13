package pl.edu.pw.ee;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

public class FileRead {

    private static final Logger LOG = Logger.getLogger(FileRead.class.getName());
    private final String HEADER_INFORMATION_FILEPATH = "src/main/resources/header_information.bin";

    public Map<Character, Integer> charFrequenciesMap(String pathToRootDir) {
        Map<Character, Integer> charFrequencies = new HashMap<>();

        try ( FileInputStream file = new FileInputStream(pathToRootDir);  Scanner scanner = new Scanner(file);) {

            while (scanner.hasNextLine()) {
                char[] charsInLine = scanner.nextLine().toCharArray();

                for (char c : charsInLine) {
                    if (c >= 128) {
                        throw new IllegalArgumentException("Text file contains characters, which are not present in the 128 code-point ASCII table.");
                    }
                    charFrequencies.put(c, charFrequencies.getOrDefault(c, 0) + 1);
                }
            }
        } catch (IOException e) {
            LOG.log(SEVERE, "[Error] An error occurred during reading character frequencies from input file.", e);
        }

        return charFrequencies;
    }

    public String readHeaderInfo() {
        DataInputStream dataInputStream;
        StringBuilder decodedHeaderBinary = new StringBuilder("");

        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(HEADER_INFORMATION_FILEPATH))));
            byte[] bytes = new byte[dataInputStream.available()];
            dataInputStream.read(bytes);
            BitSet bitSet = BitSet.valueOf(bytes);

            for (int i = 0; i <= bitSet.length(); i++) {
                if (bitSet.get(i)) {
                    decodedHeaderBinary.append('1');
                } else {
                    decodedHeaderBinary.append('0');
                }
            }

            int trailingZeroes = decodedHeaderBinary.length() % 8;
            for (int i = 0; i < trailingZeroes; i++) {
                decodedHeaderBinary.append('0');
            }

        } catch (IOException e) {
            LOG.log(SEVERE, "[Error] An error occurred during reading header information.", e);
        }

        char[] bits = decodedHeaderBinary.toString().toCharArray();
        StringBuilder header = new StringBuilder("");
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == '1') {
                header.append('1');

                if (i < bits.length - 8) {
                    String characterBinary = "";
                    for (int j = i + 1; j <= i + 8; j++) {
                        characterBinary += bits[j];
                    }
                    header.append((char) Integer.parseInt(characterBinary, 2));
                    i += characterBinary.length();
                }
            } else {
                header.append('0');
            }
        }

        return header.toString();
    }

    public int numOfChars(String pathToRootDir) {
        int numOfChars = 0;

        try ( FileInputStream file = new FileInputStream(pathToRootDir);  Scanner scanner = new Scanner(file);) {

            while (scanner.hasNextLine()) {
                char[] charsInLine = scanner.nextLine().toCharArray();
                numOfChars += charsInLine.length;
            }
        } catch (IOException e) {
            LOG.log(SEVERE, "[Error] An error occurred during reading number of character in the input file.", e);
        }

        return numOfChars;
    }
}
