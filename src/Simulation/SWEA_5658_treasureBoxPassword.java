import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
 * [문제]
 * 1. 보물상자 뚜껑은 시계방향으로 돌릴 수 있다.
 * 2. 각 변에는 동일한 개수의 숫자가 있고, 시계방향 순으로 높은 자리 숫자에 해당하며 하나의 수를 나타낸다.
 * 3. 보물상자 비밀번호는 이렇게 상자를 회전하면서 만들 수 있는 모든 수 중, k번째로 큰 수를 10진수로 만든 수이다.
 * 4. N개의 숫자가 입력으로 주어졌을 떄, 보물상자의 비밀번호를 출력하라.
 * 
 * [풀이]
 * 1. 일반적인 구현문제.
 * 2. 한 변에 있는 숫자의 개수는 n/4개 이다.
 * 3. 필요한 회전횟수는 n/4 - 1번이다. (n/4번째 회전하면 0번 회전과 같아진다.)
 * 4. 각 변에 있는 숫자의 합들을 정렬하여 k번째 숫자를 구한다.
 * 5. 우선순위큐를 사용해도 된다. 
 * (k번째가 마지막 숫자를 의미하는 것이 아니라면 전체 배열을 정렬하는 것보다 빠르게 k번째 수를 구할 수 있다.)
 */
public class SWEA_5658_treasureBoxPassword {
    private static int n,k,PA[];
    private static ArrayList<Integer> sumlist;
    private static ArrayList<Character> list;
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
         
        int T = Integer.parseInt(br.readLine());
        PA = new int[8];
        power(16,7);
        for(int t=0; t<T; t++) {
             
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            list = new ArrayList<Character>(); // 보물상자 번호들을 받을 ArrayList
            sumlist = new ArrayList<Integer>(); // 보물상자에서 나올 수 있는 숫자를 받을 ArrayList
            
            String str = br.readLine();
            
            for(int i=0; i<n; i++) {
                list.add(str.charAt(i));
            }
             
            // 회전 시 다른 숫자 조합이 나올 수 있도록 하려면 n/4-1번까지만 회전시켜야한다.
            for(int i=0; i<=(n/4)-1; i++) {
                findPassword();
                char tmp = list.get(list.size()-1);
                list.remove(list.size()-1);
                list.add(0, tmp);
            }
            // 정렬
            Collections.sort(sumlist);
            
            int prev = sumlist.get(0);
            if(k == 1) {
                System.out.printf("#%d %d\n",t+1,prev*-1);
                continue;
            }
            /*
             * 중복된 값은 count를 증가시키지 않는다.
             * 정렬 되어있기 때문에 바로 이전 값과 같은지만 검사하면 된다.
             */
            for(int i=0,count=1; i<sumlist.size(); i++) {
                if(prev == sumlist.get(i)) {
                    continue;
                }else {
                    count++;
                    prev = sumlist.get(i);
                    if(count == k) {
                        System.out.printf("#%d %d\n",t+1,sumlist.get(i)*-1);
                        break;
                    }
                }
            }
        }
    }
    
    /*
     * 한 면의 자릿수는 n/4이므로 n/4개씩 10진수로 변환하여 sumlist에 넣었다.
     */
    private static void findPassword() {
        int value = 0;
        for(int i=0; i<n; i += n/4) {
            value = 0;
            for(int j=i,mpx=n/4-1; j<i+n/4; j++,mpx--) {
                char tmp = list.get(j);
                int number =0;
                if(tmp >= '0' && tmp <= '9') {
                    value += PA[mpx]*(list.get(j)-'0');
                }else {
                    switch(tmp) {
                    case 'A' : number = 10;break;
                    case 'B' : number = 11;break;
                    case 'C' : number = 12;break;
                    case 'D' : number = 13;break;
                    case 'E' : number = 14;break;
                    case 'F' : number = 15;break;
                    }
                    value += PA[mpx]*number;
                }
         
            }
            sumlist.add(-1*value);
        }
         
         
    }
    /*
     * 계산을 빠르게 하기위해 16의 배수를 담는 배열  PA를 초기화한다.
     * n크기가 최대 28이기때문에 각 암호의 첫번째 자리의 자릿수는 최대 16^7이다. 
     * 이는 2^28 으로 -2^31 ~ 2^31인 Integer 범위 안이라 long을 사용할 필요는 없었다.
     */
    private static void power(int a, int b) {
        int value = 1;
        PA[0] = 1;
        for(int i=1; i<=b; i++) {
            value *=a;
            PA[i] = value;
        }
    }
}