import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [����]
 * 1. N X M ũ�� �̷ΰ� �ִ�.
 * 2. �̷� 1�� �̵��� �� �ִ� ĭ�� ��Ÿ����, 0�� �̵��� �� ���� ĭ�� ��Ÿ����.
 * 3. (1,1)�� ����Ͽ� (N,M)�� ��ġ�� �̵��� �� �������ϴ� �ּ� ĭ���� ���϶�.
 * 4. �� ĭ���� �ٸ� ĭ���� �̵��� ��, ���� ������ ĭ���θ� �̵��� �� �ִ�.
 * 5. ĭ�� �� ������ ���� ��ġ�� ���� ��ġ�� �����Ѵ�.
 * 
 * [����]
 * 1. �� ���� N,M (2 <= N,M <= 100)
 * 2. �׻� ������ �� �ִ� ��츸 �־�����.
 * 
 * [Ǯ��]
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
