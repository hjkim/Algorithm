import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * [문제]
 * 1. 상근이는 감옥에서 죄수 두 명을 탈옥시켜야 한다.
 * 2. 상근이는 감옥 평면도를 구했고, 여기에는 모든 벽과 문, 죄수의 위치도 나타나 있다.
 * 3. 감옥은 무인 감옥으로 죄수 두 명이 감옥에 있는 유일한 사람이다.
 * 4. 두 죄수를 탈옥시키기 위해서 열어야 하는 문의 개수를 구하라.
 * 
 * [조건]
 * 1. 테스트 케이스 T(T <= 100)
 * 2. 평면도의 높이 h와 너비 w(2 <= h,w <= 100)
 * 3. 평면도 정보는 다음과 같다.
 * 3.1. '.' : 빈 공간
 * 3.2. '*' : 벽
 * 3.3. '#' : 문
 * 3.4. '$' : 죄수의 위치
 * 
 * [풀이]
 * 1. 탈옥 방법은 두 가지라고 정의한다. 편의상 죄수는 A,B로 구분한다.
 * 2. 탈옥 방법은 다음과 같다.
 * 2.1. A가 B를 데리고 탈옥한다. : PLAN 1
 * 2.2. A와 B가 각자 탈옥한다. : PLAN 2
 * 3. A가 B를 데리고 탈옥하는 경우.
 * 3.1. A에서 B까지 이동한다.
 * 3.2. B까지 이동하면서 만난 문은 모두 '.'으로 바꾼다. 물론 문 연 횟수는 관리한다.
 * 3.3. B에서 출구까지 도달하기 위해 BFS를 사용한다.
 * 3.4. 도달할 수 있는 모든 출구 중 가장 open 횟수가 적은 것을 MinOpen에 저장한다.
 * 3.5. 만약, A가 B에 도달할 수 없다면 플랜1은 종료한다.
 * 4. A와 B가 각자 탈옥한다.
 * 4.1. A위치에서 최소 open 출구를 찾는다.
 * 4.2. B위치에서 최소 open 출구를 찾는다.
 * 
 * plan1은 맵을 직접 바꾸기 때문에, plan2를 먼저 시작하기로 한다.
 */
public class BOJ_9376_PrisonEscape {
	public static Scanner sc;
	public static int T,H,W;
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static Point prev[][];
	public static char map[][];
	public static boolean visited[][];
	public static Point prisoner[];
	public static void main(String args[]) {
		sc = new Scanner(System.in);
		T = sc.nextLine().charAt(0) - '0';
		int result=0;
		for(int t=0; t<T; t++) {
			initSetting();
			result = selectEscapeRoute();
			System.out.println(result);
		}
	}
	public static void initSetting() {
		String str[] = sc.nextLine().split(" ");
		H = Integer.parseInt(str[0]);
		W = Integer.parseInt(str[1]);
		map = new char[H][W];
		visited = new boolean[H][W];
		prisoner = new Point[2];
		prev = new Point[H][W];
		int cnt = 0;
		for(int i=0; i<H; i++) {
			String tmp = sc.nextLine();
			for(int j=0; j<W; j++) {
				map[i][j] = tmp.charAt(j);
				if(map[i][j] == '$') {
					prisoner[cnt] = new Point(i,j);
					cnt++;
				}
			}
		}
	}
	public static void initBooleanArray() {
		for(int i=0; i<H; i++) {
			Arrays.fill(visited[i], false);
		}
	}
	
