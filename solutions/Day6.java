import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("./src/input.txt");
        BufferedReader read = new BufferedReader(new FileReader(inputFile));
        List<String> lines = read.lines().collect(Collectors.toList());

        Map<String, Node> nodes = buildGraph(lines);

        Node COM = nodes.get("COM");
        System.out.println("Part One: " + countOrbits(COM));

        Node YOU = nodes.get("YOU");
        Node SAN = nodes.get("SAN");

        System.out.println("Part Two: " + minimumOrbitalTransfers(YOU, SAN));
    }

    public static Map<String, Node> buildGraph(List<String> edges) {
        Map<String, Node> nodes = new HashMap<>();
        for (String edge : edges) {
            String[] fromTo = edge.split("\\)");
            if (!nodes.containsKey(fromTo[0])) {
                nodes.put(fromTo[0], new Node());
            }

            if (!nodes.containsKey(fromTo[1])) {
                nodes.put(fromTo[1], new Node());
            }

            Node node0 = nodes.get(fromTo[0]);
            Node node1 = nodes.get(fromTo[1]);

            node0.edges.add(node1);
            node1.edges.add(node0);
        }

        return nodes;
    }

    public static int countOrbits(Node top) {
        return countOrbitsHelper(top, new Stack<>());
    }

    private static int countOrbitsHelper(Node node, Stack<Node> currentPath) {
        if (node.edges.isEmpty() || (node.edges.size() == 1 && currentPath.size() != 0)) {
            return currentPath.size();
        }

        int sum = 0;
        for (Node child : node.edges) {
            if (currentPath.isEmpty() || child != currentPath.peek()) {
                currentPath.add(node);
                sum += countOrbitsHelper(child, currentPath);
                currentPath.pop();
            }
        }

        return sum + currentPath.size();
    }

    public static int minimumOrbitalTransfers(Node start, Node end) {
        return shortestPathLength(start, end, new HashSet<>()) - 2;
    }

    private static int shortestPathLength(Node start, Node end, Set<Node> visited) {
        visited.add(start);
        if (start == end) {
            return 0;
        }

        for (Node child : start.edges) {
            if (!visited.contains(child)) {
                int length = shortestPathLength(child, end, visited);
                if (length != Integer.MAX_VALUE) {
                    return length + 1;
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    public static class Node {
        List<Node> edges;

        public Node() {
            this.edges = new ArrayList<>();
        }
    }
}
