package algorithms;

import algorithms.a.KCoreDecomposition;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;
import java.util.*;

public class BatageljZaversnik extends KCoreDecomposition {
    private HashMap<Integer, HashSet<Node>> kcoreBuckets;

    public BatageljZaversnik(UndirectedSparseGraph<Node,Edge> graph){
        super(graph);
    }
    @Override
    protected void computeKCores(){
        int maxDegree = getMaxDegree();
        HashMap<Node, Integer> nodeDegrees = initializeBuckets(maxDegree);
        for (int i = 0; i <= maxDegree; i++) {
            HashSet<Node> currentBucket = kcoreBuckets.get(i);
            while (!currentBucket.isEmpty()) {
                Iterator<Node> it = currentBucket.iterator();
                Node currentNode = it.next();
                it.remove();
                shellIndices.put(currentNode, i);
                for (Node neighbor : graph.getNeighbors(currentNode)) {
                    int neighborDegree = nodeDegrees.get(neighbor);
                    if (neighborDegree > i) {
                        kcoreBuckets.get(neighborDegree).remove(neighbor);
                        kcoreBuckets.get(neighborDegree - 1).add(neighbor);
                        nodeDegrees.put(neighbor, neighborDegree - 1);
                    }
                }
            }
        }
    }
    private int getMaxDegree(){
        int maxDegree = Integer.MIN_VALUE;
        for(Node node: graph.getVertices()){
            if(graph.degree(node) > maxDegree){
                maxDegree = graph.degree(node);
            }
        }
        return maxDegree;
    }
    public UndirectedSparseGraph<Node,Edge> getKcoreNetwork(int k){
        Set<Node> nodes = super.getKcoreNodes(k);
        UndirectedSparseGraph<Node, Edge> kcoreNetwork = new UndirectedSparseGraph<>();
        for(Node node: nodes) kcoreNetwork.addVertex(node);
        for(Node node: nodes){
            for(Node neighbor: graph.getNeighbors(node)){
                if(nodes.contains(neighbor)){
                    kcoreNetwork.addEdge(new Edge(), node,neighbor);
                }
            }
        }
        return kcoreNetwork;
    }
    public int getShellIndex(Node node){
        return shellIndices.getOrDefault(node, -1);
    }
    private HashMap<Node, Integer> initializeBuckets(int maxDegree){
        kcoreBuckets = new HashMap<>();
        HashMap<Node, Integer> nodeDegrees = new HashMap<>();
        for (int i = 0; i <= maxDegree; i++) {
            kcoreBuckets.put(i, new HashSet<>());
        }
        for (Node node : graph.getVertices()) {
            nodeDegrees.put(node, graph.degree(node));
            Set<Node> kcore = kcoreBuckets.get(graph.degree(node));
            kcore.add(node);
        }
        return nodeDegrees;
    }
}
