/**
 * Stopper:判断循环什么时候停止
 * 1、给定固定的iteration次数，达到上限之后就结束操作
 * 2、种群处于收敛状态，当平均适应度等于最大是适应度的时候
 */
package com.gyy.MultiPops_PGA;

import com.gyy.MultiPops_PGA.SGA.Population;

/**
 * @author Gloria
 *
 */
public class Stopper{
    
    public static int   maxNGen = 200000;            // Maximal number of generations to perform.             Default = 200
    public static long  maxFitCalls= 300000;        // Maximal number of fitness calls.                      Default = -1 (unbounded)
    public static int   allFitnessEqual = -1;    // Stop if all individuals have the same fitness.        Default = -1 (ignore)
    public static int   foundBestFit= -1;       // Stop if the optimum was found?                        Default = -1 (-1 -> no; 1 -> yes)
    
    private static boolean success = false; 
    
    public static boolean foundOptimum(){return success;}
    
    public static void setSuccess(boolean suc){success = suc;}
    
    public static boolean criteria(int nGen, Population population){
        return nGeneration(nGen)               || 
               fitnessCalls();                    
  }
    
    private static boolean nGeneration(int nGen){return nGen > maxNGen;}
    
    public static boolean fitnessCalls(){return (maxFitCalls == -1)? false: ParEngine.fitCalls >= maxFitCalls;}
}

