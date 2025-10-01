package analysis.network.file;

import analysis.abs.Network;
import static readers.GraphFileReader.readUndirectedSparseGraph;
/**
 * This dataset consists of 'circles' (or 'friends lists') from Facebook.
 * Facebook data was collected from survey participants using this Facebook app.
 * The dataset includes node features (profiles), circles, and ego networks.
 * Number nodes: 4039; number of edges: 88234.
 * Downloaded from <a href="https://snap.stanford.edu/data/ego-Facebook.html">...</a>.
 */
public class FacebookEgoSample extends Network{
    private final static String sourceFile = "src/data/facebook_ego.txt";
    public FacebookEgoSample(){
        super(readUndirectedSparseGraph(sourceFile), "facebook_ego");
    }
    public static void main(String[]args){
        FacebookEgoSample fbEgo = new FacebookEgoSample();
        fbEgo.interactiveCentralityAnalysis();
        fbEgo.kCoreAnalysis();
    }
}
