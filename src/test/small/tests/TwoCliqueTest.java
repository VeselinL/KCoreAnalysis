package test.small.tests;

import models.small.SmallNetworks;

import static test.small.SmallNetworkTest.testSmallNetwork;

public class TwoCliqueTest {
    public static void main(String[]args){
        testSmallNetwork(SmallNetworks.createGraphWithTwoCliques(), "Two clique graph");
    }
}
