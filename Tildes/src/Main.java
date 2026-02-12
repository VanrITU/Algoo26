import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //handling of first line of input
        int n =  Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        QuickFindUF QUF = new QuickFindUF(n);

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            String type = st.nextToken();
            int a = Integer.parseInt(st.nextToken())-1;

            if (type.equals("t")) {
                int b = Integer.parseInt(st.nextToken())-1;
                QUF.union(a, b);
            } else {
                System.out.println(QUF.sizeOf(a));
            }
        }
    }

    static class QuickFindUF {
        private int[] parent;
        private int[] size;

        public QuickFindUF(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
            Arrays.fill(size, 1);
        }

        public int find(int p) {
            while (p != parent[p])
                p = parent[p];
            return p;
        }

        public int sizeOf(int p) {
            return size[find(p)];
        }

        public void union(int p, int q) {

            int root1 = find(p);
            int root2 = find(q);
            if (root1 == root2) return;

            if (size[root1] >= size[root2])
            { int temp = root1; root1 = root2; root2 = temp; }

            parent[root1] = root2;
            size[root2] += size[root1];
        }
    }
}
