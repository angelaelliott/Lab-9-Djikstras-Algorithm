import java.util.ArrayList;
import java.util.List;

class Node {
    String name;
    List<Edge> edges = new ArrayList<>();
    double distance = Double.MAX_VALUE;
    Node previous = null;

    public Node(String name) {
        this.name = name;
    }
}
