import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;



public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        while (n != 0) {

            String[] register = new String[32];
            Arrays.fill(register, "?");

            for (int k = 1; k <= n; k++) {
                st = new StringTokenizer(br.readLine());
                String action = st.nextToken();

                if (action.equals("CLEAR")) {
                    int i = 31-(Integer.parseInt(st.nextToken()));
                    register[i] = String.valueOf(0);


                } else if (action.equals("SET")) {
                    int i = 31-(Integer.parseInt(st.nextToken()));
                    register[i] = String.valueOf(1);

                } else if (action.equals("AND")) {
                    int i = 31-(Integer.parseInt(st.nextToken()));
                    int j = 31-(Integer.parseInt(st.nextToken()));

                    if (register[i].equals("0") || register[j].equals("0")) {
                        register[i] = "0";
                    } else if (register[i].equals("1") && register[j].equals("1")) {
                        register[i] = "1";
                    } else {
                        register[i] = "?";
                    }


                    /*
                    if (register[i].equals("?") || register[j].equals("?")) {
                        register[i] = "?";
                    } else if ((Integer.parseInt(register[i])) == 1 & (Integer.parseInt(register[j])) == 1) {
                        register[i] = "1";
                    } else if ((Integer.parseInt(register[i])) == 0 & (Integer.parseInt(register[j])) == 0) {
                        register[i] = "0";
                    } else {
                        register[i] = "0";
                    }*/

                } else if (action.equals("OR")) {
                    int i = 31-(Integer.parseInt(st.nextToken()));
                    int j = 31-(Integer.parseInt(st.nextToken()));

                    if (register[i].equals("0") && register[j].equals("0")) {
                        register[i] = "0";
                    } else if (register[i].equals("1") || register[j].equals("1")) {
                        register[i] = "1";
                    } else {
                        register[i] = "?";

                    }
                }
            }
            StringBuilder sb = new StringBuilder();

            for (String bit : register) {
                sb.append(bit);
            }

            String result = sb.toString();
            System.out.println(result);

            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
        }
    }
}
