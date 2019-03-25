import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * [문제]
 * 1. 상범 빌딩에서 탈출하려고 한다.
 * 2. 상범 빌딩은 각 변의 길이가 1인 정육면체로 이루어져있다.
 * 3. 각 정육면체는 금으로 이루져 있어 지나갈 수 없거나, 비어서 지나갈 수 있다.
 * 4. 각 칸에서 인접한 6개의 칸(동,서,남,북,상,하)으로 1분의 시간을 소요해 이동할 ㅅ 윘다.
 * 5. 즉, 대각선으로 이동하는 것은 불가능하다.
 * 6. 상범빌딩의 바깥면도 모두 금으로 막혀있어 출구를 통해서만 탈출할 수 있다.
 * 7. 상범 빌딩을 탈출할 수 있는가? 할 수 있다면 얼마나 걸리겠는가?
 * 
 * [조건]
 * 1. 입력은 여러개의 테스트 케이스로 이루어진다.
 * 2. 각 테스트 케이스는 세 개의 정수 L, R, C로 시작한다.
 * 2.1 빌딩의 층 수 L(1 <= L <= 30)
 * 2.2 R(1 <= R <= 30)과 C(1 <= C <= 30)는 상범 빌딩의 한 층의 행과 열의 개수.
 * 3. 금으로 막혀 지나갈 수 없는 칸은 '#'
 * 4. 비어있는 칸 ','
 * 5. 시점 'S'
 * 6. 출구 'E'
 * 
 * [풀이]
 * 1. 3중 map을 이용한 BFS
 * 
 */
public class BOJ_6593_SangBumBuilding {
	public static Scanner sc;
	public static int L,R,C;
	public static int drow[] = {-1,0,1,0,0,0};
	public static int dcol[] = {0,1,0,-1,0,0};
	public static int dlayer[] = {0,0,0,0,-1,1};
	public static char map[][][];
	public static boolean visited[][][];
	public static Point sp;

	public static void main(String args[]) throws IOException{
		sc = new Scanner(System.in);
		boolean finish= false;
		
		while(true) {
			int value = -1;
			finish = initSetting();
			if(finish == true)
				break;
			else {
				value = findWayOut_BFS(sp);
				if(value != -1) {
					System.out.printf("Escaped in %d minute(s).\n",value);
				}else {
					System.out.println("Trapped!");
				}
			}
			
		}
		
	}
	public static boolean initSetting() {
		L = sc.nextInt();
		R = sc.nextInt();
		C = sc.nextInt();
		boolean finish = false;
		if(L == 0 && R == 0 && C == 0)
			finish = true;
		else {
			map = new char[L][R][C];
			visited = new boolean[L][R][C];
			for(int i=0; i<L; i++) {
				String str;
				if(i == 0)
					str = sc.nextLine();
				for(int j=0; j<R; j++) {
					str = sc.nextLine();
					for(int k =0; k<C; k++) {
						map[i][j][k] = str.charAt(k);
						if(map[i][j][k] == 'S') {
							sp = new Point(i,j,k);
						}
					}
				}
				str = sc.nextLine();
			}
		}
		return finish;
		
		
	}
	
	public static int findWayOut_BFS(Point startPoint) {
		Queue<Point> queue = new Queue<Point>();
		queue.add(startPoint);
		int cddtlayer, cddtrow, cddtcol, arriveTime=-1;
		boolean arrived = false;
		while(!queue.isEmpty() && arrived == false) {
			startPoint = queue.remove();
			for(int d=0; d<6; d++) {
				cddtlayer = startPoint.layer + dlayer[d];
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				
				if(cddtlayer < 0 || cddtlayer >= L)
					continue;
				if(cddtrow < 0 || cddtrow >= R)
					continue;
				if(cddtcol < 0 || cddtcol >= C)
					continue;
				if(map[cddtlayer][cddtrow][cddtcol] == '#')
					continue;
				if(visited[cddtlayer][cddtrow][cddtcol] == true)
					continue;
				
				visited[cddtlayer][cddtrow][cddtcol] = true;
				
				Point fresh = new Point(cddtlayer,cddtrow,cddtcol);
				fresh.time = startPoint.time + 1;
				queue.add(fresh);
				
				if(map[fresh.layer][fresh.row][fresh.col] == 'E') {
					arrived = true;
					arriveTime = fresh.time;
					break;
				}
			}
		}
		
		if(arrived == true) {
			return arriveTime;
		}else {
			return -1;
		}
	}
	
	
	public static class Point{
		int layer, row, col, time;
		public Point(int l, int r, int c) {
			this.layer = l;
			this.row = r;
			this.col = c;
			this.time = 0;
		}
	}
	
	public static class Queue<T> {
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
			if(first != null) {
				return false;
			}else {
				return true;
			}
		}
	}
}
