package analysis.abs;

import component.BFSComponents;
import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.*;
import graph.*;
import algorithms.BatageljZaversnik;
import utils.CorrelationUtils;
import utils.CorrelationUtils.CorrelationType;
import writers.GraphFileWriter;
import static utils.centrality.CentralityUtils.*;
import static utils.GraphUtils.*;
import static utils.VisualizationUtils.plotRegression;
import static utils.DataConversionUtils.toDoubleArrays;

public abstract class Network {
    protected final UndirectedSparseGraph<Node,Edge> graph;
    protected BatageljZaversnik bz;
    protected HashMap<Node,Integer> shellIndices;
    protected final String name;

    private static final String FILE_SUFFIX = "_metrics.csv";
    private static final String RESULTS_DIR = "src/results/";
    private static final int CENTRALITY_THRESHOLD = 10_000;
    private static final int PLOT_THRESHOLD = 10_000;
    private static final int AVERAGE_PATH_THRESHOLD = 10_000;
    private static final int DIAMETER_THRESHOLD = 10_000;
    private static final Scanner IN = new Scanner(System.in);

    public Network(UndirectedSparseGraph<Node,Edge> graph, String networkName){
        this.graph = graph;
        this.name = networkName;
        bz = new BatageljZaversnik(graph);
        this.shellIndices = bz.getShellIndices();
    }

    public UndirectedSparseGraph<Node,Edge> getGraph(){
        return this.graph;
    }

    public String getName(){
        return this.name;
    }

    public void kCoreAnalysis() {
        // Iterate through cores and analyze each one
        for (int k = 0; k <= bz.maxShellIndex(); k++) {
            if(k == 0) System.out.println("\nFULL NETWORK: ");
            else System.out.println("\n" + k + "-CORE:");
            UndirectedSparseGraph<Node, Edge> kCore = bz.getKcoreNetwork(k);
            if (kCore != null && kCore.getVertexCount() > 0) {
                analyzeEachCore(kCore, k);
            } else {
                System.out.printf("  [%s] %d-core is empty%n.", this.name, k);
            }
        }
    }

    public void analyzeEachCore(UndirectedSparseGraph<Node, Edge> kCore, int k) {
        int V = kCore.getVertexCount();
        int E = kCore.getEdgeCount();
        double coreDensity = (graphDensity(kCore)) * 100.0;

        // Compute connected components
        Set<Set<Node>> coreConnectedComps = BFSComponents.findConnectedComps(kCore);
        int coreNumComponents = coreConnectedComps.size();

        // Find largest component
        Set<Node> largestCompNodes = coreConnectedComps.stream()
                .max(Comparator.comparingInt(Set::size))
                .orElse(Set.of());
        int coreLargestSize = largestCompNodes.size();

        double corePercNodesLargest = (V > 0) ? (coreLargestSize * 100.0 / V) : 0.0;

        // Build subgraph of largest component
        UndirectedSparseGraph<Node, Edge> largestCoreComp = new UndirectedSparseGraph<>();
        largestCompNodes.forEach(largestCoreComp::addVertex);

        for (Edge e : kCore.getEdges()) {
            var endpoints = kCore.getEndpoints(e);
            Node u = endpoints.getFirst();
            Node v = endpoints.getSecond();
            if (largestCompNodes.contains(u) && largestCompNodes.contains(v)) {
                // add the original edge, not a new one
                if (!largestCoreComp.containsEdge(e)) {
                    largestCoreComp.addEdge(e, u, v);
                }
            }
        }
        double corePercEdgesLargest = (E > 0) ? (largestCoreComp.getEdgeCount() * 100.0 / E) : 0.0;
        Map<Node, Double> clusteringCoeffs = Metrics.clusteringCoefficients(kCore);
        double sum = 0.0;
        for (double coeff : clusteringCoeffs.values()) {
            sum += coeff;
        }
        double avgClustering = sum / clusteringCoeffs.size();
        // Print results
        System.out.printf("  [%s] Number of nodes in %d-core: %d%n", name, k, V);
        System.out.printf("  [%s] Number of edges in %d-core: %d%n", name, k, E);
        System.out.printf("  [%s] Average degree of %d-core %.4f%n", name, k, avgDegree(kCore));
        System.out.printf("  [%s] Density of %d-core: %.3f%%%n", name, k, coreDensity);
        if(kCore.getVertexCount() < AVERAGE_PATH_THRESHOLD){
            System.out.printf("  [%s] Average path length in %d-core is: %.3f%n", name, k, averagePath(kCore));
        }else{
            System.out.println("Average path skipped (graph too large)");
        }
        System.out.printf("  [%s] Number of connected components in %d-core: %d%n", name, k, coreNumComponents);
        System.out.printf("  [%s] Average clustering coefficient of %d-core: %.4f%%%n", name, k, avgClustering*100.0);
        System.out.printf("  [%s] Percentage of nodes in largest component %d-core: %.2f%%%n", name, k, corePercNodesLargest);
        System.out.printf("  [%s] Percentage of edges in largest component %d-core: %.2f%%%n", name, k, corePercEdgesLargest);
        if (largestCoreComp.getVertexCount() < DIAMETER_THRESHOLD) {
            System.out.printf("  [%s] Diameter of largest component %d-core: %d%n",name, k, computeDiameter(largestCoreComp));
        } else {
            System.out.println("  Diameter: skipped (too large)");
        }
    }

