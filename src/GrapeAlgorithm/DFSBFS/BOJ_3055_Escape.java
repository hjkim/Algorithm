package goBOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 고슴도치는 비버 굴로 도망가 호수를 피하려 한다.
 * 2. 지도는 R헁 C열로 이루어져 있다.
 * 2.1. '.' : 빈 칸
 * 2.2. '*' : 물이 차 있는 칸.
 * 2.3. 'X' : 돌.
 * 2.4. 'D' : 비버 굴.
 * 2.5. 'S' : 고슴도치 위치.
 * 3. 고슴도치는 현재 있는 칸과 입접한 네 칸 중 하나로 이동할 수 있다(상하좌우).
 * 4. 물도 매 분마다 비어있는 칸으로 확장한다.
 * 5. 물과 고슴도치는 돌을 통과할 수 없다.
 * 6. 고슴도치는 물로 차있는 칸으로 이동할 수 없다.
 * 7. 물도 비버 굴로 이동할 수 없다.
 * 8. 고슴도치는 물이  찰 예정인 칸으로 이동할 수 없다.
 * 9. 지도가 주어졌을 때,
 * 	고슴도치가 안전하게 비버 굴로 이동하기 위해 필요한 최소 시간을 구하라.
 * 
 * [조건]
 * 1. 지도 세로R, 가로C는 자연수. (1 <= R,C <= 50)
 * 
 * [풀이]
 * 1. 고슴도치와 물에 대해 각각 BFS를 수행한다.
 * 2. 각 BFS는 level 1만 수행하도록 한다.
 */
public class BOJ_3055_Escape {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int R,C;
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static char map[][];
	public static boolean visitedW[][], visitedD[][];
	public static Point dochi;
	public static Queue<Point> queueW, queueD;
	public static void main(String args[]) throws IOException{
		int result;
		initSetting();
		result = startEscape();
		
		if(result == -1) {
			System.out.println("KAKTUS");
		}else {
			System.out.println(result);
		}
		
	}
	public static int startEscape() {
		int timevalue = -1;
		while(queueD.isEmpty() == false && timevalue == -1  ) {
			if(queueW.isEmpty() == false) {
				moveWater_BFS();
			}
			
			
			timevalue = moveDochi_BFS();
		}
		return timevalue;
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		map = new char[R][C];
		visitedW = new boolean[R][C];
		visitedD = new boolean[R][C];
		queueW = new LinkedList<Point>();
		queueD = new LinkedList<Point>();
		
		for(int i=0; i<R; i++) {
			String str = br.readLine();
			for(int j=0; j<C; j++) {
				map[i][j] = str.charAt(j);
				if(map[i][j] == 'S') {
					dochi = new Point(i,j);
					map[i][j] = '.';
					queueD.add(dochi);
				}else if(map[i][j] == '*') {
					queueW.add(new Point(i,j));
				}
			}
		}
	}
	
	public static void moveWater_BFS() {
		int cddtrow, cddtcol, limitLevel;
		Point startPoint;
		limitLevel = queueW.peek().level +1;
		
		while(queueW.isEmpty() != true) {
			startPoint = queueW.poll();
			if(startPoint.level == limitLevel) {
				queueW.add(startPoint);
				break;
			}
			
			for(int d=0; d<4; d++) {
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				
				if(cddtrow < 0 || cddtrow >= R || cddtcol < 0 || cddtcol >= C)
					continue;
				if(visitedW[cddtrow][cddtcol] == true)
					continue;
				if(map[cddtrow][cddtcol] == 'D' || map[cddtrow][cddtcol] == 'X')
					continue;
				
				visitedW[cddtrow][cddtcol] = true;
				map[cddtrow][cddtcol] = '*';
				Point fresh = new Point(cddtrow, cddtcol);
				fresh.level = startPoint.level + 1;
				queueW.add(fresh);
			}
		}
		
	}
	
	public static int moveDochi_BFS() {
		int cddtrow, cddtcol, limitLevel, movingTime=-1;
		Point startPoint;
		limitLevel = queueD.peek().level +1;
		
		while(!queueD.isEmpty()) {
			if(queueD.peek().level == limitLevel) {
				break;
			}else {
				startPoint = queueD.poll();
			}
			
			for(int d=0; d<4; d++) {
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				
				if(cddtrow < 0 || cddtrow >= R || cddtcol < 0 || cddtcol >= C)
					continue;
				if(visitedD[cddtrow][cddtcol] == true || map[cddtrow][cddtcol] == 'X')
					continue;
				if(map[cddtrow][cddtcol] == '*')
					continue;
				
				visitedD[cddtrow][cddtcol] = true;
				
				Point fresh = new Point(cddtrow, cddtcol);
				fresh.level = startPoint.level + 1;
				queueD.add(fresh);
				
				if(map[fresh.row][fresh.col] == 'D') {
					movingTime = fresh.level;
					break;
				}
			}
			
		}
		return movingTime;
		
	}
	
	static class Point{
		int row, col, level;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}
}
