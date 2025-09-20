package utils;

import algorithms.BatageljZaversnik;
import algorithms.StraightForward;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.Edge;
import graph.Node;
import java.util.HashMap;
import java.util.Map;

public class GraphUtils {
    public static double avgDegree(UndirectedSparseGraph<Node,Edge> graph){
        if(graph.getVertexCount() == 0) return 0;
        double sum = 0;
        for(Node node:graph.getVertices()){
            sum += graph.degree(node);
        }
        return sum / graph.getVertexCount();
    }
    public static UndirectedSparseGraph<Node, Edge> deepCopy(UndirectedSparseGraph<Node,Edge> originalGraph){
        UndirectedSparseGraph<Node,Edge> graphCopy= new UndirectedSparseGraph<>();
        Map<Node,Node> oldToNew = new HashMap<>(); //Map original nodes to their copies
        try {
            //Copy all nodes from the original graph
            for (Node ogNode : originalGraph.getVertices()) {
                Node newNode = new Node(ogNode.getId());
                graphCopy.addVertex(newNode);
                oldToNew.put(ogNode, newNode);
            }
            // Copy all edges, connecting the new node instances
            for (Edge oldEdge : originalGraph.getEdges()) {
                Pair<Node> endpoints = originalGraph.getEndpoints(oldEdge);
                Node sourceNode = oldToNew.get(endpoints.getFirst());
                Node destNode = oldToNew.get(endpoints.getSecond());
                graphCopy.addEdge(new Edge(oldEdge.getLabel()), sourceNode, destNode);
            }
        }catch (OutOfMemoryError e) {
            int nodes = originalGraph.getVertexCount();
            int edges = originalGraph.getEdgeCount();
            throw new IllegalStateException(
                    String.format("Graph too large to copy (nodes=%d, edges=%d). " +
                            "Lower graph size or increase JVM heap with -Xmx.", nodes, edges), e);
        }
        return graphCopy;
    }
    public static void compareBatageljZaversnikAndStraightForwardAlgo(UndirectedSparseGraph<Node,Edge> graph){
        if(graph.getVertexCount() == 0){
            System.out.println("The supplied graph is empty, continuing with the next one...");
            return;
        }
        BatageljZaversnik bz = new BatageljZaversnik(graph, false);
        long start = System.nanoTime();
        bz.run();
        long duration = System.nanoTime() - start;
        Map<Node,Integer> bzShellIndices = bz.getShellIndices();
        System.out.println("Batagelj-Zaversnik algorithm took "+(duration / 1_000_000_000.0)+" seconds.");
        start = System.nanoTime();
        Map<Node,Integer> sfShellIndices = StraightForward.straightForwardAlgorithm(graph);
        duration = System.nanoTime() - start;
        System.out.println("Straight forward algorithm took "+(duration / 1_000_000_000.0)+" seconds.");
        if(bzShellIndices.isEmpty()){
            System.out.println("Batagelj-Zaversnik algorithm made an error - map of shell indices is empty.");
            return;
        }else if(sfShellIndices.isEmpty()){
            System.out.println("Straight forward algorithm made an error - map of shell indices is empty.");
            return;
        }
        if(!bzShellIndices.equals(sfShellIndices)){
            for(Node node: bzShellIndices.keySet()){
                int bzIndex = bzShellIndices.get(node);
                int sfIndex = sfShellIndices.get(node);
                if(bzIndex != sfIndex){
                    System.out.println("Mismatch of indexes: "+node+
                            " Batagelj-Zaversnik: "+bzIndex+
                            " Straight forward: "+sfIndex);
                }
            }
        }else{
            int maxIndex = bz.maxShellIndex();
            System.out.println("Max shell index in the graph is "+maxIndex);
            System.out.println("Number of nodes inside the max core is "+bz.getKcoreNodes(maxIndex).size());
            System.out.println("Both the Batagelj-Zaversnik and straight forward algorithm produced identical shell indices.");
        }
    }
}
