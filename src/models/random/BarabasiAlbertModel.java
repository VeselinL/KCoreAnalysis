package models.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarabasiAlbertModel {
    private final UndirectedSparseGraph<Node,Edge> graph;
    final static int MAX_ATTEMPTS = 10000;
    List<Node> degrees;
    public BarabasiAlbertModel(int N,int m0,int m, double p){
        if(m > m0 || m < 1){
            throw new IllegalArgumentException("Number of new edges(m) must be >=1 and <= number of initial nodes(m0)!");
        }
        degrees = new ArrayList<>();
        graph = new ErdosRenyiModel(m0, p).getGraph();
        initDegrees();
        Random random = new Random();
        for(int i = m0+1;i<=N;i++){
            Node newNode = new Node(i);
            graph.addVertex(newNode);
            int edgesAdded = 0, attempts = 0;
            while(edgesAdded < m && attempts <= MAX_ATTEMPTS){
                Node oldNode = degrees.get(random.nextInt(degrees.size()));
                attempts++;
                if(edgeCanBeAdded(oldNode, newNode)){
                    addNodes(oldNode, newNode);
                    edgesAdded++;
                }
            }
            if(edgesAdded < m){
                System.out.println("Could only add "+edgesAdded+" for node "+newNode);
            }
        }
    }
    private void initDegrees(){
        for(Node node: graph.getVertices()){
            for(int i  = 0;i<graph.degree(node);i++){
                degrees.add(node);
            }
        }
    }
    private boolean edgeCanBeAdded(Node oldNode, Node newNode){
        return !(graph.isNeighbor(oldNode, newNode) || newNode.equals(oldNode));

    }
    private void addNodes(Node oldNode, Node newNode){
        graph.addEdge(new Edge(), oldNode,newNode);
        degrees.add(oldNode);
        degrees.add(newNode);
    }
    public UndirectedSparseGraph<Node,Edge> getGraph(){
        return this.graph;
    }
}
