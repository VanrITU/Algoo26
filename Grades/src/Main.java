import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Iterator;

import java.util.Comparator;



public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        //Student[] students = new Student[n];
        String[][] students = new String[n][n];


        for  (int i = 0; i < n; i++) {
            String name = st.nextToken();
            String grade = st.nextToken();

            students[i][0] = name;
            students[i][1] = grade;


        }










    }

    /*
    public static class Student implements Comparable<Student> {
        public String name;
        public int grade;

        public Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public int getGrade() {
            return grade;
        }

        @Override
        public int compareTo(Student other) {
            return this.grade - other.grade; // Sort by ID in ascending order
        }*/

    }










}
