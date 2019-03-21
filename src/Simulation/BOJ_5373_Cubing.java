import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * 삼성전자 DS부문 기출문제.
 * [문제]
 * 1. 루빅스 큐브는 삼차원 퍼즐이다.
 * 2. 보통 루빅스 큐브는 3 x 3 x 3개의 작은 정육면체로 이루어져 있다.
 * 3. 퍼즐을 풀려면 각 면에 있는 아홉 개의 작은 정육면체의 색이 동일해야 한다.
 * 4. 큐브는 각 면을 양방향으로 90도만큼 돌릴 수 있도록 만들어져 있다.
 * 5. 회전을 마친 이후에, 다른 면을 돌릴 수 있다.
 * 6. 이렇게 큐브의 서로 다른 면을 돌리다 보면, 색을 섞을 수 있다.
 * 7. 이 문제에서 루빅스 큐브가 모두 풀린 상태에서 시작한다.
 * 8. 각 면의 색상은 다음과 같다.
 * 8.1. 윗 면 : 흰색, 아랫 면 : 노란색
 * 8.2. 앞 면 : 빨간색, 뒷 면 : 오렌지색
 * 8.3. 왼쪽 면 : 초록색, 오른쪽 면 : 파란색
 * 9. 큐브를 돌린 방법이 순서대로 주어진다. 
 * 이때, 모두 돌린 다음에 가장 윗 면의 색상을 구하는 프로그램을 구하시오.
 * 
 * [조건]
 * 1. 큐브 돌린 횟수 n (1 <= n <= 1000)
 * 2. 큐브 돌린 방법. 각 면의 이니셜, 회전 방향.
 * 2.1. U : 윗 면 : w, D : 아랫 면 : y
 * 2.2. F : 앞 면 : r, B : 뒷 면 : o
 * 2.3. L : 왼쪽 면 : g, R : 오른쪽 면 : b
 * 3.4. + : 시계방향, - : 반시계방향
 * 
 * [풀이]
 * 1. 면 돌리기.
 * 1.1. 돌려야하는 면을 수작업으로 지정. -> spinFace(int Face, char dir)
 * 2. 옆 돌리기
 * 2.1. 돌려야하는 면에 인접한 블럭들을 돌려야한다.
 * 2.2. 면에 인접한 블럭들을 순서대로 list에 받아 조작한 뒤 다시 넣는다.
 * 2.3. 블럭을 한방향으로 받아야하기 때문에 connected 배열을 만들어 받는 순서를 정해둔다.
 * 2.4. spinEdge함수에서 문자들을 순서대로 받는다.
 * 2.5. changeCubeValue에서는 spinEdge에서 list에서 값을 받았던 순서대로 값을 넣어준다.
 */
