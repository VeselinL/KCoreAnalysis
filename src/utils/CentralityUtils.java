package utils;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.util.HashMap;

public class CentralityUtils {
    public static HashMap<Node,Double> computeBetweennessCentrality(UndirectedSparseGraph<Node, Edge> graph){
        BetweennessCentrality<Node,Edge> bc = new BetweennessCentrality<>(graph);
        HashMap<Node, Double> bcScores = new HashMap<>();
        for(Node node: graph.getVertices()){
            bcScores.put(node, bc.getVertexScore(node));
        }
        return bcScores;
    }
    public static HashMap<Node, Double> computeClosenessCentrality(UndirectedSparseGraph<Node,Edge> graph){
        ClosenessCentrality<Node,Edge> cc = new ClosenessCentrality<>(graph);
        HashMap<Node, Double> ccScores = new HashMap<>();
        for(Node node: graph.getVertices()){
            ccScores.put(node, cc.getVertexScore(node));
        }
        return ccScores;
    }
    public static HashMap<Node, Double> computeEigenvectorCentrality(UndirectedSparseGraph<Node,Edge> graph){
        EigenvectorCentrality<Node, Edge> ec = new EigenvectorCentrality<>(graph);
        ec.setMaxIterations(1000);
        ec.setTolerance(1e-6);
        ec.evaluate();
        HashMap<Node, Double> ecScores = new HashMap<>();
        for(Node node: graph.getVertices()){
            ecScores.put(node, ec.getVertexScore(node));
        }
        return ecScores;
    }
}
