package algorithms;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import utils.GraphUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import graph.*;

public class StraightForward{
    public static  HashMap<Node, Integer> straightForwardAlgorithm(UndirectedSparseGraph<Node, Edge> originalGraph) {
        UndirectedSparseGraph<Node,Edge> graphCopy = GraphUtils.deepCopy(originalGraph);

        HashMap<Node, Integer> shellIndices = new HashMap<>();
        Set<Node> remainingNodes = new HashSet<>(graphCopy.getVertices());
        int k = 0;

        while (!remainingNodes.isEmpty()) {
            Set<Node> nodesToRemove = new HashSet<>();
            // Collect all vertices with degree less than k
            for (Node node : remainingNodes) {
                if (graphCopy.degree(node) < k) {
                    nodesToRemove.add(node);
                }
            }
            // If no vertices are found with degree < k, increment k
            if (nodesToRemove.isEmpty()) {
                k++;
                // If k exceeds the maximum possible degree, break the loop
                if (k > graphCopy.getVertexCount()) {
                    break;
                }
                continue; // Restart the loop with the new, higher k
            }
            // Assign the current k-core value (k-1) to the removed vertices and remove them
            for (Node node : nodesToRemove) {
                shellIndices.put(node, k - 1);
                graphCopy.removeVertex(node);
            }
            remainingNodes.removeAll(nodesToRemove);
        }

        // Handle any remaining vertices (these belong to the highest k-core)
        for (Node node : remainingNodes) {
            shellIndices.put(node, k - 1);
        }

        return shellIndices;
    }
}