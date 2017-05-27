/**
 * 
 */
package com.gyy.MultiPops_PGA;

import java.io.IOException;

import com.gyy.MultiPops_PGA.SGA.Population;



/**
 * @author Gloria
 * 
 */
public abstract class Solvers{
    
    private boolean dummy = false;
    protected int currentGeneration = 0;            // Current generation for this solver. This is updated by each nextGeneration() call.
    
    public void setDummy(boolean dum){dummy = dum;}
    public boolean getDummy(){return dummy;}
    
    public void incrementCurrentGeneration(){currentGeneration++;}
    public int getCurrentGeneration(){return currentGeneration;}
    
    public abstract int        getN();
    public abstract Population getCurrentPopulation();
    public abstract double     getAvgFitness();
    public abstract int        getFitnessCalls();
    
    public abstract boolean    nextGeneration();
}

