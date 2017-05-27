/**
 * 
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 *
 */
public class Griewank_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 600;

    private double MIN = -600;

    //value = 0
    public static double function(double x1, double x2) {
        double fun;
        double fun1 = x1*x1/4000+x2*x2/4000;
        double fun2 = Math.cos(x1/Math.sqrt(1))*Math.cos(x2/Math.sqrt(2));
        fun = fun1-fun2+1;
        return fun;
    }
}
