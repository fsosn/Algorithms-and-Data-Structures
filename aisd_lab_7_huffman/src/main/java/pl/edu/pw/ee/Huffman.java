package pl.edu.pw.ee;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    public int huffman(String pathToRootDir, boolean compress) {
        return compress ? compress(pathToRootDir) : decompress(pathToRootDir);
    }

    private int compress(String pathToRootDir) {
        FileRead fileRead = new FileRead();
        Map<Character, Integer> charFrequencies = fileRead.charFrequenciesMap(pathToRootDir);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (char character : charFrequencies.keySet()) {
            pq.add(new Node(character, charFrequencies.get(character)));
        }

        while (pq.size() != 1) {
            pq.add(new Node(pq.poll(), pq.poll()));
        }

        Node root = pq.poll();

        FileWrite fileWrite = new FileWrite();
        fileWrite.headerInformation(root);

        Map<Character, String> charCodes = new HashMap<>();
        huffmanCode(charCodes, root, "");

        return fileWrite.encodeText(charCodes, pathToRootDir);
    }

    private int decompress(String pathToRootDir) {
        int numOfCharsInOutFile = 0;

        FileRead fileRead = new FileRead();
        String headerInformation = fileRead.readHeaderInfo();

        return numOfCharsInOutFile;
    }

    private void huffmanCode(Map<Character, String> charCodes, Node node, String code) {
        if (!node.isLeaf()) {
            huffmanCode(charCodes, node.getLeftChild(), code + '0');
            huffmanCode(charCodes, node.getRightChild(), code + '1');
        } else {
            charCodes.put(node.getCharacter(), code);
        }
    }
}
