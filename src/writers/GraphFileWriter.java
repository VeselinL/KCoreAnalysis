package writers;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class GraphFileWriter {
    public static void writeUndirectedSparseGraphMetrics(UndirectedSparseGraph<Node,Edge> graph,
                                                         HashMap<Node, Integer> shellIndices,
                                                         HashMap<Node, Double> cc,
                                                         HashMap<Node, Double> bc,
                                                         HashMap<Node, Double> ec,
                                                         String file,
                                                         boolean fullCentralities){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            if(fullCentralities){
                bw.write("node,degree,shell_index,closeness,betweenness,eigenvector");
            }else{
                bw.write("node,degree,shell_index,harmonic_closeness,approx_betweenness,approx_eigenvector");
            }
            bw.newLine();
            StringBuilder sb = new StringBuilder();
            for(Node node: graph.getVertices()){
                sb.setLength(0);
                sb.append(node).append(",")
                        .append(graph.degree(node)).append(",")
                        .append(shellIndices.getOrDefault(node, -1)).append(",");;
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
