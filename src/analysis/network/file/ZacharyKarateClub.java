package analysis.network.file;
import analysis.abs.Network;
import static readers.GraphFileReader.readUndirectedSparseGraph;

public class ZacharyKarateClub extends Network {
    private final static String sourceFile = "src/data/zachary_karate_club.txt";
    public ZacharyKarateClub(){
        super(readUndirectedSparseGraph(sourceFile), "zachary_karate_club");
    }
    public static void main(String[]args){
        ZacharyKarateClub zkc = new ZacharyKarateClub();
        zkc.interactiveCentralityAnalysis();
        zkc.kCoreAnalysis();
    }
}
