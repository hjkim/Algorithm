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
 * 1. N X N 크기의 공간에 물고기 M마리와 아기 상어 1 마리가 있다.
 * 2. 한 칸에는 물고기가 최대 1마리 존재한다.
 * 3. 아기 상와 물고기는 모두 크기를 가지고 있다.
 * 4. 크기는 모두 자연수이며 처음 아기 상어의 크기는 2이다.
 * 5. 아기 상어는 1초에 상하좌우로 인접한 한 칸씩 이동한다.
 * 6. 아기 상어는 자신보다 큰 물고기가 있는 칸만 지나갈 수 없다.
 * 7. 즉, 크기가 같은 물고기가 있는 칸은 지나갈 수 있다.
 * 8. 아기 상어는 자신보다 작은 물고기만 먹을 수 있다.
 * 9. 아기 상어 이동 방법
 * 9.1. 더 이상 먹을 수 있는 물고기가 없다면 끝.
 * 9.2. 먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
 * 9.3. 먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
 * 9.4. 거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기를 먹는다.
 * 10. 물고기를 먹는데 걸리는 시간은 없다. 즉, 작은 물고기가 있는 칸으로 이동과 동시에 물고기를 먹는다.
 * 
 * [조건]
 * 1. 공간의 크기 N(2 <= N <= 20)
 * 2. N개의 줄에 공강의 상태가 주어진다.
 * 3. 공간의 상태는 0,1,2,3,4,5,6,9로 이루어져 있다.
 * 3.1. 0: 빈 칸
 * 3.2. 1,2,3,4,5,6, : 칸에 있는 물고기의 크기.
 * 3.3. 9 : 아기 상어의 위치.
 * 
 * [풀이]
 * 1. 물고기를 찾을 때마다 공간 전체 BFS를 돌려 먹을 수 있는 물고기를 list에 담는다.
 * 2. BFS 한 라운드가 끝나면 list를 확인한다.
 * 3. list에 한 마리만 담겨 있으면 그 물고기를 먹으러 이동한다.
 * 4. list에 여러 마리가 담겨 있으면 순차적으로 탐색한다.
 * 5. BFS Level이 가장 작은 물고기, level이 같다면 가장 row가 작은 물고기, row가 같다면 가장 col이 작은 물고기를 찾아 먹는다.
 * 6. 아기 상어의 exp, sharkSize는 전역변수로 관리한다.
 */

public class BOJ_16236_BabyShark {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N, map[][], exp, sharkSize;
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static Point sharkPoint;
	public static boolean visited[][];
	public static void main(String args[]) throws IOException{
		int time=-1;
		initSetting();
		time = haveMealTime();
		System.out.println(time);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		visited = new boolean[N][N];
		exp = 0;
		sharkSize = 2;
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 9) {
					map[i][j] = 0;
					sharkPoint = new Point(i,j);
				}
			}
		}
	}
	public static int haveMealTime() {
		int time;
		Point nextPoint = new Point(sharkPoint.row, sharkPoint.col);
		time = 0;
		while(true) {
			nextPoint = findFish_BFS(nextPoint.row, nextPoint.col);
			
			if(nextPoint.row != -1) {
				exp++;
				if(exp == sharkSize) {
					sharkSize++;
					exp = 0;
				}
				map[nextPoint.row][nextPoint.col] = 0;
			}else {
				break;
			}
			initBooleanArray();
			time += nextPoint.level;
		}
		return time;
	}
	public static Point findFish_BFS(int srow, int scol) {
		int cddtrow, cddtcol;
		Point target;
		Point startPoint = new Point(srow,scol);
		ArrayList<Point> fishList = new ArrayList<Point>();
		Queue<Point> queue = new LinkedList<Point>();
		queue.add(startPoint);
		visited[startPoint.row][startPoint.col] = true;
		
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
			for(int d=0; d<4; d++) {
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				
				if(cddtrow < 0 || cddtrow >= N || cddtcol < 0 || cddtcol >= N)
					continue;
				if(visited[cddtrow][cddtcol] == true)
					continue;
				
				visited[cddtrow][cddtcol] = true;
				
				if(map[cddtrow][cddtcol] <= sharkSize) {
					Point nextPoint = new Point(cddtrow, cddtcol);
					nextPoint.level = startPoint.level + 1;
					queue.add(nextPoint);
					if(map[cddtrow][cddtcol] != 0 && map[cddtrow][cddtcol] < sharkSize) {
						Point cddtFish = new Point(cddtrow,cddtcol);
						cddtFish.level = startPoint.level +1;
						fishList.add(cddtFish);
					}
				}
				
			}
		}
		if(!fishList.isEmpty()) {
			target = findCorrectTarget(fishList);
		}else {
			target = new Point(-1,-1);
		}
		
		return target;
		
	}
	
	public static Point findCorrectTarget(ArrayList<Point> fishList) {
		Point target = fishList.get(0);
		
		if(fishList.size() > 1) {
			for(int i=1; i<fishList.size(); i++) {
				
				if(fishList.get(i).level < target.level) {
					
					target = fishList.get(i);
				
				}else if(fishList.get(i).level == target.level) {
					
					if(fishList.get(i).row < target.row) {
					
						target = fishList.get(i);
					
					}else if(fishList.get(i).row == target.row) {
						
						if(fishList.get(i).col < target.col) {
							
							target = fishList.get(i);
							
						}
					}
				}
			}
		}
		
		return target;
	}
	
	public static void initBooleanArray() {
		for(int j=0; j<N; j++) {
			Arrays.fill(visited[j], false);
		}
	}
	
	static class Point{
		int row, col, level;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
			this.level = 0;
		}
	}
}
