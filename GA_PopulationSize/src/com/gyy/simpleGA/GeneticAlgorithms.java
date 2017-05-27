/**
 * 
 */
package com.gyy.simpleGA;

/**
 * @author Gloria
 *
 */
import java.io.*;  
class GeneticAlgorithms{  
    public static double crossoverRate;//交叉概率  
    public static double mutateRate;//变异概率  
    public static int maxGeneration;//进化代数  
    public static int populationSize;//群体大小  
      
    static {  
        maxGeneration  = 40;  
        populationSize = 20;  
        crossoverRate = 0.65;  
        mutateRate = 0.015;  
    }  
      
    public static void main(String[] args)throws IOException{  

        FileWriter fw = new FileWriter("result.txt");  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw);  
          
        Population pop = new Population(populationSize);  
        pop.initPopulation();  

        pw.println("初始种群:\n" + pop);  
        
        long startTime = System.currentTimeMillis();
        while(!pop.isEvolutionDone()){  
            pop.evolve();  
            pw.print("第"+pop.getGeneration()+"代：");
            System.out.println("第"+pop.getGeneration()+"代：");
            pw.print("当代适应度最好的个体：" + pop.bestIndividual ); 
            System.out.println("当代适应度最好的个体： "+pop.bestIndividual);
            pw.print("    截止当代为止的最好的个体：" + pop.currentBest ); 
            System.out.println("截止当代为止的最好的个体："+pop.currentBest);
            pw.println("");          
        }
        long endTime = System.currentTimeMillis();
        System.out.println("the total evolve time: "+(endTime-startTime));
        pw.println();  
        pw.println("第"+ pop.getGeneration()  + "代群体:\n" + pop);  

        pw.close();  
    }  
      
    public void print(){  

    }  
}  
