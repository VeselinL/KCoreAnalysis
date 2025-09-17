package models.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ErdosRenyiModel {
    private UndirectedSparseGraph<Node, Edge> graph;
    private HashMap<Integer, Node> nodes;
    private double p;
    private int n;
    public ErdosRenyiModel(int nodeCount, double prob){
        this.n = nodeCount;
        this.p = prob;
        graph = new UndirectedSparseGraph<>();
        nodes = new HashMap<>();
        for(int i = 1;i<=n;i++){
            Node current = new Node(i);
            nodes.put(i, current);
            graph.addVertex(current);
        }
        Random random = new Random();
        for(int i = 1;i<n;i++){
            for(int j = i+1;j<=n;j++){
                if(random.nextDouble() <= p){
                    graph.addEdge(new Edge(),nodes.get(i),nodes.get(j));
                }
            }
        }
    }
    public UndirectedSparseGraph<Node,Edge> getGraph(){
        return this.graph;
    }
    public void compareAvgDegree(){
        double expected = p * (n-1);
        double actual = avgDegree();
        System.out.println("Expected avg degree is "+ expected+", actual one is "+actual+".");
    }
    public double avgDegree(){
        double sum = 0;
        for(Node node:graph.getVertices()){
            sum += graph.degree(node);
        }
        return sum / graph.getVertexCount();
    }
}
