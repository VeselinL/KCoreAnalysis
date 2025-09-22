package algorithms;

import algorithms.abs.KCoreDecomposition;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;
import java.util.*;

public class BatageljZaversnik extends KCoreDecomposition {
    private HashMap<Integer, LinkedHashSet<Node>> kcoreBuckets;

    public BatageljZaversnik(UndirectedSparseGraph<Node,Edge> graph){
        this(graph,true);
    }
    /**
     * Creates the Batagelj–Zaversnik instance.
     * @param autoRun if true, the algorithm is executed immediately
     */
    public BatageljZaversnik(UndirectedSparseGraph<Node,Edge> graph, boolean autoRun){
        super(graph);
        if(autoRun){
            run();
        }
    }
    @Override
    protected void computeKCores(){
        int maxDegree = getMaxDegree();
        HashMap<Node, Integer> nodeDegrees = initializeBuckets(maxDegree);
        for (int k = 0; k <= maxDegree; k++) {
            LinkedHashSet<Node> currentBucket = kcoreBuckets.get(k);
            while (!currentBucket.isEmpty()) {
                Iterator<Node> it = currentBucket.iterator();
                Node currentNode = it.next();
                it.remove();
                shellIndices.put(currentNode, k);
                for (Node neighbor : graph.getNeighbors(currentNode)) {
                    int neighborDegree = nodeDegrees.get(neighbor);
                    if (neighborDegree > k) {
                        kcoreBuckets.get(neighborDegree).remove(neighbor);
                        kcoreBuckets.get(neighborDegree - 1).add(neighbor);
                        nodeDegrees.put(neighbor, neighborDegree - 1);
                    }
                }
            }
        }
    }
    private int getMaxDegree(){
        int maxDegree = 0;
        for(Node node: graph.getVertices()){
            maxDegree = Math.max(maxDegree, graph.degree(node));
        }
        return maxDegree;
    }
    private HashMap<Node, Integer> initializeBuckets(int maxDegree){
        kcoreBuckets = new HashMap<>();
        HashMap<Node, Integer> nodeDegrees = new HashMap<>();
        for (int i = 0; i <= maxDegree; i++) {
            kcoreBuckets.put(i, new LinkedHashSet<>());
        }
        try {
            for (Node node : graph.getVertices()) {
                nodeDegrees.put(node, graph.degree(node));
                LinkedHashSet<Node> kcore = kcoreBuckets.get(graph.degree(node));
                kcore.add(node);
            }
        }catch (OutOfMemoryError e){
            throw new IllegalStateException("Too many nodes to initialize(nodes="+graph.getVertexCount()+")." +
                    " Lower node count or increase JVM heap with -Xmx");
        }
        return nodeDegrees;
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
    public int getKCoreNodeCount(int k){return getKcoreNodes(k).size(); }
    public int maxShellIndex(){
        return shellIndices.isEmpty() ? 0 : Collections.max(shellIndices.values());
    }
}
