package com.gyy.Shrink_dynNP;

import java.util.Random;

class Mutation {
    private double pMutation; // Probability of mutation;

    public Mutation(double pMutation) {
        this.pMutation = pMutation;
    }

    public double getPMutation() {
        return pMutation;
    }

    public void mutate(Individual[] newIndividuals) {
        if (pMutation > 0) // Perform mutation only if pMutation > 0.
            for (int i = 0; i < newIndividuals.length; i++)
                for (int j = 0; j < 20; j++)
                    if (random.nextDouble() < pMutation) {
                        char allele = newIndividuals[i].getAllele(j);
                        if (allele == '0')
                            newIndividuals[i].setAllele(j, '1');
                        else
                            newIndividuals[i].setAllele(j, '0');
                    }
    }

    public static Random random = new Random();
}