import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 * [문제]
 * 1. 피보나치 함수를 호출했을 때 0과 1이 각각 몇 번 출력되는지 구하라.
 * 2. n == 0일 떄 0, n == 1일 때 1.
 * 
 * [조건]
 * 1. 각 테스트 케이스에 인자로 주어지는 N. (0 <= N <= 40)
 */
public class BOJ_1003_FibonacciFunction {
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static int max=41;
	public static Pair D[];
	public static boolean visited[];
	public static void main(String args[]) throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		D = new Pair[max];
		visited = new boolean[max];

		int T = Integer.parseInt(br.readLine());
		for(int t = 0; t < T; t++) {
			int N = Integer.parseInt(br.readLine());
			
			Pair result = fibonacci(N);
			bw.write(result.zero + " " + result.one + "\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	public static Pair fibonacci(int n) {
		if(visited[n] == true) {
			return D[n];
		}else if(n == 0) {
			D[0] = new Pair(1,0);
			return D[0];
		}else if(n == 1){
			D[1] = new Pair(0,1);
			return D[1];
		}else {
			visited[n] = true;
			Pair a,b;
			a = fibonacci(n-1);
			b = fibonacci(n-2);
			D[n] = new Pair(a.zero+b.zero,a.one+b.one);
			return D[n];
		}		
	}
	
	public static class Pair{
		int zero, one;
		public Pair(int z, int o) {
			this.zero = z;
			this.one = o;
		}
	}
}
