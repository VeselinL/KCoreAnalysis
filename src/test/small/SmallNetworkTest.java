package test.small;
import algorithms.BatageljZaversnik;
import analysis.network.file.ZacharyKarateClub;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Node;
import graph.Edge;
import models.small.SmallNetworks;
import java.util.Set;
import static utils.GraphUtils.edgesToString;
import static utils.GraphUtils.nodeDegrees;
import static utils.VisualizationUtils.drawNetwork;
import static readers.GraphFileReader.readUndirectedSparseGraph;

public class SmallNetworkTests {
    public static void main(String[]args){
        testSmallNetwork(SmallNetworks.createGraphFromPresentation(),"Graph from presentation");
        testSmallNetwork(SmallNetworks.createGraphWithTwoCliques(), "Two clique graph");
        testSmallNetwork(SmallNetworks.createGraphWithFourPartitions(),"Four partition graph");
        testSmallNetwork(new ZacharyKarateClub().getGraph(), "Zachary Karate Club");
    }
    public static void testSmallNetwork(UndirectedSparseGraph<Node,Edge> graph, String nameOfGraph){
        System.out.println("\n"+nameOfGraph.toUpperCase()+":");
        BatageljZaversnik bz = new BatageljZaversnik(graph);
        System.out.println(nodeDegrees(graph));
        drawNetwork(graph, nameOfGraph);
        for(int k = 0;k <= bz.maxShellIndex();k++){
            System.out.println("\n"+k+"-CORE:");
            Set<Node> kCoreNodes = bz.getKcoreNodes(k);
            UndirectedSparseGraph<Node,Edge> kCore = bz.getKcoreNetwork(k);
            System.out.println("Number of nodes in "+k+"-core: "+kCore.getVertexCount());
            System.out.println("Number of edges in "+k+"-core: "+kCore.getEdgeCount());
            System.out.println("Nodes in "+k+"-core:");
            System.out.println(kCoreNodes);
            System.out.println("Edges in "+k+"-core:");
            System.out.println(edgesToString(kCore));
            drawNetwork(kCore, nameOfGraph+"'s "+k+"-core subgraph");
        }
    }
}

