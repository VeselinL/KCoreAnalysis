package test.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import models.random.ErdosRenyiModel;
import graph.*;

import static utils.GraphUtils.compareBatageljZaversnikAndStraightForwardAlgo;

public class ErdosRenyiTest {
    public static void main(String[]args){
        int[] sizes = {1_000, 10_000, 20_000};
        double[] probs = {0.01, 0.05, 0.1};
        for(int n: sizes){
            for(double p: probs){
                test(n,p);
            }
        }
    }
    private static void test(int n, double p){
        try {
            System.out.println(("Erdos-renyi model with " + n + " nodes and " + (p * 100) + "% chance for node connection:").toUpperCase());
            ErdosRenyiModel erModel = new ErdosRenyiModel(n, p);
            UndirectedSparseGraph<Node, Edge> erGraph = erModel.getGraph();
            erModel.compareAvgDegree();
            compareBatageljZaversnikAndStraightForwardAlgo(erGraph);
            System.out.println();
        }catch(IllegalArgumentException e) {
            System.out.println("Invalid parameters: " + e.getMessage()+"\n");
        }
    }
}
