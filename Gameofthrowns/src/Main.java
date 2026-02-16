import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int counter = 0;
        int place = 0;
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());


        for (int i = 1; i <= k; i++) {
            String action = st.nextToken();

            if (action.equals("undo")) {
                int j = Integer.parseInt(st.nextToken());

                //something with stack
                //pop items in stack amount of j times to undo

                //update counter the opposite of action in stack


            } else {
                int num = Integer.parseInt(action);

                counter += num;
                //put num in stack


                

            }
            place = counter%n;

        }
        System.out.println(place);
    }

}
