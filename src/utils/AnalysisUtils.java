package utils;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import graph.Edge;
import graph.Node;
import models.SmallNetworks;

import javax.swing.*;
import java.awt.*;

public class AnalysisUtils {
    public static void drawGraph(UndirectedSparseGraph<Node, Edge> graph, String title) {
        FRLayout<Node, Edge> layout = new FRLayout<>(graph);
        layout.setSize(new Dimension(600, 600)); // set the size of the drawing area

        BasicVisualizationServer<Node, Edge> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(650, 650));

        // Customize node labels
        vv.getRenderContext().setVertexLabelTransformer(node -> String.valueOf(node.getId()));

        // Create a JFrame to show the graph
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[]args){
        drawGraph(SmallNetworks.createGraphWithFourPartitions(), "Graph");
    }
}
