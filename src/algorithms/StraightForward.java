package algorithms;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import utils.GraphUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import graph.*;

import static utils.GraphUtils.maxDegree;

public class StraightForward{
    public static  HashMap<Node, Integer> straightForwardAlgorithm(UndirectedSparseGraph<Node, Edge> originalGraph) {
        UndirectedSparseGraph<Node,Edge> graphCopy = GraphUtils.deepCopy(originalGraph);

        HashMap<Node, Integer> shellIndices = new HashMap<>();
        Set<Node> remainingNodes = new HashSet<>(graphCopy.getVertices());
        int k = 0;

        while (!remainingNodes.isEmpty()) {
            Set<Node> nodesToRemove = new HashSet<>();
            // collect all vertices with degree less than k
            for (Node node : remainingNodes) {
                if (graphCopy.degree(node) < k) {
                    nodesToRemove.add(node);
                }
            }
            if (nodesToRemove.isEmpty()) {
                k++;
                // if k exceeds the maximum possible degree, break the loop
                if (k > maxDegree(graphCopy)) {
                    break;
                }
                continue; // restart the loop with the new, higher k
            }
            for (Node node : nodesToRemove) {
                shellIndices.put(node, k - 1);
                graphCopy.removeVertex(node);
            }
            remainingNodes.removeAll(nodesToRemove);
        }

        // handle remaining nodes
        for (Node node : remainingNodes) {
            shellIndices.put(node, k - 1);
        }

        return shellIndices;
    }
}