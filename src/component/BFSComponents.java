package component;

import analysis.network.generated.FourPartition;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.util.*;

public class BFSComponents {
    public static Set<Set<Node>> findConnectedComps(UndirectedSparseGraph<Node,Edge> graph){
        Set<Node> visited = new HashSet<>();
        Set<Set<Node>> connectedComps = new HashSet<>();
        for(Node node: graph.getVertices()){
            if(!visited.contains(node)){
                connectedComps.add(identifyComp(node, graph, visited));
            }
        }
        return connectedComps;
    }
    private static Set<Node> identifyComp(Node start, UndirectedSparseGraph<Node,Edge> graph, Set<Node> visited){
        Set<Node> connectedComp = new HashSet<>();
        Queue<Node> queue = new ArrayDeque<>();

        queue.add(start);
        connectedComp.add(start);
        visited.add(start);

        while(!queue.isEmpty()) {
            Node current = queue.remove();
            for (Node neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    connectedComp.add(neighbor);
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return connectedComp;
    }
}
