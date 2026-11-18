import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private Graph graph;

    @BeforeEach
    public void setup() {
        graph = new Graph();
        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 3);
        graph.addEdge("A", "D", 5.5);
        graph.sortNodesAlphabetically();
    }

    @Test
    public void testDistanceToSelfIsZero() {
        Node startNode = graph.getOrCreateNode("B");
        Main.runDijkstra(startNode);

        assertEquals(0.0, startNode.distance);
        assertEquals("B dist: 0", Main.buildPathString(startNode));
    }

    @Test
    public void testShortestPathAdjacentNode() {
        Node startNode = graph.getOrCreateNode("B");
        Main.runDijkstra(startNode);

        Node targetNode = graph.getOrCreateNode("A");
        assertEquals(2.0, targetNode.distance);
        assertEquals("B -> A dist: 2", Main.buildPathString(targetNode));
    }

    @Test
    public void testShortestPathMultiHop() {
        Node startNode = graph.getOrCreateNode("B");
        Main.runDijkstra(startNode);

        Node targetNode = graph.getOrCreateNode("C");
        assertEquals(5.0, targetNode.distance);
        assertEquals("B -> A -> C dist: 5", Main.buildPathString(targetNode));
    }

    @Test
    public void testUnreachableNode() {
        graph.addEdge("Z", "Y", 1); // Disconnected component

        Node startNode = graph.getOrCreateNode("B");
        Main.runDijkstra(startNode);

        Node unreachableNode = graph.getOrCreateNode("Z");
        assertEquals(Double.MAX_VALUE, unreachableNode.distance);
    }
}