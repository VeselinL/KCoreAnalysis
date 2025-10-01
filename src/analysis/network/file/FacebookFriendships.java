package analysis.network.file;

import analysis.abs.Network;
import static readers.GraphFileReader.readUndirectedSparseGraph;

/**
 * A small network representing friendship among users on Facebook. The origin of these data is unclear
 * Downloaded from <a href="https://wwwlovre.appspot.com/support.jsp">...</a>.
 */
public class FacebookFriendships extends Network{
    private final static String sourceFile= "src/data/facebook_friends.txt";
    public FacebookFriendships(){
        super(readUndirectedSparseGraph(sourceFile),"facebook_friendships");
    }
    public static void main(String[]args){
        FacebookFriendships ff = new FacebookFriendships();
        ff.interactiveCentralityAnalysis();
        ff.kCoreAnalysis();
    }
}
