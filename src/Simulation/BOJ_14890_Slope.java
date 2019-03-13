import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 크기가 N X N인 지도가 있다.
 * 2. 지도의 각 칸에는 그 곳의 높이가 적혀져 있다.
 * 3. 지도에서 지나갈 수 있는 길이 몇 개인지 알아보려고 한다.
 * 4. 길이란 한 행 또는 한 열 전부를 나타내며, 한쪽 끝에서 다른쪽 끝까지 지나가는 것이다.
 * 4.1. 즉, 길의 총 개수는 2N개 이다.
 * 5. 길을 지나갈 수 있으려면 길에 속한 모든 칸의 높이가 같아야 한다.
 * 6. 또는, 경사로를 놓아서 지나갈 수 있는 길을 만들 수 있다.
 * 7. 경사로는 높이가 항상 1이며, 길이는 L이다. 
 * 7.1. 또한 경사로 개수는 매우 많아 부족할 일이 없다.
 * 7.2. 경사로는 낮은 칸에 놓으며, L개의 연속된 칸에 경사로의 바닥이 모두 접해야 한다.
 * 7.3. 낮은 칸과 높은 칸의 차이는 1이어야 한다.
 * 7.4. 경사로를 노호을 낮은 칸의 높이는 모두 같아야 하고, L개의 칸이 연속되어 있어야 한다.
 * 8. 다음과 같은 경우에는 경사로를 놓을 수 없다.
 * 8.1. 경사로를 놓은 곳에 또 경사로를 놓는 경우.
 * 8.2. 낮은 칸과 높은 칸의 높이 차이가 1이 아닌 경우.
 * 8.3. 낮은 지점의 칸의 높이가 모두 같지 않거나, L개가 연속되지 않는 경우.
 * 8.4. 경사로를 놓다가 범위를 벗어나는 경우.
 * 9. 지도가 주어졌을 때, 지나갈 수 있는 길의 개수를 구하라.
 * 
 * [조건]
 * 1. 지도 크기 N (2 <= N <= 100)
 * 2. 경사로 길이 L (1 <= L <= N)
 * 3. 각 칸의 높이는 10보다 작거나 같은 자연수이다.
 * 
 * [풀이]
 * 1. (0,i), (i,0) 에서 시작. (1 <= i <= N).
 * 2. bLength로 같은 높이로 연속된 좌표의 길이를 관리.
 * 3. (이전높이 - 현재높이)의 절대값이 1보다 크면 break.
 * 4. (이전높이 - 현재높이) == 1인 경우.
 * 4.1. building = true. 건설 중임을 알림.
 * 4.2. L이 1이라 현재좌표 도착과 동시에 건설이 완료되면, bLenth = 0. building = false.
 * 5. (이전높이 - 현재높이) == -1인 경우.
 * 5.1. bLenth >= L이면  이전에 경사로가 건설할 수 있고, 현재 위치에 도달할 수 있다.
 * 6. (이전높이 - 현재높이) == 0인 경우.
 * 6.1. bLenth++
 * 6.2. 경사로 건설 중이면서 bLenth가 L에 도달했으면 bLength = 0, building = false. 건설종료.
 * 7. 벽에 도달했는데 아직 건설중이면 fail = true.
 * 8. 벽에 무사히 도착하면 routeCount 증가.
 * 9. 0은 동쪽, 1은 남쪽.
 * 
 * [주의]
 * 1. L이 1인 경우, 높이가 다른 새로운 위치에 도달함과 동시에 경사로 건설이 완료.
 * 2. 경사로는 겹칠 수 없다.
 * 3. ((이전높이 - 현재높이) == -1 && bLength >= L)인 경우, 경사로 건설 가능.
 */
public class BOJ_14890_Slope {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N, L, map[][],routeCount;
	public static int drow[] = {0,1};
	public static int dcol[] = {1,0};
	public static void main(String args[]) throws IOException{
		initSetting();
		
		for(int i=1; i<=N; i++) {
			checkRoute(i,0);
			checkRoute(i,1);
		}
		System.out.println(routeCount);
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		routeCount = 0;
		map = new int[N+1][N+1];
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	public static void checkRoute(int index, int dir) {
		int cddtrow, cddtcol, prevHeight, bLength;
		boolean fail, building;
		fail = false;     // Construction fail or not.
		building = false; // Construction is in progress or not.
	
		if(dir == 0) {
			cddtrow = index;
			cddtcol = 1;
		}else {
			cddtrow = 1;
			cddtcol = index;
		}
		prevHeight = map[cddtrow][cddtcol];
		bLength = 1;
		for(int i=2; i<=N; i++) {
			int gap;
			cddtrow += drow[dir];
			cddtcol += dcol[dir];
			gap = prevHeight - map[cddtrow][cddtcol]; 
			if(gap == 0) {
				bLength++;
				if(building == true) { // 이전과 높이가 같고 경사로 건설중이면.
					if(bLength == L) { // 경사로 건설 종료이면.
						bLength = 0;
						building = false;
					}
				}
			}else if(gap == 1) { // 이전이 더 높음.
				if(building == true) { // 이전과 높이 차이가 1이고 경사로 건설중이면.
					fail = true;
					break;
				}else {
					prevHeight = map[cddtrow][cddtcol];
					building = true;
					bLength = 1;
					if(bLength >= L) { // 도착과 동시에 경사로 길이를 만족하면 건설 종료.
						building = false;
						bLength = 0;
					}
				}
			}else if(gap == -1) { // 이전이 더 낮음.
				if(building == false && bLength >= L) {
					bLength = 1;
					prevHeight = map[cddtrow][cddtcol];
				}else {
					fail = true;
					break;
				}
			}else {
				fail = true;
				break;
			}
		}
		if(building == true) {
			fail = true;
		}
		
		
		if(fail == false) {
			routeCount++;
			/* 이동 가능한 길 확인.
			if(dir == 0 ) {
				System.out.printf("row index : %d \n",index);
			}else {
				System.out.printf("col index : %d \n",index);
			}
			*/
		}
	}
}
