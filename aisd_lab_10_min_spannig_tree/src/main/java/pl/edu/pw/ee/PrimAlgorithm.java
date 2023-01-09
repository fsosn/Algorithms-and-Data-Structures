package pl.edu.pw.ee;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithm implements MinSpanningTree {

    Queue<Edge> pq;
    List<String> visited;
    Graph graph;
    List<ParentNode> adjList;
    String result;

    private void initialize() {
        this.pq = new PriorityQueue<>();
        this.visited = new ArrayList<>();
        this.graph = new Graph();
        this.adjList = new ArrayList<>();
        this.result = "";
    }

    @Override
    public String findMST(String pathToFile) {
        checkPathToFile(pathToFile);
        initialize();

        adjList = graph.buildAdjacencyListFromFile(pathToFile);

        ParentNode source = adjList.get(0);
        visited.add(source.getLabel());

        while (visited.size() != adjList.size()) {
            for (int i = 0; i < source.getNumOfLinks(); i++) {
                Node destination = source.getAdjListElement(i);
                pq.add(new Edge(source.getLabel(), destination.getLabel(), destination.getWeight()));
            }

            String sourceLabel;
            String destinationLabel;
            int weight;

            do {
                Edge edge = pq.remove();
                sourceLabel = edge.getSourceLabel();
                destinationLabel = edge.getDestinationLabel();
                weight = edge.getWeight();
            } while (visited.contains(sourceLabel) && visited.contains(destinationLabel));

            if (!visited.contains(destinationLabel)) {
                visited.add(destinationLabel);
            }

            if (!result.equals("")) {
                result += "|";
            }
            result += sourceLabel + "_" + String.valueOf(weight) + "_" + destinationLabel;

            source = adjList.get(Graph.getIndexOfLabel(destinationLabel, adjList));
        }

        return result;
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
