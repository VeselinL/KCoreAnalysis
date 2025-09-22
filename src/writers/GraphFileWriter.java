package writers;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphFileWriter {
    public static void writeUndirectedSparseGraph(UndirectedSparseGraph<Node,Edge> graph,
                                                  HashMap<Node, Integer> nodes, HashMap<Node, Double> cc,
                                                  HashMap<Node, Double> bc, HashMap<Node, Double> ec,
                                                  String file){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            bw.write("node,closeness,betweenness,eigenvector");
            bw.newLine();
            StringBuilder sb = new StringBuilder();
            for(Node node: graph.getVertices()){
                sb.setLength(0);
                sb.append(nodes.get(node)).append(",");
                Double ccVal = cc.getOrDefault(node, 0.0);
                Double bcVal = bc.getOrDefault(node, 0.0);
                Double ecVal = ec.getOrDefault(node, 0.0);
                sb.append(ccVal).append(",");
                sb.append(bcVal).append(",");
                sb.append(ecVal);
                bw.write(sb.toString());
                bw.newLine();
            }
        }catch(IOException e){
            throw new GraphWriteException("Error writing to file: ", e);
        }
    }
}
