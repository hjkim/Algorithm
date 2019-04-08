import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_16234_PopulationMovement {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N,L,R,map[][];
	public static int unionMap[][];
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};

	public static void main(String args[]) throws IOException{
		int count = 0;
		initSetting();
		while(true) {
			int value = findUnion();
			if(value == (N*N))
				break;
			else {
				count++;
			}
		}
		System.out.println(count);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		unionMap = new int[N][N];
		
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	public static int findUnion() {
		int num = 1;

		initUnionMap();
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(unionMap[i][j] == 0) {
					//unionList = new ArrayList<Point>();
					findCandidateCountry_BFS(num,i,j);
					num++;
				}
			}
		}
		return num-1;
	}
	public static void initUnionMap() {
		for(int i=0; i<N; i++) {
			Arrays.fill(unionMap[i], 0);
		}
	}
	public static void findCandidateCountry_BFS(int num, int row, int col) {
		Queue<Point> queue = new LinkedList<Point>();
		ArrayList<Point> unionList = new ArrayList<Point>();
		Point startPoint = new Point(row,col);
		unionMap[startPoint.row][startPoint.col]= num;
		unionList.add(startPoint);
		queue.add(startPoint); 
		int populationSum = map[startPoint.row][startPoint.col], numSum=1, value=0;
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
	
			int cddtrow, cddtcol;
			for(int d=0; d<4; d++) {
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				int gap;
				if(cddtrow < 0 || cddtrow >= N || cddtcol < 0 || cddtcol >= N)
					continue;
				if(unionMap[cddtrow][cddtcol] > 0)
					continue;
				gap = Math.abs(map[cddtrow][cddtcol] - map[startPoint.row][startPoint.col]);
				if(gap < L || gap > R)
					continue;
				
				unionMap[cddtrow][cddtcol] = num;
				populationSum += map[cddtrow][cddtcol];
				numSum += 1;
				unionList.add(new Point(cddtrow,cddtcol));
				
				Point fresh = new Point(cddtrow,cddtcol);
				queue.add(fresh);
				
			}
		}
		if(numSum != 0) {
			value = populationSum/numSum;
			for(int i=0; i<unionList.size(); i++) {
				int r = unionList.get(i).row;
				int c = unionList.get(i).col;
				map[r][c] = value;
			}
		}
	}
	public static class Point{
		int row, col;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}
}
