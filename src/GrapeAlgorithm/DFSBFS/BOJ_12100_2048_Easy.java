import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_12100_2048_Easy {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int map[][], N, max;
	public static void main(String args[]) throws IOException{
		initSetting();
		startGame_DFS(0);
		System.out.println(max);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		max = -1;
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
	}
	public static void startGame_DFS(int len) {
		if(len == 5) {
			int value = findBiggestNumber();

			if(value > max) {
				max = value;
			
			}
		}else {
			int localMap[][] = copyGlobalMap();
			for(int d=0; d<4; d++) {
				
				switch(d) {
				case 0 : actGravity_North();startGame_DFS(len+1);restoreGlobalMap(localMap);break;
				case 1 : actGravity_East();startGame_DFS(len+1);restoreGlobalMap(localMap);break;
				case 2 : actGravity_South();startGame_DFS(len+1);restoreGlobalMap(localMap);break;
				case 3 : actGravity_West();startGame_DFS(len+1);break;
				}

			}
		}
	}
	public static int[][] copyGlobalMap() {
		int tmp[][]= new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				tmp[i][j] = map[i][j];
			}
		}
		return tmp;
	}
	public static void restoreGlobalMap(int localMap[][]) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = localMap[i][j];
			}
		}
	}

	public static void actGravity_North() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int j=0; j<N; j++) {
			for(int i=0; i<N; i++) {
				if(map[i][j] == 0)
					continue;
				list.add(map[i][j]);
				map[i][j] = 0;
			}
			
			list = calcWindowSum(list);
			
			for(int i=0; i<list.size();i++) {
				map[i][j] = list.get(i);
			}
			list.clear();
		}
	}
	public static void actGravity_East() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<N; i++) {
			for(int j=N-1; j>=0; j--) {
				if(map[i][j] == 0)
					continue;
				list.add(map[i][j]);
				map[i][j] = 0;
			}
			list = calcWindowSum(list);
			
			for(int j=N-1,x=0; x<list.size();j--,x++) {
	
				map[i][j] = list.get(x);
			}
			list.clear();
		}
	}
	public static void actGravity_South() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int j=0; j<N; j++) {
			for(int i=N-1; i>=0; i--) {
				if(map[i][j] == 0)
					continue;
				list.add(map[i][j]);
				map[i][j] = 0;
			}
			list = calcWindowSum(list);
			
			for(int i=N-1,x=0; x<list.size();i--,x++) {
				map[i][j] = list.get(x);
			}
			list.clear();
		}
	}
	
	public static void actGravity_West() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] == 0)
					continue;
				list.add(map[i][j]);
				map[i][j] = 0;
			}
			list = calcWindowSum(list);
			
			for(int j=0; j<list.size();j++) {
				map[i][j] = list.get(j);
			}
			list.clear();
		}
	}
	public static ArrayList<Integer> calcWindowSum(ArrayList<Integer> list) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		int left=0, right=1;
		int valueA, valueB;
		
			while(right < list.size()) {
				valueA = list.get(left);
				valueB = list.get(right);
				
				if(valueA == valueB) {	
					tmp.add(list.get(left)*2);
					list.remove(left);
					list.remove(left);
				}else {
					tmp.add(list.get(left));
					list.remove(left);
				}
			}
			while(left < list.size()) {
				tmp.add(list.get(left));
				list.remove(left);
			}
			list.clear();
			list = tmp;
		
		return list;
		
	}
	public static int findBiggestNumber() {
		int value = -1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] > value) {
					value = map[i][j];
				}
			}
		}
		return value;
	}
}

