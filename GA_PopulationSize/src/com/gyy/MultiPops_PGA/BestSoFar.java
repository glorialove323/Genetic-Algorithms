/*
 * 寻找截止当代以前的最佳个体
 */


package com.gyy.MultiPops_PGA;

import com.gyy.MultiPops_PGA.SGA.Population;


public class BestSoFar{
	
	private int        iteration;
	private int        solverPosition;
	private int        generation;
	private Population population;
	private int        bestPosition;
	private double     fitness = -(Double.MAX_VALUE);
	
	
	public BestSoFar(){}													
	
	public int 			   getIteration(){return iteration;}
	public int        getSolverPosition(){return solverPosition;}
	public int            getGeneration(){return generation;}
	public Population     getPopulation(){return population;}
	public int          getBestPosition(){return bestPosition;}
	public double            getFitness(){return fitness;}
	
	public void          setIteration(int iteration){this.iteration = iteration;}
	public void        setSolverPosition(int solver){this.solverPosition = solver;}
	public void        setGeneration(int generation){this.generation = generation;}
	public void setPopulation(Population population){this.population = population;}
	public void            setPosition(int position){this.bestPosition = position;}
	public void           setFitness(double fitness){this.fitness = fitness;}
	
	public void updateBest(Solvers solvers, int solverPosition){
		this.iteration = ParEngine.iteration;
		//fitness = -(Double.MAX_VALUE);
		Population currentPopulation = solvers.getCurrentPopulation();
		double currentFit = currentPopulation.getBestFit();
		if(currentFit > fitness){											// NOTE: variable success == foundOnes(currentPopulation) is for testing only.
			this.solverPosition = solverPosition;
			generation = solvers.getCurrentGeneration();
			population = currentPopulation;
			this.bestPosition = currentPopulation.getBestPos();
			fitness = currentFit;
		}
	}	
}




