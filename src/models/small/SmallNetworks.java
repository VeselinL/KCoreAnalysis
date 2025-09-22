package models.small;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Node;
import java.util.ArrayList;
import java.util.List;
public class SmallNetworks {
    public static UndirectedSparseGraph<Node, Edge> createGraphFromPresentation() {
        UndirectedSparseGraph<Node, Edge> graph = new UndirectedSparseGraph<>();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Node node = new Node(i);
            graph.addVertex(node);
            nodes.add(node);
        }
        graph.addEdge(new Edge(), nodes.get(0), nodes.get(1));
        graph.addEdge(new Edge(), nodes.get(0), nodes.get(2));
        graph.addEdge(new Edge(), nodes.get(0), nodes.get(3));
        graph.addEdge(new Edge(), nodes.get(0), nodes.get(9));
        graph.addEdge(new Edge(), nodes.get(0), nodes.get(14));
        graph.addEdge(new Edge(), nodes.get(1), nodes.get(2));
        graph.addEdge(new Edge(), nodes.get(1), nodes.get(3));
        graph.addEdge(new Edge(), nodes.get(1), nodes.get(4));
        graph.addEdge(new Edge(), nodes.get(2), nodes.get(3));
        graph.addEdge(new Edge(), nodes.get(2), nodes.get(4));
        graph.addEdge(new Edge(), nodes.get(2), nodes.get(6));
        graph.addEdge(new Edge(), nodes.get(2), nodes.get(8));
        graph.addEdge(new Edge(), nodes.get(3), nodes.get(9));
        graph.addEdge(new Edge(), nodes.get(3), nodes.get(6));
        graph.addEdge(new Edge(), nodes.get(4), nodes.get(5));
        graph.addEdge(new Edge(), nodes.get(6), nodes.get(7));
        graph.addEdge(new Edge(), nodes.get(7), nodes.get(8));
        graph.addEdge(new Edge(), nodes.get(14), nodes.get(10));
        graph.addEdge(new Edge(), nodes.get(14), nodes.get(11));
        graph.addEdge(new Edge(), nodes.get(14), nodes.get(12));
        graph.addEdge(new Edge(), nodes.get(14), nodes.get(13));
        return graph;
    }
    public static UndirectedSparseGraph<Node,Edge> createGraphWithTwoCliques(){
        UndirectedSparseGraph<Node,Edge> graph = new UndirectedSparseGraph<>();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Node node = new Node(i);
            graph.addVertex(node);
            nodes.add(node);
        }
        //Form 2 cliques
        formClique(graph, nodes, 0, 5);
        formClique(graph, nodes, 5, 10);
        graph.addEdge(new Edge(), nodes.get(4), nodes.get(5)); //Connect them

        //Add remaining edges
        graph.addEdge(new Edge(), nodes.get(0), nodes.get(16));
        graph.addEdge(new Edge(), nodes.get(0), nodes.get(10));
        graph.addEdge(new Edge(), nodes.get(1), nodes.get(13));
        graph.addEdge(new Edge(), nodes.get(2), nodes.get(14));
        graph.addEdge(new Edge(), nodes.get(3), nodes.get(15));
        graph.addEdge(new Edge(), nodes.get(6), nodes.get(17));
        graph.addEdge(new Edge(), nodes.get(7), nodes.get(18));
        graph.addEdge(new Edge(), nodes.get(8), nodes.get(18));
        graph.addEdge(new Edge(), nodes.get(9), nodes.get(19));
        graph.addEdge(new Edge(), nodes.get(10), nodes.get(11));
        graph.addEdge(new Edge(), nodes.get(11), nodes.get(12));

        return graph;
    }
    public static UndirectedSparseGraph<Node,Edge> createGraphWithFourPartitions(){
        UndirectedSparseGraph<Node, Edge> graph = new UndirectedSparseGraph<>();
        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i < 18; i++) {
            Node node = new Node(i);
            graph.addVertex(node);
            nodes.add(node);
        }
        //Form a 6-node cycle
        for (int i = 0; i < 6; i++) {
            graph.addEdge(new Edge(), nodes.get(i), nodes.get((i + 1) % 6));
        }
        //Form a 5-node star subgraph
        for (int i = 7; i <= 10; i++) {
            graph.addEdge(new Edge(), nodes.get(6), nodes.get(i));
        }
        graph.addEdge(new Edge(), nodes.get(11), nodes.get(12));
        graph.addEdge(new Edge(), nodes.get(12), nodes.get(13));
        graph.addEdge(new Edge(), nodes.get(13), nodes.get(11));
        graph.addEdge(new Edge(), nodes.get(14), nodes.get(15));
        graph.addEdge(new Edge(), nodes.get(16), nodes.get(17));
        graph.addEdge(new Edge(), nodes.get(2), nodes.get(6));

        return graph;

    }
    private static void formClique(UndirectedSparseGraph<Node, Edge> graph, List<Node> nodes, int start, int end) {
        for (int i = start; i < end; i++) {
            for (int j = i + 1; j < end; j++) {
                graph.addEdge(new Edge(), nodes.get(i), nodes.get(j));
            }
        }
    }
}
