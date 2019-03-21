import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 방향 없는 그래프가 주어졌을 떄, 연결 요소의 개수를 구하는 프로그램을 작성하시오.
 * 
 * [풀이]
 * 1. BFS
 */
public class BOJ_11724_NumberOfConnectedComponent {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N,M;
	public static boolean visited[];
	public static ArrayList<Integer> list[];
	public static void main(String args[]) throws IOException{
		int count = 0;
		initSetting();
		
		for(int i=1; i<=N; i++) {
			if(visited[i] == false) {
				searchGrape_BFS(i);
				count++;
			}
		}
		System.out.println(count);
	}
	
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		visited = new boolean[N+1];
		list = new ArrayList[N+1];
		for(int i=0; i<=N; i++) {
			list[i] = new ArrayList<Integer>();
		}
		for(int i=0; i<M; i++) {
			int a,b;
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			list[a].add(b);
			list[b].add(a);
		}
	}
	public static void searchGrape_BFS(int startPoint) {
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(startPoint);
		int cddtIndex;
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
			
			for(int i=0; i < list[startPoint].size(); i++) {
				cddtIndex = list[startPoint].get(i);
				if(visited[cddtIndex] == true)
					continue;
				
				visited[cddtIndex] = true;
				queue.add(cddtIndex);
			}
		}
	}

}
