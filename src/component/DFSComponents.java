package component;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.util.*;

public class DFSComponents {
    public static Set<Set<Node>> findConnectedComps(UndirectedSparseGraph<Node,Edge> graph){
        Set<Node> visited = new HashSet<>();
        Set<Set<Node>> connectedComps = new HashSet<>();
        for(Node node: graph.getVertices()){
            if(!visited.contains(node)){
                connectedComps.add(identifyCompIterative(node, graph, visited));
            }
        }
        return connectedComps;
    }
    private static Set<Node> identifyCompRecursive(Node start, UndirectedSparseGraph<Node,Edge> graph, Set<Node> visited){
        Set<Node> connectedComp = new HashSet<>();
        visited.add(start);
        connectedComp.add(start);
        dfs(start, graph, visited, connectedComp);
        return connectedComp;
    }
    private static void dfs(Node current, UndirectedSparseGraph<Node,Edge> graph, Set<Node> visited, Set<Node> connectedComp){
        for(Node neighbor: graph.getNeighbors(current)){
            if(!visited.contains(neighbor)){
                connectedComp.add(neighbor);
                visited.add(neighbor);
                dfs(neighbor, graph, visited, connectedComp);
            }
        }
    }
    private static Set<Node> identifyCompIterative(Node start, UndirectedSparseGraph<Node,Edge> graph, Set<Node> visited){
        Set<Node> connectedComp = new HashSet<>();
        Stack<Node> dfsStack = new Stack<>();

        visited.add(start);
        connectedComp.add(start);
        dfsStack.push(start);

        while(!dfsStack.isEmpty()){
            Node current = dfsStack.pop();
            for(Node neighbor: graph.getNeighbors(current)){
                if(!visited.contains(neighbor)){
                    visited.add(neighbor);
                    connectedComp.add(neighbor);
                    dfsStack.push(neighbor);
                }
            }
        }
        return connectedComp;
    }
}
