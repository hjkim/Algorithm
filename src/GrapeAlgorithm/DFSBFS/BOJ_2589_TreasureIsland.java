import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 지도의 각 칸은 육지(L) 또는 바다(W)로 구분된다.
 * 2. 이동은 상하좌우로 이웃한 육지로만 가능하다.
 * 3. 한칸 이동하는데 한 시간 걸린다.
 * 4. 보물이 묻혀 있는 두 곳 간의 최단 거리로 이동하는 시간을 구하라.
 * 
 * [조건]
 * 1. 지도 크기 (Width, Height <= 50)
 */
public class BOJ_2589_TreasureIsland {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int Width,Height,map[][];
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static boolean visited[][];
	public static ArrayList<Point> landList;
	
	public static void main(String args[]) throws IOException{
		int maxtime=-1;
		initSetting();
		
		for(int i=0; i<landList.size(); i++) {
			int time;
			initBooleanArray();
			Point startPoint = new Point(landList.get(i).row, landList.get(i).col);
			time = findShortestDistance_BFS(startPoint);
			if(time > maxtime)
				maxtime = time;
		}
		System.out.println(maxtime);
	}
	public static void initBooleanArray() {
		for(int j=0; j<Height; j++) {
			Arrays.fill(visited[j], false);
		}
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		Height = Integer.parseInt(st.nextToken());
		Width = Integer.parseInt(st.nextToken());
		map = new int[Height][Width];
		landList = new ArrayList<Point>();
		visited = new boolean[Height][Width];
		
		for(int i=0; i<Height; i++) {
			String str = br.readLine();
			for(int j=0; j<Width; j++) {
				if(str.charAt(j) == 'W') {
					map[i][j] = 1;
				}else {
					landList.add(new Point(i,j));
				}
			}
		}
		
		
	}
	
	public static int findShortestDistance_BFS(Point startPoint) {
		Queue<Point> queue = new LinkedList<Point>();
		queue.add(startPoint);
		visited[startPoint.row][startPoint.col] = true;
		int cddtrow, cddtcol;
		int maxtime = -1;
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
			for(int d=0; d<4; d++) {
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				if(cddtrow < 0 || cddtrow >= Height || cddtcol < 0 || cddtcol >= Width)
					continue;
				if(map[cddtrow][cddtcol] == 1|| visited[cddtrow][cddtcol] == true)
					continue;
				visited[cddtrow][cddtcol] = true;
				Point fresh = new Point(cddtrow,cddtcol);
				fresh.time = startPoint.time + 1;
				queue.add(fresh);
				
				if(maxtime < fresh.time) {
					maxtime = fresh.time;
				}
			}
		}
		return maxtime;
	}
	
	static class Point{
		int row, col, time;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
			this.time = 0;
		}
	}
}
