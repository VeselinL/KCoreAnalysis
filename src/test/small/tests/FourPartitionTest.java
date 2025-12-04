package test.small.tests;

import models.small.SmallNetworks;

import static test.small.SmallNetworkTest.testSmallNetwork;

public class FourPartitionTest {
    public static void main(String[] args){
        testSmallNetwork(SmallNetworks.createGraphWithFourPartitions(),"Four partition graph");
    }
}
