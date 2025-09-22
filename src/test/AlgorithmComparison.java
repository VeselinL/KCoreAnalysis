package test;

import algorithms.BatageljZaversnik;
import algorithms.StraightForward;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Node;
import java.util.Map;

public class AlgorithmComparison {
    public static void compareBatageljZaversnikAndStraightForwardAlgo(UndirectedSparseGraph<Node, Edge> graph){
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
