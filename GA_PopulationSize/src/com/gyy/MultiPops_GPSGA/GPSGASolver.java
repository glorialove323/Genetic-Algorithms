package com.gyy.MultiPops_GPSGA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GPSGASolver  {

    static List<Population> m_List = null;
    static int MaxFitnessCalls = 200000;
    public static int m_nInitalSize;
    static int m_nFitnessCalls;
    static double m_fBestFit = -Double.MAX_VALUE;
    static{
       m_nInitalSize = 20;
    }
    
    static StringBuffer  buf = new StringBuffer();
    
    static void reset(){
    	m_fBestFit = -Double.MAX_VALUE;
    	m_nFitnessCalls = 0;
    }
    
    public static void evolve(Population pop)
    {
        Crossover cros = new UniformCrossover(0.65, 0.5);
        Mutation muta = new Mutation(0.015);  
        Individual[] inv = cros.cross(pop);
        for (int i = 0; i < pop.getPopSize(); i++){
            pop.setIndividual(i, inv[i], 0);
        }
        muta.mutate(pop.getIndividuals());
    }
    public static boolean stop(){
        /*
        for (int i = 0; i < m_List.size(); i++){
            m_nFitnessCalls += m_List.get(i).getFitnessCalls();
        }
        */
        if (m_nFitnessCalls > MaxFitnessCalls){
        	return true;
        }
        
    	return false;
    }
    public static void evolve(Population pop, int fitnessCalls){
    	while(fitnessCalls>0){
    		Selection selection = new TourWithoutReplacement(pop.getPopSize(), 2);
        	SelectedSet selectedSet = selection.select(pop);
        	
        	Crossover crossover = new UniformCrossover(0.65, 0.5);
            Individual[] newIndividuals = crossover.cross(selectedSet);
            
            Mutation mutation = new Mutation(0.0);
            mutation.mutate(newIndividuals);
            FullReplacement replacement = new FullReplacement();
            replacement.replace(pop, newIndividuals); 
            pop.calAvgFitness();
            
            fitnessCalls -= newIndividuals.length;
            m_nFitnessCalls += newIndividuals.length;
            pop.incFinessCalls(newIndividuals.length);
            buf.append(m_nFitnessCalls+"\t"+pop.getPopSize()+"\t"+(-getCurrentBestFit())+"\r\n");
    	}

    }
    
    public static void run() throws IOException{
        m_List = new ArrayList<Population>();
        int i = 0;
        int j = 0;
        int nPopSize = m_nInitalSize;
        int M = 2 * nPopSize;
        Population pop1 = new Population(nPopSize);
        Population pop2 = new Population(nPopSize * 2);
        m_List.add(i, pop1);
        m_List.add(i+1, pop2);
        
        while (stop() == false){
            evolve(pop1, M);
            evolve(pop2, M);
            if (pop1.expired(pop2)){
                i++;
                pop1 = pop2;
                pop2 = new Population(pop1.getPopSize() * 2, pop1);
                M = pop2.getPopSize();
                m_List.add(i+1, pop2);
                evolve(pop2, pop1.getFitnessCalls());
            }
        }

        FileWriter fw = new FileWriter("data_txt/GPSGA_Branin.txt", true);  
        BufferedWriter bw = new BufferedWriter(fw);  
        PrintWriter pw = new PrintWriter(bw); 
        pw.print(buf);
        buf.delete(0, buf.length());
        pw.flush();
        pw.close(); 
        fw.close();   
    }
    
    public static double getCurrentBestFit(){
        double fBestFit = -Double.MAX_VALUE;
        for (int j = 0; j < m_List.size(); j++){
            Population pop = m_List.get(j);
            
            if (fBestFit < pop.getBestFit()){
                fBestFit = pop.getBestFit();
            }
        }
        if (m_fBestFit < fBestFit){
        	m_fBestFit = fBestFit;
        }
        return m_fBestFit;
    }
    
    public static void main(String [] args) throws IOException{
    	File f = new File("data_txt/GPSGA_Branin.txt");
    	if (!f.exists())
    	{
    		f.createNewFile();
    	}
    	FileWriter fw =  new FileWriter(f);
    	fw.write("");
    	fw.close();
    	
    	for(int i = 0;i<10;i++){
    	    buf.append("RUN \t"+(i+1)+"\r\n");
    	    reset();
    		run();
    	}
    }
}
