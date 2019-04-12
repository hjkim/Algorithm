import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_16235_WoodInvestment {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N,M,K,injectionMap[][],nutrientMap[][], deadTrees[][], survivalTrees;
	public static ArrayList<Pair> trees[][];
	public static int drow[] = {-1,-1,0,1,1,1,0,-1};
	public static int dcol[] = {0,1,1,1,0,-1,-1,-1};
	public static void main(String args[]) throws IOException{
		initSetting();
		startTreeInvestment(K);
		System.out.println(survivalTrees);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		injectionMap = new int[N+1][N+1];
		nutrientMap = new int[N+1][N+1];
		deadTrees = new int[N+1][N+1];
		survivalTrees = 0;
		trees = new ArrayList[N+1][N+1];
		for(int i=1; i<=N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				injectionMap[i][j] = Integer.parseInt(st.nextToken());
				nutrientMap[i][j] = 5;
				trees[i][j] = new ArrayList<Pair>();
			}
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int x,y,z;
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			z = Integer.parseInt(st.nextToken());
			
			trees[x][y].add(new Pair(z));
			survivalTrees++;
		}
	}
	public static void startTreeInvestment(int limit) {
		for(int i=0; i<limit; i++) {
			meetSpring();
			meetSummer();
			meetFall();
			meetWinter();
		}
	}
	public static void meetSpring() {
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				if(trees[i][j].size() == 0)
					continue;
				Collections.sort(trees[i][j]);
				
				for(int k=0; k<trees[i][j].size(); k++) {
					
					if(nutrientMap[i][j] >= trees[i][j].get(k).age) {
						nutrientMap[i][j] -= trees[i][j].get(k).age;
						trees[i][j].get(k).age++;
					}else {
						//trees[i][j].get(k).survival = false;
						deadTrees[i][j] += trees[i][j].get(k).age/2;
						survivalTrees--;
						trees[i][j].remove(k);
						k--;
					}
				}
			}
		}
	}
	public static void meetSummer() {
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				nutrientMap[i][j] += deadTrees[i][j];
				deadTrees[i][j] = 0;
			}
		}
	}
	public static void meetFall() {
		int cddtrow, cddtcol;
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				for(int k=0; k<trees[i][j].size(); k++) {
					if(trees[i][j].get(k).age%5 == 0) {
						for(int d=0; d<8; d++) {
							cddtrow = i+drow[d];
							cddtcol = j+dcol[d];
							
							if(cddtrow <= 0 || cddtrow > N)
								continue;
							if(cddtcol <= 0 || cddtcol > N)
								continue;
							
							trees[cddtrow][cddtcol].add(new Pair(1));
							survivalTrees++;
						}
					}
				}
			}
		}
	}
	public static void meetWinter() {
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				nutrientMap[i][j] += injectionMap[i][j];
			}
		}
	}
	public static class Pair implements Comparable<Pair>{
		int age;
		
		public Pair(int a) {
			this.age = a;
		}
		@Override
		public int compareTo(Pair p) {
			if(this.age > p.age) {
				return 1;
			}else if(this.age < p.age){
				return -1;
			}else {
				return 0;
			}
		}
	}
	
}