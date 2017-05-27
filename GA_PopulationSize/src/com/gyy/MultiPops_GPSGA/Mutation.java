package com.gyy.MultiPops_GPSGA;

import java.util.Random;

import com.gyy.MultiPops_PGA.ParEngine;

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
                for (int j = 0; j < ParEngine.chromLen; j++)
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