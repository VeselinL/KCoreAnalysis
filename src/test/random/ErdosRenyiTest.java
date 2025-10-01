package test.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import models.random.ErdosRenyiModel;
import graph.*;

import java.util.HashMap;
import java.util.Map;

import static utils.centrality.ApproximationUtils.*;
import static utils.centrality.CentralityUtils.*;
import static test.AlgorithmComparison.*;

public class ErdosRenyiTest {
    public static void main(String[]args){
        int[] sizes = {1_000, 10_000, 20_000};
        double[] probs = {0.01, 0.05, 0.1};
        test(1000, 0.1);
        //for(int n: sizes){
          //  for(double p: probs){
            //    test(n,p);
            //}
        //}
    }
    private static void test(int n, double p){
        try {
            System.out.println(("Erdos-renyi model with " + n + " nodes and " + (p * 100) + "% chance for node connection:").toUpperCase());
            ErdosRenyiModel erModel = new ErdosRenyiModel(n, p);
            UndirectedSparseGraph<Node, Edge> erGraph = erModel.getGraph();
            //erModel.compareAvgDegree();
            //compareBatageljZaversnikAndStraightForwardAlgo(erGraph);
            long start = System.nanoTime();
            HashMap<Node, Double> aec = approximateEigenvectorCentrality(erGraph, 100);
            long duration = System.nanoTime() - start;
            double seconds = duration / 1_000_000_000.0;
            System.out.println(seconds + " s");
            start = System.nanoTime();
            HashMap<Node,Double> ec = computeEigenvectorCentrality(erGraph);
            duration = System.nanoTime() - start;
            seconds = duration / 1_000_000_000.0;
            System.out.println(seconds + " s");
            System.out.println("Approximate Eigenvector Centrality:");
            for (Map.Entry<Node, Double> entry : aec.entrySet()) {
                System.out.print(entry.getKey() + " -> " + entry.getValue()+" | ");
            }
            System.out.println("\nExact Eigenvector Centrality:");
            for (Map.Entry<Node, Double> entry : ec.entrySet()) {
                System.out.print(entry.getKey() + " -> " + entry.getValue()+" | ");
            }
        }catch(IllegalArgumentException e) {
            System.out.println("Invalid parameters: " + e.getMessage()+"\n");
        }
    }
}
