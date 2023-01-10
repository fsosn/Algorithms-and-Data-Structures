package pl.edu.pw.ee.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.edu.pw.ee.exception.ReadingGraphFromFileException;

public class GraphReader {

    private final String lineRegex = "^\\S+ \\S+ \\d+$";
    private final Pattern pattern = Pattern.compile(lineRegex);

    private final String fileToPath;
    private final List<Edge> edges;

    public GraphReader(String fileToPath) {
        validateData(fileToPath);

        this.fileToPath = fileToPath;
        this.edges = new ArrayList<>();

        readAndSort();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private void validateData(String fileToPath) {
        if (fileToPath == null) {
            throw new IllegalArgumentException("File to path arg cannot be null!");
        }
    }

    private void readAndSort() {
        readGraphFromFile();
        sortEdgesByPriority();
    }

    private void readGraphFromFile() {
        Edge edge;
        int i = 1;

        try ( BufferedReader reader = new BufferedReader(new FileReader(fileToPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                edge = parseToEdge(line, i);
                removeEdgeIfAlreadyExists(edge);
                edges.add(edge);
                i++;
            }
        } catch (IOException e) {
            throw new ReadingGraphFromFileException("Cannot read file from path!", e);
        }
    }

    private Edge parseToEdge(String line, int lineNumber) {
        String[] args;
        Node start;
        Node end;
        int weight;

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            args = line.split(" ");
            start = new Node(args[0]);
            end = new Node(args[1]);
            weight = Integer.parseInt(args[2]);

            return new Edge(start, end, weight);
        } else {
            throw new ReadingGraphFromFileException(
                    format("Cannot correctly parse line %d from file", lineNumber));
        }
    }

    private void removeEdgeIfAlreadyExists(Edge edge) {
        String srcName = edge.getSrc().getName();
        String destName = edge.getDest().getName();

        for (Edge otherEdge : this.edges) {
            String otherEdgeSrcName = otherEdge.getSrc().getName();
            String otherEdgeDestName = otherEdge.getDest().getName();

            if ((srcName.equals(otherEdgeSrcName) && destName.equals(otherEdgeDestName))
                    || (srcName.equals(otherEdgeDestName) && destName.equals(otherEdgeSrcName))) {
                this.edges.remove(otherEdge);
                return;
            }
        }
    }

    private void sortEdgesByPriority() {
        EdgeWeightComparator weightComparator = new EdgeWeightComparator();

        Collections.sort(edges, weightComparator);
    }

}
