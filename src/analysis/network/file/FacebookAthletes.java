package analysis.network.file;

import analysis.abs.Network;

import java.util.Collections;

import static readers.GraphFileReader.readUndirectedSparseGraph;
import static utils.GraphUtils.*;

/**
 * This network represents blue verified Facebook page networks in athletes category.
 * Nodes represent the pages and edges are mutual  The csv files contain the edges --
 * nodes are indexed from 0. Number of nodes: 13,866; number of edges: 86,858.
 * Downloaded from <a href="https://snap.stanford.edu/data/gemsec-Facebook.html">...</a>
 */
public class FacebookAthletes extends Network {
    private final static String sourceFile = "src/data/facebook_athletes.csv";
    public FacebookAthletes(){
        super(readUndirectedSparseGraph(sourceFile), "facebook_athletes");
    }
    public static void main(String[]args){
        FacebookAthletes fbAthletes = new FacebookAthletes();
        //fbAthletes.interactiveCentralityAnalysis();
        //fbAthletes.kCoreAnalysis();
        System.out.println(avgDegree(fbAthletes.graph));
        System.out.println(Collections.max(fbAthletes.shellIndices.values()));
        System.out.println(Collections.min(fbAthletes.shellIndices.values()));

    }
}
