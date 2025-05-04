package djikstra;

import java.util.*;

public class Dijkstra {

    public static int dijkstra(Map<String, Map<String, Integer>> graph, String start, String end, boolean isUndirected) {
        Map<String, Map<String, Integer>> processedGraph = preprocessGraph(graph, isUndirected);

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        queue.add(new Node(start, 0));

        Map<String, Integer> distances = new HashMap<>();
        distances.put(start, 0);

        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (visited.contains(current.name)) continue;
            visited.add(current.name);

            if (current.name.equals(end)) return current.distance;

            Map<String, Integer> neighbors = processedGraph.getOrDefault(current.name, new HashMap<>());
            for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                String neighborName = neighbor.getKey();
                int weight = neighbor.getValue();

                int newDistance = current.distance + weight;
                int knownDistance = distances.getOrDefault(neighborName, Integer.MAX_VALUE);

                if (newDistance < knownDistance) {
                    distances.put(neighborName, newDistance);
                    queue.add(new Node(neighborName, newDistance));
                }
            }
        }

        return -1;
    }

    private static Map<String, Map<String, Integer>> preprocessGraph(Map<String, Map<String, Integer>> graph, boolean isUndirected) {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        for (Map.Entry<String, Map<String, Integer>> entry : graph.entrySet()) {
            String from = entry.getKey();
            Map<String, Integer> neighbors = entry.getValue();

            for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                String to = neighbor.getKey();
                int weight = neighbor.getValue() == null ? 1 : neighbor.getValue(); // Default to 1 if null

                result.computeIfAbsent(from, k -> new HashMap<>()).put(to, weight);
                if (isUndirected) {
                    result.computeIfAbsent(to, k -> new HashMap<>()).put(from, weight);
                }
            }
        }

        return result;
    }

    static class Node {
        String name;
        int distance;
        Node(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        Map<String, Map<String, Integer>> graph = new HashMap<>();

        graph.put("1", new HashMap<>());
        graph.get("1").put("2", 1);
        graph.get("1").put("3", 1);
        graph.get("1").put("5", 1);

        graph.put("2", new HashMap<>());
        graph.get("2").put("4", 1);

        graph.put("3", new HashMap<>());
        graph.get("3").put("5", 1);

        graph.put("4", new HashMap<>());
        graph.get("4").put("3", 1);

        graph.put("5", new HashMap<>());

        System.out.println("Starting Node\tEnding Node\tOutput");

        System.out.println("1\t\t2\t\t" + dijkstra(graph, "1", "2", false));
        System.out.println("1\t\t5\t\t" + dijkstra(graph, "1", "5", false));
        System.out.println("2\t\t5\t\t" + dijkstra(graph, "2", "5", false));
        System.out.println("5\t\t1\t\t" + dijkstra(graph, "5", "1", false));
        System.out.println("2\t\t1\t\t" + dijkstra(graph, "2", "1", false));
    }

}

