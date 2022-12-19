package pl.edu.pw.ee;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Logger;
import static java.util.logging.Level.SEVERE;

public class FileRead extends FileOperation {

    private final String pathToRootDir;

    private static final Logger LOG = Logger.getLogger(FileRead.class.getName());

    public FileRead(String pathToRootDir) {
        this.pathToRootDir = pathToRootDir;
    }

    public Map<Character, Integer> charFrequenciesMap() {
        String filePath = this.pathToRootDir + INPUT_FILE;
        FileOperation.checkPathToFile(filePath);

        Map<Character, Integer> charFrequencies = new HashMap<>();
        int character;
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((character = br.read()) != -1) {
                if (character >= 128 || character < 0) {
                    throw new IllegalArgumentException("Text file contains characters, which are not present in the 128 code-point ASCII table.");
                }
                charFrequencies.put((char) character, charFrequencies.getOrDefault((char) character, 0) + 1);
            }
            br.close();
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during reading character frequencies from input file.", e);
            throw new RuntimeException("Failed to find file during reading character frequencies from input file.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Reading character frequencies from input file failed.", e);
            throw new RuntimeException("Failed to read character frequencies from input file", e);
        }
        if (charFrequencies.isEmpty()) {
            throw new IllegalArgumentException("Input text file is empty, does not contain any characters.");
        }

        return charFrequencies;
    }

    public String readHeaderInformation() {
        String headerFilePath = this.pathToRootDir + HEADER_INFORMATION_FILE;
        FileOperation.checkPathToFile(headerFilePath);

        DataInputStream dataInputStream;
        StringBuilder decodedHeaderBinary = new StringBuilder("");

        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(headerFilePath))));
            byte[] byteArray = new byte[dataInputStream.available()];
            dataInputStream.read(byteArray);
            BitSet bitSet = BitSet.valueOf(byteArray);

            for (int i = 0; i < bitSet.length() - 1; i++) {
                if (bitSet.get(i)) {
                    decodedHeaderBinary.append('1');
                } else {
                    decodedHeaderBinary.append('0');
                }
            }
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "Could not find file during reading header information.", e);
            throw new RuntimeException("Failed to find file during reading header information.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during reading header information.", e);
            throw new RuntimeException("Failed to read header information.", e);
        }

        char[] bits = decodedHeaderBinary.toString().toCharArray();
        StringBuilder header = new StringBuilder("");

        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == '1') {
                header.append('1');
                if (i <= bits.length - 8) {
                    String characterBinary = "";
                    for (int j = i + 1; j < i + 8; j++) {
                        characterBinary += bits[j];
                    }
                    char binaryToCharacter = (char) Integer.parseInt(characterBinary, 2);

                    header.append((char) binaryToCharacter);
                    i += characterBinary.length();
                }
            } else {
                header.append('0');
            }
        }

        return header.toString();
    }
}
