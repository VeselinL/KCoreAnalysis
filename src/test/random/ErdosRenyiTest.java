package test.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import models.random.ErdosRenyiModel;
import graph.*;

import static test.AlgorithmComparison.*;
import static utils.GraphUtils.avgDegree;

public class ErdosRenyiTest {
    public static void main(String[]args){
        int[] sizes = {1_000, 10_000, 20_000};
        double[] probs = {0.0006, 0.001,0.01, 0.05, 0.1};
        for(int size: sizes){
            for(double prob: probs){
                test(size, prob);
            }
        }
    }
    private static void test(int n, double p){
        try {
            System.out.println(("Erdos-renyi model with " + n + " nodes and " + (p * 100) + "% chance for node connection:").toUpperCase());
            UndirectedSparseGraph<Node, Edge> erGraph = new ErdosRenyiModel(n, p).getGraph();

            System.out.printf("Expected average degree is %.3f; actual one is %.3f.%n", p * (n-1), avgDegree(erGraph));
            int expectedEdges = (int)Math.round(p * (n * (n - 1)) / 2);
            System.out.printf("Expected number of edges is %,d; actual number of edges is %,d.%n", expectedEdges, erGraph.getEdgeCount());

            compareBatageljZaversnikAndStraightForwardAlgo(erGraph);
            System.out.println();
        }catch(IllegalArgumentException e) {
            System.out.println("Invalid parameters: " + e.getMessage()+"\n");
        }
    }
}
