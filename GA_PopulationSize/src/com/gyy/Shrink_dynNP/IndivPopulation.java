/**
 * 
 */
package com.gyy.Shrink_dynNP;


/**
 * @author Gloria
 *
 */
public class IndivPopulation extends Population{

    public IndivPopulation(int popSize){
        super(popSize);
        for(int i = 0;i<popSize;i++){
            individuals[i].generateIndividual();
        }
        this.calFitnessValues();
    }
}
