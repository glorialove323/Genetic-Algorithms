package com.gyy.MultiPops_PGA.SGA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;

import com.gyy.MultiPops_PGA.*;

public class SGASolver extends Solvers {
    public int N; //population size

    private Population currentPopulation;

    private int fitnessCalls;

    private double avgFitness;
    
    private static int tourSize = 2;
    private static double pCrossover = 0.65;  
    private static double pSwap = 0.5;
    private static double pMutation = 0.0;
    
    private Selection selection;
    private Crossover crossover; 
    private Mutation mutation; 
    private IReplacement replacement;
    
    static StringBuffer  buf = new StringBuffer();

    public SGASolver(int currentN) {
        this.N = currentN;
        currentPopulation = new IndivPopulation(currentN);
        fitnessCalls = this.N;
        selection = new TourWithoutReplacement(N,tourSize);
        crossover = new UniformCrossover(pCrossover, pSwap);  
        mutation = new Mutation(pMutation);
        replacement = new FullReplacement();
    }

    public int getN() {
        return N;
    }

    public Population getCurrentPopulation() {
        return currentPopulation;
    }

    public int getFitnessCalls() {
        return fitnessCalls;
    }

    public double getAvgFitness() {
        return avgFitness;
    }

    public boolean nextGeneration(){
        currentGeneration++;
        SelectedSet selectedSet = selection.select(currentPopulation);                                                                       // SELECTION.
        Individual[] newIndividuals = crossover.cross(selectedSet);
        mutation.mutate(newIndividuals);
        replacement.replace(currentPopulation, newIndividuals); 
        fitnessCalls += newIndividuals.length; 
        ParEngine.fitCalls += newIndividuals.length;
        avgFitness = currentPopulation.calAvgFitness();
        return Stopper.criteria(currentGeneration, currentPopulation); 
    }
}
