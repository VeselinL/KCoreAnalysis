package test.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;
import models.random.BarabasiAlbertModel;
import static test.AlgorithmComparison.*;

public class BarabasiAlbertTest {
    public static void main(String[]args){
        test(1000,500,100,0.05);
        test(10_000,2000,500,0.1);
        test(10_000, 500, 100, 0.1);
    }
    private static void test(int N, int m0, int m, double p){
        try {
            BarabasiAlbertModel baModel = new BarabasiAlbertModel(N, m0, m, p);
            UndirectedSparseGraph<Node,Edge> baGraph = baModel.getGraph();
            System.out.printf("Barabasi-Albert model with %d total number of nodes, %d initial nodes, %d number of new edges and %.0f%% probability:%n",N,m0,m,(p*100));
            baModel.compareAvgDegree();
            compareBatageljZaversnikAndStraightForwardAlgo(baGraph);
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters: " + e.getMessage());
        }
    }
}
