package algorithms.abs;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class KCoreDecomposition {
    protected final UndirectedSparseGraph<Node, Edge> graph;
    protected HashMap<Node, Integer> shellIndices = new HashMap<>();
    public KCoreDecomposition(UndirectedSparseGraph<Node,Edge> graph){
        this.graph = graph;
    }
    public void run(){
        computeKCores();
    }
    protected abstract void computeKCores();
    public abstract UndirectedSparseGraph<Node, Edge> getKcoreNetwork(int k);

    public HashMap<Node, Integer> getShellIndices() {
        return shellIndices;
    }
    public Set<Node> getKcoreNodes(int k) {
        Set<Node> kcoreNodes = new HashSet<>();
        for(Node node: graph.getVertices()){
            if (shellIndices.getOrDefault(node, -1) >= k) {
                kcoreNodes.add(node);
            }
        }
        return kcoreNodes;
    }
}
