/**
 * 
 */
package com.gyy.Shrink_SAMNP;

import java.util.Arrays;

/**
 * @author Gloria
 * 
 */
public class Population {
    protected int popSize;

    public Individual[] individuals;

    protected int worstPos, bestPos;

    protected double avgFit, worstFit, bestFit = -Double.MAX_VALUE;

    protected double[] fitness;
    
    Individual bestIndividual;// 当代适应度最好的个体

    Individual worstIndividual;// 当代适应度最差的个体

    Individual currentBest; // 截止当代之前适应度最好的个体
    
    @SuppressWarnings("unused")
    private int worstIndex;//bestIndividual对应的数组下标  
    
    /*
     * constructor of an empty population
     */
    public Population(int popSize) {
        this.popSize = popSize;
        individuals = new Individual[popSize];
        for (int i = 0; i < individuals.length; i++)
            individuals[i] = new Individual();
        fitness = new double[popSize];
        //initPopulation();
    }
    public Population(int popSize, Population pop) {
        this.popSize = popSize;
        individuals = new Individual[popSize];
        int i;
        for (i = 0; i < pop.getPopSize() && i < individuals.length; i++){
            individuals[i] = pop.getIndividualCopy(i);
        }
        for (; i < individuals.length; i++){
            individuals[i] = new Individual();
             individuals[i].generateIndividual();
        }
            
        fitness = new double[popSize];
        //initPopulation();
    }
    
    public void initPopulation(){
        for(int i =0;i<individuals.length;i++){
            individuals[i].generateIndividual();
            //individuals[i].calFitness();
        }
    }
    
    
    public Individual[] getBestIndividuals(int nSize)
    		throws NullPointerException{
    	if (nSize > popSize){
    		throw new NullPointerException("too big size");
		}
    	Individual[] indivs = new Individual[nSize];
    	if (nSize == popSize){
    		for (int i = 0; i < nSize; i++){
    			indivs[i] = individuals[i];
    		}
    	}
    	else{
        	sortIndividual();
        	for (int i = 0; i < nSize; i++){
        		indivs[i] = individuals[popSize - i - 1];
        	}
    	}
    	return indivs;
    }
    
    
    public void sortIndividual(){
    	Arrays.sort(individuals);
    }

    //获得当前种群中最好的个体
    public void findBestIndividual(){
        bestIndividual = worstIndividual = individuals[0]; 
        for(int i = 1; i <getPopSize();i++){  
            if(individuals[i].getFitness() > bestIndividual.getFitness()){  
                bestIndividual = individuals[i];  
            }  
            if(individuals[i].getFitness() < worstIndividual.getFitness()){  
                worstIndividual = individuals[i];  
                worstIndex = i;  
            }  
        }  
    }
    
    public Individual getBestIndividual(){
    	if (bestIndividual == null){
    		findBestIndividual();
    	}
        return bestIndividual;
    }
    
    //获得截止当前种群中最好的个体
    public void findCurrentBest(){
        if(SAMNPSolver.getGeneration() == 1 ){//初始种群  
            currentBest = (Individual)bestIndividual.clone();  
        }else{  
            if(bestIndividual.getFitness() > currentBest.getFitness())  
                currentBest = (Individual)bestIndividual.clone();  
        }
    }
    
    public Individual getCurrentBest(){
        return currentBest;
    }
    
    public int getPopSize() {
        return this.popSize;
    }
    public Individual[] getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public int getWorstPos() {
        return worstPos;
    }

    public int getBestPos() {
        return bestPos;
    }

    public double getAvgFit() {
        return avgFit;
    }

    public double getWorstFit() {
        return worstFit;
    }

    public double getBestFit() {
        return bestFit;
    }

    public double[] getFitness() {
        return fitness;
    }

    public double getFitness(int i) {
        return fitness[i];
    }

    public void setWorstPos(int wPos) {
        worstPos = wPos;
    }

    public void setBestPos(int bPos) {
        bestPos = bPos;
    }

    public void setWorstFit(double wFit) {
        worstFit = wFit;
    }

    public void setBestFit(double bFit) {
        bestFit = bFit;
    }
    
    
    public void setIndividual(int position, Individual indiv, double fit) {
        individuals[position] = indiv;
        fitness[position] = fit;
    }
    
    public void setIndividual(Individual indiv[]) {
        for (int i = 0; i < indiv.length && i < popSize; i++){
            individuals[i] = indiv[i];
        }
        
    }
   
    //深拷贝
    public Individual getIndividualCopy(int i){                   
        Individual indiv = new Individual();
        indiv.generateIndividual();
        for(int j = 0; j < 20; j++)
        {
            indiv.setAllele(j, individuals[i].getAllele(j));
        }
        indiv.calFitness();
        return indiv;
    }

    public void calFitnessValues() {
        worstFit = Double.MAX_VALUE;
        bestFit = -Double.MAX_VALUE;
        for (int i = 0; i < this.popSize; i++) {
                double newFit = individuals[i].calFitness();
                fitness[i] = newFit;
                if (newFit < worstFit) { 
                    worstPos = i; 
                    worstFit = newFit;
                }
                if (newFit > bestFit) { 
                    bestPos = i; 
                    bestFit = newFit;
                }
            }
        calAvgFitness();
    }

    public double calAvgFitness() {
        double sumFit = 0;
        double BestFit = -Double.MAX_VALUE;
        for (int i = 0; i < this.popSize; i++)
        {
            sumFit += fitness[i];
            if (fitness[i] > BestFit)
            {
                BestFit = fitness[i];
            }
        }
        avgFit = (double) sumFit / ((double) this.popSize);
        setBestFit(BestFit);
        return avgFit;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < this.popSize; i++) {
            for (int j = 0; j < 20; j++)
                str += individuals[i].getAllele(j);
            str += "\n";
        }
        return str;
    }
    
    public void dumpMyself(){
        System.out.println("size : "+popSize+", bestFitness is "+bestFit);
    }
    public int getFeval(){
    	int nFeval = 0;
    	for (int i = 0; i < popSize; i++){
    		if (individuals[i].isChanged()){
    			nFeval++;
    			individuals[i].setChanged(false);
    		}
    	}
    	return nFeval;
    }
}

