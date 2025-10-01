package analysis.network.file;
import analysis.abs.Network;
import static readers.GraphFileReader.readUndirectedSparseGraph;
import static models.small.SmallNetworks.*;

public class Small extends Network {
    private final static String sourceFile = "src/data/zachary_karate_club.txt";
    public Small(){
        super(createGraphWithFourPartitions(), "small");
    }
    public static void main(String[]args){
        Small zkc = new Small();
        zkc.interactiveCentralityAnalysis();
        zkc.kCoreAnalysis();
    }
}
