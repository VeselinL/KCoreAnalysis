package models.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Node;

import java.util.ArrayList;
import java.util.Random;

import static utils.GraphUtils.avgDegree;

public class ErdosRenyiModel {
    private UndirectedSparseGraph<Node, Edge> graph;
    private ArrayList<Node> nodes;
    private double p;
    private int n;
    public ErdosRenyiModel(int nodeCount, double prob){
        if(nodeCount < 0){
            throw new IllegalArgumentException("Node count can't be less than 0!");
        }
        if(nodeCount == 0) {
            System.out.println("WARNING: Creating an empty graph..");
        }
        if(prob > 1){
            prob = 1;
        }else if(prob < 0){
            throw new IllegalArgumentException("Probability must be in [0,1] !");
        }
        this.n = nodeCount;
        this.p = prob;
        graph = new UndirectedSparseGraph<>();
        nodes = new ArrayList<>();
        for(int i = 0;i<n;i++){
            Node current = new Node(i);
            nodes.add(current);
            graph.addVertex(current);
        }
        Random random = new Random();
        for(int i = 0;i<n;i++){
            for(int j = i+1;j<n;j++){
                if(random.nextDouble() <= p){
                    graph.addEdge(new Edge(),nodes.get(i),nodes.get(j));
                }
            }
        }
    }
    public UndirectedSparseGraph<Node,Edge> getGraph(){
        return this.graph;
    }
}
