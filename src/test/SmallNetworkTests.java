package test;
import algorithms.BatageljZaversnik;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import graph.Node;
import graph.Edge;
import models.SmallNetworks;
import java.util.Set;
import java.util.stream.Collectors;
import static utils.AnalysisUtils.drawGraph;

public class SmallNetworkTests {
    public static void main(String[]args){
        testSmallNetwork(SmallNetworks.createGraphFromPresentation(),"Graph from presentation");
        testSmallNetwork(SmallNetworks.createGraphWithTwoCliques(), "Two clique graph");
        testSmallNetwork(SmallNetworks.createGraphWithFourPartitions(),"Four partition graph");
    }
    public static void testSmallNetwork(UndirectedSparseGraph<Node,Edge> graph, String nameOfGraph){
        System.out.println("\n"+nameOfGraph.toUpperCase()+":");
        BatageljZaversnik bz = new BatageljZaversnik(graph);
        drawGraph(graph, nameOfGraph);
        for(int k = 0;k <= bz.maxShellIndex();k++){
            System.out.println("\n"+k+"-CORE:");
            Set<Node> kCoreNodes = bz.getKcoreNodes(k);
            UndirectedSparseGraph<Node,Edge> kCore = bz.getKcoreNetwork(k);
            System.out.println("Number of nodes in "+k+"-core: "+kCore.getVertexCount());
            System.out.println("Number of edges in "+k+"-core: "+kCore.getEdgeCount());
            System.out.println("Nodes in "+k+"-core:");
            System.out.println(kCoreNodes);
            System.out.println("Edges in "+k+"-core:");
            printEdges(kCore);
            drawGraph(kCore, nameOfGraph+"'s "+k+"-core subgraph");
        }
    }
    public static void printEdges(UndirectedSparseGraph<Node,Edge> graph){
        String edgesString = graph.getEdges().stream()
                .map(edge -> {
                    Pair<Node> link = graph.getEndpoints(edge);
                    if(link != null) {
                        return link.getFirst() + "-" + link.getSecond();
                    } else {
                        return "";
                    }
                })
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(", "));
        System.out.println(edgesString);
    }
}

