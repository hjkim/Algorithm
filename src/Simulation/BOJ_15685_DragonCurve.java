import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_15685_DragonCurve {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N,MAX,map[][];
	public static int dx[] = {1,0,-1,0};
	public static int dy[] = {0,-1,0,1};
	public static ArrayList<Integer> curveList;
	public static void main(String args[]) throws IOException{
		startDragonCurve();
	}
	public static void startDragonCurve() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		MAX = 100;
		N = Integer.parseInt(br.readLine());
		map = new int[MAX+1][MAX+1];
		curveList = new ArrayList<Integer>();
		int count = 0;
		int x,y,d,g;
		for(int i=0; i<N; i++) {
			
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			
			addCurve(d,g);
			applyToMap(x,y);
			curveList.clear();
		}
		count = countBox();
		System.out.println(count);
	}
	public static void addCurve(int d, int g) {
		curveList.add(d);
		int length = 1;
		
		for(int i=0; i<g; i++) {
			for(int j=length-1; j>=0; j--) {
				curveList.add((curveList.get(j)+1)%4);
				length++;
			}
		}
		
	}
	public static void applyToMap(int x, int y) {
		int cddtx = x, cddty = y;
		map[cddty][cddtx] = 1;

		for(int i=0; i<curveList.size(); i++) {
			cddty += dy[curveList.get(i)];
			cddtx += dx[curveList.get(i)];
			
			if(cddty < 0 || cddty > MAX || cddtx < 0 || cddtx > MAX)
				continue;
			
			map[cddty][cddtx] = 1;
		}
	}
	public static int countBox() {
		int count = 0;
		for(int j=0; j<MAX; j++) {
			for(int i=0; i<MAX; i++) {
				if(map[j][i] == 1 && map[j+1][i] == 1 && map[j][i+1] == 1 && map[j+1][i+1] == 1)
					count++;
			}
		}
		return count;
	}
}	
