package CodeTraining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * [����]
 * 1. ������������ �����϶�.
 * 
 * [����]
 * 1. �����ϰ����ϴ� �� N.(1 <= N <= 1000000000)
 * 
 * [Ǯ��]
 * 1. �̹����� �պ�����(Merge Sort)�� Ǭ��.
 * 2. �������� �˰����� �������� ������ ������ ��������� �����Ѵ�.
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
		
		//�迭�� ���� �����Ͱ� �� ������ �ʾ��� ���.
		for(int i=0; i <= mid - part1; i++) {
			arr[index + i] = tmp[part1 + i];
		}
		/*
		 * �迭�� ���� �����Ͱ� while������ �� �������ʾҴ��� 
		 * ������ �̹� arr�� ���� �ֱ⶧���� �� �۾��� �� �ʿ� ����.
		 * * �츮�� �ϳ��� �迭�� ���� �۾��ϰ� ������ ������ ��.
		 */
	}
	
	public static void printArray(int arr[]) {
		for(int i=0; i<N; i++) {
			System.out.printf("%d",arr[i]);
		}
		System.out.println();
	}
	
	
}
