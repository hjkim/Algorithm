package DFSBFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/*
 * [����]
 * 1. ������� ����� ������ �ִ�.
 * 2. ����� ������ ������ ������ �����ϰ�, ������ ��ȣ�� ���δ�.
 * 3. ������ �Է��Ͽ� �������� ����ϰ�, �� ������ ���ϴ� ���� ���� ������������ �����Ͽ� ����϶�.
 * 
 * [Ǯ��]
 * 1. ��� ��ǥ�� ���� BFS.
 */
public class BOJ_2667_NumberingComplex {
	public static int map[][],n;
	public static int drow[] = {-1,0,1,0};
	public static int dcol[] = {0,1,0,-1};
	public static boolean visited[][];
	
	public static void main(String args[]) throws IOException{
		final ArrayList<Integer> list = new ArrayList<Integer>();
		
		initSetting();
	
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(visited[i][j] == false && map[i][j] == 1) {
					int value;
					visited[i][j] = true;
					value = findComplex(i,j);
					list.add(value);
				}
			}
		}
		Collections.sort(list);
		printList(list);
	}
	public static void printList(ArrayList<Integer> list) {
		System.out.println(list.size());
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	public static void initSetting() throws IOException{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		visited = new boolean[n][n];
		for(int i=0; i<n; i++) {
			String str = br.readLine();
			for(int j=0;j<n; j++) {
				if(str.charAt(j) == '0') {
					map[i][j] = 0;
				}else {
					map[i][j] = 1;
				}
			}
		}
	}
	public static int findComplex(int row, int col) {
		Queue<NCpoint> queue = new LinkedList<NCpoint>();
		NCpoint startPoint = new NCpoint(row,col);
		queue.add(startPoint);
		int count = 0;
		while(!queue.isEmpty()) {
			startPoint = queue.poll();
			count++;
			for(int d=0; d<4; d++) {
				int nextrow = startPoint.row + drow[d];
				int nextcol = startPoint.col + dcol[d];
				if(nextrow < 0 || nextrow >= n || nextcol < 0 || nextcol >= n)
					continue;
				if(map[nextrow][nextcol] == 0 || visited[nextrow][nextcol] == true)
					continue;
				
				NCpoint fresh = new NCpoint(nextrow, nextcol);
				queue.add(fresh);
				visited[nextrow][nextcol] = true;
				
			}
		}
		return count;
	}
}

class NCpoint{
	int row,col;
	public NCpoint(int a, int b) {
		this.row = a;
		this.col = b;
	}
}