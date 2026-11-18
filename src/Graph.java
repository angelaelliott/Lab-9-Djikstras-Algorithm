import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Graph {
    List<Node> nodes = new ArrayList<>();

    public Node getOrCreateNode(String name) {
        for (Node n : nodes) {
            if (n.name.equals(name)) return n;
        }
        Node newNode = new Node(name);
        nodes.add(newNode);
        return newNode;
    }

    public void addEdge(String srcName, String destName, double weight) {
        Node src = getOrCreateNode(srcName);
        Node dest = getOrCreateNode(destName);

        src.edges.add(new Edge(dest, weight));
        dest.edges.add(new Edge(src, weight));
    }

    public void sortNodesAlphabetically() {
        nodes.sort(Comparator.comparing(n -> n.name));
    }

    public void resetNodes() {
        for (Node n : nodes) {
            n.distance = Double.MAX_VALUE;
            n.previous = null;
        }
    }
}