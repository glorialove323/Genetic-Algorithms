/**
 * 
 */
package com.gyy.simpleGA;


/**
 * @author Gloria
 *
 */
class G1Individual extends Individual {  

    private double x;
    
    G1Individual(int chromlen){  
        genelen = 20;  
        chrom = new Chromosome(chromlen);  
    }  
      
    //编码  
    public void coding(){  
        String code;
        code = codingVariable(x);
        chrom.setGene(0, 19, code);
    }  
      
    //解码  
    public void decode(){  
        String gene;
        gene = chrom.getGene(0, 19);
        x = decodeGene(gene);
    }  
      
    //计算目标函数值  
    public  void calTargetValue(){  
        decode();  
        targetValue = g1Function(x);  
    }  
      
    //计算个体适应度  
    public void calFitness(){  
        fitness = getTargetValue();  
    }  
      
    private String codingVariable(double x){  
        double y = (((x+2)*Math.pow(2, 20))/3);
        String code = Integer.toBinaryString((int) y);  
          
        StringBuffer codeBuf = new StringBuffer(code);  
        for(int i = code.length(); i<genelen; i++)  
            codeBuf.insert(0,'0');  
              
        return codeBuf.toString();  
    }  
      
    private double decodeGene(String gene){  
        int value ;  
        double decode;  
        value = Integer.parseInt(gene, 2);  
        decode = value/(Math.pow(2, 20))*3-2;
          
        return decode;  
    }  
          
    public String toString(){  
        String str = "";  
        ///str = "基因型:" + chrom + "  ";  
        ///str+= "表现型:" + "[x1,x2]=" + "[" + x1 + "," + x2 + "]" + "\t";  
        str+="函数值:" + g1Function(x) + "\n";  
          
        return str;      
    }  
      
    /** 
     *G1函数
     *y = -xsin(10πx)+1
     *-2.0 =< x <= 1.0
     */  
    public static double g1Function(double x){  
        double fun;  
        fun = -x*(Math.sin(10*(Math.PI)*x))+1;  
          
        return fun;  
    }  
      
    //随机产生个体  
    public void generateIndividual(){  
       //Math.random()的取值范围是[0,1]      
        x = Math.random()*3-2;
        
        //同步编码和适应度  
        coding();  
        calTargetValue();  
        calFitness();  
    }  
}  