	public static int selectEscapeRoute() {
		int value[] = new int[2], result;
		int openPoint = -1;
		for(int i=0; i<2; i++) {
			value[i] = findWayOut_BFS(new Point(prisoner[i].row,prisoner[i].col));
			initBooleanArray();
		}
		result = value[0] + value[1];
		
		openPoint = findAnotherPrisoner_BFS(prisoner[0],prisoner[1]);
		if(openPoint != -1) {// 데리러 갈 수 있다면!
			int res = 0;
			Point freshStart = new Point(prisoner[0].row, prisoner[0].col);
			freshStart.open = openPoint;
			initBooleanArray();
			res = findWayOut_BFS(freshStart);
			if(res < result)
				result = res;
		}
		return result;
		
	}
	public static int findAnotherPrisoner_BFS(Point pr1, Point pr2) {
		Queue<Point> queue = new Queue<Point>();
		int cddtrow, cddtcol, openPoint = -1;
		Point startPoint;
		boolean arrived = false;
		visited[pr1.row][pr1.col] = true;
		queue.add(pr1);
		
		while(!queue.isEmpty() && arrived == false) {
			startPoint = queue.remove();
			for(int d=0; d<4; d++) {
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				if(cddtrow < 0 || cddtrow >= H || cddtcol < 0 || cddtcol >= W)
					continue;
				if(visited[cddtrow][cddtcol] == true || map[cddtrow][cddtcol] == '*')
					continue;
				visited[cddtrow][cddtcol] = true;
				prev[cddtrow][cddtcol] = new Point(startPoint.row, startPoint.col);
				Point fresh = new Point(cddtrow, cddtcol);
				fresh.open = startPoint.open;
				if(map[fresh.row][fresh.col] == '#') {
					fresh.open++;
					//map[fresh.row][fresh.col]= '.'; 
				}
				if(fresh.row != 0 && fresh.col != 0) {
					queue.add(fresh);
				}
				if(fresh.row == pr2.row && fresh.col == pr2.col) {
					arrived = true;
					openPoint = fresh.open;
					removeGate(fresh,pr1);
					break;
				}
			}
		}
		return openPoint;
	}
	
	public static void removeGate(Point startPoint, Point target) {
		while(startPoint.row != target.row || startPoint.col != target.col) {
			if(map[startPoint.row][startPoint.col] == '#') {
				map[startPoint.row][startPoint.col] = '.';
			}
			startPoint = prev[startPoint.row][startPoint.col];
		}
	}
	
	public static int findWayOut_BFS(Point startPoint) {
		Queue<Point> queue = new Queue<Point>();
		queue.add(startPoint);
		int cddtrow, cddtcol, openMin;
		visited[startPoint.row][startPoint.col] = true;
		openMin = 987654321;
		if(startPoint.row == 0 || startPoint.row == H-1 || startPoint.col == 0 || startPoint.col == W-1) {
			openMin = 0;
		}else {
			while(!queue.isEmpty()) {
				startPoint = queue.remove();
				for(int d=0; d<4; d++) {
					cddtrow = startPoint.row + drow[d];
					cddtcol = startPoint.col + dcol[d];
				
					if(cddtrow < 0 || cddtrow >= H || cddtcol < 0 || cddtcol >= W)
						continue;
					if(visited[cddtrow][cddtcol] == true || map[cddtrow][cddtcol] == '*')
						continue;
					visited[cddtrow][cddtcol] = true;
					
					Point fresh = new Point(cddtrow, cddtcol);
					fresh.open = startPoint.open;
					if(map[fresh.row][fresh.col] == '#') {
						fresh.open++;
					}
					
					if(fresh.row == 0 || fresh.row == H-1 || fresh.col == 0 || fresh.col == W-1) {
						if(fresh.open < openMin)
							openMin = fresh.open;
					}else {
						queue.add(fresh);
					}
				}
			}
		}
		
		return openMin;		
	}
	
	public static class Point{
		int row, col, open;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
			this.open = 0;
		}
	}
	
	public static class Queue<T>{
		class Node<T>{
			private T data;
			private Node<T> next;
			public Node(T data) {
				this.data = data;
			}
		}
		public Node<T> first;
		public Node<T> last;
		
		public void add(T item) {
			Node<T> t = new Node<T>(item);
			
			if(last != null) {
				last.next = t;
			}
			last = t;
			if(first == null) {
				first = last;
			}
		}
		
		public T remove() {
			if(first == null) {
				throw new NoSuchElementException();
			}
			
			T data = first.data;
			first = first.next;
			
			if(first == null) {
				last = null;
			}
			return data;
		}
		
		public T peek() {
			if(first == null) {
				throw new NoSuchElementException();
			}
			return first.data;
		}
		
		public boolean isEmpty() {
			if(first == null) {
				return true;
			}else {
				return false;
			}
		}
	}
	
}
