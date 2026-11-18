import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);
        System.out.print("Graph file to read from: ");
        String filename = consoleScanner.nextLine().trim();

        Graph graph = new Graph();

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNext()) {
                String u = fileScanner.next();
                String v = fileScanner.next();
                double weight = fileScanner.nextDouble();
                graph.addEdge(u, v, weight);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return;
        }

        graph.sortNodesAlphabetically();

        // Print nodes list
        List<String> nodeNames = new ArrayList<>();
        for (Node n : graph.nodes) nodeNames.add(n.name);
        System.out.println("List of nodes: " + String.join(", ", nodeNames));

        // Get start node
        System.out.print("Node to start at: ");
        String startName = consoleScanner.nextLine().trim();

        Node startNode = null;
        for (Node n : graph.nodes) {
            if (n.name.equals(startName)) startNode = n;
        }

        if (startNode == null) {
            System.out.println("Node not found in graph.");
            return;
        }

        // Run algorithm and print
        runDijkstra(startNode);

        for (Node target : graph.nodes) {
            System.out.println(buildPathString(target));
        }
    }

    public static void runDijkstra(Node start) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
        start.distance = 0.0;
        pq.add(start);

        while (!pq.isEmpty()) {
            Node u = pq.poll();

            for (Edge edge : u.edges) {
                Node v = edge.target;
                double newDist = u.distance + edge.weight;

                if (newDist < v.distance) {
                    pq.remove(v); // Remove it so the priority queue resorts correctly when added back
                    v.distance = newDist;
                    v.previous = u;
                    pq.add(v);
                }
            }
        }
    }

    public static String buildPathString(Node target) {
        if (target.distance == 0.0) {
            return target.name + " dist: 0";
        }

        List<String> path = new ArrayList<>();
        Node curr = target;
        while (curr != null) {
            path.add(curr.name);
            curr = curr.previous;
        }
        Collections.reverse(path);

        String pathStr = String.join(" -> ", path);
        String distStr = (target.distance == (long) target.distance)
                ? String.format("%d", (long) target.distance)
                : String.valueOf(target.distance);

        return pathStr + " dist: " + distStr;
    }
}

