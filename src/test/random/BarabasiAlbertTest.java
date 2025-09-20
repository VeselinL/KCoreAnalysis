package test.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;
import models.random.BarabasiAlbertModel;
import static utils.GraphUtils.compareBatageljZaversnikAndStraightForwardAlgo;

public class BarabasiAlbertTest {
    public static void main(String[]args){
        int[] sizes = {10_000, 20_000};
        int[] initialSizes = {100, 500, 1_000, 5_000};
        double[] probs = {0.01, 0.05, 0.1};
        int[] newEdgesCount = {100, 200, 500};
        test(1000,500,100,0.05);
        test(10_000,2000,500,0.1);
        test(10_000, 500, 100, 0.1);

    }
    private static void test(int N, int m0, int m, double p){
        try {
            BarabasiAlbertModel baModel = new BarabasiAlbertModel(1000, 2000, 10, 0.05);
            UndirectedSparseGraph<Node,Edge> baGraph = baModel.getGraph();
            System.out.printf("Barabasi-Albert model with %d total number of nodes, %d initial nodes, %d number of new edges and %.3g probability:%n",N,m0,m,p);
            baModel.compareAvgDegree();
            compareBatageljZaversnikAndStraightForwardAlgo(baGraph);
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters: " + e.getMessage());
        }
    }
}
