package DFSBFS;

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
 * [����]
 * 1. DFS, BFS�� Ž���� ��� ���� ���.
 * 2. �湮�� �� �ִ� ������ ���� ���� ��� ���� ��ȣ�� ���� ���� ���� �湮.
 * 3. �� �̻� �湮�� �� �ִ� ���� ���� ��� ����.
 * 4. ���� ��ȣ�� 1���� N����
 * 
 * [����]
 * 1. ������ ���� N (1 <= N <= 1000)
 * 2. ������ ���� M (1 <= M <= 10000)
 * 3. Ž���� ������ ��ȣ V
 * 4. ������ �����ϴ� �� ������ ��ȣ ���� M�� �־�����.
 * 5. �� ���� ���̿� ���� ���� ������ ���� �� �ִ�.
 * 6. �Է����� �־����� ������ ������̴�.
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
