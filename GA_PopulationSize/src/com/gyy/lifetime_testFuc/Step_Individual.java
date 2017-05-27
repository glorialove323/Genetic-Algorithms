/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 *
 */
public class Step_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 100;

    private double MIN = -100;

    //value = 0
    public static double function(double x1, double x2) {
        double fun;
        fun =( Math.pow(Math.floor(x1+0.5), 2)+Math.pow(Math.floor(x2+0.5), 2));
        return fun;
    }
}
