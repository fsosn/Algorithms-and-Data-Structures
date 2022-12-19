package pl.edu.pw.ee;

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Stack;

public class Huffman {

    public int huffman(String pathToRootDir, boolean compress) {
        FileOperation.checkPathToDirectory(pathToRootDir);

        return compress ? compress(pathToRootDir) : decompress(pathToRootDir);
    }

    private int compress(String pathToRootDir) {
        FileRead read = new FileRead(pathToRootDir);
        FileWrite write = new FileWrite(pathToRootDir);

        Map<Character, Integer> charFrequencies = read.charFrequenciesMap();
        Node root = buildTree(charFrequencies);

        write.headerInformation(root);

        Map<Character, String> charCodes = new HashMap<>();
        huffmanCode(charCodes, root, "");

        return write.encodeText(charCodes);
    }

    private int decompress(String pathToRootDir) {
        FileRead read = new FileRead(pathToRootDir);
        FileWrite write = new FileWrite(pathToRootDir);

        String headerInformation = read.readHeaderInformation();
        Node root = rebuildTree(headerInformation);

        return write.decodeText(root);
    }

    private void huffmanCode(Map<Character, String> charCodes, Node node, String code) {
        if (!node.isLeaf()) {
            huffmanCode(charCodes, node.getLeftChild(), code + '0');
            huffmanCode(charCodes, node.getRightChild(), code + '1');
        } else {
            charCodes.put(node.getCharacter(), code);
        }
    }

    private Node buildTree(Map<Character, Integer> charFrequencies) {
        Queue<Node> pq = new PriorityQueue<>();

        for (char character : charFrequencies.keySet()) {
            pq.add(new Node(character, charFrequencies.get(character)));
        }
        if (pq.size() == 1) {
            pq.add(new Node('\0', 1));
        }
        while (pq.size() > 1) {
            pq.add(new Node(pq.poll(), pq.poll()));
        }

        return pq.poll();
    }

    private Node rebuildTree(String headerInformation) {
        char[] headerCharArray = headerInformation.toCharArray();

        Stack<Node> nodeStack = new Stack<>();

        for (int i = 0; i < headerCharArray.length; i++) {
            if (headerCharArray[i] == '0') {
                Node rightChild = nodeStack.pop();
                Node leftChild = nodeStack.pop();
                nodeStack.push(new Node(leftChild, rightChild));
            } else if (headerCharArray[i] == '1') {
                nodeStack.push(new Node(headerCharArray[++i], 0));
            }
        }

        return nodeStack.pop();
    }
}
