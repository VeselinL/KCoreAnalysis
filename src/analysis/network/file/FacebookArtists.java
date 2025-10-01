package analysis.network.file;

import analysis.abs.Network;
import static readers.GraphFileReader.readUndirectedSparseGraph;

/**
 * This network represents blue verified Facebook page networks in artist category.
 * Nodes represent the pages and edges are mutual  The csv files contain the edges --
 * nodes are indexed from 0. Number of nodes: 50,515; number of edges: 819,306.
 * Downloaded from <a href="https://snap.stanford.edu/data/gemsec-Facebook.html">...</a>
 */
public class FacebookArtists extends Network {
    private final static String sourceFile = "src/data/facebook_artists.csv";
    public FacebookArtists(){
        super(readUndirectedSparseGraph(sourceFile), "facebook_artists");
    }
    public static void main(String[]args){
        FacebookArtists fbArtists = new FacebookArtists();
        fbArtists.interactiveCentralityAnalysis();
        fbArtists.kCoreAnalysis();
    }
}
