package analysis.network.file;
import analysis.abs.Network;
import static readers.GraphFileReader.readUndirectedSparseGraph;
/**
 This is protein-protein interaction network that contains physical interactions
 between proteins that are experimentally documented in humans, such as metabolic
 enzyme-coupled interactions and signaling interactions. Nodes represent human
 proteins and edges represent physical interaction between proteins in a human cell.
 Number of nodes: 21557; number of edges: 342353.
 Downloaded from <a href="https://snap.stanford.edu/biodata/datasets/10000/10000-PP-Pathways.html">...</a>
 */
public class ProteinPathways extends Network {
    private final static String sourceFile = "src/data/PP-Pathways_ppi.csv";
    public ProteinPathways(){
        super(readUndirectedSparseGraph(sourceFile), "protein_pathways");
    }
    public static void main(String[]args){
        ProteinPathways proteinPathways = new ProteinPathways();
        proteinPathways.interactiveCentralityAnalysis();
        proteinPathways.kCoreAnalysis();

    }
}