public class BOJ_5373_Cubing3 {
	public static BufferedReader br;
	public static StringTokenizer st;
	public static int N;
	public static Pair connected[][];
	public static char cube[][][], color[] = {'w','y','r','o','g','b'};
	public static void main(String args[]) throws IOException{
		initSetting();
		for(int t=0; t<N; t++) {
			initCube();			
			inputCommand();
			printCube();
		}
	}
	public static void inputCommand() throws IOException{
		int number = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<number; i++) {
			String str = st.nextToken().toString();
			char dir = str.charAt(1);
			switch(str.charAt(0)) {
			case 'U' : spinFace(0,dir);spinEdge(0,dir);break;
			case 'D' : spinFace(1,dir);spinEdge(1,dir);break;
			case 'F' : spinFace(2,dir);spinEdge(2,dir);break;
			case 'B' : spinFace(3,dir);spinEdge(3,dir);break;
			case 'L' : spinFace(4,dir);spinEdge(4,dir);break;
			case 'R' : spinFace(5,dir);spinEdge(5,dir);break;
				
			}
		}
		
	}
	public static void initSetting() throws IOException{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		cube = new char[6][3][3];
		connected = new Pair[6][4];
		
		connected[0][0] = new Pair(3,'r',0,true);
		connected[0][1] = new Pair(4,'r',0,true);
		connected[0][2] = new Pair(2,'r',0,true);
		connected[0][3] = new Pair(5,'r',0,true);
		
		connected[1][0] = new Pair(2,'r',2,false);
		connected[1][1] = new Pair(4,'r',2,false);
		connected[1][2] = new Pair(3,'r',2,false);
		connected[1][3] = new Pair(5,'r',2,false);
		
		connected[2][0] = new Pair(0,'r',2,false);
		connected[2][1] = new Pair(4,'c',2,true);
		connected[2][2] = new Pair(1,'r',0,true);
		connected[2][3] = new Pair(5,'c',0,false);
		
		connected[3][0] = new Pair(0,'r',0,true);
		connected[3][1] = new Pair(5,'c',2,true);
		connected[3][2] = new Pair(1,'r',2,false);
		connected[3][3] = new Pair(4,'c',0,false);
		
		connected[4][0] = new Pair(0,'c',0,false);
		connected[4][1] = new Pair(3,'c',2,true);
		connected[4][2] = new Pair(1,'c',0,false);
		connected[4][3] = new Pair(2,'c',0,false);

		connected[5][0] = new Pair(0,'c',2,true);
		connected[5][1] = new Pair(2,'c',2,true);
		connected[5][2] = new Pair(1,'c',2,true);
		connected[5][3] = new Pair(3,'c',0,false);
		
	}
	public static void spinFace(int face, char dir) {
		char tmp[] = new char[3];
		for(int i=0; i<3; i++) {
			tmp[i] = cube[face][0][i];
		}
		if(dir == '-') {
			//1행
			cube[face][0][0] = cube[face][0][2];
			cube[face][0][1] = cube[face][1][2];
			cube[face][0][2] = cube[face][2][2];
			//3열
			cube[face][1][2] = cube[face][2][1];
			cube[face][2][2] = cube[face][2][0];
			//3행
			cube[face][2][1] = cube[face][1][0];
			//1열
			cube[face][2][0] = tmp[0];
			cube[face][1][0] = tmp[1];
			
		}else if(dir == '+') {
			//1행
			cube[face][0][2] = cube[face][0][0];
			cube[face][0][1] = cube[face][1][0];
			cube[face][0][0] = cube[face][2][0];
			//1열
			cube[face][1][0] = cube[face][2][1];
			cube[face][2][0] = cube[face][2][2];
			//3행
			cube[face][2][1] = cube[face][1][2];
			//3열
			cube[face][2][2] = tmp[2];
			cube[face][1][2] = tmp[1];
		}
	}
	public static void spinEdge(int face, char dir) {
		boolean normal = false;

			ArrayList<Character> list = new ArrayList<Character>();
			for(int i=0; i<4; i++) {
				normal = connected[face][i].normal;
				int edgeFace = connected[face][i].face;
				if(connected[face][i].rc == 'r') {
					int row = connected[face][i].num;
					
					if(normal == true) {
						for(int j=0; j<3; j++) {
							list.add(cube[edgeFace][row][j]);
						}
					}else {
						for(int j=2; j>=0; j--) {
							list.add(cube[edgeFace][row][j]);
						}
					}
					
					
				}else if(connected[face][i].rc == 'c'){
					int col = connected[face][i].num;
					
					if(normal == true) {
						for(int j=0; j<3; j++) {
							list.add(cube[edgeFace][j][col]);
						}
					}else {
						for(int j=2; j>=0; j--) {
							list.add(cube[edgeFace][j][col]);
						}
					}
				}
				
			}
			
			changeCubeValue(face,list,dir);
		
	}
	
	public static void changeCubeValue(int face, ArrayList<Character> list, char dir) {
		char arr[] = new char[3];
		
		if(dir == '+') {
			for(int i=0; i<3; i++) {
				arr[i] = list.remove(0);
			}
			for(int i=0; i<3; i++) {
				list.add(arr[i]);
			}
		}else {
			for(int i=0; i<3; i++) {
				arr[i] = list.remove(list.size()-1);
			}
			for(int i=0; i<3; i++) {
				list.add(0,arr[i]);
			}
		}
		boolean normal = false;
		for(int i=0; i<4; i++) {
			normal = connected[face][i].normal;
			int edgeFace = connected[face][i].face;
			if(connected[face][i].rc == 'r') {
				int row = connected[face][i].num;
				
				if(normal == true) {
					for(int j=0; j<3; j++) {
						cube[edgeFace][row][j] = list.remove(0);
					}
				}else {
					for(int j=2; j>=0; j--) {
						cube[edgeFace][row][j] = list.remove(0);
					}
				}
				
				
			}else if(connected[face][i].rc == 'c'){
				int col = connected[face][i].num;
				
				if(normal == true) {
					for(int j=0; j<3; j++) {
						cube[edgeFace][j][col] = list.remove(0);
					}
				}else {
					for(int j=2; j>=0; j--) {
						cube[edgeFace][j][col] = list.remove(0);
					}
				}
			}
			
		}
	}
	
	public static void initCube() {
		for(int c=0; c<6; c++) {
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					cube[c][i][j] = color[c];
				}
			}
		}
	}
	
	public static void printCube() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				System.out.printf("%c",cube[0][i][j]);
			}
			System.out.println();
		}
	}
	public static class Pair{
		int face,num;
		char rc;
		boolean normal;
		public Pair(int face, char rc, int num, boolean normal) {
			this.face= face;
			this.rc = rc;
			this.num = num;
			this.normal = normal;
		}
	}
}
