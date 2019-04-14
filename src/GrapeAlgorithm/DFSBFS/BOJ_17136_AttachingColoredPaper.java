import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_17136_AttachingColoredPaper {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int map[][],N,blockSize,paperCount[],min;
	public static ArrayList<ColoredPaper> list;
	public static ColoredPaper paper;
	public static boolean fail;
	public static int counter[];
	public static void main(String args[]) throws IOException{
		initSetting();
		checkMap(0,0);
		
		if(min == 987654321) {
			System.out.println(-1);
		}else {
			System.out.println(min);
		}
		
	}
	public static void initSetting() throws IOException{
		N = 10; blockSize = 0; min = 987654321;
		br = new BufferedReader(new InputStreamReader(System.in));
		map = new int[N+1][N+1];
		list = new ArrayList<ColoredPaper>();
		paperCount = new int[6];
		counter = new int[6];
		for(int i=1; i<=5; i++) {
			counter[i] = 5;
		}
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1) {
					blockSize++;
				}
			}
		}
	}
	public static void checkMap(int blockCount, int len) {
		if(len >= min) {
			return;
		}
		
		if(blockCount == blockSize) {
			if(min > len) {
				min = len;
			}
			
		}else {
			boolean pass = false;
			int x=-1,y=-1;
			for(int i=0; i<N && pass == false; i++) {
				for(int j=0; j<N; j++) {
					if(map[i][j] == 1) {
						x= i;y=j;
						pass = true;
						break;
					}
				}
			}
			
			if(pass == true) {
				for(int k=5; k >= 1; k--) {
					if(counter[k] == 0)
						continue;
					boolean check = checkAttachingPossibility(k,x,y);
					if(check == false)
						continue;
					
					attachColoredPaper(k, x, y);
					counter[k]--;
					checkMap(blockCount+(k*k),len+1);
					counter[k]++;
					removeColoredPaper(k, x, y);
				}
			}
			
			
		}
	}
	
	public static boolean checkAttachingPossibility(int num, int row, int col) {
		boolean pass = true;
		for(int i=row; i<row+num && pass == true; i++) {
			for(int j=col; j<col+num && pass == true; j++) {
				if(i < 0 || i >= N || j < 0 || i >= N) {
					pass = false;
					break;
				}
				
				if(map[i][j] == 0) {
					pass = false;
					break;
				}
					
			}
		}
		return pass;
	}
	public static void attachColoredPaper(int num, int row, int col) {
		for(int i=row; i<row+num; i++) {
			for(int j=col; j<col+num; j++) {
				map[i][j] = 0;
			}
		}
	}
	public static void removeColoredPaper(int num, int row, int col) {
		for(int i=row; i<row+num; i++) {
			for(int j=col; j<col+num; j++) {
				map[i][j] = 1;
			}
		}
	}
	public static int calcBlockSum() {
		int sum =0;
		for(int i=1; i<paperCount.length; i++) {
			sum += paperCount[i]*((i)*(i));
		}
		return sum;
	}
	public static int calcPaperSum() {
		int sum =0;
		for(int i=1; i<paperCount.length; i++) {
			sum += paperCount[i];
		}
		return sum;
	}
	public static class ColoredPaper implements Comparable<ColoredPaper>{
		int num, arr[];
		
		public ColoredPaper(int n, int [] a) {
			this.num = n;
			this.arr = a;
		}
		@Override
		public int compareTo(ColoredPaper p) {
			if(this.num > p.num) {
				return 1;
			}else if(this.num < p.num) {
				return -1;
			}else
				return 0;
		}
	}
	
}

