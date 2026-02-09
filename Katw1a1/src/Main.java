import java.io.ByteArrayInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        //handling of the first line of the scanner
        String firstLine = scan.nextLine();
        String[] details =  firstLine.split(" ");
        String n = details[0]; //amount of singletons
        String m  = details[1]; //amount of following lines






        int i = 0;
        while (i <= m) {
            String line  = scan.nextLine();
            String[] queries = line.split(" ");
            String type = queries[0];
            String s = queries[1];
            String t = queries[2];

            UF(s);


            if (type.equals("?")) {
                //if query

            } else if (type.equals("=")) {
                //if union

            }





            i++;

        }

    }

}