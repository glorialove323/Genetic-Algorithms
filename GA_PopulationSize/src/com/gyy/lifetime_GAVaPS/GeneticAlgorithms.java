/**
 * GAVaPS
 * 缺点：1、当初始种群很差的时候，个体的淘汰率也很大，导致最后的个体削减的很快，实验结果不理想
 * 缺点：2、当初始种群很好的时候，个体的淘汰率很小，导致种群中的个体猛涨，影响进化效率
 * 缺点：3、很难运行到一个比较合适的时机，使得实验结果不错且种群中的个数不猛涨
 * 缺点：4、相比简单遗传算法而言，所需进化时间要久一些
 */
package com.gyy.lifetime_GAVaPS;

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
    public static double crossoverRate;//交叉概率  
    public static double mutateRate;//变异概率  
    public static int populationSize;//群体大小  
    public static int maxFitnessCalls;
    public static double reproductionRatio;
    public static int chromLen;
    public static int popSize;
      
    static {  
        popSize = 20;
        chromLen = 20;
        populationSize = 20;  
        crossoverRate = 0.65;  
        mutateRate = 0.015;
        reproductionRatio = 0.4;
        maxFitnessCalls = 200000;
    }  
    static StringBuffer  buf = new StringBuffer();
    
    public static void run() throws IOException{   
    	
    	Evolve.reset();         
        Population pop = new Population(populationSize);  
        pop.initPopulation();

        while (!Evolve.isEvolutionDone() && (!Evolve.isPopSizeZero(pop))) {
            Evolve.evolve(pop);
            buf.append(Evolve.m_nFitnessCalls + "\t" + pop.getPopSize() + "\t"
                    + (-pop.currentBest.getFitness() + "\r\n"));
        }          
        FileWriter fw = new FileWriter("data_txt/GAVAPS_Branin.txt", true);  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw); 
        pw.print(buf);
        buf.delete(0, buf.length());
        pw.flush();
        pw.close(); 
        fw.close();
    }  
    
    public static void main(String[] args)throws IOException{
    	File f = new File("data_txt/GAVAPS_Branin.txt");
    	if (!f.exists())
    	{
    		f.createNewFile();
    	}
    	FileWriter fw =  new FileWriter(f);
    	fw.write("");
    	fw.close();
    	for(int i = 0;i<10;i++){
    	    buf.append("RUN \t"+(i+1)+"\r\n");
    		run();  		
    	}
    }

}
