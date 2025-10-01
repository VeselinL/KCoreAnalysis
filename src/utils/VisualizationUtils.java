package utils;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import graph.Edge;
import graph.Node;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.AnnotationText;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class VisualizationUtils {
    public static void drawNetwork(UndirectedSparseGraph<Node, Edge> graph, String title) {
        FRLayout<Node, Edge> layout = new FRLayout<>(graph);
        layout.setSize(new Dimension(600, 600)); // set the size of the drawing area

        BasicVisualizationServer<Node, Edge> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(650, 650));

        vv.getRenderContext().setVertexLabelTransformer(node -> String.valueOf(node.getId()));
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
    public static void plotRegression(double[] x, double[] y, String xLabel, String yLabel, String name) {
        double pearson = new PearsonsCorrelation().correlation(x, y);
        double spearman = new SpearmansCorrelation().correlation(x, y);

        SimpleRegression regression = new SimpleRegression(true);
        for (int i = 0; i < x.length; i++) {
            regression.addData(x[i], y[i]);
        }
        double slope = regression.getSlope();
        double intercept = regression.getIntercept();

        XYChart chart = new XYChartBuilder()
                .width(600)
                .height(500)
                .title(name+": "+xLabel + " vs " + yLabel)
                .xAxisTitle(xLabel)
                .yAxisTitle(yLabel)
                .build();

        chart.getStyler().setMarkerSize(7);

        chart.addSeries("Data", x, y)
                .setMarker(SeriesMarkers.CIRCLE)
                .setMarkerColor(Color.BLUE)
                .setLineStyle(SeriesLines.NONE);

        double[] x_line = { Arrays.stream(x).min().orElse(0), Arrays.stream(x).max().orElse(0) };
        double[] y_line = {
                intercept + slope * x_line[0],
                intercept + slope * x_line[1]
        };

        chart.addSeries("Regression Line", x_line, y_line)
                .setMarker(SeriesMarkers.NONE)
                .setLineColor(Color.RED)
                .setLineStyle(SeriesLines.SOLID);

        chart.addAnnotation(new AnnotationText(
                String.format("Pearson=%.3f, Spearman=%.3f", pearson, spearman),
                500,
                15,
                true
        ));
        new SwingWrapper<>(chart).displayChart();
    }
}
