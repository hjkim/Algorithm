import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 새 집의 크기는 N X N 격자판.
 * 2. 각 칸은 빈 칸이거나 벽이다.
 * 3. 파이프를 옮기려 한다. 파이프는 -- or | or \ 형태를 가진다.
 * 4. -- 와 | 는 두칸을 차지한다. \는 4칸을 차지한다.
 * 5. --는 동쪽이나 남동쪽으로만 이동시킬 수 있다.
 * 6. |는 남쪽이나 남동쪽으로만 이동시킬 수 있다.
 * 7. \는 동쪽이나 남쪽, 남동쪽으로만 이동시킬 수 있다.
 * 5. 가장 처음 파이프는 (1,1)와 (1,2)를 차지하고 있으며 방향은 가로이다.
 * 6. 파이프 한쪽 끝을 (N,N)로 이동시키는 방법의 개수를 구해보자.
 * 
 * [조건]
 * 1. 집의 크기 N(3 <= N <= 16)
 * 2. 빈 칸은 0, 벽은 1로 주어진다. (1,1)과 (1,2)는 항상 빈 칸이다.
 * 3. 이동시킬 수 없는 경우에는 0을 출력한다.
 * 
 * [풀이]
 * 1. DFS로 풀자.
 * 2. 방향은 가로, 세로, 대각선. (0은 동쪽, 1은 남쪽, 2는 남동쪽)
 * 3. 전역 count를 두고 (N,N)에 도달할 때마다 count증가.
 * 4. count == 0 이면 0 출력.
 */
public class BOJ_17070_MovePipe {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N, map[][],count;
	public static int drow[] = {0,1,1};
	public static int dcol[] = {1,0,1};
	public static void main(String args[]) throws IOException{
			
		initSetting();
		long start = System.currentTimeMillis();
		findRoute_DFS(new Point(1,2),0);
		long finish = System.currentTimeMillis();
		System.out.println(count);
		System.out.println(finish - start);

	}
	
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		count = 0;
		map = new int[N+1][N+1];
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	public static void findRoute_DFS(Point head, int prevDir) {
		
		if(head.row == N && head.col == N) {
			count++;
		}else {
			int cddtrow, cddtcol;
			for(int d=0; d<3; d++) {
				if(prevDir == 0 && d == 1)
					continue;
				else if(prevDir == 1 && d == 0)
					continue;
				
				cddtrow = head.row + drow[d];
				cddtcol = head.col + dcol[d];
				
				if(cddtrow < 1 || cddtrow > N || cddtcol < 1 || cddtcol > N)
					continue;
				if(map[cddtrow][cddtcol] == 1)
					continue;
				if(d == 2 && (map[cddtrow-1][cddtcol] == 1 || map[cddtrow][cddtcol-1] == 1))
					continue;
				
				findRoute_DFS(new Point(cddtrow,cddtcol), d);
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
