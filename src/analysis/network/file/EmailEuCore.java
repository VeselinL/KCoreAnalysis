package analysis.network.file;

import static readers.GraphFileReader.readUndirectedSparseGraph;

import analysis.abs.Network;

/**
 * The network was generated using email data from a large European research institution.
 * We have anonymized information about all incoming and outgoing email between members of
 * the research institution. There is an edge (u, v) in the network if person u sent person v at least one email.
 * The e-mails only represent communication between institution members (the core), and the dataset
 * does not contain incoming messages from or outgoing messages to the rest of the world.
 * Number of nodes: 1005; number of edges: 25571.
 * Downloaded from <a href="https://snap.stanford.edu/data/email-Eu-core.html">...</a>
 */
public class EmailEuCore extends Network{
    private final static String sourceFile = "src/data/email-Eu-core.txt";
    public EmailEuCore(){
        super(readUndirectedSparseGraph(sourceFile),"email_eu_core");
    }
    public static void main(String[]args){
        EmailEuCore euc = new EmailEuCore();
        euc.interactiveCentralityAnalysis();
        euc.kCoreAnalysis();
    }
}
