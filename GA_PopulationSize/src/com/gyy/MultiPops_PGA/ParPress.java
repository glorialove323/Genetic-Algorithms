package com.gyy.MultiPops_PGA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import com.gyy.MultiPops_PGA.SGA.Population;


public class ParPress{
	
	private static BufferedWriter testFileOut;
	
	private static BufferedWriter testFileOutStats;
		
	public static void printString(String str){							// NOTE: Use this method to print simultaneous in 
		System.out.println(str);										//	 	 the console and in the testFileOut.
/*		try{	
			testFileOut.write("\n" + str);
		}catch(Exception e){System.err.println("ERROR: " + e.getMessage());} */
	}
	
	private static void printStats(String str){							// NOTE: Use this method to print in the STATS file.										//	 	 the console and in the testFileOut.
		/*try{	
			testFileOutStats.write(str + "\n");
		}catch(Exception e){System.err.println("ERROR: " + e.getMessage());}*/ 
	}
	
	
	public static void printInitialInfo(){
		String str;
			str = "############ - PARAMETERLESS METHOD - ############ - PARAMETERLESS METHOD - ############\n#" +
				  "\n#" +
				  "\n#   PARAMETERLESS: " +
				  "\n#     ->    Number of Runs = " + GeneticAlgorithms.parRuns      +
				  "\n#     -> Initial Pop. Size = " + ParEngine.N0               +
				  "\n#     ->        nextSolver = " + ParEngine.nextSolver;
				  
		 str   += "\n#" +
				  "\n#   EALGORITHM: SGA"                                        +
				  "\n#" +
				  "\n#   PROBLEM:" +
				  "\n#     ->              Name = new Problem"                   +
				  "\n#     ->       String size = " + ParEngine.chromLen         +
				  "\n#" +
				  "\n#   STOPPER:" + 
				  "\n#     ->    maxGenerations = " + Stopper.maxNGen            +
				  "\n#     ->       maxFitCalls = " + Stopper.maxFitCalls        +					 
				  "\n#     ->   allFitnessEqual = " + Stopper.allFitnessEqual    +
				  "\n#     ->      foundBestFit = " + Stopper.foundBestFit       +
				  "\n#" +
				  "\n############################################################################\n";
		printString(str);		
		
		printStats("StringSize  TotalIteration  BestPopIteration  HighestPopSize  BestPopSize  TotalFitCalls  BestPopFitCalls  AvgFitness  BestFitness");
	}
	
	/*
	 * 打印初始化信息
	 */
	public static void printRunInitialInfo(int r){
		printString("\n##### START RUN " + (r+1) + "/" + GeneticAlgorithms.parRuns + " #####"     + 
			 	      "##### START RUN " + (r+1) + "/" + GeneticAlgorithms.parRuns + " #####"     +
			 	      "##### START RUN " + (r+1) + "/" + GeneticAlgorithms.parRuns + " #####\n\n" +
			 	      "Iteration           Pop. Size           Generation           Avg. Fitness           BestCurrentFitness           BestFitnessSoFar");
	}
	
	
	public static void printCurrentSolverInfo( Solvers currentSolver, int solverPosition){
		if(ParEngine.iteration%30 == 0)
			printString("\nIteration           Pop. Size           Generation           Avg. Fitness           BestCurrentFitness           BestFitnessSoFar");
			printString(String.format("%5d %18d (%d) %16d %24.3f %25.3f %25.5f (%d)%n",
							ParEngine.iteration, 
							currentSolver.getN(), solverPosition,
							currentSolver.getCurrentGeneration(),
							currentSolver.getAvgFitness(),
							currentSolver.getCurrentPopulation().getBestFit(),
							ParEngine.bestSoFar.getFitness(),
							ParEngine.bestSoFar.getPopulation().getPopSize()  //(%d)%n
					)

				);	
	        // System.out.println(ParEngine.bestSoFar.getFitness());
	}
	
	public static void printRunFinalStats(Solvers currentSolver, int highestN){
		Population population = currentSolver.getCurrentPopulation();
		printStats(String.format("%6d %13d %15d %16d %14d %16d %13d %15.2f %12.2f",
						ParEngine.chromLen,
						ParEngine.iteration,
						currentSolver.getCurrentGeneration(),
						highestN,
						currentSolver.getN(),
						ParEngine.fitCalls,
						currentSolver.getFitnessCalls(),
						population.getAvgFit(), 
						population.getBestFit())
					);
	}

	/*
	 * 打印最终的运行结果信息
	 */
	public static void printRunFinalInfo(int r){
	    DecimalFormat df = new DecimalFormat("######0.00000"); 
		String str = "\n############################################################################" +
	                 "\n#"                                                                            +
					 "\n#               Success: " + Stopper.foundOptimum()                           + 
					 "\n#  Current Success Rate: " + GeneticAlgorithms.nSuccess + "/" + (r+1)             +					 
					 "\n#   Total Fitness Calls: " + ParEngine.fitCalls                               +
					 "\n#  Best Population Size: " + ParEngine.bestSoFar.getPopulation().getPopSize()       +
					 "\n#          Best Fitness: " + df.format(ParEngine.bestSoFar.getFitness())                 +
					 "\n#"                                                                            +
					 "\n######## END RUN " + (r+1) + "/" + GeneticAlgorithms.parRuns + " #####"           + 
					      "##### END RUN " + (r+1) + "/" + GeneticAlgorithms.parRuns + " #####"           +
					      "##### END RUN " + (r+1) + "/" + GeneticAlgorithms.parRuns + " ########\n\n\n";
		printString(str);
	}
	
	
	public static void printFinalInfo(){
		printString("\nSUCCESS RATE = " + GeneticAlgorithms.nSuccess + "/" + GeneticAlgorithms.parRuns + "\n");
		closeTestFiles();
	}
	
	private static void closeTestFiles(){		
		try{testFileOut.close();}
		catch(Exception e){System.err.println("ERROR: " + e.getMessage());}
		try{testFileOutStats.close();}
		catch(Exception e){System.err.println("ERROR: " + e.getMessage());}
	}
	
    public static void printData() throws IOException {
        StringBuffer buf = new StringBuffer();
        buf.append(ParEngine.fitCalls + "\t" + ParEngine.bestSoFar.getPopulation().getPopSize() + "\t"
                + (ParEngine.bestSoFar.getFitness()) + "\r\n");
        FileWriter fw = new FileWriter("data_txt/PGA_Rosenbrock.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.print(buf);
        buf.delete(0, buf.length());
        pw.flush();
        pw.close();
        fw.close();
    }
	
}
