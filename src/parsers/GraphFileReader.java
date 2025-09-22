package parsers;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphFileReader {
    public static UndirectedSparseGraph<Node, Edge> readUndirectedSparseGraph(String file) throws GraphReadException {
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            UndirectedSparseGraph<Node,Edge> graph = new UndirectedSparseGraph<>();
            Map<Integer, Node> nodes = new HashMap<>();
            String line;
            int selfLoopCounter = 0;
            int duplicateEdgesCounter = 0;
            int lineCounter = 0;
            while((line = br.readLine()) != null){
                lineCounter++;
                if(line.startsWith("#"))continue;
                if(line.matches(".*[a-zA-Z].*")) continue;
                String[] nodePair;
                if(line.contains(",")) {
                    nodePair = line.split(",\\s*");
                } else {
                    nodePair = line.trim().split("\\s+");
                }
                if (nodePair.length != 2) {
                    System.err.println("Invalid number of values on line " + lineCounter + ": '" + line + "'. Skipping.");
                    continue;
                }
                try{
                    int srcId = Integer.parseInt(nodePair[0]);
                    int dstId = Integer.parseInt(nodePair[1]);
                    Node src = nodes.computeIfAbsent(srcId, id -> {
                        Node n = new Node(id);
                        graph.addVertex(n);
                        return n;
                    });
                    Node dst = nodes.computeIfAbsent(dstId, id -> {
                        Node n = new Node(id);
                        graph.addVertex(n);
                        return n;
                    });
                    if(src.equals(dst)) {
                        selfLoopCounter++;
                    }else if(graph.isNeighbor(src, dst)){
                        duplicateEdgesCounter++;
                    }else{
                        graph.addEdge(new Edge(), src, dst);
                    }
                }catch (NumberFormatException e) {
                    throw new GraphReadException("Number format error on line " + lineCounter + ": '" + line + "'", e);
                } catch (IllegalArgumentException e) {
                    throw new GraphReadException("Invalid line on line " + lineCounter + ": '" + line + "'", e);
                }
            }
            if(selfLoopCounter > 0){
                System.out.printf("There are %d self loops!%n", selfLoopCounter);
            }
            if(duplicateEdgesCounter > 0){
                System.out.printf("There are %d duplicate edges!%n", duplicateEdgesCounter);
            }
            System.out.printf("Successful import! Network with %d nodes and %d edges.%n",graph.getVertexCount(), graph.getEdgeCount());
            return graph;
        }catch(IOException e){
            throw new GraphReadException("Error reading graph file: " + file, e);
        }
    }
}
