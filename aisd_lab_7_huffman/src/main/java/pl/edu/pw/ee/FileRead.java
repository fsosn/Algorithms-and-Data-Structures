package pl.edu.pw.ee;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

public class FileRead {

    private static final Logger LOG = Logger.getLogger(FileRead.class.getName());

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
