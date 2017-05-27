/**
 * 
 */
package com.gyy.lifetime_APGA;

/**
 * @author Gloria
 * 
 */
public class Individual {
    static int defaultChromLength = 20;

    static int defaultGeneLength = 10;

    public Chromosome chrom;

    private double x1, x2;

    private double indivFitness = 0;

    private int indivLifetime = 0;

    private int indivAge = 0;

    final int MinLT = 1;

    final int MaxLT = 15;

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

    public Individual() {
        chrom = new Chromosome(defaultChromLength);
        generateIndividual();
    }

    // 获取个体的适应度值
    public double getFitness() {
       // indivFitness = calFitness();
        return indivFitness;
    }

    public void resetFitness() {
        this.indivFitness = 0;
    }

    // 计算个体的适应度值
    public double calFitness() {
        decode();
        indivFitness = function(x1, x2);
        return indivFitness;
    }

    // 计算个体的age值，每执行一次进化算法，就加1
    public int increaseAge() {
        return indivAge++;
    }

    public int getIndivAge() {
        return indivAge;
    }

    public void resetAge() {
        this.indivAge = 0;
    }

    public void setIndivAge(int indivAge) {
        this.indivAge = indivAge;
    }

    public int getLiftime() {
        return indivLifetime;
    }

    public void resetLifetime() {
        this.indivLifetime = 0;
    }

    public int calLifetime(double bestFitness, double worstFitness, double avgFitness) {
        indivFitness = getFitness();
        if (indivFitness <= avgFitness) {
            indivLifetime = (int) (MinLT + 1.0 / 2 * (MaxLT - MinLT) * (indivFitness - worstFitness)
                    / (avgFitness - worstFitness));
        } else {
            indivLifetime = (int) (1.0 / 2 * (MinLT + MaxLT) + 1.0 / 2 * (MaxLT - MinLT) * (indivFitness - avgFitness)
                    / (bestFitness - avgFitness));
        }
        return indivLifetime;
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

    // 解码
    private String codingVariable1(double x) {
        double y = (((x + Math.abs(MIN1)) * Math.pow(2, 10)) / (MAX1 + Math.abs(MIN1)));
        String code = Integer.toBinaryString((int) y);

        StringBuffer codeBuf = new StringBuffer(code);
        for (int i = code.length(); i < defaultGeneLength; i++)
            codeBuf.insert(0, '0');

        return codeBuf.toString();
    }
    // 解码
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



    public void mutateSingleBit(int index) {
        String gene, gn;
        gene = chrom.getGene(index, index);
        gn = gene.equals("0") ? "1" : "0";
        chrom.setGene(index, index, gn);
    }

    public String toString() {
        String str = "";

        str = "基因型:" + chrom + "  ";
        str += "表现型:" + "x1=" + x1 + "," + "x2=" + x2 + "\t";

        str += "函数值:" + function(x1, x2) + "\n";

        return str;
    }

    // 随机产生个体
    public void generateIndividual() {

        x1 = Math.random() * (MAX1 - MIN1) + MIN1;
        x2 = Math.random() * (MAX2 - MIN2) + MIN2;
        coding();
        calFitness();
    }

    public Individual clone() {
        Individual inv = new Individual();
        try {
            inv.chrom = chrom.clone();
            inv.indivFitness = indivFitness;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return inv;
    }

}
