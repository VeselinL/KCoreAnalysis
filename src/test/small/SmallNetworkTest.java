package test.small;
import algorithms.BatageljZaversnik;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Node;
import graph.Edge;

import java.util.Set;
import static utils.GraphUtils.edgesToString;
import static utils.GraphUtils.nodeDegrees;
import static utils.VisualizationUtils.drawNetwork;

public class SmallNetworkTest {
    public static void testSmallNetwork(UndirectedSparseGraph<Node,Edge> graph, String nameOfGraph){
        System.out.println("\n"+nameOfGraph.toUpperCase()+":");
        BatageljZaversnik bz = new BatageljZaversnik(graph);
        System.out.println(nodeDegrees(graph));
        drawNetwork(graph, nameOfGraph);
        for(int k = 0;k <= bz.maxShellIndex();k++){
            if(k == 0) System.out.println("\n"+"FULL NETWORK: ");
            else System.out.println("\n"+k+"-CORE:");
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

