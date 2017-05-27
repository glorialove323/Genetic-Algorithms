/**
 * APGA
 * 优点：1、相比GAVaPS而言，运行时间大大减少，与简单遗传算法的时间相近
 * 优点：2、相比GAVaPS而言，种群规模确实不会疯长了
 * 缺点：1、可就是因为种群规模变化太小，或者种群个体淘汰率过高，导致最终的实验结果很差
 */
package com.gyy.lifetime_APGA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * @author Gloria
 * 
 */
public class GeneticAlgorithms {
    public static double crossoverRate;// 交叉概率

    public static double mutateRate;// 变异概率

    public static int populationSize;// 群体大小

    public static int maxFitnessCalls;

    public static int chromLen;

    public static int popSize;

    static {
        popSize = 20;
        chromLen = 20;
        populationSize = 20;
        crossoverRate = 0.65;
        mutateRate = 0.015;
        maxFitnessCalls = 200000;
    }

    static StringBuffer buf = new StringBuffer();

    public static void run() throws IOException {

        Evolve.reset();
        Population pop = new Population(populationSize);
        pop.initPopulation();

        while (!Evolve.isEvolutionDone() && (!Evolve.isPopSizeZero(pop))) {
            Evolve.evolve(pop);

            buf.append(Evolve.m_nFitnessCalls + "\t" + pop.getPopSize() + "\t"
                    + (-(pop.getCurBestIndividual().getFitness())) +"\r\n");
        }
        FileWriter fw = new FileWriter("data_txt/APGA_Branin.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println(buf);
        buf.delete(0, buf.length());// 要清空stringbuffer
        pw.flush();
        pw.close();
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        File f = new File("data_txt/APGA_Branin.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        fw.write("");
        fw.close();
        for (int i = 0; i < 10; i++) {
            buf.append("RUN \t" + (i + 1) + "\r\n");
            run();
        }
    }

}
