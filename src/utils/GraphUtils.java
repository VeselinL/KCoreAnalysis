package utils;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.Edge;
import graph.Node;
import java.util.HashMap;
import java.util.Map;

public class GraphUtils {
    public static UndirectedSparseGraph<Node, Edge> deepCopy(UndirectedSparseGraph<Node,Edge> originalGraph){
        UndirectedSparseGraph<Node,Edge> graphCopy= new UndirectedSparseGraph<>();
        Map<Node,Node> oldToNew = new HashMap<>(); //Map original nodes to their copies
        //Copy all nodes from the original graph
        for(Node ogNode:originalGraph.getVertices()){
            Node newNode = new Node(ogNode.getId());
            graphCopy.addVertex(newNode);
            oldToNew.put(ogNode, newNode);
        }
        // Copy all edges, connecting the new node instances
        for(Edge oldEdge : originalGraph.getEdges()){
            Pair<Node> endpoints = originalGraph.getEndpoints(oldEdge);
            Node sourceNode = oldToNew.get(endpoints.getFirst());
            Node destNode = oldToNew.get(endpoints.getSecond());
            graphCopy.addEdge(new Edge(oldEdge.getLabel()), sourceNode,destNode);
        }
        return graphCopy;
    }
}
