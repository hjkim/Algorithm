package goBOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 사과를 먹으면 뱀이 길어진다.
 * 2. 뱀이 벽 또는 자신과 부딪히면 게임이 끝난다.
 * 3. 게임은 N X N 보드 위에서 직행된다.
 * 4. 뱀은 맨위 맨좌측에 위치한다.
 * 5. 뱀의 길이는 1이다.
 * 6. 뱀은 처음에 오른쪽을 향한다.
 * 7. 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.'
 * 8. 이동한 칸에 사과가 있다면, 사과를 없애고 꼬리는 움직이지 않는다.(늘어남)
 * 9. 이동한 칸에 사과가 없다면, 몸길이르 줄여 꼬리가 위치한 칸으르 비운다.(그대로)
 * 
 * [조건]
 * 1. 보드 크기 N(2 <= N <= 100)
 * 2. 사과 개수 K(0 <= K <= 100)
 * 3. 1행1열에는 사과가 없다.
 * 4. 뱀 방향 변환 횟수 L(1 <= L <= 100)
 * 5. 방향 전환 시간 X(1 <= x <= 10000)
 * 6. 왼쪽 L, 오른쪽 D
 */
public class BOJ_3190_Snake {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int map[][], N, K, L;
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static ArrayList<Point> snakePoint;
	public static ArrayList<Action> actionList;
	public static void main(String args[]) throws IOException{
		int finishTime = -1;
		initSetting();
		finishTime = move();
		System.out.println(finishTime);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N+1][N+1];
		K = Integer.parseInt(br.readLine());
		snakePoint = new ArrayList<Point>();
		
		for(int i=0; i<K; i++) {
			int row, col;
			st = new StringTokenizer(br.readLine());
			row = Integer.parseInt(st.nextToken());
			col = Integer.parseInt(st.nextToken());
			map[row][col] = 1;
		}
		
		L = Integer.parseInt(br.readLine());
		actionList = new ArrayList<Action>();
		
		for(int i=0; i<L; i++) {
			int time;
			char dir;
			st = new StringTokenizer(br.readLine());
			time = Integer.parseInt(st.nextToken());
			dir = st.nextToken().charAt(0);
			actionList.add(new Action(time,dir));
		}
		
	}
	public static int move() {
		Point headPoint = new Point(1,1);
		snakePoint.add(new Point(headPoint.row, headPoint.col));
		map[headPoint.row][headPoint.col] = 9;
		int time=0, direction =1;
		while(true) {
			headPoint.row += drow[direction];
			headPoint.col += dcol[direction];
			time++;
			if(headPoint.row <= 0 || headPoint.row > N || headPoint.col <= 0 || headPoint.col > N)
				break;
			if(map[headPoint.row][headPoint.col] == 9)
				break;
			
			
			
			if(map[headPoint.row][headPoint.col] != 1) {
				map[snakePoint.get(0).row][snakePoint.get(0).col] = 0;
				snakePoint.remove(0);
			}
			
			snakePoint.add(new Point(headPoint.row, headPoint.col));
			map[headPoint.row][headPoint.col] = 9;
			
			if(!actionList.isEmpty() && (time == actionList.get(0).time)) {
				char leftright = actionList.get(0).dir;
				if(leftright == 'L') {
					direction = (direction+3)%4;
				}else if(leftright == 'D') {
					direction =  (direction+1)%4;
				}
				actionList.remove(0);
			}
			
		}
		return time;
	}
	static class Point{
		int row, col;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}
	
	static class Action{
		int time;
		char dir;
		public Action(int t, char d) {
			this.time = t;
			this.dir = d;
		}
	}
}
