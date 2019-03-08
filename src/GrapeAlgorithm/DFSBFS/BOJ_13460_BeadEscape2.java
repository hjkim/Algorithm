import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 보드 세로N, 가로 M
 * 2. 보드에는 구멍이 하나 있다.
 * 3. 빨간 구슬과 파란 구슬을 하나 씩 넣는다.
 * 4. 빨간 구슬만 탈출 시킨다. 파란 구슬을 탈출시키면 실패다.
 * 5. 상하좌우로 굴릴 수 있다.
 * 6. 구슬들은 동시에 움직인다.
 * 7. 구슬드은 같은 칸에 존재할 수 없다.
 * 8. 기울이는 동작을 그만하는 것은 더 이상 구슬이 움직이지 않을 때까지이다.(중요)
 * 
 * [조건]
 * 1. 세로, 가로를 의미하는두 정수 N, M(3 <= N,M <= 10)
 * 2. 문자열은 . # O R B 로 이루어져 있다.
 * 3. '.'은 빈 칸.
 * 4. '#'은 벽.
 * 5. 'O'는 구멍.
 * 6. 'R'은 빨간 구슬 위치.
 * 7. 'B'는 파란 구슬 위치.
 * 8. 10번 이하로 움직여서 빨간 구슬을 탈출시킬 수 없으면 -1 출력.
 * 
 * [주의]
 * 1. 구슬이 한 직선 상에서 겹치는 경우.
 * 2. 실패시 continue.
 * 
 * 주의해야할 부분이 무엇인지 파악할 것.
 */

public class BOJ_13460_BeadEscape2 {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int map[][], N, M, min;
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static Point nodeR, nodeB;
	public static void main(String args[]) throws IOException {
	
		initSetting();	
		actGravity_DFS(1,nodeR,nodeB,10,0);
		
		if(min == 987654321) {
			System.out.println(-1);
		}else {
			System.out.println(min);
		}
	}

	public static void initSetting() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
	
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		min = 987654321;
		map = new int[N][M];

		for(int i=0; i<N; i++) {
			String str = br.readLine();
			for(int j=0; j<M; j++) {
				if(str.charAt(j) == '#') {
					map[i][j] = 9;
				}else if(str.charAt(j) == '.') {
					continue;
				}else if(str.charAt(j) == 'R') {
					nodeR = new Point(i,j);
				}else if(str.charAt(j) == 'B') {
					nodeB = new Point(i,j);
				}else if(str.charAt(j) == 'O') {
					map[i][j] = -9;
				}
			}
		}
	}
	// 중복을 허용하는 DFS
	public static void actGravity_DFS(int len, Point nodeA, Point nodeB, int goal, int prevDir) {
		
		if(len >= min) {
			return;
		}
		if(len > goal) {
			return;
		}
		for(int d=0; d<4; d++) {
			if(prevDir == d && len != 1)
				continue;

				//방향 왼쪽, 오른쪽일때 조심
				int tmpRow, tmpCol;
				boolean overlap = false,escape = false,fail = false;
				Point freshA, freshB;
				tmpRow = nodeA.row;tmpCol = nodeA.col;
				
				while(true) {
					
					tmpRow += drow[d];
					tmpCol += dcol[d];
					
					//구슬 끼리 겹칠 때 착각했음.
					if(tmpRow == nodeB.row && tmpCol == nodeB.col) {
						overlap = true;
					}else if(map[tmpRow][tmpCol] == -9) {
						escape = true;
						break;
					}else if(map[tmpRow][tmpCol] == 9) {
						tmpRow -= drow[d];
						tmpCol -= dcol[d];
						if(overlap == true) {
							tmpRow -= drow[d];
							tmpCol -= dcol[d];
						}
						break;
					}
				}
				freshA = new Point(tmpRow,tmpCol);
				
				tmpRow = nodeB.row;tmpCol = nodeB.col;
				
				while(true) {
					tmpRow += drow[d];
					tmpCol += dcol[d];
					
					if(map[tmpRow][tmpCol] == -9) {
						fail = true;
						break;
					}else if(map[tmpRow][tmpCol] == 9) {
						tmpRow -= drow[d];
						tmpCol -= dcol[d];
						
						if(tmpRow == freshA.row && tmpCol == freshA.col) {
							tmpRow -= drow[d];
							tmpCol -= dcol[d];
						}
						break;
					}
				}
				
				freshB = new Point(tmpRow, tmpCol);
				
				if(escape == true && fail == false) {
					if(len < min) {
						min = len;
					}
					break;
				}
				if(fail == true) {
					continue;
				}
					
				actGravity_DFS(len+1, freshA, freshB, goal,d);
		}
	}
	
	static class Point{
		int row, col;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}
	
}
