package CodeTraining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 * [문제]
 * 1. N개의 수가 주어졌을 때, 이를 오름차순으로 정려하는 프로그램을 작성하시오.
 * [주의]
 * 1. 출력량이 많아 IO 오버해드를 줄여줄 필요가 있다.
 */
public class BOJ_2750_SortingNumber_QuickSort {
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static int N;
	public static void main(String args[]) throws IOException{
		int arr[];
		arr = initSetting();
		quickSort(arr,0,N-1);
		printArray(arr);
	}
	public static int[] initSetting()throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		N = Integer.parseInt(br.readLine());
		int tmp[] = new int[N];
		for(int i=0; i<N; i++) {
			tmp[i] = Integer.parseInt(br.readLine());
		}
		return tmp;
	}
	
	public static void quickSort(int A[], int left, int right) {
		int part2 = partition(A, left, right);
		if(left < part2 -1) {
			quickSort(A, left, part2 -1);
		}
		if(part2 < right) {
			quickSort(A, part2, right);
		}
		
	}
	
	public static int partition(int A[], int left, int right) {
		int pivot = A[(left + right)/2];
		
		while(left <= right) {
			while(left <= right && A[left] < pivot) {
				left++;
			}
			
			while(left <= right && A[right] > pivot) {
				right--;
			}
			if(left <= right) {
				swap(A,left,right);
				left++;
				right--;
			}
		}
		return left;
	}
	
	public static void swap(int A[], int left, int right) {
		int tmp = A[left];
		A[left] = A[right];
		A[right] = tmp;
	}
	
	public static void printArray(int A[]) throws IOException{
		for(int i=0; i<N; i++) {
			//System.out.println(A[i]);
			bw.write(String.valueOf(A[i])+"\n");
		}
		bw.flush();
		bw.close();
	}
	
}
