/**
 * Crossover:
 * use uniform crossover
 */
package com.gyy.Shrink_dynNP;

import java.util.Random;

/**
 * @author Gloria
 *
 */
abstract class Crossover {
	protected double pCrossover;

	abstract public Individual[] cross(Population selectedSet);
}

class UniformCrossover extends Crossover {
	private double pSwap;

	public UniformCrossover(double pCrossover, double pSwap) {
		this.pCrossover = pCrossover;
		this.pSwap = pSwap;
	}

	public Individual[] cross(Population selectedSet) {
		int NS = selectedSet.getPopSize();
		Individual[] newIndividuals = new Individual[NS];
		int i = 0, j = 0;
		for (i = 0, j = 0; i < NS; i += 2) {
			j = i + 1;
			if (j < NS) {
				Individual indiv1 = selectedSet.getIndividualCopy(i), indiv2 = selectedSet.getIndividualCopy(i + 1);
				if (random.nextDouble() < pCrossover)
					for (int k = 0; k < 20; k++)
						if (random.nextDouble() < pSwap) {
							char allele = indiv1.getAllele(k);
							indiv1.setAllele(k, indiv2.getAllele(k));
							indiv2.setAllele(k, allele);
						}
				newIndividuals[i] = indiv1;
				newIndividuals[j] = indiv2;
			} else {
				newIndividuals[i] = selectedSet.getIndividualCopy(i);
			}

		}
		return newIndividuals;
	}

	public static Random random = new Random();
}
