/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 * 
 */
public class SixHumpCamelBack_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 5;

    private double MIN = -5;

    //value = -1.03
    public static double function(double x1, double x2) {
        double fun;
        fun = (4 * Math.pow(x1, 2) - 2.1 * Math.pow(x1, 4) + Math.pow(x1, 6) / 3 + x1 * x2 - 4 * Math.pow(x2, 2) + 4
                * Math.pow(x2, 4));
        return fun;
    }
}
