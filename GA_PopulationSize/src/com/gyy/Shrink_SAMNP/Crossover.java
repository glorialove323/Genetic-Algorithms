/**
 * Crossover:
 * use uniform crossover
 */
package com.gyy.Shrink_SAMNP;

import java.util.Random;


/**
 * @author Gloria
 *
 */
abstract class Crossover{
    protected double pCrossover;
    abstract public void cross(Population selectedSet);
}

class UniformCrossover extends Crossover{
    private double pSwap;
    
    public UniformCrossover(double pCrossover, double pSwap){
        this.pCrossover = pCrossover;
        this.pSwap = pSwap;
    }
    
    public void cross(Population selectedSet){
        int NS = selectedSet.getPopSize();
        for(int i = 0; i < NS - 1; i += 2){
        	boolean bChanged = false;
            Individual indiv1 = selectedSet.getIndividualCopy(i),
                       indiv2 = selectedSet.getIndividualCopy(i+1);
            if(random.nextDouble() < pCrossover)
                for(int j = 0; j < 20; j++)  
                    if(random.nextDouble() < pSwap){
                        char allele = indiv1.getAllele(j);
                        indiv1.setAllele(j, indiv2.getAllele(j));
                        indiv2.setAllele(j, allele);
                    }
            selectedSet.setIndividual(i, indiv1, indiv1.calFitness());
            selectedSet.setIndividual(i+1, indiv2, indiv2.calFitness());
        }
    }
    
    public static Random random = new Random();
}

