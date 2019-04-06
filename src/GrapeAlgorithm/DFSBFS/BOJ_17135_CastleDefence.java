import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_17135_CastleDefence {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static ArrayList<Point> originalLocation, archerList;
	public static Point[] enemyLocation;
	public static boolean visited[], removed[];
	public static int N,M,D,EN,removeMax;
	public static void main(String args[]) throws IOException{
		initSetting();
		disposeArcher_DFS(0,0);
		System.out.println(removeMax);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		removeMax = -1;
		originalLocation = new ArrayList<Point>();
		archerList = new ArrayList<Point>();
		visited = new boolean[M];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				int tmp = Integer.parseInt(st.nextToken());
				if(tmp == 1) {
					originalLocation.add(new Point(i,j));
				}
			}
		}
		EN = originalLocation.size();
		removed = new boolean[EN];
	}
	public static void deepCopy() {
		Point[] tmp = new Point[EN];
		for(int i=0; i<EN; i++) {
			Point t = originalLocation.get(i);
			tmp[i] = new Point(t.row,t.col);
		}
		enemyLocation = tmp;
	}
	public static int removeEnemy() {
		int aliveMan = EN, count = 0;
		
		boolean[] localRemoved;
		while(aliveMan > 0) {
			int removeCount = 0;
			localRemoved = new boolean[EN];
			for(int i=0; i<archerList.size(); i++) {
				Point start = archerList.get(i);
				int index, minDist, minCol;
				minDist = 987654321;
				minCol = 987654321;
				index = -1;
				for(int j=0; j<EN; j++) {
					int dist;
					if(removed[j] == true)
						continue;
					dist = calcDistance(start,enemyLocation[j]);
					if(dist > D)
						continue;
					
					if(minDist > dist) {
						minDist = dist;
						minCol = enemyLocation[j].col;
						index = j;
					}else if(minDist == dist) {
						if(minCol > enemyLocation[j].col) {
							minCol = enemyLocation[j].col;
							index = j;
						}
					}
				}
				if(index != -1) {
					localRemoved[index] = true;
				}
			}
			for(int i=0; i<EN; i++) {
				if(removed[i] == true)
					continue;
				if(localRemoved[i] == true) {
					removed[i] = true;
					count++;
					aliveMan--;
				}
			}
			removeCount = moveEnemy();
			aliveMan -= removeCount;
		}
		return count;
	}
	public static int moveEnemy() {
		int removeCount = 0;
		for(int i=0; i<EN; i++) {
			if(removed[i] == true)
				continue;
			
			enemyLocation[i].row++;
			
			if(enemyLocation[i].row >= N) {
				removed[i] = true;
				removeCount++;
			}
		}
		return removeCount;
	}
	public static void disposeArcher_DFS(int len, int node) {
		if(len == 3) {
			int count;
			deepCopy();
			Arrays.fill(removed, false);
			count = removeEnemy();
			
			if(count > removeMax) {
				removeMax = count;
			}
		}else {
			for(int i=node; i<M; i++) {
				if(visited[i] == true)
					continue;
				visited[i] = true;
				archerList.add(new Point(N,i));
				disposeArcher_DFS(len+1,i+1);
				archerList.remove(archerList.size()-1);
				visited[i] = false;
			}
		}
	}
	public static int calcDistance(Point a, Point b) {
		int rowgap, colgap;
		rowgap = Math.abs(a.row-b.row);
		colgap = Math.abs(a.col-b.col);
		return (rowgap+colgap);
	}
	public static class Point{
		int row, col;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}
}
