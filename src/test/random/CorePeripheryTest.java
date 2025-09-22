package test.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import static models.random.CorePeripheryModel.*;
import graph.*;
import static test.AlgorithmComparison.*;
import static utils.GraphUtils.avgDegree;

public class CorePeripheryTest {
    public static void main(String[]args){
        testDiscreteModel(1000, 100, 0.5, 0.1);
        testDiscreteModel(10_000, 500, 0.5, 0.1);
        testDiscreteModel(20_000, 2000, 0.75, 0.1);

        testContinuousModel(100);
        testContinuousModel(1000);
        testContinuousModel(10_000);
    }
    public static void testDiscreteModel(int n, int nc, double cc, double pc){
        UndirectedSparseGraph<Node,Edge> graph = discreteModel(n, nc, cc, pc);
        System.out.printf("Discrete Core-Periphery model with %d total nodes, %d core nodes, %d periphery nodes," +
                " %.3g probability for core-core connection and %.3g probability for core-periphery connection.%n", n, nc,(n-nc), cc, pc);
        System.out.printf("Average degree in the graph is %.3f.%n",avgDegree(graph));
        compareBatageljZaversnikAndStraightForwardAlgo(graph);
        System.out.println();
    }
    public static void testContinuousModel(int n){
        UndirectedSparseGraph<Node,Edge> graph = continuousModel(n);
        System.out.printf("Continuous Core-Periphery model with %d nodes.%n", n);
        System.out.printf("Average degree in the graph is %.3f.%n",avgDegree(graph));
        compareBatageljZaversnikAndStraightForwardAlgo(graph);
        System.out.println();
    }
}
