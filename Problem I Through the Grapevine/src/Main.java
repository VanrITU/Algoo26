import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

import java.io.*;

import java.util.NoSuchElementException;

//Bag
import java.util.Iterator;

public class Main {
    public static int m;
    public static int n;
    public static int d;

    public static int rumoredHeardBy = 0;

    public static void main(String[] args) throws IOException {

        //--- Setup for all the input and parsing
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken()); //amount of people
        m = Integer.parseInt(st.nextToken()); //amount of connections
        d = Integer.parseInt(st.nextToken()); //amount of days

        People[] persons = new People[n];


        //runs through all people
        for (int i = 0; i < n; i++) {
            //should save everyone as a person Class
            st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            int skep = Integer.parseInt(st.nextToken());
            persons[i] = new People(name, skep, i);

        }

        Digraph graph = new Digraph(n);


        //runs through all connections
        for (int i = 0; i < m; i++) {
            //runs through all connections

            st = new StringTokenizer(br.readLine());
            String name1 = st.nextToken();
            String name2 = st.nextToken();

            People p1 = People.findByName(persons, name1);
            People p2 = People.findByName(persons, name2);

            //undirected graph adding connections (both ways)
            graph.addEdge(p1.getId(), p2.getId());
            graph.addEdge(p2.getId(), p1.getId());

        }


        //final line should save the name for this is the person that starts
        st = new StringTokenizer(br.readLine());
        String personZero = st.nextToken();
        People p0 = People.findByName(persons, personZero);


        //empty array for amount of distinct people have told each other persons
        int[] timesHeard = new int[n];
        boolean[] believes = new boolean[n];

        believes[p0.getId()] = true; //ads the right personZero first and that they always believe by default


        //day cycle
         for (int i = 0; i < d; i++) {
             boolean[] newBelieves = believes.clone();

             for (int j = 0; j < n; j++) {
                 //makes sure that the following code is only for all who does believe the rumor because they can spread it
                 if (!believes[j]) continue;

                 //go through only neighbors that dont belive already ad increase times they heard the rumor
                 for (int k : graph.adj(j)) {
                     if (!believes[k]) {
                         timesHeard[k]++;

                         //compares times heard and their individual skeptism in order to decide if the believe or not
                         if (timesHeard[k] >= (int) persons[k].getSkep()) {
                             newBelieves[k] = true;
                         }
                     }
                 }
             }
             believes = newBelieves;
         }

         //makes sure to exclude person zero in all the persons and add all does who are not o
        for (int i = 0; i < n; i++) {
            if (believes[i] && i != p0.getId()) {
                rumoredHeardBy++;
            }
        }



