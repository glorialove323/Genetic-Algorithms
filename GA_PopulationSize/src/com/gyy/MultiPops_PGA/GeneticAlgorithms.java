
package com.gyy.MultiPops_PGA;

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
    public static long parRuns = 1; 

    public static int nSuccess = 0; 

    static StringBuffer  buf = new StringBuffer();
    
    public static void run() throws IOException{
        
        ParEngine parEngine = new ParEngine();

        for (int r = 0; r <ParEngine.parRuns; r++) {
          //  ParPress.printRunInitialInfo(r);
            nSuccess += parEngine.RUN(r);
           // ParPress.printRunFinalInfo(r);
        }      
    }
    
    public static void main(String [] args) throws IOException{
        File f = new File("data_txt/PGA_Rosenbrock.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        fw.write("");
        fw.close();
        for (int i = 0; i < 10; i++) {
            FileWriter fw2 = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw2);  
            PrintWriter pw = new PrintWriter(bw); 
            pw.print("RUN  " + (i+1)+"\r\n");
            buf.delete(0, buf.length());
            pw.flush();
            pw.close();
            fw2.close();
            run();
        }
         
    }
}
