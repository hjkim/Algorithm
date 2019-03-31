import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
 * [����]
 * 1. �������� �Ѳ��� �ð�������� ���� �� �ִ�.
 * 2. �� ������ ������ ������ ���ڰ� �ְ�, �ð���� ������ ���� �ڸ� ���ڿ� �ش��ϸ� �ϳ��� ���� ��Ÿ����.
 * 3. �������� ��й�ȣ�� �̷��� ���ڸ� ȸ���ϸ鼭 ���� �� �ִ� ��� �� ��, k��°�� ū ���� 10������ ���� ���̴�.
 * 4. N���� ���ڰ� �Է����� �־����� ��, ���������� ��й�ȣ�� ����϶�.
 * 
 * [Ǯ��]
 * 1. �Ϲ����� ��������.
 * 2. �� ���� �ִ� ������ ������ n/4�� �̴�.
 * 3. �ʿ��� ȸ��Ƚ���� n/4 - 1���̴�. (n/4��° ȸ���ϸ� 0�� ȸ���� ��������.)
 * 4. �� ���� �ִ� ������ �յ��� �����Ͽ� k��° ���ڸ� ���Ѵ�.
 * 5. �켱����ť�� ����ص� �ȴ�. 
 * (k��°�� ������ ���ڸ� �ǹ��ϴ� ���� �ƴ϶�� ��ü �迭�� �����ϴ� �ͺ��� ������ k��° ���� ���� �� �ִ�.)
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
            list = new ArrayList<Character>(); // �������� ��ȣ���� ���� ArrayList
            sumlist = new ArrayList<Integer>(); // �������ڿ��� ���� �� �ִ� ���ڸ� ���� ArrayList
            
            String str = br.readLine();
            
            for(int i=0; i<n; i++) {
                list.add(str.charAt(i));
            }
             
            // ȸ�� �� �ٸ� ���� ������ ���� �� �ֵ��� �Ϸ��� n/4-1�������� ȸ�����Ѿ��Ѵ�.
            for(int i=0; i<=(n/4)-1; i++) {
                findPassword();
                char tmp = list.get(list.size()-1);
                list.remove(list.size()-1);
                list.add(0, tmp);
            }
            // ����
            Collections.sort(sumlist);
            
            int prev = sumlist.get(0);
            if(k == 1) {
                System.out.printf("#%d %d\n",t+1,prev*-1);
                continue;
            }
            /*
             * �ߺ��� ���� count�� ������Ű�� �ʴ´�.
             * ���� �Ǿ��ֱ� ������ �ٷ� ���� ���� �������� �˻��ϸ� �ȴ�.
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
     * �� ���� �ڸ����� n/4�̹Ƿ� n/4���� 10������ ��ȯ�Ͽ� sumlist�� �־���.
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
     * ����� ������ �ϱ����� 16�� ����� ��� �迭  PA�� �ʱ�ȭ�Ѵ�.
     * nũ�Ⱑ �ִ� 28�̱⶧���� �� ��ȣ�� ù��° �ڸ��� �ڸ����� �ִ� 16^7�̴�. 
     * �̴� 2^28 ���� -2^31 ~ 2^31�� Integer ���� ���̶� long�� ����� �ʿ�� ������.
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