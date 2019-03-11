import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * [����]
 * 1. �� ���� ũ��� N X N ������.
 * 2. �� ĭ�� �� ĭ�̰ų� ���̴�.
 * 3. �������� �ű�� �Ѵ�. �������� -- or | or \ ���¸� ������.
 * 4. -- �� | �� ��ĭ�� �����Ѵ�. \�� 4ĭ�� �����Ѵ�.
 * 5. --�� �����̳� ���������θ� �̵���ų �� �ִ�.
 * 6. |�� �����̳� ���������θ� �̵���ų �� �ִ�.
 * 7. \�� �����̳� ����, ���������θ� �̵���ų �� �ִ�.
 * 5. ���� ó�� �������� (1,1)�� (1,2)�� �����ϰ� ������ ������ �����̴�.
 * 6. ������ ���� ���� (N,N)�� �̵���Ű�� ����� ������ ���غ���.
 * 
 * [����]
 * 1. ���� ũ�� N(3 <= N <= 16)
 * 2. �� ĭ�� 0, ���� 1�� �־�����. (1,1)�� (1,2)�� �׻� �� ĭ�̴�.
 * 3. �̵���ų �� ���� ��쿡�� 0�� ����Ѵ�.
 * 
 * [Ǯ��]
 * 1. DFS�� Ǯ��.
 * 2. ������ ����, ����, �밢��. (0�� ����, 1�� ����, 2�� ������)
 * 3. ���� count�� �ΰ� (N,N)�� ������ ������ count����.
 * 4. count == 0 �̸� 0 ���.
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
