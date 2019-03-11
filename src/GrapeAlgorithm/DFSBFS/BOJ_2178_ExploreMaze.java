import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. N X M 크기 미로가 있다.
 * 2. 미로 1은 이동할 수 있는 칸을 나타내고, 0은 이동할 수 없는 칸을 나타낸다.
 * 3. (1,1)을 출발하여 (N,M)의 위치로 이동할 때 지나야하는 최소 칸수를 구하라.
 * 4. 한 칸에서 다른 칸으로 이동할 때, 서로 인접한 칸으로만 이동할 수 있다.
 * 5. 칸을 셀 때에는 시작 위치와 도착 위치도 포함한다.
 * 
 * [조건]
 * 1. 두 정수 N,M (2 <= N,M <= 100)
 * 2. 항상 도착할 수 있는 경우만 주어진다.
 * 
 * [풀이]
 * 1. BFS
 */
public class BOJ_2178_ExploreMaze {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N,M,map[][],min;
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static boolean visited[][];
	public static void main(String args[]) throws IOException{
		initSetting();
		findExit_BFS(new Point(1,1));
		System.out.println(min);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N+1][M+1];
		visited = new boolean[N+1][M+1];
		min = 987654321;
		for(int i=1; i<=N; i++) {
			String str = br.readLine();
			for(int j=0; j<M; j++) {
				map[i][j+1] = str.charAt(j) - '0';
			}
		}
	}
	public static void findExit_BFS(Point startPoint) {
		Queue<Point> queue = new LinkedList<Point>();
		queue.add(startPoint);
		int cddtrow, cddtcol;
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
			for(int d=0; d<4; d++) {
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				if(cddtrow < 1 || cddtrow > N || cddtcol < 1 || cddtcol > M)
					continue;
				if(map[cddtrow][cddtcol] == 0)
					continue;
				if(visited[cddtrow][cddtcol] == true)
					continue;
				
				visited[cddtrow][cddtcol] = true;
				Point fresh = new Point(cddtrow,cddtcol);
				fresh.level = startPoint.level + 1;
				queue.add(fresh);
				
				if(fresh.row == N && fresh.col == M) {
					if(fresh.level < min) {
						min = fresh.level;
						break;
					}
				}
			}
		}
	}
	public static class Point{
		int row,col,level;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
			this.level = 1;
		}
	}
}
