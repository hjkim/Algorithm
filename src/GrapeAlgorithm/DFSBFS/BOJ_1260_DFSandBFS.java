import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. DFS, BFS로 탐색한 결과 각각 출력.
 * 2. 방문할 수 있는 정점이 여러 개인 경우 정점 번호가 작은 것을 먼저 방문.
 * 3. 더 이상 방문할 수 있는 점이 없는 경우 종료.
 * 4. 정점 번호눈 1부터 N까지
 * 
 * [조건]
 * 1. 정점의 개수 N (1 <= N <= 1000)
 * 2. 간선의 개수 M (1 <= M <= 10000)
 * 3. 탐색할 정점의 번호 V
 * 4. 간선이 연결하는 두 정점의 번호 쌍이 M개 주어진다.
 * 5. 두 정점 사이에 여러 개의 간선이 있을 수 있다.
 * 6. 입력으로 주어지는 간선은 양방향이다.
 */

public class BOJ_1260_DFSandBFS {
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static StringTokenizer st;
	public static int N,M,V;
	public static ArrayList<Integer> list[];
	public static boolean visited[];
	
	public static void main(String args[]) throws IOException{
		
		initSetting();
		DFS(V);bw.write("\n");
		Arrays.fill(visited, false);
		BFS(V);bw.write("\n");
		bw.flush();
		bw.close();
		br.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public static void initSetting() throws IOException{
		
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		V = Integer.parseInt(st.nextToken());
		
		visited = new boolean[N+1];
		list = (ArrayList<Integer>[]) new ArrayList[N+1];
		
		for(int i=1; i<=N; i++) {
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
		for(int i=1; i<=N; i++) {
			Collections.sort(list[i]);
		}
	}
	
	public static void DFS(int node) throws IOException{
		int length = list[node].size();
		int cddtnode;
		visited[node] = true;
		if(node == V) {
			bw.write(String.valueOf(node));	
		}else {
			bw.write(" " + String.valueOf(node));
		}
		
		for(int i=0; i<length; i++) {
			cddtnode = list[node].get(i);
			if(visited[cddtnode] == true)
				continue;
			//visited[cddtnode] = true;
			DFS(cddtnode);
		}
	}
	
	public static void BFS(int startPoint) throws IOException{
		int length, cddtnode;
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(startPoint);
		visited[startPoint] = true;
		bw.write(String.valueOf(startPoint));
		
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
			length = list[startPoint].size();
			
			for(int i=0; i<length; i++) {
				cddtnode = list[startPoint].get(i);
				if(visited[cddtnode] == true)
					continue;
				visited[cddtnode] = true;
				queue.add(cddtnode);
				bw.write(" " + String.valueOf(cddtnode));
			}
		}
		
	}
}
