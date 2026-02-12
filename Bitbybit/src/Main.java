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
                    int i = Integer.parseInt(st.nextToken());
                    register[31 - i] = String.valueOf(0);


                } else if (action.equals("SET")) {
                    int i = Integer.parseInt(st.nextToken());
                    register[31 - i] = String.valueOf(1);

                } else if (action.equals("AND")) {
                    int i = Integer.parseInt(st.nextToken());
                    int j = Integer.parseInt(st.nextToken());

                    if (register[i].equals("?") || register[j].equals("?")) {
                        register[31 - i] = "?";
                    } else if ((Integer.parseInt(register[i])) == 1 & (Integer.parseInt(register[j])) == 1) {
                        register[31 -i] = "1";
                    } else if ((Integer.parseInt(register[i])) == 0 & (Integer.parseInt(register[j])) == 0) {
                        register[31 -i] = "0";
                    } else {
                        register[31 - i] = "0";
                    }

                } else if (action.equals("OR")) {
                    int i = Integer.parseInt(st.nextToken());
                    int j = Integer.parseInt(st.nextToken());

                    if (register[i].equals("?") || register[j].equals("?")) {
                        register[31- i] = "?";
                    } else if ((Integer.parseInt(register[i])) == 1 & (Integer.parseInt(register[j])) == 1) {
                        register[31 - i] = "1";
                    } else if ((Integer.parseInt(register[i])) == 0 & (Integer.parseInt(register[j])) == 0) {
                        register[31 - i] = "0";
                    } else {
                        register[31 -i] = "1";

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
