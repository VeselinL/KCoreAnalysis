package algorithms;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;
import utils.GraphUtils;

import java.util.HashSet;
import java.util.Set;

public class StraightForward{
    public static UndirectedSparseGraph<Node,Edge> straightForwardAlgorithm(UndirectedSparseGraph<Node,Edge> originalGraph, int k){

        UndirectedSparseGraph<Node,Edge> copy = GraphUtils.deepCopy(originalGraph);
        Set<Node> nodes = new HashSet<>(copy.getVertices());

        boolean change = true;
        while(change){
            change = false;
            for(Node node: nodes){
                if(copy.containsVertex(node)) {
                    if (copy.degree(node) < k) {
                        copy.removeVertex(node);
                        change = true;
                    }
                }
            }
        }
        return copy;
    }
}