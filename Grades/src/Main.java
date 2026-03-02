import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashMap;

public class Main {
    public static class Student implements Comparable<Student> {
        String name;
        String grade;

        static HashMap<String, Integer> gradeSys = new HashMap<>();

        static {
            gradeSys.put("A", 50);
            gradeSys.put("B", 100);
            gradeSys.put("C", 150);
            gradeSys.put("D", 200);
            gradeSys.put("E", 250);
            gradeSys.put("FX", 300);
            gradeSys.put("F", 350);

        }


        public Student(String name, String grade) {
            this.name = name;
            this.grade = grade;

        }

        public String getName() {
            return name;
        }

        public String getGrade() {
            return grade;
        }

        @Override
        public int compareTo(Student w) {

            //min opskrift
            int vGrade = 0;
            int wGrade = 0;

            if (this.getGrade().startsWith("FX")) {
                vGrade = gradeSys.get("FX");
            } else {
                vGrade = gradeSys.get(""+this.getGrade().charAt(0));
            }

            if (w.getGrade().startsWith("FX")) {
                wGrade = gradeSys.get("FX");
            } else {
                wGrade = gradeSys.get(""+ w.getGrade().charAt(0));
            }

            if (this.getGrade().endsWith("+")) {
                vGrade -= this.getGrade().length();
            } else if (this.getGrade().endsWith("-")) {
                vGrade += this.getGrade().length();
            }

            if (w.getGrade().endsWith("+")) {
                wGrade -= w.getGrade().length();
            } else if (w.getGrade().endsWith("-")) {
                wGrade += w.getGrade().length();
            }

            if (vGrade == wGrade) return this.getName().compareTo(w.getName()); //alphabetic value
            return vGrade < wGrade ? -1 : 1;


        }



    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        Student[] students = new Student[n];


        for  (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            //System.out.println(name);
            String grade = st.nextToken();
            students[i] = new Student(name, grade);
        }

        Merge.sort(students);
        for (Student student : students) {
            System.out.println(student.getName());
        }


    }



    public class Merge {

        // This class should not be instantiated.
        private Merge() {
        }

        // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
        private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
            // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
            assert isSorted(a, lo, mid);
            assert isSorted(a, mid + 1, hi);

            // copy to aux[]
            for (int k = lo; k <= hi; k++) {
                aux[k] = a[k];
            }

            // merge back to a[]
            int i = lo, j = mid + 1;
            for (int k = lo; k <= hi; k++) {
                if (i > mid) a[k] = aux[j++];
                else if (j > hi) a[k] = aux[i++];
                else if (less(aux[j], aux[i])) a[k] = aux[j++];
                else a[k] = aux[i++];
            }

            // postcondition: a[lo .. hi] is sorted
            assert isSorted(a, lo, hi);
        }

        // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
        private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
            if (hi <= lo) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, aux, lo, mid);
            sort(a, aux, mid + 1, hi);
            merge(a, aux, lo, mid, hi);
        }

        /**
         * Rearranges the array in ascending order, using the natural order.
         *
         * @param a the array to be sorted
         */
        public static void sort(Comparable[] a) {
            Comparable[] aux = new Comparable[a.length];
            sort(a, aux, 0, a.length - 1);
            assert isSorted(a);
        }


        /***************************************************************************
         *  Helper sorting function.
         ***************************************************************************/

        // is v < w ?
        private static boolean less(Comparable v, Comparable w) {
            return v.compareTo(w) < 0;
        }

        /***************************************************************************
         *  Check if array is sorted - useful for debugging.
         ***************************************************************************/
        private static boolean isSorted(Comparable[] a) {
            return isSorted(a, 0, a.length - 1);
        }

        private static boolean isSorted(Comparable[] a, int lo, int hi) {
            for (int i = lo + 1; i <= hi; i++)
                if (less(a[i], a[i - 1])) return false;
            return true;
        }


        /***************************************************************************
         *  Index mergesort.
         ***************************************************************************/
        // stably merge a[lo .. mid] with a[mid+1 .. hi] using aux[lo .. hi]
        private static void merge(Comparable[] a, int[] index, int[] aux, int lo, int mid, int hi) {

            // copy to aux[]
            for (int k = lo; k <= hi; k++) {
                aux[k] = index[k];
            }

            // merge back to a[]
            int i = lo, j = mid + 1;
            for (int k = lo; k <= hi; k++) {
                if (i > mid) index[k] = aux[j++];
                else if (j > hi) index[k] = aux[i++];
                else if (less(a[aux[j]], a[aux[i]])) index[k] = aux[j++];
                else index[k] = aux[i++];
            }
        }

        /**
         * Returns a permutation that gives the elements in the array in ascending order.
         *
         * @param a the array
         * @return a permutation {@code p[]} such that {@code a[p[0]]}, {@code a[p[1]]},
         * ..., {@code a[p[n-1]]} are in ascending order
         */
        public static int[] indexSort(Comparable[] a) {
            int n = a.length;
            int[] index = new int[n];
            for (int i = 0; i < n; i++)
                index[i] = i;

            int[] aux = new int[n];
            sort(a, index, aux, 0, n - 1);
            return index;
        }

        // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
        private static void sort(Comparable[] a, int[] index, int[] aux, int lo, int hi) {
            if (hi <= lo) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, index, aux, lo, mid);
            sort(a, index, aux, mid + 1, hi);
            merge(a, index, aux, lo, mid, hi);
        }


    }


}
