/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 *
 */
public class Schwefel_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 500;

    private double MIN = -500;

    //value = -837
    public static double function(double x1, double x2) {
        double fun;
        fun = -x1*Math.sin(Math.sqrt((Math.abs(x1))))+(-x2*Math.sin(Math.sqrt((Math.abs(x2)))));
        return fun;
    }
}
