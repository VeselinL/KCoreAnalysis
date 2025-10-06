package utils;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.Edge;
import graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphUtils {
    public static double avgDegree(UndirectedSparseGraph<Node,Edge> graph){
        if(graph.getVertexCount() == 0) return 0;
        return 2.0 * graph.getEdgeCount() / graph.getVertexCount();
    }
    public static int maxDegree(UndirectedSparseGraph<Node,Edge> graph){
        int maxDegree = -1;
        for(Node node: graph.getVertices()){
            maxDegree = Math.max(maxDegree, graph.degree(node));
        }
        return maxDegree;
    }
    public static UndirectedSparseGraph<Node, Edge> deepCopy(UndirectedSparseGraph<Node,Edge> originalGraph){
        UndirectedSparseGraph<Node,Edge> graphCopy= new UndirectedSparseGraph<>();
        Map<Node,Node> oldToNew = new HashMap<>(); //Map original nodes to their copies
        try {
            // Copy all nodes from the original graph
            for (Node ogNode : originalGraph.getVertices()) {
                Node newNode = new Node(ogNode.getId());
                graphCopy.addVertex(newNode);
                oldToNew.put(ogNode, newNode);
            }
            // Copy all edges, connecting the new node instances
            for (Edge oldEdge : originalGraph.getEdges()) {
                Pair<Node> endpoints = originalGraph.getEndpoints(oldEdge);
                Node sourceNode = oldToNew.get(endpoints.getFirst());
                Node destNode = oldToNew.get(endpoints.getSecond());
                graphCopy.addEdge(new Edge(oldEdge.getLabel()), sourceNode, destNode);
            }
        }catch (OutOfMemoryError e) {
            int nodes = originalGraph.getVertexCount();
            int edges = originalGraph.getEdgeCount();
            throw new IllegalStateException(
                    String.format("Graph too large to copy (nodes=%d, edges=%d). " +
                            "Lower graph size or increase JVM heap with -Xmx.", nodes, edges), e);
        }
        return graphCopy;
    }
    public static String edgesToString(UndirectedSparseGraph<Node,Edge> graph){
        return graph.getEdges().stream()
                .map(edge -> {
                    Pair<Node> link = graph.getEndpoints(edge);
                    if(link != null) {
                        return link.getFirst() + "-" + link.getSecond();
                    } else {
                        return "";
                    }
                })
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(", "));
    }
    public static HashMap<Node,Integer> nodeDegrees(UndirectedSparseGraph<Node,Edge> graph){
        HashMap<Node, Integer> degrees = new HashMap<>();
        for(Node node: graph.getVertices()){
            degrees.put(node, graph.degree(node));
        }
        return degrees;
    }
    public static double graphDensity(UndirectedSparseGraph<Node,Edge> graph){
        int V = graph.getVertexCount();
        int E = graph.getEdgeCount();
        if (V <= 1) return 0.0;
        return 2.0 * E / (V * (V - 1));
    }
    public static int computeDiameter(UndirectedSparseGraph<Node, Edge> graph) {
        int n = graph.getVertexCount();

        if (n == 0) return 0;
        DijkstraShortestPath<Node, Edge> dsp = new DijkstraShortestPath<>(graph);
        int diameter = 0;
        for (Node u : graph.getVertices()) {
            for (Node v : graph.getVertices()) {
                if (!u.equals(v)) {
                    Number dist = dsp.getDistance(u, v);
                    if (dist != null && dist.intValue() > diameter) {
                        diameter = dist.intValue();
                    }
                }
            }
        }
        return diameter;
    }
    public static double averagePath(UndirectedSparseGraph<Node, Edge> graph) {
        if (graph.getVertexCount() == 0) return 0.0;

        UnweightedShortestPath<Node, Edge> usp = new UnweightedShortestPath<>(graph);
        double totalLength = 0;
        long totalPairs = 0;

        List<Node> nodes = new ArrayList<>(graph.getVertices());
        for (int i = 0; i < nodes.size(); i++) {
            Node u = nodes.get(i);
            Map<Node, Number> distances = usp.getDistanceMap(u);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node v = nodes.get(j);
                if (distances.containsKey(v)) {
                    totalLength += distances.get(v).doubleValue();
                    totalPairs++;
                }
            }
        }
        return totalLength / totalPairs;
    }
}
