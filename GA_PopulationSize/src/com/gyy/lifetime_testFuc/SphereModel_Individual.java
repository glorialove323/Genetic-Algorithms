/**
 * Sphere Model
 * x1*x1+x2*x2
 * -100<=x<=100
 * f(0,0)=0
 */
package com.gyy.lifetime_testFuc;

/**
 * @author Gloria
 *
 */
public class SphereModel_Individual {   
    //GAVaPS
    //final int MaxLT = 7;
    //APGA
    //final int MaxLT = 15;
    
    private double MAX = 100.0;
    
    private double MIN = -100.0;
 
    //value = 0
    public static double function(double x1, double x2){
        double fun;
        fun = 1/(x1*x1+x2*x2);
        return fun;
    }
}
