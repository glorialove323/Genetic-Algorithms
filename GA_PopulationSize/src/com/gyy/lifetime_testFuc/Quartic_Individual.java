/**
 * 
 */
package com.gyy.lifetime_testFuc;

import java.util.Random;

/**
 * @author Gloria
 * 
 */
public class Quartic_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 1.28;

    private double MIN = -1.28;

    //value = 0
    public static double function(double x1, double x2) {
        double fun;
        fun = (Math.pow(x1, 4) + Math.random() + 2 * Math.pow(x2, 4) + Math.random());
        return fun;
    }
}
