/**
 * 
 */
package com.gyy.Shrink_dynNP;

/**
 * @author Gloria
 *
 */
public class Reduction {

    public Population reduction(Population oldPop, Population newPop){
        int oldPopSize = oldPop.getPopSize();
        int newPopSize = oldPopSize/2;
        for(int i=0;i<newPopSize;i++){
            Individual newIndiv = new Individual();
            Individual indiv1 = oldPop.getIndividual(i);
            Individual indiv2 = oldPop.getIndividual((oldPopSize/2)+i);
            if(indiv1.getFitness()>=indiv2.getFitness()){
                newIndiv = indiv1.clone();
            }
            else{
                newIndiv = indiv2.clone();
            }
            newPop.setIndividual(i, newIndiv, newIndiv.getFitness());           
        }
        newPop.calFitnessValues();
        return newPop;
    }
}
