package TSP;

import java.util.Random;
import java.util.Vector;

public class Chromosome implements Cloneable{
	
	private int[] tour;
	private int[][] distance;
	private int cityNum;
	private double fitness;
	
	public Chromosome(){
		cityNum = 30;
		tour = new int[cityNum];
		distance = new int[cityNum][cityNum];
	}
	//创建一个javabean类，必须在类中创建一个无参数的构造方法
	
	public Chromosome(int num, int[][] distance){
		this.cityNum = num;
		tour = new int[cityNum];
		this.distance = distance;
	}
	
	//definition of chromosome
	
	public void randomGeneration() {
		Vector<Integer> allowedCities = new Vector<Integer>();
		for(int i = 0; i<cityNum;i++){
			allowedCities.add(Integer.valueOf(i));
		}
		
		Random r = new Random(System.currentTimeMillis());//避免random产生相同的随机数，使用当前时间作为种子
		for(int i = 0;i<cityNum;i++){
			int index = r.nextInt(allowedCities.size());
			int selectedCity= allowedCities.get(index).intValue();
			tour[i] = selectedCity;
			allowedCities.remove(index);
		}	
	}
	//initialization of chromosome(random)
	
	public void print(){
		for (int i = 0;i<cityNum;i++){
			System.out.print(tour[i]+",");
		}
		System.out.println();
		System.out.println("Its fitness measure is:"+getFitness());
	}
	//print the information of chromosome
	
	private double calculatefitness(){
		double fitness = 0.0;
		int len = 0;
		for(int i=0;i<cityNum-1;i++){
			len+=distance[this.tour[i]][this.tour[i+1]];
		}
		len+=distance[0][tour[cityNum-1]];
		fitness = 1.0/len;
		return fitness;
	}

	public int[] getTour() {
		return tour;
	}

	public void setTour(int[] tour) {
		this.tour = tour;
	}

	public int[][] getDistance() {
		return distance;
	}

	public void setDistance(int[][] distance) {
		this.distance = distance;
	}

	public int getCityNum() {
		return cityNum;
	}

	public void setCityNum(int cityNum) {
		this.cityNum = cityNum;
	}

	public double getFitness() {
		this.fitness = calculatefitness();
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	protected Object clone() throws CloneNotSupportedException {
		Chromosome chromosome = (Chromosome) super.clone();
		chromosome.cityNum = this.cityNum;
		chromosome.distance = this.distance.clone();
		chromosome.tour = this.tour.clone();
		chromosome.fitness = this.fitness;
		return chromosome;
	}
}
