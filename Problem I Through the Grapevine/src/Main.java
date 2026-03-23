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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken()); //amount of people
        m = Integer.parseInt(st.nextToken()); //amount of connections
        d = Integer.parseInt(st.nextToken()); //amount of days

        People[] persons = new People[n];

        DirectedEdge[] edges = new DirectedEdge[m];

        EdgeWeightedDigraph weightedDigraph = new EdgeWeightedDigraph(m);


        for (int i = 0; i < n; i++) {
            //runs through all people
            //should save everyone as a person Class
            st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            double skep = Double.parseDouble(st.nextToken());

            persons[i] = new People(name, skep, i);



        }

        for (int i = 0; i < m; i++) {
            //runs through all connections
            //should call the graph thingy
            st = new StringTokenizer(br.readLine());
            String name1 = st.nextToken();
            String name2 = st.nextToken();

            People p1 = People.findByName(persons, name1);
            People p2 = People.findByName(persons, name2);


            assert p1 != null;
            assert p2 != null;

            edges[i]=new DirectedEdge(p1.getId(),p2.getId(),p1.getSkep());



        }


        //final line should save the name for this is the person that starts
        st = new StringTokenizer(br.readLine());
        String personZero = st.nextToken();
        People p0 = People.findByName(persons, personZero);


        //days running
        for (int i = 0; i < d; i++) {
            //function to run for each day
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


    public class Digraph {
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

    public class BreadthFirstDirectedPaths {
        private static final int INFINITY = Integer.MAX_VALUE;
        private boolean[] marked;  // marked[v] = is there an s->v path?
        private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
        private int[] distTo;      // distTo[v] = length of shortest s->v path

        /**
         * Computes the shortest path from {@code s} and every other vertex in {@code digraph}.
         * @param digraph the digraph
         * @param s the source vertex
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public BreadthFirstDirectedPaths(Digraph digraph, int s) {
            marked = new boolean[digraph.V()];
            distTo = new int[digraph.V()];
            edgeTo = new int[digraph.V()];
            for (int v = 0; v < digraph.V(); v++)
                distTo[v] = INFINITY;
            validateVertex(s);
            bfs(digraph, s);
        }

        /**
         * Computes the shortest path from any one of the source vertices in {@code sources}
         * to every other vertex in {@code digraph}.
         * @param digraph the digraph
         * @param sources the source vertices
         * @throws IllegalArgumentException if {@code sources} is {@code null}
         * @throws IllegalArgumentException if {@code sources} contains no vertices
         * @throws IllegalArgumentException unless each vertex {@code v} in
         *         {@code sources} satisfies {@code 0 <= v < V}
         */
        public BreadthFirstDirectedPaths(Digraph digraph, Iterable<Integer> sources) {
            marked = new boolean[digraph.V()];
            distTo = new int[digraph.V()];
            edgeTo = new int[digraph.V()];
            for (int v = 0; v < digraph.V(); v++)
                distTo[v] = INFINITY;
            validateVertices(sources);
            bfs(digraph, sources);
        }

        // BFS from single source
        private void bfs(Digraph digraph, int s) {
            Queue<Integer> queue = new Queue<Integer>();
            marked[s] = true;
            distTo[s] = 0;
            queue.enqueue(s);
            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (int w : digraph.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        queue.enqueue(w);
                    }
                }
            }
        }

        // BFS from multiple sources
        private void bfs(Digraph digraph, Iterable<Integer> sources) {
            Queue<Integer> queue = new Queue<Integer>();
            for (int s : sources) {
                marked[s] = true;
                distTo[s] = 0;
                queue.enqueue(s);
            }
            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (int w : digraph.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        queue.enqueue(w);
                    }
                }
            }
        }

        /**
         * Is there a directed path from the source {@code s} (or sources) to vertex {@code v}?
         * @param v the vertex
         * @return {@code true} if there is a directed path, {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public boolean hasPathTo(int v) {
            validateVertex(v);
            return marked[v];
        }

        /**
         * Returns the number of edges in a shortest path from the source {@code s}
         * (or sources) to vertex {@code v}?
         * @param v the vertex
         * @return the number of edges in such a shortest path
         *         (or {@code Integer.MAX_VALUE} if there is no such path)
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int distTo(int v) {
            validateVertex(v);
            return distTo[v];
        }

        /**
         * Returns a shortest path from {@code s} (or sources) to {@code v}, or
         * {@code null} if no such path.
         * @param v the vertex
         * @return the sequence of vertices on a shortest path, as an Iterable
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<Integer> pathTo(int v) {
            validateVertex(v);

            if (!hasPathTo(v)) return null;
            Stack<Integer> path = new Stack<Integer>();
            int x;
            for (x = v; distTo[x] != 0; x = edgeTo[x])
                path.push(x);
            path.push(x);
            return path;
        }

        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            int V = marked.length;
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }

        // throw an IllegalArgumentException if vertices is null, has zero vertices,
        // or has a vertex not between 0 and V-1
        private void validateVertices(Iterable<Integer> vertices) {
            if (vertices == null) {
                throw new IllegalArgumentException("argument is null");
            }
            int vertexCount = 0;
            for (Integer v : vertices) {
                vertexCount++;
                if (v == null) {
                    throw new IllegalArgumentException("vertex is null");
                }
                validateVertex(v);
            }
            if (vertexCount == 0) {
                throw new IllegalArgumentException("zero vertices");
            }
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
