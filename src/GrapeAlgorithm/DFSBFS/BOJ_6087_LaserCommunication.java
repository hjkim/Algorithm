import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_6087_LaserCommunication {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static char map[][];
	public static int N,M,turnValueMap[][];
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static boolean visited[][];
	public static Point laserSpot[];
	public static void main(String args[]) throws IOException{
		initSetting();
		findAnotherLaserSpot(laserSpot[0]);
		System.out.println(turnValueMap[laserSpot[1].row][laserSpot[1].col]);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		laserSpot = new Point[2];
		map = new char[N][M];
		visited = new boolean[N][M];
		turnValueMap = new int[N][M];
		int index = 0;
		for(int i=0; i<N; i++) {
			String str = br.readLine();
			for(int j=0; j<M; j++) {
				map[i][j] = str.charAt(j);
				if(map[i][j] == 'C') {
					laserSpot[index] = new Point(i,j);
					index++;
				}
			}
		}
		
	}
	
	public static void findAnotherLaserSpot(Point startPoint) {
		Queue<Point> queue = new LinkedList<Point>();
		queue.add(startPoint);
		
		int cddtrow, cddtcol, cddtturn;
		
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
			
			for(int d=0; d<4; d++) {
				if((d+2)%4 == startPoint.dir)
					continue;
				cddtrow = startPoint.row + drow[d];
				cddtcol = startPoint.col + dcol[d];
				if(startPoint.dir == -1) {
					cddtturn = 0;
				}else if(startPoint.dir == d) {
					cddtturn = startPoint.turn;
				}else {
					cddtturn = startPoint.turn + 1;
				}
				if(cddtrow < 0 || cddtrow >= N)
					continue;
				if(cddtcol < 0 || cddtcol >= M)
					continue;
				if(map[cddtrow][cddtcol] == '*')
					continue;
				
				if(visited[cddtrow][cddtcol] == true && turnValueMap[cddtrow][cddtcol] < cddtturn)
					continue;
				
				if(visited[cddtrow][cddtcol] == false) {
					visited[cddtrow][cddtcol] = true;
					turnValueMap[cddtrow][cddtcol] = cddtturn;	
				}else if(turnValueMap[cddtrow][cddtcol] > cddtturn){
					turnValueMap[cddtrow][cddtcol] = cddtturn;
				}
				
				if(!(cddtrow == laserSpot[1].row && cddtcol == laserSpot[1].col)) {
					Point fresh = new Point(cddtrow, cddtcol);
					fresh.dir = d;
					fresh.turn = cddtturn;
					queue.add(fresh);	
				}
				
			}
		}
	}
	
	
	public static class Point{
		int row, col, dir, turn;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
			this.dir = -1;
			this.turn = 0;
		}
	}
}