        //final print
        System.out.println(rumoredHeardBy);

    }

    //class for people in the network
    //tracks name and skepticism
    public static class People {
        String name;
        double skep;
        int id;

        public People(String name, double skep, int id) {
            this.name = name;
            this.skep = skep;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public double getSkep() {
            return skep;
        }

        public int getId() {
            return id;
        }

        public static People findByName(People[] pArr, String name) {
            for (People p : pArr) {
                if (p.getName().equals(name)) {
                    return p;
                }
            }
            return null;
        }


    }


    //Princeton

    //Digraph Class
    public static class Digraph {
        private static final String NEWLINE = System.getProperty("line.separator");

        private final int V;           // number of vertices in this digraph
        private int E;                 // number of edges in this digraph
        private Bag<Integer>[] adj;    // adj[v] = adjacency list for vertex v
        private int[] indegree;        // indegree[v] = indegree of vertex v

        /**
         * Initializes an empty digraph with <em>V</em> vertices.
         *
         * @param  V the number of vertices
         * @throws IllegalArgumentException if {@code V < 0}
         */
        public Digraph(int V) {
            if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
            this.V = V;
            this.E = 0;
            indegree = new int[V];
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
        }

        /**
         * Initializes a new digraph that is a deep copy of the specified digraph.
         *
         * @param  digraph the digraph to copy
         * @throws IllegalArgumentException if {@code digraph} is {@code null}
         */
        public Digraph(Digraph digraph) {
            if (digraph == null) throw new IllegalArgumentException("argument is null");

            this.V = digraph.V();
            this.E = digraph.E();
            if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");

            // update indegrees
            indegree = new int[V];
            for (int v = 0; v < V; v++)
                this.indegree[v] = digraph.indegree(v);

            // update adjacency lists
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }

            for (int v = 0; v < digraph.V(); v++) {
                // reverse so that adjacency list is in same order as original
                Stack<Integer> reverse = new Stack<Integer>();
                for (int w : digraph.adj[v]) {
                    reverse.push(w);
                }
                for (int w : reverse) {
                    adj[v].add(w);
                }
            }
        }

        /**
         * Returns the number of vertices in this digraph.
         *
         * @return the number of vertices in this digraph
         */
        public int V() {
            return V;
        }

        /**
         * Returns the number of edges in this digraph.
         *
         * @return the number of edges in this digraph
         */
        public int E() {
            return E;
        }


        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }

        /**
         * Adds the directed edge v→w to this digraph.
         *
         * @param  v the tail vertex
         * @param  w the head vertex
         * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
         */
        public void addEdge(int v, int w) {
            validateVertex(v);
            validateVertex(w);
            adj[v].add(w);
            indegree[w]++;
            E++;
        }

        /**
         * Returns the vertices adjacent from vertex {@code v} in this digraph.
         *
         * @param  v the vertex
         * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<Integer> adj(int v) {
            validateVertex(v);
            return adj[v];
        }

        /**
         * Returns the number of directed edges incident from vertex {@code v}.
         * This is known as the <em>outdegree</em> of vertex {@code v}.
         *
         * @param  v the vertex
         * @return the outdegree of vertex {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int outdegree(int v) {
            validateVertex(v);
            return adj[v].size();
        }

        /**
         * Returns the number of directed edges incident to vertex {@code v}.
         * This is known as the <em>indegree</em> of vertex {@code v}.
         *
         * @param  v the vertex
         * @return the indegree of vertex {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int indegree(int v) {
            validateVertex(v);
            return indegree[v];
        }

        /**
         * Returns the reverse of the digraph.
         *
         * @return the reverse of the digraph
         */
        public Digraph reverse() {
            Digraph reverse = new Digraph(V);
            for (int v = 0; v < V; v++) {
                for (int w : adj(v)) {
                    reverse.addEdge(w, v);
                }
            }
            return reverse;
        }

        /**
         * Returns a string representation of the graph.
         *
         * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
         *         followed by the <em>V</em> adjacency lists
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V + " vertices, " + E + " edges " + NEWLINE);
            for (int v = 0; v < V; v++) {
                s.append(String.format("%d: ", v));
                for (int w : adj[v]) {
                    s.append(String.format("%d ", w));
                }
                s.append(NEWLINE);
            }
            return s.toString();
        }

        /**
         * Returns a string representation of this digraph in DOT format,
         * suitable for visualization with Graphviz.
         *
         * To visualize the digraph, install Graphviz (e.g., "brew install graphviz").
         * Then use one of the graph visualization tools
         *    - dot    (hierarchical or layer drawing)
         *    - neato  (spring model)
         *    - fdp    (force-directed placement)
         *    - sfdp   (scalable force-directed placement)
         *    - twopi  (radial layout)
         *
         * For example, the following commands will create graph drawings in SVG
         * and PDF formats
         *    - dot input.dot -Tsvg -o output.svg
         *    - dot input.dot -Tpdf -o output.pdf
         *
         * To change the digraph attributes (e.g., vertex and edge shapes, arrows, colors)
         *  in the DOT format, see https://graphviz.org/doc/info/lang.html
         *
         * @return a string representation of this digraph in DOT format
         */
        public String toDot() {
            StringBuilder s = new StringBuilder();
            s.append("digraph {" + NEWLINE);
            s.append("node[shape=circle, style=filled, fixedsize=true, width=0.3, fontsize=\"10pt\"]" + NEWLINE);
            s.append("edge[arrowhead=normal]" + NEWLINE);
            for (int v = 0; v < V; v++) {
                for (int w : adj[v]) {
                    s.append(v + " -> " + w + NEWLINE);
                }
            }
            s.append("}" + NEWLINE);
            return s.toString();
        }



    }

    //Bag Class
    public static class Bag<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of bag
        private int n;               // number of elements in bag

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty bag.
         */
        public Bag() {
            first = null;
            n = 0;
        }

        /**
         * Returns true if this bag is empty.
         *
         * @return {@code true} if this bag is empty;
         *         {@code false} otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this bag.
         *
         * @return the number of items in this bag
         */
        public int size() {
            return n;
        }

        /**
         * Adds the item to this bag.
         *
         * @param  item the item to add to this bag
         */
        public void add(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }


        /**
         * Returns an iterator that iterates over the items in this bag in arbitrary order.
         *
         * @return an iterator that iterates over the items in this bag in arbitrary order
         */
        public Iterator<Item> iterator()  {
            return new LinkedIterator(first);
        }

        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext()  {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }


    }

    //Queue Class
    public class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of queue
        private Node<Item> last;     // end of queue
        private int n;               // number of elements on queue

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty queue.
         */
        public Queue() {
            first = null;
            last  = null;
            n = 0;
        }

        /**
         * Returns true if this queue is empty.
         *
         * @return {@code true} if this queue is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this queue.
         *
         * @return the number of items in this queue
         */
        public int size() {
            return n;
        }

        /**
         * Returns the item least recently added to this queue.
         *
         * @return the item least recently added to this queue
         * @throws NoSuchElementException if this queue is empty
         */
        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            return first.item;
        }

        /**
         * Adds the item to this queue.
         *
         * @param  item the item to add
         */
        public void enqueue(Item item) {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
            if (isEmpty()) first = last;
            else           oldlast.next = last;
            n++;
        }

        /**
         * Removes and returns the item on this queue that was least recently added.
         *
         * @return the item on this queue that was least recently added
         * @throws NoSuchElementException if this queue is empty
         */
        public Item dequeue() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            n--;
            if (isEmpty()) last = null;   // to avoid loitering
            return item;
        }

        /**
         * Returns a string representation of this queue.
         *
         * @return the sequence of items in FIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this) {
                s.append(item);
                s.append(' ');
            }
            return s.toString();
        }

        /**
         * Returns an iterator that iterates over the items in this queue in FIFO order.
         *
         * @return an iterator that iterates over the items in this queue in FIFO order
         */
        public Iterator<Item> iterator()  {
            return new LinkedIterator(first);
        }

        // a linked-list iterator
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }



    }



}
