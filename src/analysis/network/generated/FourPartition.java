package analysis.network.generated;

import analysis.abs.Network;

import static models.small.SmallNetworks.createGraphWithFourPartitions;

public class FourPartition extends Network {
    public FourPartition(){
        super(createGraphWithFourPartitions(), "four_partitions");
    }
    public static void main(String[]args){
        FourPartition fourPartition = new FourPartition();
        fourPartition.interactiveCentralityAnalysis();
        fourPartition.kCoreAnalysis();
    }
}
