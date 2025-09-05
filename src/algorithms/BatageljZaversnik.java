package algorithms;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BatageljZaversnik {
    private UndirectedSparseGraph<Node, Edge> graph;
    private HashMap<Node, Integer> shellIndices;
    private HashMap<Integer, HashSet<Node>> kcoreNodes;
    public BatageljZaversnik(UndirectedSparseGraph<Node,Edge> graph){
        this.graph = graph;
        kcoreNodes = new HashMap<>();
        computeKCores();
    }
    private void computeKCores(){
        int max_degree = getMaxDegree();

        HashMap<Node, Integer> degrees = new HashMap<>();
        HashMap<Integer, HashSet<Node>>kcores = new HashMap<>();

        for (int i = 0; i < max_degree + 1; i++) {
            kcores.put(i, new HashSet<Node>());
        }
        for (Node node : graph.getVertices()) {
            degrees.put(node, graph.degree(node));
            Set<Node> kcore = kcores.get(graph.degree(node));
            kcore.add(node);
        }
        shellIndices = new HashMap<>();
        for (int i = 0; i <= max_degree; i++) {
            HashSet<Node> kcore = kcores.get(i);
            kcoreNodes.put(i, new HashSet<>(kcores.get(i)));
            while (!kcore.isEmpty()) {
                Iterator<Node> it = kcore.iterator();
                Node current_node = it.next();
                it.remove();
                shellIndices.put(current_node, i);
                for (Node neighbor : graph.getNeighbors(current_node)) {
                    int neighbor_degree = degrees.get(neighbor);
                    if (neighbor_degree > i) {
                        kcores.get(neighbor_degree).remove(neighbor);
                        kcores.get(neighbor_degree - 1).add(neighbor);
                        degrees.put(neighbor, neighbor_degree - 1);
                    }
                }
            }
        }
    }
    private int getMaxDegree(){
        int max_degree = Integer.MIN_VALUE;
        for(Node node: graph.getVertices()){
            if(graph.degree(node) > max_degree){
                max_degree = graph.degree(node);
            }
        }
        return max_degree;
    }
    public UndirectedSparseGraph<Node,Edge> getKcoreNetwork(int k){
        Set<Node> nodes = getKcoreNodes(k);
        UndirectedSparseGraph<Node, Edge> kcoreNetwork = new UndirectedSparseGraph<>();
        for(Node node: nodes) kcoreNetwork.addVertex(node);
        for(Node node: nodes){
            for(Node neighbor: graph.getNeighbors(node)){
                if(nodes.contains(neighbor)){
                    kcoreNetwork.addEdge(new Edge(""), node,neighbor);
                }
            }
        }
        return kcoreNetwork;
    }
    public int getShellIndex(Node node){
        return shellIndices.getOrDefault(node, -1);
    }
    public HashMap<Node, Integer> getShellIndices(){
        return shellIndices;
    }
    public Set<Node> getKcoreNodes(int k) {
        return kcoreNodes.getOrDefault(k,new HashSet<>());
    }
}
