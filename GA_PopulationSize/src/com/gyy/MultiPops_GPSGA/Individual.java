/**
 * 
 */
package com.gyy.MultiPops_GPSGA;

/**
 * @author Gloria
 * 
 */
public class Individual {
    static int defaultGeneLength;

    static int defaultChromLength = 20;

    public Chromosome chrom;

    private double x1, x2;

    private double indivFitness = -Double.MAX_VALUE;

    private char[] individual = new char[20];
    
    private double MAX1 = 10;

    private double MIN1 = -5;
    
    private double MAX2 = 15;
    private double MIN2 = 0;

    public static double function(double x1, double x2) {
        double fun;
        int a =1;
        double b = 5.1/(4*Math.PI*Math.PI);
        double c = 5/Math.PI;
        int d =6;
        int e = 10;
        double f = 1/(8*Math.PI);
        fun = a*(x2-b*x1*x1+c*x1-d)*(x2-b*x1*x1+c*x1-d)+e*(1-f)*Math.cos(x1)+e;
        return -fun;
    }
    
    // shadow copy
    public Individual(char[] individual) {
        this.individual = individual;
    }

    // not shadow copy
    public Individual(Individual indiv) {
        individual = indiv.getIndividual();
    }

    public char[] getIndividual() {
        return individual;
    }

    public Individual() {
        defaultGeneLength = 10;
        chrom = new Chromosome(defaultChromLength);
        // generateIndividual();
    }

    public char getAllele(int j) {
        return individual[j];
    }

    public void setAllele(int j, char c) {
        individual[j] = c;
    }

    // 获取个体的适应度值
    public double getFitness() {
        return indivFitness;
    }

    // 计算个体的适应度值
    public double calFitness() {
        decode();
        indivFitness = function(x1, x2);
        return indivFitness;
    }

    public int getChromlen() {
        return chrom.getLength();
    }

    public boolean setChrom(int begin, int end, String gene) {
        return chrom.setGene(begin, end, gene);
    }

    public String getChrom(int begin, int end) {
        return chrom.getGene(begin, end);
    }

    // 编码
    public void coding() {
        String code1, code2;
        code1 = codingVariable1(x1);
        code2 = codingVariable2(x2);
        chrom.setGene(0, 9, code1);
        chrom.setGene(10, 19, code2);
    }

    // 解码
    public void decode() {
        String gene1, gene2;
        gene1 = chrom.getGene(0, 9);
        gene2 = chrom.getGene(10, 19);
        x1 = decodeGene1(gene1);
        x2 = decodeGene2(gene2);
    }

    private String codingVariable1(double x) {
        double y = (((x + Math.abs(MIN1)) * Math.pow(2, 10)) / (MAX1 + Math.abs(MIN1)));
        String code = Integer.toBinaryString((int) y);

        StringBuffer codeBuf = new StringBuffer(code);
        for (int i = code.length(); i < defaultGeneLength; i++)
            codeBuf.insert(0, '0');

        return codeBuf.toString();
    }
    private String codingVariable2(double x) {
        double y = (((x + Math.abs(MIN2)) * Math.pow(2, 10)) / (MAX2 + Math.abs(MIN2)));
        String code = Integer.toBinaryString((int) y);

        StringBuffer codeBuf = new StringBuffer(code);
        for (int i = code.length(); i < defaultGeneLength; i++)
            codeBuf.insert(0, '0');

        return codeBuf.toString();
    }

    private double decodeGene1(String gene) {
        int value;
        double decode;
        value = Integer.parseInt(gene, 2);
        decode = value / (Math.pow(2, 10)) * (MAX1 + Math.abs(MIN1)) + MIN1;
        return decode;
    }
    private double decodeGene2(String gene) {
        int value;
        double decode;
        value = Integer.parseInt(gene, 2);
        decode = value / (Math.pow(2, 10)) * (MAX2 + Math.abs(MIN2)) + MIN2;
        return decode;
    }

    public String toString() {
        String str = "";
        str += "函数值:" + function(x1, x2) + "\n";

        return str;
    }


    // 随机产生个体
    public void generateIndividual() {
        chrom = new Chromosome(defaultChromLength);
        x1 = Math.random() * (MAX1 - MIN1) + MIN1;
        x2 = Math.random() * (MAX2 - MIN2) + MIN2;
        coding();
        // calFitness();
    }

    public Individual clone() {
        Individual inv = new Individual();
        try {
            inv.chrom = chrom.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return inv;
    }
}
