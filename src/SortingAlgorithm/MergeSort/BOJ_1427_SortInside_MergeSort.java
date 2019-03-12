package CodeTraining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * [문제]
 * 1. 내림차순으로 정렬하라.
 * 
 * [조건]
 * 1. 정렬하고자하는 수 N.(1 <= N <= 1000000000)
 * 
 * [풀이]
 * 1. 이번에는 합병정렬(Merge Sort)로 푼다.
 * 2. 분할정복 알고리즘의 일종으로 구간을 나누어 재귀적으로 정렬한다.
 */
public class BOJ_1427_SortInside_MergeSort {
	public static BufferedReader br;
	public static int N;
	public static void main(String args[]) throws IOException{
		int arr[];
		arr = initSetting();
		mergeSort(arr);
		printArray(arr);
	}
	public static int[] initSetting() throws IOException{
		 br = new BufferedReader(new InputStreamReader(System.in));
		 String str = br.readLine();
		 N = str.length();
		 int tmp[] = new int[N];
		 for(int i=0; i<N; i++) {
			 tmp[i] = str.charAt(i)-'0';
		 }
		 return tmp;
	}
	
	public static void mergeSort(int arr[]) {
		int tmp[] = new int[N];
		mergeSort(arr, tmp, 0, N-1);
	}
	
	public static void mergeSort(int arr[], int tmp[], int start, int end) {
		if(start < end) {
			int mid = (start + end) / 2;
			mergeSort(arr, tmp, start, mid);
			mergeSort(arr, tmp, mid+1, end);
			merge(arr, tmp, start, mid, end);
		}
	}
	
	public static void merge(int arr[], int tmp[], int start, int mid, int end) {
		for(int i = start; i<= end; i++) {
			tmp[i] = arr[i];
		}
		int part1 = start;
		int part2 = mid +1;
		int index = start;
		while(part1 <= mid && part2 <= end) {
			if(tmp[part1] > tmp[part2]) {
				arr[index] = tmp[part1];
				part1++;
			}else {
				arr[index] = tmp[part2];
				part2++;
			}
			index++;
		}
		
		//배열의 앞쪽 데이터가 다 적히지 않았을 경우.
		for(int i=0; i <= mid - part1; i++) {
			arr[index + i] = tmp[part1 + i];
		}
		/*
		 * 배열의 뒤쪽 데이터가 while문에서 다 적히지않았더라도 
		 * 뒤쪽은 이미 arr에 적혀 있기때문에 위 작업을 할 필요 없음.
		 * * 우리는 하나의 배열에 대해 작업하고 있음을 인지할 것.
		 */
	}
	
	public static void printArray(int arr[]) {
		for(int i=0; i<N; i++) {
			System.out.printf("%d",arr[i]);
		}
		System.out.println();
	}
	
	
}
