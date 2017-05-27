/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 *
 */
public class Branin_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX1 = 10;

    private double MIN1 = -5;
    
    private double MAX2 = 15;
    private double MIN2 = 0;

    public static double function(double x1, double x2) {
        double fun;
        int a =1;
        double b = 5.1/(4*Math.PI*Math.PI);
        double c = 5/Math.PI;
        int d =6;
        int e = 10;
        double f = 1/(8*Math.PI);
        fun = a*(x2-b*x1*x1+c*x1-d)*(x2-b*x1*x1+c*x1-d)+e*(1-f)*Math.cos(x1)+e;
        return fun;
    }
}
