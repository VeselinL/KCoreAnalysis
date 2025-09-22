package utils;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

public class CorrelationUtils {
    public enum CorrelationType{
        PEARSON,
        SPEARMAN
    }
    public static double computeCorrelation(double[] x, double[] y, CorrelationType type) {
        return switch (type) {
            case PEARSON -> new PearsonsCorrelation().correlation(x, y);
            case SPEARMAN -> new SpearmansCorrelation().correlation(x, y);
        };
    }
}
