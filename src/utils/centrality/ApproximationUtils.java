package utils.centrality;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ApproximationUtils {
    public static HashMap<Node, Double> approximateClosenessCentrality(
            UndirectedSparseGraph<Node, Edge> graph, int sampleSize) {

        HashMap<Node, Double> closenessScores = new HashMap<>();
        List<Node> nodes = new ArrayList<>(graph.getVertices());
        int n = nodes.size();

        //handle edge cases
        if (n == 0 || sampleSize == 0) {
            return closenessScores;
        }

        // select random sample nodes
        Set<Node> sampleNodes = new HashSet<>();
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        while (sampleNodes.size() < Math.min(sampleSize, n)) {
            sampleNodes.add(nodes.get(rand.nextInt(n)));
        }

        // initialize scores to 0.0
        for (Node v : nodes) {
            closenessScores.put(v, 0.0);
        }

        // run BFS from each sample node
        for (Node s : sampleNodes) {
            Map<Node, Integer> distances = bfsDistances(graph, s);

            for (Map.Entry<Node, Integer> e : distances.entrySet()) {
                Node v = e.getKey();
                int dist = e.getValue();
                if (dist > 0) {
                    double reciprocalDistance = 1.0 / dist;
                    closenessScores.put(v, closenessScores.get(v) + reciprocalDistance);
                }
            }
        }
        return closenessScores;
    }
    public static HashMap<Node, Double> approximateBetweennessCentrality(
            UndirectedSparseGraph<Node, Edge> graph, int sampleSize) {

        HashMap<Node, Double> betweennessScores = new HashMap<>();
        List<Node> nodes = new ArrayList<>(graph.getVertices());
        int n = nodes.size();

        // handle edge cases
        if (n == 0 || sampleSize == 0) {
            return betweennessScores;
        }

        // initialize scores to 0.0
        for (Node v : nodes) {
            betweennessScores.put(v, 0.0);
        }

        // select random sample nodes
        Set<Node> sampleNodes = new HashSet<>();
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        while (sampleNodes.size() < Math.min(sampleSize, n)) {
            sampleNodes.add(nodes.get(rand.nextInt(n)));
        }

        // Run BFS for each sample node
        for (Node s : sampleNodes) {
            // data structures for Brandes' algorithm
            Stack<Node> S = new Stack<>();
            Map<Node, List<Node>> P = new HashMap<>();
            Map<Node, Double> sigma = new HashMap<>();
            Map<Node, Integer> dist = new HashMap<>();
            Queue<Node> Q = new LinkedList<>();

            for (Node w : nodes) {
                P.put(w, new ArrayList<>());
                sigma.put(w, 0.0);
                dist.put(w, -1);
            }

            dist.put(s, 0);
            sigma.put(s, 1.0);
            Q.add(s);

            while (!Q.isEmpty()) {
                Node v = Q.poll();
                S.push(v);

                for (Node w : graph.getNeighbors(v)) {
                    if (dist.get(w) < 0) {
                        Q.add(w);
                        dist.put(w, dist.get(v) + 1);
                    }
                    if (dist.get(w) == dist.get(v) + 1) {
                        sigma.put(w, sigma.get(w) + sigma.get(v));
                        P.get(w).add(v);
                    }
                }
            }

            // accumulate scores
            Map<Node, Double> delta = new HashMap<>();
            for (Node w : nodes) {
                delta.put(w, 0.0);
            }

            while (!S.isEmpty()) {
                Node w = S.pop();
                for (Node v : P.get(w)) {
                    double pathFraction = sigma.get(v) / sigma.get(w);
                    delta.put(v, delta.get(v) + pathFraction * (1.0 + delta.get(w)));
                }
                if (!w.equals(s)) {
                    betweennessScores.put(w, betweennessScores.get(w) + delta.get(w));
                }
            }
        }
        return betweennessScores;
    }
    public static HashMap<Node, Double> approximateEigenvectorCentrality(
            UndirectedSparseGraph<Node, Edge> graph, int iterations) {

        HashMap<Node, Double> scores = new HashMap<>();
        // initialize scores to 1.0
        for (Node node : graph.getVertices()) {
            scores.put(node, 1.0);
        }

        // power iteration
        for (int i = 0; i < iterations; i++) {
            HashMap<Node, Double> newScores = new HashMap<>();
            double normalizationFactor = 0.0;

            // calculate eigenvector scores
            for (Node node : graph.getVertices()) {
                double newScore = 0.0;
                for (Node neighbor : graph.getNeighbors(node)) {
                    newScore += scores.getOrDefault(neighbor, 0.0);
                }
                newScores.put(node, newScore);
                normalizationFactor += newScore * newScore; // Calculate squared sum for L2 normalization
            }

            // normalize the scores
            if (normalizationFactor > 0) {
                double sqrtSum = Math.sqrt(normalizationFactor);
                for (Node node : graph.getVertices()) {
                    newScores.put(node, newScores.get(node) / sqrtSum);
                }
            }
            scores = newScores;
        }

        return scores;
    }

    private static Map<Node, Integer> bfsDistances(UndirectedSparseGraph<Node, Edge> graph, Node source) {
        Map<Node, Integer> distances = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();

        for (Node node : graph.getVertices()) {
            distances.put(node, -1);
        }
        distances.put(source, 0);
        queue.add(source);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentDist = distances.get(current);

            for (Node neighbor : graph.getNeighbors(current)) {
                if (distances.get(neighbor) == -1) {
                    distances.put(neighbor, currentDist + 1);
                    queue.add(neighbor);
                }
            }
        }
        return distances;
    }
}
