/**
 * selection:
 * use tournament selection without replacement
 * 随机地从种群中挑选出一定数目（Tour）的个体，然后将最好的个体作为父代。
 * Tour取值范围：[2，Nind]
 */
package com.gyy.MultiPops_GPSGA;

import java.util.Random;

/**
 * @author Gloria
 * 
 */
abstract class Selection{
    public int NS; 

        
    public Selection(){}                                // Default empty constructor.
    public int getNS(){return NS;}
    abstract SelectedSet select(Population population);
}

class TourWithoutReplacement extends Selection{
    int tourSize;                                       // Default binary tournament
    public TourWithoutReplacement(int selectionSetPopSize,int tourSize){    
        this.NS =selectionSetPopSize;
        this.tourSize = tourSize;
    }
    
    public SelectedSet select(Population population){
        int N = population.getPopSize();
        SelectedSet selectedSet = new SelectedSet(NS);  
        int  k = N/tourSize;                            
        int ks = NS/k;                                  
        int rs = NS%k;                                 
        int ls = ks*k;                                 
        int maxPos = 0;                                 
        for(int i = 0; i < ks; i++){
            int pos = i*k;                             
            int[] numbers = shuffle(N);
            for(int j = 0; j < k*tourSize; j += tourSize){
                maxPos = tourSelect(population, selectedSet, numbers, j);
                selectedSet.setIndividual(pos++, population.individuals[maxPos], population.getFitness(maxPos));
            }
        }
        int[] numbers = shuffle(N);
        for(int j = 0; j < rs*tourSize; j += tourSize){
            maxPos = tourSelect(population, selectedSet, numbers, j);
            selectedSet.setIndividual(ls++, population.individuals[maxPos], population.getFitness(maxPos));
        }
        return selectedSet;
    }
    
    private int tourSelect(Population population, SelectedSet selectedSet, int[] numbers, int j){
        int maxPos = numbers[j];
        double maxFit = population.getFitness(maxPos);
        for(int i = j+1; i < j+tourSize; i++){
            int currentPos = numbers[i];
            double currentFit = population.getFitness(currentPos);
            if(currentFit > maxFit){
                maxPos = currentPos;
                maxFit = currentFit;
            }
        }
        return maxPos;
    }
    
    private int[] shuffle(int n){
        int[] numbers = new int[n];
        for(int i = 0; i < n; i++)
            numbers[i] = i;
        for(int i = 1; i < n; i++){
            int r = i + random.nextInt(n - i);
            int temp = numbers[i-1];
            numbers[i-1] = numbers[r];
            numbers[r] = temp;
        }
        return numbers;
    }
    
    public static Random random = new Random();

}
