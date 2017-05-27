/**
 * 
 */
package com.gyy.lifetime_APGA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




/**
 * @author Gloria
 * 
 */
public class Evolve {
    private static int generation; //种群的代数  
    public static int m_nFitnessCalls;
    public static void reset(){
        generation = 0;
        m_nFitnessCalls = 0;
    }
    public static void evolve(Population pop) {
             incIndivAge(pop);
             incGeneration();
             recombine(pop);
             m_nFitnessCalls += pop.getPopSize();
             elimination(pop); 
             pop.findBestIndividual();
             generation++;             
    }
    public static void resetGeneration(){
    	generation = 0;
    }
    /*
     * APGA的操作过程： 1、构造一个auxPopulation，种群规模为2 2、对该种群进行交叉和变异
     * 3、将这些个体加入到原来的种群中去 (暂定全部加入到原来的种群中去) 4、根据lifetime机制来消除个体
     * 5、lifetime值MinLT为1，MaxLT为15
     */
    /*
     * 每次进入循环，种群中的所有个体的age值都增加1
     */
    private static void incIndivAge(Population pop) {
        for (int i = 0; i < pop.getPopSize(); i++) {
            Individual indiv = pop.getIndividual(i);
            indiv.increaseAge();
        }
    }
    //计算目标函数值、适应度、找出最优个体。  
    public static void incGeneration(){  
        generation ++;      
    }      

    // ========================recombine操作==========================
    // --------------------------------------------------------------
    private static Population recombine(Population pop) {
        /*
         * select p*popsize individuals randomly
         */
        int auxPopSize = 2;

        List<Individual> auxPopList = new ArrayList<Individual>();

        for (int i = 0; i < auxPopSize; i++) {
            Individual indiv = pop.getIndividual((int) (Math.random() * pop.getPopSize()));
            Individual mIndiv = (Individual) indiv.clone();
            mIndiv.resetFitness();
            mIndiv.resetAge();
            mIndiv.resetLifetime();
            auxPopList.add(mIndiv);
        }
        /*
         * generate new individuals using crossover and mutation based on these individuals
         */
        crossover(auxPopList);
        mutate(auxPopList);
        /* auxPopulation
         * evaluate fitness of every new individuals and calculat liftime and set age 1
         */
        int newAuxPopSize = auxPopList.size();
       // System.out.println("generation: "+getGeneration());
       // System.out.println("oldPopSize: "+pop.getPopSize());
       // System.out.println("newAuzPopSize: "+newAuxPopSize);
        Population auxPopulation = new Population(0);
        for(int i =0;i<newAuxPopSize;i++){
            Individual mIndiv = auxPopList.get(i);
            mIndiv.calFitness();//==0?
            mIndiv.increaseAge();
            auxPopulation.addIndividual(mIndiv);
        }
        
        double bestFitness = auxPopulation.getBestFitness();
        double worstFitness = auxPopulation.getWorstFitness();
        double avgFitness = auxPopulation.getAvgFitness();
        for(int i =0;i<auxPopulation.getPopSize();i++){
            auxPopulation.getIndividual(i).calLifetime(bestFitness, worstFitness, avgFitness);
        }
        
        /* all population
         * combine the old with new individuals
         */
        for(int i =0;i<auxPopulation.getPopSize();i++){
            Individual indiv = auxPopulation.getIndividual(i);
            pop.addIndividual(indiv);
        }
        return pop;
    }

    // ============================elimination操作======================
    // ----------------------------------------------------------------
    private static void elimination(Population pop) {
        int delete = 0;
        for (int i = 0; i < pop.getPopSize();) {
            Individual indiv = pop.getIndividual(i);
            int indivLifetime = indiv.getLiftime();
            int indivAge = indiv.getIndivAge();

           // System.out.println("indiv [" + i + "]" + " lifetime: " + indivLifetime + " age: " + indivAge);

            // 执行删除机制
            if (indivAge >= indivLifetime) {
                pop.deleteIndividual(i);
                delete = delete + 1;
               // System.out.println("indiv [" + i + "]" + " lifetime: " + indivLifetime + " is removed...");
            } else {
                i++;
               // System.out.println("lifetime >= age, cannot be removed...");
            }
        }
       // System.out.println("delete individuals: " + delete);
       // System.out.println("after elimination, current poplation size:" + pop.getPopSize());

    }

    /*
     * 单点交叉操作
     */
    public static void crossover(List<Individual> popList) {
        for (int i = 0; i < popList.size() / 2 * 2; i += 2) {
            int rnd;
            // 随机两两配对
            rnd = rand(i, popList.size());

            if (rnd != i)
                exchange(popList, i, rnd);

            rnd = rand(i, popList.size());
            if (rnd != i + 1)
                exchange(popList, i + 1, rnd);

            // 交叉
            double random = rand();

            if (random < GeneticAlgorithms.crossoverRate) {
                cross(popList, i);
            }
        }
    }

    // 执行交叉操作
    private static void cross(List<Individual> popList, int i) {
        String chromFragment1, chromFragment2;// 基因片段

        int rnd = rand(0,  GeneticAlgorithms.chromLen- 1);// 交叉点为rnd之后,可能的位置有chromlen - 1个.
        chromFragment1 = popList.get(i).getChrom(rnd + 1, GeneticAlgorithms.chromLen - 1);
        chromFragment2 = popList.get(i + 1).getChrom(rnd + 1, GeneticAlgorithms.chromLen - 1);

        popList.get(i).setChrom(rnd + 1, GeneticAlgorithms.chromLen - 1, chromFragment2);
        popList.get(i + 1).setChrom(rnd + 1, GeneticAlgorithms.chromLen - 1, chromFragment1);
    }

    // 产生随机数
    private static int rand(int start, int end) {// 产生区间为[start , end)的随机整数
        return (int) (rand() * (end - start) + start);
    }

    // 交换
    private static void exchange(List<Individual> popList, int src, int dest) {
        Collections.swap(popList, src, dest);
    }

    /*
     * 变异操作
     */
    public static void mutate(List<Individual> popList) {
        for (int i = 0; i < popList.size(); i++) {
            for (int j = 0; j < GeneticAlgorithms.chromLen; j++) {
                if (rand() < GeneticAlgorithms.mutateRate) {
                    popList.get(i).mutateSingleBit(j);
                    // /System.out.print("变异"+ i +" - "+ j + "  ");///
                }
            }
        }
    }

    // 产生随机数
    private static double rand() {
        return Math.random();
    }
    
    public static int getGeneration(){
        return generation;
    }
    //判断进化是否完成  
    public static boolean isEvolutionDone(){  
//        if(getGeneration() < GeneticAlgorithms.maxGeneration)  
//            return false;  
        if(m_nFitnessCalls < GeneticAlgorithms.maxFitnessCalls)
            return false;
        return true;      
    }  
    
    public static boolean isPopSizeZero(Population pop){
        if(pop.getPopSize()>0){
           // System.out.println("popsize: "+pop.getPopSize());
            return false;
        }else{
          //  System.out.println("popsize: "+pop.getPopSize());   
            return true; 
        }
    }

}
