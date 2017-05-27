/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 * 
 */
public class Rastrigin_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 5.12;

    private double MIN = -5.12;

    //value = 0
    public static double function(double x1, double x2) {
        double fun;
        fun = (Math.pow(x1, 2) - 10 * Math.cos(2 * Math.PI * x1) + 10 + Math.pow(x2, 2) - 10
                * Math.cos(2 * Math.PI * x2) + 10);
        return fun;
    }
}
