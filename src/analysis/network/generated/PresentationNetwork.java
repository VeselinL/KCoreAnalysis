package analysis.network.generated;

import analysis.abs.Network;
import analysis.network.file.FacebookArtists;

import static models.small.SmallNetworks.createGraphFromPresentation;

public class PresentationNetwork extends Network {
    public PresentationNetwork(){
        super(createGraphFromPresentation(), "presentation_network");
    }
    public static void main(String[]args){
        PresentationNetwork presentationNetwork = new PresentationNetwork();
        presentationNetwork.kCoreAnalysis();
    }
}
