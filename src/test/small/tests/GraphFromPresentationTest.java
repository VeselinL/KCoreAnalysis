package test.small.tests;

import models.small.SmallNetworks;

import static test.small.SmallNetworkTest.testSmallNetwork;

public class GraphFromPresentationTest {
    public static void main(String[] args){
        testSmallNetwork(SmallNetworks.createGraphFromPresentation(),"Graph from presentation");
    }
}