    public void interactiveCentralityAnalysis() {
        System.out.printf("Network '%s' loaded: %,d nodes, %,d edges.%n",
                name, graph.getVertexCount(), graph.getEdgeCount());
        System.out.print("Generate shell index vs degree plot? (y/n)?");
        if(IN.nextLine().trim().equals("y")){
            plotShellIndexAndDegree();
        }

        if (graph.getVertexCount() > CENTRALITY_THRESHOLD) {
            System.out.printf("Graph has %,d nodes: full centralities may take a while. ",graph.getVertexCount());
        }
        System.out.print("Compute full centralities? (y/n): ");
        String resp = IN.nextLine().trim();
        boolean computeFullCentralities = resp.equalsIgnoreCase("y");

        System.out.print("Write metrics to file when done? (y/n): ");
        resp = IN.nextLine().trim();
        boolean writeFiles = resp.equalsIgnoreCase("y");

        boolean doPlots = false;
        System.out.print("Generate detailed plots (shell vs centralities)? (y/n): ");
        if (IN.nextLine().trim().equals("y") && graph.getVertexCount() < PLOT_THRESHOLD) {
            doPlots = true;
        } else if (graph.getVertexCount() >= PLOT_THRESHOLD) {
            System.out.printf("Skipping detailed plots (too many nodes: %,d).%n", graph.getVertexCount());
        }
        System.out.println();
        runCentralityAnalysis(computeFullCentralities, writeFiles, doPlots);
        IN.close();
    }

    protected void runCentralityAnalysis(boolean computeFullCentralities, boolean writeFiles, boolean doPlots) {
        System.out.printf("[%s] Running analysis: computeFullCentralities=%b, writeFiles=%b, doPlots=%b%n",
                name, computeFullCentralities, writeFiles, doPlots);

        HashMap<Node, Double> cc;
        HashMap<Node, Double> bc;
        HashMap<Node, Double> ec;

        if (computeFullCentralities) {
            System.out.printf("[%s] Computing closeness centrality...%n", name);
            cc = computeClosenessCentrality(graph);

            System.out.printf("[%s] Computing betweenness centrality...%n", name);
            bc = computeBetweennessCentrality(graph);

            System.out.printf("[%s] Computing eigenvector centrality...%n", name);
            ec = computeEigenvectorCentrality(graph);
        } else {
            int sampleSize = (int) Math.sqrt(graph.getVertexCount());
            int iterations = 100;
            System.out.printf("[%s] Approximating centrality scores...%n", name);
            System.out.printf("[%s] Approximating closeness centrality (sampleSize = sqrt(V) = %d)...%n", name, sampleSize);
            cc = utils.centrality.ApproximationUtils.approximateClosenessCentrality(graph, sampleSize);

            System.out.printf("[%s] Approximating betweenness centrality (sampleSize = sqrt(V) = %d)...%n", name, sampleSize);
            bc = utils.centrality.ApproximationUtils.approximateBetweennessCentrality(graph, sampleSize);

            System.out.printf("[%s] Approximating eigenvector centrality (iterations=%d)...%n", name, iterations);
            ec = utils.centrality.ApproximationUtils.approximateEigenvectorCentrality(graph, iterations);
        }

        if (writeFiles) {
            String out = RESULTS_DIR + name + FILE_SUFFIX;
            System.out.printf("[%s] Writing metrics to %s ...%n", name, out);
            GraphFileWriter.writeUndirectedSparseGraphMetrics(graph, shellIndices, cc, bc, ec, out, computeFullCentralities);
            System.out.printf("[%s] Done writing %s%n%n", name, out);
        }
        if(doPlots) {
            plotAndReport(shellIndices, cc, "Closeness centrality");
            plotAndReport(shellIndices, bc, "Betweenness centrality");
            if(ec != null) plotAndReport(shellIndices, ec, "Eigenvector centrality");
        }
    }

    public void runCentralityAnalysisBatchMode(boolean fullCentrality, boolean writeFiles) {
        System.out.printf("[%s] Running batch analysis: computeFullCentralities=true, writeFiles=true, doPlots=false%n", name);
        runCentralityAnalysis(fullCentrality,writeFiles, false);
    }

    public void plotShellIndexAndDegree(){
        HashMap<Node, Integer> nodeDegrees = nodeDegrees(graph);
        plotAndReport(shellIndices, nodeDegrees, "Degree");
    }

    protected void plotAndReport(HashMap<Node, ? extends Number> xData, HashMap<Node, ? extends Number> yData, String yLabel) {
        double[][] arrays = toDoubleArrays(xData, yData);

        double pearsonCoefficient = CorrelationUtils.computeCorrelation(arrays[0], arrays[1], CorrelationType.PEARSON);
        double spearmanCoefficient = CorrelationUtils.computeCorrelation(arrays[0], arrays[1], CorrelationType.SPEARMAN);

        System.out.printf("Pearson correlation for %s and %s: %.3f.%n", "Shell index", yLabel, pearsonCoefficient);
        System.out.printf("Spearman correlation for %s and %s: %.3f.%n", "Shell index", yLabel, spearmanCoefficient);

        if(arrays[0].length < PLOT_THRESHOLD){
            plotRegression(arrays[0], arrays[1], "Shell index", yLabel, name);
        } else {
            System.out.println("Skipping plot for " + yLabel + " (too many points: " + arrays[0].length + ")");
        }
    }
}
