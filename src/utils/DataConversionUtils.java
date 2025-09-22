package utils;

import graph.Node;

import java.util.HashMap;

public class DataConversionUtils {
    public static double[][] toDoubleArrays(
            HashMap<Node, ? extends Number> xData,
            HashMap<Node, ? extends Number> yData) {
        int size = Math.min(xData.size(), yData.size());
        double[] x = new double[size];
        double[] y = new double[size];
        int idx = 0;
        for (Node n : xData.keySet()) {
            if (yData.containsKey(n)) {
                x[idx] = xData.get(n).doubleValue();
                y[idx] = yData.get(n).doubleValue();
                idx++;
            }
        }
        if (idx < size) {
            double[] xTrimmed = new double[idx];
            double[] yTrimmed = new double[idx];
            System.arraycopy(x, 0, xTrimmed, 0, idx);
            System.arraycopy(y, 0, yTrimmed, 0, idx);
            return new double[][] { xTrimmed, yTrimmed };
        }

        return new double[][] { x, y };
    }


}