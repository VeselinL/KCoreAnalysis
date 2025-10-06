package utils.centrality;

import analysis.network.file.ZacharyKarateClub;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;
import java.util.*;

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
            double score = cc.getVertexScore(node);
            if (Double.isNaN(score) || Double.isInfinite(score)) {
                score = 0.0;
            }
            ccScores.put(node, score);
        }
        return ccScores;
    }
    public static HashMap<Node, Double> computeEigenvectorCentrality(UndirectedSparseGraph<Node, Edge> graph) {
        HashMap<Node, Double> ev = new HashMap<>();
        // assign 0 to isolated nodes
        for (Node v : graph.getVertices()) {
            if (graph.degree(v) == 0) {
                ev.put(v, 0.0);
            }
        }
        // build subgraph of only non-isolated nodes
        UndirectedSparseGraph<Node, Edge> subgraph = new UndirectedSparseGraph<>();
        for (Node v : graph.getVertices()) {
            if (graph.degree(v) > 0) subgraph.addVertex(v);
        }
        for (Edge e : graph.getEdges()) {
            var endpoints = graph.getEndpoints(e);
            if (subgraph.containsVertex(endpoints.getFirst()) && subgraph.containsVertex(endpoints.getSecond())) {
                subgraph.addEdge(new Edge(e.getLabel()), endpoints.getFirst(), endpoints.getSecond());
            }
        }
        // run eigenvector only on the no-isolated nodes subgraph, avoids exceptions
        if (subgraph.getVertexCount() > 0) {
            EigenvectorCentrality<Node, Edge> evc = new EigenvectorCentrality<>(subgraph);
            evc.evaluate();
            for (Node v : subgraph.getVertices()) {
                ev.put(v, evc.getVertexScore(v));
            }
        }

        return ev;
    }
}
