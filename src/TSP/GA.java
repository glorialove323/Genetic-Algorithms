/**
 * @Copyright: Copyright (c) 2010 LiRen, Inc. All rights reserved.
 */
package TSP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.security.PrivateKey;

/**
 * Create Date:	2015年12月7日 下午8:54:35
 * @version:	V3.0.0
 * @author:		Terry.Li
 */
public class GA {
	private Chromosome[] chromosomes;
	private Chromosome[] nextGeneration;
	private int N;
	private int cityNum;
	private double pc_t;//交叉率
	private double pm_t;//变异率
	private int MAX_GEN;
	private int bestLength;
	private int[] bestTour;
	private double bestFitness;
	private double[] averageFitness;
	private int[][] distance;
	private String filename;
	
	public GA(){
		N= 100;
		cityNum= 30;
		pc_t = 0.9;
		pm_t = 0.1;
		MAX_GEN = 1000;
		bestLength=0;
		bestTour = new int[cityNum];
		bestFitness=0.0;
		averageFitness=new double[MAX_GEN];
		chromosomes= new Chromosome[N];
		distance = new int[cityNum][cityNum];
		
	}
	
	/**
	 * constructor of GA class
	 * @param n 种群规模
	 * @param num 城市规模
	 * @param g 运行代数
	 * @param pc 交叉率
	 * @param pm 变异率
	 * @param filename 数据文件名
	 */
	
	public GA(int n,int num, int g, double pc,double pm, String filename){
		this.N=n;
		this.cityNum=num;
		this.MAX_GEN=g;
		this.pc_t=pc;
		this.pm_t=pm;
		bestTour=new int[cityNum];
		averageFitness=new double[MAX_GEN];
		bestFitness=0.0;
		chromosomes=new Chromosome[N];
		distance=new int[cityNum][cityNum];
		this.filename=filename;
	}
	
	public void solve()throws IOException {
		System.out.println("----------------Start initilization------------");
		init();
		System.out.println("----------------End initilization--------------");
		System.out.println("----------------Start evolution------------------");
		for(int i=0;i<MAX_GEN;i++){
			System.out.println("---------Start generation "+i+"------------");
			evolve(i);
			System.out.println("---------End generation"+i+"---------------");
		}
		System.out.println("---------------End evolution-----------------");
		printOptimal();
		outputResults();
		
	}
	
	/**
	 * 初始化GA
	 * @throws IOException
	 */
	
	@SuppressWarnings("resource")
	private void init() throws IOException{
		int [] x;
		int [] y;
		String strbuff;
		BufferedReader data = new BufferedReader(new FileInputStream(filename));
		
		distance = new int[cityNum][cityNum];
		x = new int[cityNum];
		y = new int[cityNum];
		for(int i = 0; i<cityNum;i++){
			strbuff= data.readLine();
			String[] strcol = strbuff.split("");
			x[i]=Double.valueOf(strcol[1]).intValue();
			y[i]=Double.valueOf(strcol[2]).intValue();
		}
		  //计算距离矩阵 ，针对具体问题，距离计算方法也不一样，此处用的是att48作为案例，它有48个城市，距离计算方法为伪欧氏距离，最优值为10628 

		for(int i=0;i<cityNum-1;i++){
			distance[i][i]=0;
			for(int j=i+1;j<cityNum;j++){
				double rij=Math.sqrt((x[i]-x[j])*(x[i]-x[j]+(y[i]-y[j])*(y[i]-y[j]));
				int tij=(int)Math.round(rij);//四舍五入函数
				
				distance[i][j]=tij;
				distance[j][i]=distance[i][j];
			}
			distance[cityNum-1][cityNum-1]=0;
			
			for(int i=0;i<N;i++){
				Chromosome chromosome=new Chromosome(cityNum,distance);
				chromosome.randomGeneration();
				chromosome[i]=chromosome;
				chromosome.print();
			}
		}
		
		private void evolve(int g){
			double[] selectionP=new double[N];
			double sum=0.0;
			double tmp=0.0;
			
			for(int i=0;i<N;i++){
				sum += chromosomes[i].getFitness();
				if(chromosomes[i].getFitness()>bestFitness){
					bestFitness=chromosomes[i].getFitness();
					bestLength=(int)(1.0/bestFitness);
					for(int j=0;j<cityNum;j++){
						bestTour[j]=chromosomes[i].getTour()[j];
					}
				}
			}
			
			averageFitness[g]=sum/N;
			
			System.out.println("The average fitness in"+g+"generation is:"+averageFitness[g]+",and the best fitness is:"+bestFitness);
			for(int i=0;i<N;i++){
				tmp+=chromosomes[i].getFitness()/sum;
				selectionP[i]=tmp;
			}
		}
	}
}
