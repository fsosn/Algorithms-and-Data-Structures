package pl.edu.pw.ee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class PrimAlgorithmTest {

    private final String data = "src/main/resources/correct_small_data.txt";
    private final String incorrect_data_non_alphabetic = "src/main/resources/incorrect_data_non_alphabetic.txt";
    private final String incorrect_data_loop = "src/main/resources/incorrect_data_loop.txt";

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFilepathIsNull() {
        //given
        PrimAlgorithm prim = new PrimAlgorithm();

        //when
        prim.findMST(null);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFilepathIsEmpty() {
        //given
        PrimAlgorithm prim = new PrimAlgorithm();

        //when
        prim.findMST("");

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFilepathDoesNotExist() {
        //given
        PrimAlgorithm prim = new PrimAlgorithm();

        //when
        prim.findMST("src/main/resources/nosuchfile.txt");

        //then
        assert false;
    }

    @Test
    public void shouldReturnCorrectResultWhenFindingMST() {
        //given
        PrimAlgorithm prim = new PrimAlgorithm();
        String expected = "A_3_B|B_1_C|C_1_D|D_7_E";

        //when
        String actual = prim.findMST(data);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnCorrectValuesAfterConstructingNode() {
        //given
        Node node;
        String givenLabel = "a";
        int givenWeight = 1;

        //when
        node = new Node(givenLabel, givenWeight);
        String retrievedLabel = node.getLabel();
        int retrievedWeight = node.getWeight();

        //then
        assertEquals(givenLabel, retrievedLabel);
        assertEquals(givenWeight, retrievedWeight);
    }

    @Test
    public void shouldReturnCorrectValuesAfterConstructingEdge() {
        //given
        Edge edge;
        String givenLabelSource = "a";
        String givenLabelDestination = "b";
        int givenWeight = 1;

        //when
        edge = new Edge(givenLabelSource, givenLabelDestination, givenWeight);
        String retrievedLabelSource = edge.getSourceLabel();
        String retrievedLabelDestination = edge.getDestinationLabel();
        int retrievedWeight = edge.getWeight();

        //then
        assertEquals(givenLabelSource, retrievedLabelSource);
        assertEquals(givenLabelDestination, retrievedLabelDestination);
        assertEquals(givenWeight, retrievedWeight);
    }

    @Test
    public void shouldReturnCorrectValuesAfterConstructingParentNode() {
        //given
        ParentNode parentNode;
        String givenLabel = "a";
        String givenChildNodeLabel = "B";
        int givenChildNodeWeight = 3;

        //when
        parentNode = new ParentNode(givenLabel);
        String retrievedLabel = parentNode.getLabel();
        Node node = new Node(givenChildNodeLabel, givenChildNodeWeight);
        parentNode.addNodeToAdjList(node);

        //then
        assertEquals(givenLabel, retrievedLabel);
        assertEquals(1, parentNode.getNumOfLinks());
        assertEquals(1, parentNode.getNumOfLinks());
        assertEquals(node, parentNode.getAdjListElement(0));
        assertEquals(givenChildNodeLabel, parentNode.getAdjListElement(0).getLabel());
        assertEquals(givenChildNodeWeight, parentNode.getAdjListElement(0).getWeight());
        assertEquals(1, parentNode.getNumOfLinks());
        assertEquals(true, parentNode.checkIfAdjListContainsLabel(givenChildNodeLabel));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNodeLabelsAreNonAlphabetic() {
        //given
        PrimAlgorithm prim = new PrimAlgorithm();

        //when
        prim.findMST(this.incorrect_data_non_alphabetic);

        //then
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenThereIsLoopInGraph() {
        //given
        PrimAlgorithm prim = new PrimAlgorithm();

        //when
        prim.findMST(this.incorrect_data_loop);

        //then
        assert false;
    }
}
