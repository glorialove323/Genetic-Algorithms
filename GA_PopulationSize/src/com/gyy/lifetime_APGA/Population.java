/**
 * 
 */
package com.gyy.lifetime_APGA;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Gloria
 * 
 */
class Population {

    // List类型，可以随时进行增删
    List<Individual> popList;

    Individual indiv;//种群中某个特定的个体

    Individual bestIndividual;// 当代适应度最好的个体

    Individual worstIndividual;// 当代适应度最差的个体

    Individual currentBest; // 截止当代之前适应度最好的个体
    Individual currentWorst;

    private int popSize;//种群中个体的个数

    private double avgFitness; // 平均适应度

    private double bestFitness; // 最高适应度值

    private double worstFitness; // 最低适应度值
    
    @SuppressWarnings("unused")
    private int worstIndex;//bestIndividual对应的数组下标  

    // 构造函数
    public Population(int popSize) {
        popList = new ArrayList<Individual>();
        for (int i = 0; i < popSize; i++) {
            Individual indiv = new Individual();
            popList.add(indiv);
        }
    }

    // 初始化种群
    public void initPopulation() {
        for (int i = 0; i < getPopSize(); i++) {
            getIndividual(i).generateIndividual();
            Individual indiv = getIndividual(i);
            indiv.setIndivAge(0);
        }   
        double bestFitness = getBestFitness();
        double worstFitness = getWorstFitness();
        double avgFitness = getAvgFitness();
        for(int i =0;i<getPopSize();i++){
            Individual indiv = getIndividual(i);
            indiv.calLifetime(bestFitness,worstFitness,avgFitness);
        }
    }

    public Individual getCurBestIndividual(){
        return currentBest;
    }
    // 获取种群中的特定个体
    public Individual getIndividual(int index) {
        indiv = popList.get(index);
        return indiv;
    }
    //向种群中加入特定的个体
    public void addIndividual(Individual indiv){
        popList.add(indiv);
    }
    //在种群中删除特定的个体
    public void deleteIndividual(int index){
        popList.remove(index);
    }

    //获取种群的个数
    public int getPopSize(){
        popSize = popList.size();
        return popSize;
    }

    // 获取总的适应度值
    public double calTotalFitness() {
        double totalFitness = 0;
        for (int i = 0; i < popList.size(); i++) {
            totalFitness += popList.get(i).getFitness();
        }
        return totalFitness;
    }
    
    //获得种群中最佳的个体
    public Individual findBestIndividual(){
        if (popList.size() == 0)
        {
            bestIndividual = null;
            worstIndividual = null;
            return null;
        }
        bestIndividual = worstIndividual = popList.get(0);  
        for(int i = 1; i <getPopSize();i++){ 
            popList.get(i).calFitness();
        }
        for(int i = 1; i <getPopSize();i++){ 
            if(popList.get(i).getFitness() > bestIndividual.getFitness()){  
                bestIndividual = popList.get(i);  
            }  
            if(popList.get(i).getFitness() < worstIndividual.getFitness()){  
                worstIndividual = popList.get(i);  
                worstIndex = i;  
            }  
        }  
        if( Evolve.getGeneration() == 1 ){//初始种群  
            currentBest = (Individual)bestIndividual.clone();  
        }else{  
            if(bestIndividual.getFitness() > currentBest.getFitness())  
                currentBest = (Individual)bestIndividual.clone();  
        }  
      return bestIndividual;
    }

    // 获取平均适应度值
    public double getAvgFitness() {
        avgFitness = calTotalFitness() / popList.size();
        return avgFitness;
    }
    
    //获取当前种群中的最高适应度值
    public double getBestFitness(){
        bestFitness = getIndividual(0).getFitness();
        for(int i=1;i<getPopSize();i++){
            double fitness = getIndividual(i).getFitness();
            if(bestFitness <= fitness){
                bestFitness = fitness;
            }
        }       
        return bestFitness;
    }
    
  //获取当前种群中的最低适应度值
    public double getWorstFitness(){
        worstFitness = getIndividual(0).getFitness();
        for(int i=1;i<getPopSize();i++){
            double fitness = getIndividual(i).getFitness();
            if(worstFitness>= fitness){
                worstFitness = fitness;
            }
        }
        return worstFitness;
    }
    
    public String toString(){  
        String str=""; 
        for(int i = 0 ; i < popList.size() ; i++)  
            str += popList.get(i);  
        return str;  
    } 
}
