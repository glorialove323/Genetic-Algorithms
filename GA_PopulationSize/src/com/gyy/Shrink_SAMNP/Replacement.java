package com.gyy.Shrink_SAMNP;



abstract class IReplacement{
	abstract void replace(Population population, Individual[] newIndividuals);
}

class FullReplacement extends IReplacement{										
	public void replace(Population population, Individual[] newIndividuals){
		double newBestFit = Double.MIN_VALUE;
		for(int i = 0; i < newIndividuals.length; i++){
			double newFit = newIndividuals[i].calFitness();
			population.setIndividual(i, newIndividuals[i], newFit);
			if(newFit > newBestFit){											
				population.setBestPos(i);										
				newBestFit = newFit;
			}
		}
		population.setBestFit(newBestFit);
	}
}






