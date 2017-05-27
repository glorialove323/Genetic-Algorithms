/**
 * Rosenbrock函数: 
 *f(x1,x2) = 100*(x1**2 - x2)**2 + (1 - x1)**2 
 *在当x在[-2.048 , 2.048]内时， 
 *函数有两个极大点: 
 *f(2.048 , -2.048) = 3897.7342 
 *f(-2.048,-2.048) = 3905.926227 
 *其中后者为全局最大点。
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 * 
 */
public class Rosenbrock_Individual {
    final int MinLT = 1;

    // GAVaPS
    //final int MaxLT = 7;

    // APGA
    //final int MaxLT = 15;

    private double MAX = 2.048;

    private double MIN = -2.048;

    //value = 3905
    public static double function(double x1, double x2) {
        double fun;
        fun = (100 * (x1 * x1 - x2) * (x1 * x1 - x2) + (1 - x1) * (1 - x1));
        return fun;
    }
}
