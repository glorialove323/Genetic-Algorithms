package com.gyy.Shrink_dynNP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class dynNPSolver {

    static List<Population> m_List = null;

    public static int m_nInitalSize;

    public static int maxnfeval = 200000;

    public static int pmax = 4;

    static int m_nFitnessCalls = 0;
    static {
        m_nInitalSize = 200;
    }

    static StringBuffer buf = new StringBuffer();

    static List<Double> listBestFit = new ArrayList<Double>();

    public static void evolve(Population pop) {
        int oldPopSize = pop.getPopSize();

        int nMaxfitnessCalls = maxnfeval / pmax;
        int nFitnessCalls = 0;
        double fBestFitness = -(Double.MAX_VALUE);
        
        while (nFitnessCalls <= nMaxfitnessCalls) {
            Crossover cros = new UniformCrossover(0.65, 0.5);
            Mutation muta = new Mutation(0.015);
            Individual[] inv = cros.cross(pop);
            for (int j = 0; j < pop.getPopSize(); j++) {
                pop.setIndividual(j, inv[j], 0);
            }
            muta.mutate(pop.getIndividuals());
            nFitnessCalls += pop.getPopSize();
            m_nFitnessCalls += pop.getPopSize();
            pop.setFinessCalls(nFitnessCalls);
            buf.append(m_nFitnessCalls + "\t" + pop.getPopSize() + "\t");

            pop.calFitnessValues();
            if (pop.getBestFit() > fBestFitness) {
                fBestFitness = pop.getBestFit();
            }
            listBestFit.add(fBestFitness);
            buf.append(-(getCurrentBestFit()) + "\r\n");
        }

    }

    public static Population reduce(Population pop) {
        int oldPopSize = pop.getPopSize();
        int newPopSize = oldPopSize / 2;
        Population newPop = new Population(newPopSize);
        Reduction rect = new Reduction();
        rect.reduction(pop, newPop);
        return newPop;
    }

    public static void run() throws IOException {
        int fitnessCalls = 0;
        Population pop = new IndivPopulation(m_nInitalSize);

        while (m_nFitnessCalls < maxnfeval) {
            evolve(pop);

            Population newPop = reduce(pop);
            pop = newPop;
        }

        FileWriter fw = new FileWriter("data_txt/dynNP_Step.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.print(buf);
        buf.delete(0, buf.length());
        pw.flush();
        pw.close();
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        File f = new File("data_txt/dynNP_Step.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        fw.write("");
        fw.close();
        for (int i = 0; i < 10; i++) {
            buf.append("RUN \t" + (i + 1) + "\r\n");
            reset();
            run();
        }
    }

    public static void reset() {
        m_nFitnessCalls = 0;
        listBestFit.clear();
    }

    public static double getCurrentBestFit() {
        double fBestFit = -Double.MAX_VALUE;
        for (int i = 0; i < listBestFit.size(); i++) {
            if (listBestFit.get(i) > fBestFit) {
                fBestFit = listBestFit.get(i);
            }
        }
        return fBestFit;
    }

}
