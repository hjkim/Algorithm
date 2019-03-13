import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * [����]
 * 1. ũ�Ⱑ N X N�� ������ �ִ�.
 * 2. ������ �� ĭ���� �� ���� ���̰� ������ �ִ�.
 * 3. �������� ������ �� �ִ� ���� �� ������ �˾ƺ����� �Ѵ�.
 * 4. ���̶� �� �� �Ǵ� �� �� ���θ� ��Ÿ����, ���� ������ �ٸ��� ������ �������� ���̴�.
 * 4.1. ��, ���� �� ������ 2N�� �̴�.
 * 5. ���� ������ �� �������� �濡 ���� ��� ĭ�� ���̰� ���ƾ� �Ѵ�.
 * 6. �Ǵ�, ���θ� ���Ƽ� ������ �� �ִ� ���� ���� �� �ִ�.
 * 7. ���δ� ���̰� �׻� 1�̸�, ���̴� L�̴�. 
 * 7.1. ���� ���� ������ �ſ� ���� ������ ���� ����.
 * 7.2. ���δ� ���� ĭ�� ������, L���� ���ӵ� ĭ�� ������ �ٴ��� ��� ���ؾ� �Ѵ�.
 * 7.3. ���� ĭ�� ���� ĭ�� ���̴� 1�̾�� �Ѵ�.
 * 7.4. ���θ� ��ȣ�� ���� ĭ�� ���̴� ��� ���ƾ� �ϰ�, L���� ĭ�� ���ӵǾ� �־�� �Ѵ�.
 * 8. ������ ���� ��쿡�� ���θ� ���� �� ����.
 * 8.1. ���θ� ���� ���� �� ���θ� ���� ���.
 * 8.2. ���� ĭ�� ���� ĭ�� ���� ���̰� 1�� �ƴ� ���.
 * 8.3. ���� ������ ĭ�� ���̰� ��� ���� �ʰų�, L���� ���ӵ��� �ʴ� ���.
 * 8.4. ���θ� ���ٰ� ������ ����� ���.
 * 9. ������ �־����� ��, ������ �� �ִ� ���� ������ ���϶�.
 * 
 * [����]
 * 1. ���� ũ�� N (2 <= N <= 100)
 * 2. ���� ���� L (1 <= L <= N)
 * 3. �� ĭ�� ���̴� 10���� �۰ų� ���� �ڿ����̴�.
 * 
 * [Ǯ��]
 * 1. (0,i), (i,0) ���� ����. (1 <= i <= N).
 * 2. bLength�� ���� ���̷� ���ӵ� ��ǥ�� ���̸� ����.
 * 3. (�������� - �������)�� ���밪�� 1���� ũ�� break.
 * 4. (�������� - �������) == 1�� ���.
 * 4.1. building = true. �Ǽ� ������ �˸�.
 * 4.2. L�� 1�̶� ������ǥ ������ ���ÿ� �Ǽ��� �Ϸ�Ǹ�, bLenth = 0. building = false.
 * 5. (�������� - �������) == -1�� ���.
 * 5.1. bLenth >= L�̸�  ������ ���ΰ� �Ǽ��� �� �ְ�, ���� ��ġ�� ������ �� �ִ�.
 * 6. (�������� - �������) == 0�� ���.
 * 6.1. bLenth++
 * 6.2. ���� �Ǽ� ���̸鼭 bLenth�� L�� ���������� bLength = 0, building = false. �Ǽ�����.
 * 7. ���� �����ߴµ� ���� �Ǽ����̸� fail = true.
 * 8. ���� ������ �����ϸ� routeCount ����.
 * 9. 0�� ����, 1�� ����.
 * 
 * [����]
 * 1. L�� 1�� ���, ���̰� �ٸ� ���ο� ��ġ�� �����԰� ���ÿ� ���� �Ǽ��� �Ϸ�.
 * 2. ���δ� ��ĥ �� ����.
 * 3. ((�������� - �������) == -1 && bLength >= L)�� ���, ���� �Ǽ� ����.
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
				if(building == true) { // ������ ���̰� ���� ���� �Ǽ����̸�.
					if(bLength == L) { // ���� �Ǽ� �����̸�.
						bLength = 0;
						building = false;
					}
				}
			}else if(gap == 1) { // ������ �� ����.
				if(building == true) { // ������ ���� ���̰� 1�̰� ���� �Ǽ����̸�.
					fail = true;
					break;
				}else {
					prevHeight = map[cddtrow][cddtcol];
					building = true;
					bLength = 1;
					if(bLength >= L) { // ������ ���ÿ� ���� ���̸� �����ϸ� �Ǽ� ����.
						building = false;
						bLength = 0;
					}
				}
			}else if(gap == -1) { // ������ �� ����.
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
			/* �̵� ������ �� Ȯ��.
			if(dir == 0 ) {
				System.out.printf("row index : %d \n",index);
			}else {
				System.out.printf("col index : %d \n",index);
			}
			*/
		}
	}
}
