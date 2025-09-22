package utils;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.Edge;
import graph.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphUtils {
    public static double avgDegree(UndirectedSparseGraph<Node,Edge> graph){
        if(graph.getVertexCount() == 0) return 0;
        double sum = 0;
        for(Node node:graph.getVertices()){
            sum += graph.degree(node);
        }
        return sum / graph.getVertexCount();
    }
    public static UndirectedSparseGraph<Node, Edge> deepCopy(UndirectedSparseGraph<Node,Edge> originalGraph){
        UndirectedSparseGraph<Node,Edge> graphCopy= new UndirectedSparseGraph<>();
        Map<Node,Node> oldToNew = new HashMap<>(); //Map original nodes to their copies
        try {
            //Copy all nodes from the original graph
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
}
