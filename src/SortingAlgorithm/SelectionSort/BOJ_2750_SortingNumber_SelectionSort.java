import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 * [문제]
 * 1. N개의 수가 주어졌을 때, 이를 오름차순으로 정려하는 프로그램을 작성하시오.
 * 
 * [풀이]
 * 1. 선택정렬.
 */

public class BOJ_2750_SortingNumber_SelectionSort {
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static int N;
	public static void main(String args[]) throws IOException{
		int arr[] = initSetting();
		selectionSort(arr);
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
	public static void selectionSort(int arr[]) {
		selectionSort(arr,0);
	}
	public static void selectionSort(int arr[], int start) {
		if(start < arr.length - 1) {
			int minIndex = start;
			for(int i= start; i < arr.length; i++){
				if(arr[i] < arr[minIndex]) {
					minIndex = i;
				}
			}
			swap(arr,start,minIndex);
			selectionSort(arr,start+1);
		}
	}
	public static void swap(int arr[], int index1, int index2) {
		int tmp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = tmp;
	}
	
	public static void printArray(int arr[]) throws IOException{
		for(int i=0; i<arr.length; i++){
			bw.write(String.valueOf(arr[i])+"\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}
}
