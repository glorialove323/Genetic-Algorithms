package com.gyy.Shrink_SAMNP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SAMNPSolver {

	private static int generation; // 种群的代数
	static List<Population> m_List = null;
	public static int m_nInitalSize;
	public static int maxnfeval = 200000;
	public static int pmax = 4;
	public static double fPopsizeFloat = 0.05;
	static int m_nFitnessCalls;

	static {
		m_nInitalSize = 200;
	}
	 
	static StringBuffer  buf = new StringBuffer();
    static List<Double> listBestFit = new ArrayList<Double>();
	  
	public static Population IncreaseNP(Population pop) {
		int nPopSize = pop.getPopSize();
		nPopSize = (int) ((double) nPopSize * (double) (1.0 + fPopsizeFloat));
		Population newPop = new Population(nPopSize);
		newPop.initPopulation();
		Individual[] invs = pop.getIndividuals();
		for (int i = 0; i < invs.length; i++) {
			newPop.setIndividual(i, invs[i], 0);
		}
		newPop.calFitnessValues();
		return newPop;
	}

	public static Population DecreaseNP(Population pop) {
		int nPopSize = pop.getPopSize();
		nPopSize = (int) ((double) nPopSize * (double) (1.0 - fPopsizeFloat));
		Population newPop = new Population(nPopSize);
		newPop.initPopulation();
		Individual[] invs = pop.getBestIndividuals(nPopSize);
		for (int i = 0; i < invs.length; i++) {
			newPop.setIndividual(i, invs[i], 0);
		}
		newPop.calFitnessValues();
		return newPop;
	}

	public static int evolve(Population pop) {
		Crossover cross = new UniformCrossover(0.65, 0.5);
		Mutation muta = new Mutation(0.015);
		cross.cross(pop);
		muta.mutate(pop.getIndividuals());
		pop.calFitnessValues();
		m_nFitnessCalls+=pop.getPopSize();    
		return m_nFitnessCalls;		
}

	public static void run() throws IOException {
		Population pop = new IndivPopulation(m_nInitalSize);
		int nFeval = 0;
		pop.calFitnessValues();
		nFeval = pop.getPopSize();
		double fBestFitness = -Double.MAX_VALUE;
		Individual inv = pop.getBestIndividual();
		int p = 0;
		generation = 1;

		while (nFeval < maxnfeval) {
			nFeval = evolve(pop);
	        buf.append(nFeval+"\t"+pop.getPopSize()+"\t");
			double fCurBestFitness = pop.getBestFit();
			if (fCurBestFitness > fBestFitness) {
				fBestFitness = fCurBestFitness;
				inv = pop.getBestIndividual();
				p = 0;
			} else {
				p++;
			}
			buf.append(-fBestFitness+"\r\n");
			if (p >= 4) {
	            listBestFit.add(fBestFitness);
				pop = IncreaseNP(pop);
				p = 0;
				generation++;
			} else if (p == 0) {
			    listBestFit.add(fBestFitness);
				pop = DecreaseNP(pop);
				generation++;
			}

		}
		FileWriter fw = new FileWriter("data_txt/SAMNP_Step.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.print(buf);
		buf.delete(0, buf.length());

		pw.flush();
		pw.close();
		fw.close();
	}

	public static int getGeneration() {
		return generation;
	}

	public static void main(String[] args) throws IOException {
		File f = new File("data_txt/SAMNP_Step.txt");
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);
		fw.write("");
		fw.close();
		for (int i = 0; i < 10; i++) {
		    buf.append("run \t"+(i+1)+"\r\n");
		    reset();
			run();
		}
	}
	
    public static void reset(){
        m_nFitnessCalls = 0;
        listBestFit.clear();
    }
    
    public static double getCurrentBestFit(){
        double fBestFit = -Double.MAX_VALUE;
        for(int i = 0; i < listBestFit.size(); i++){
            if(listBestFit.get(i)>fBestFit){
                fBestFit= listBestFit.get(i);
            }           
        }
        return fBestFit;
    }
}
