/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 *
 */
public class Easom_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 100;

    private double MIN = -100;

    public static double function(double x1, double x2) {
        double fun;
        fun = -Math.cos(x1)*Math.cos(x2)*Math.exp(-(x1-Math.PI)*(x1-Math.PI)+(x2-Math.PI)*(x2-Math.PI));
        return fun;
        //return 1/fun
        //func 1/fitness
    }
}
