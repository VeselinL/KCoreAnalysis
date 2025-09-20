package models.random;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.util.*;

public class CorePeripheryModel {
    public static UndirectedSparseGraph<Node,Edge> discreteModel(int n, int nc, double cc, double pc){
        if(n <= 0){
            System.out.println("Empty graph, skipping..");
            return new UndirectedSparseGraph<>();
        }
        if (cc < 0 || cc > 1 || pc < 0 || pc > 1) {
            throw new IllegalArgumentException("cc and pc must be in [0,1]");
        }
        UndirectedSparseGraph<Node,Edge> graph = new UndirectedSparseGraph<>();
        Map<String, Set<Node>> classes= new HashMap<>();
        Random random = new Random();
        Set<Node> core = new HashSet<>();
        Set<Node> periphery = new HashSet<>();
        classes.put("core", core);
        classes.put("periphery", periphery);
        for(int i = 0;i<nc;i++){
            Node newNode = new Node(i);
            core.add(newNode);
            graph.addVertex(newNode);
        }
        for(int i = nc;i<n;i++){
            Node newNode = new Node(i);
            periphery.add(newNode);
            graph.addVertex(newNode);
        }
        List<Node> nodes = new ArrayList<>(graph.getVertices());
        for(int i = 0;i<nodes.size();i++){
            for(int j = i + 1;j < nodes.size();j++){
                Node src = nodes.get(i);
                Node dst = nodes.get(j);
                if(core.contains(src) && core.contains(dst)) {
                    if (random.nextDouble() < cc) {
                        graph.addEdge(new Edge(), src, dst);
                    }
                }else if(core.contains(src) || core.contains(dst)){
                    if(random.nextDouble() < pc){
                        graph.addEdge(new Edge(), src,dst);
                    }
                }
            }
        }
        return graph;
    }
    public static UndirectedSparseGraph<Node,Edge> continuousModel(int n){
        if(n == 0){
            System.out.println("Empty graph, skipping..");
            return new UndirectedSparseGraph<>();
        }
        UndirectedSparseGraph<Node,Edge> graph = new UndirectedSparseGraph<>();
        Map<Node,Double> coreness = new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i<n;i++){
            Node newNode = new Node(i);
            coreness.put(newNode, random.nextDouble());
            graph.addVertex(newNode);
            nodes.add(newNode);
        }
        for(int i = 0;i<n;i++){
            for(int j =i+1;j<n;j++){
                Node src = nodes.get(i);
                Node dst = nodes.get(j);
                double probability = coreness.get(src) * coreness.get(dst);
                if(random.nextDouble() < probability){
                    graph.addEdge(new Edge(), src,dst);
                }
            }
        }
        return graph;
    }
}
