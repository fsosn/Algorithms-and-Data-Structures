package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {

    List<ParentNode> adjList;

    public List<ParentNode> buildAdjacencyListFromFile(String filepath) {
        checkPathToFile(filepath);

        this.adjList = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Scanner sc;
                sc = new Scanner(line);

                String firstLabel = sc.next();
                checkIfLabelIsAlphabetic(firstLabel);

                ParentNode firstParentNode = new ParentNode(firstLabel);
                if (!isThereAlreadySuchLabel(firstLabel)) {
                    adjList.add(firstParentNode);
                }

                String secondLabel = sc.next();
                checkIfLabelIsAlphabetic(secondLabel);

                if (firstLabel.equals(secondLabel)) {
                    throw new IllegalArgumentException("Graph cannot have loops.");
                }

                ParentNode secondParentNode = new ParentNode(secondLabel);
                if (!isThereAlreadySuchLabel(secondLabel)) {
                    adjList.add(secondParentNode);
                }

                if (!sc.hasNextInt()) {
                    throw new IllegalArgumentException("Graph text information is incorrectly formatted, could not find weight.");
                }

                int weight = sc.nextInt();
                Node nodeConnectedToFirstParent = new Node(secondLabel, weight);
                Node nodeConnectedToSecondParent = new Node(firstLabel, weight);

                int firstIndex = getIndexOfLabel(firstLabel, adjList);
                if (!adjList.get(firstIndex).checkIfAdjListContainsLabel(firstLabel)) {
                    adjList.get(firstIndex).addNodeToAdjList(nodeConnectedToFirstParent);
                }

                int secondIndex = getIndexOfLabel(secondLabel, adjList);
                if (!adjList.get(secondIndex).checkIfAdjListContainsLabel(secondLabel)) {
                    adjList.get(secondIndex).addNodeToAdjList(nodeConnectedToSecondParent);
                }
                sc.close();
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("File could not be read correctly.");
        }

        if (adjList.isEmpty()) {
            throw new IllegalArgumentException("Graph could not have been built from given text file.");
        }

        return adjList;
    }

    private void checkIfLabelIsAlphabetic(String label) {
        for (char c : label.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                throw new IllegalArgumentException("Node labels have to be alphabetic.");
            }
        }
    }

    private boolean isThereAlreadySuchLabel(String label) {
        if (this.adjList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < this.adjList.size(); i++) {
            if (this.adjList.get(i).getLabel().equals(label)) {
                return true;
            }
        }
        return false;
    }

    public static int getIndexOfLabel(String label, List<ParentNode> adjList) {
        int index = -1;
        for (int i = 0; i < adjList.size(); i++) {
            if (adjList.get(i).getLabel().equals(label)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("There are no nodes with such label: " + label);
        }

        return index;
    }

    private void checkPathToFile(String filepath) {
        if (filepath == null) {
            throw new IllegalArgumentException("Given file path string cannot be null.");
        }
        if (filepath.equals("")) {
            throw new IllegalArgumentException("Given file path string cannot be empty.");
        }

        Path path = Paths.get(filepath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File " + filepath + " does not exists.");
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("File " + filepath + " cannot be read.");
        }
    }
}
