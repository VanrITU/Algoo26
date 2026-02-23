import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Iterator;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int counter = 0;
        int place = 0;
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        Main.Stack countStack = new Main.Stack(); //made static
        countStack.push(0);


        for (int i = 1; i <= k; i++) {
            String action = st.nextToken();

            if (action.equals("undo")) {
                int num = Integer.parseInt(st.nextToken());

                for (int j = 1; j <= num; j++) {
                    countStack.pop();

                }
                counter = (int) countStack.peek();

            } else {
                int num = Integer.parseInt(action);

                counter += num;
                countStack.push(counter);

            }
            place = ((counter%n)+n)%n;

        }
        System.out.println(place);
    }



    public static class Stack<Integer> implements Iterable<Integer> {
        private Node<Integer> first;     // top of stack
        private int n;                // size of the stack

        // helper linked list class
        private static class Node<Integer> {
            private Integer item;
            private Node<Integer> next;
        }

        /**
         * Initializes an empty stack.
         */
        public Stack() {
            first = null;
            n = 0;
        }

        /**
         * Returns true if this stack is empty.
         *
         * @return true if this stack is empty; false otherwise
         */


        /**
         * Returns the number of Integers in this stack.
         *
         * @return the number of Integers in this stack
         */
        public int size() {
            return n;
        }

        /**
         * Adds the Integer to this stack.
         *
         * @param item the Integer to add
         */
        public void push(Integer item) {
            Node<Integer> oldfirst = first;
            first = new Node<Integer>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }

        /**
         * Removes and returns the Integer most recently added to this stack.
         *
         * @return the Integer most recently added
         */
        public Integer pop() {

            Integer item = first.item;        // save Integer to return
            first = first.next;            // delete first node
            n--;
            return item;                   // return the saved Integer
        }


        /**
         * Returns (but does not remove) the Integer most recently added to this stack.
         *
         * @return the Integer most recently added to this stack
         */
        public Integer peek() {

            return first.item;
        }

        /**
         * Returns a string representation of this stack.
         *
         * @return the sequence of Integers in this stack in LIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Integer item : this) {
                s.append(item);
                s.append(' ');
            }
            return s.toString();
        }


        /**
         * Returns an iterator to this stack that iterates through the Integers in LIFO order.
         *
         * @return an iterator to this stack that iterates through the Integers in LIFO order
         */
        public Iterator<Integer> iterator() {
            return new LinkedIterator(first);
        }

        // the iterator
        private class LinkedIterator implements Iterator<Integer> {
            private Node<Integer> current;

            public LinkedIterator(Node<Integer> first) {
                current = first;
            }

            // is there a next Integer?
            public boolean hasNext() {
                return current != null;
            }

            // returns the next Integer
            public Integer next() {
                Integer item = current.item;
                current = current.next;
                return item;
            }
        }




    }

}
