package test.small.tests;

import analysis.network.file.ZacharyKarateClub;
import static test.small.SmallNetworkTest.testSmallNetwork;

public class ZacharyKarateClubTest {
    public static void main(String[] args){
        testSmallNetwork(new ZacharyKarateClub().getGraph(), "Zachary Karate Club");
    }
}
