package CodeTraining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * [����]
 * 1. N���� ���� �־����� ��, �̸� ������������ �����ϴ� ���α׷��� �ۼ��Ͻÿ�.
 */
public class BOJ_2750_SortingNumber_QuickSort {
	public static BufferedReader br;
	public static int N;
	public static void main(String args[]) throws IOException{
		int arr[];
		arr = initSetting();
		quickSort(arr,0,N-1);
		printArray(arr);
	}
	public static int[] initSetting()throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
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
			while(A[left] < pivot) {
				left++;
			}
			
			while(A[right] > pivot) {
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
	
	public static void printArray(int A[]) {
		for(int i=0; i<N; i++) {
			System.out.println(A[i]);
		}
	}
	
}
