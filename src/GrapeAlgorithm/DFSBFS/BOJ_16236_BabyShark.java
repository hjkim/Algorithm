import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [����]
 * 1. N X N ũ���� ������ ����� M������ �Ʊ� ��� 1 ������ �ִ�.
 * 2. �� ĭ���� ����Ⱑ �ִ� 1���� �����Ѵ�.
 * 3. �Ʊ� ��� ������ ��� ũ�⸦ ������ �ִ�.
 * 4. ũ��� ��� �ڿ����̸� ó�� �Ʊ� ����� ũ��� 2�̴�.
 * 5. �Ʊ� ���� 1�ʿ� �����¿�� ������ �� ĭ�� �̵��Ѵ�.
 * 6. �Ʊ� ���� �ڽź��� ū ����Ⱑ �ִ� ĭ�� ������ �� ����.
 * 7. ��, ũ�Ⱑ ���� ����Ⱑ �ִ� ĭ�� ������ �� �ִ�.
 * 8. �Ʊ� ���� �ڽź��� ���� ����⸸ ���� �� �ִ�.
 * 9. �Ʊ� ��� �̵� ���
 * 9.1. �� �̻� ���� �� �ִ� ����Ⱑ ���ٸ� ��.
 * 9.2. ���� �� �ִ� ����Ⱑ 1�������, �� ����⸦ ������ ����.
 * 9.3. ���� �� �ִ� ����Ⱑ 1�������� ���ٸ�, �Ÿ��� ���� ����� ����⸦ ������ ����.
 * 9.4. �Ÿ��� ����� ����Ⱑ ���ٸ�, ���� ���� �ִ� �����, �׷��� ����Ⱑ �����������, ���� ���ʿ� �ִ� ����⸦ �Դ´�.
 * 10. ����⸦ �Դµ� �ɸ��� �ð��� ����. ��, ���� ����Ⱑ �ִ� ĭ���� �̵��� ���ÿ� ����⸦ �Դ´�.
 * 
 * [����]
 * 1. ������ ũ�� N(2 <= N <= 20)
 * 2. N���� �ٿ� ������ ���°� �־�����.
 * 3. ������ ���´� 0,1,2,3,4,5,6,9�� �̷���� �ִ�.
 * 3.1. 0: �� ĭ
 * 3.2. 1,2,3,4,5,6, : ĭ�� �ִ� ������� ũ��.
 * 3.3. 9 : �Ʊ� ����� ��ġ.
 * 
 * [Ǯ��]
 * 1. ����⸦ ã�� ������ ���� ��ü BFS�� ���� ���� �� �ִ� ����⸦ list�� ��´�.
 * 2. BFS �� ���尡 ������ list�� Ȯ���Ѵ�.
 * 3. list�� �� ������ ��� ������ �� ����⸦ ������ �̵��Ѵ�.
 * 4. list�� ���� ������ ��� ������ ���������� Ž���Ѵ�.
 * 5. BFS Level�� ���� ���� �����, level�� ���ٸ� ���� row�� ���� �����, row�� ���ٸ� ���� col�� ���� ����⸦ ã�� �Դ´�.
 * 6. �Ʊ� ����� exp, sharkSize�� ���������� �����Ѵ�.
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
