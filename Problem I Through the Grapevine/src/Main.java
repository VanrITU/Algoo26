import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

import java.io.*;
import java.util.Random;


//Edge weighted digraph
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


    //Directed Edge Class
    public static class DirectedEdge {
        private final int v;
        private final int w;
        private final double weight;

        /**
         * Initializes a directed edge from vertex {@code v} to vertex {@code w} with
         * the given {@code weight}.
         * @param v the tail vertex
         * @param w the head vertex
         * @param weight the weight of the directed edge
         * @throws IllegalArgumentException if either {@code v} or {@code w}
         *    is a negative integer
         * @throws IllegalArgumentException if {@code weight} is {@code NaN}
         */
        public DirectedEdge(int v, int w, double weight) {
            if (v < 0) throw new IllegalArgumentException("Vertex names must be non-negative integers");
            if (w < 0) throw new IllegalArgumentException("Vertex names must be non-negative integers");
            if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        /**
         * Returns the tail vertex of the directed edge.
         * @return the tail vertex of the directed edge
         */
        public int from() {
            return v;
        }

        /**
         * Returns the head vertex of the directed edge.
         * @return the head vertex of the directed edge
         */
        public int to() {
            return w;
        }

        /**
         * Returns the weight of the directed edge.
         * @return the weight of the directed edge
         */
        public double weight() {
            return weight;
        }

        /**
         * Returns a string representation of the directed edge.
         * @return a string representation of the directed edge
         */
        public String toString() {
            return v + "->" + w + " " + String.format("%5.2f", weight);
        }


    }

    //Weighted DiGraph Class
    public static class EdgeWeightedDigraph {
        private static final String NEWLINE = System.getProperty("line.separator");

        private final int V;                // number of vertices in this digraph
        private int E;                      // number of edges in this digraph
        private Bag<DirectedEdge>[] adj;    // adj[v] = adjacency list for vertex v
        private int[] indegree;             // indegree[v] = indegree of vertex v

        /**
         * Initializes an empty edge-weighted digraph with {@code V} vertices and 0 edges.
         *
         * @param  V the number of vertices
         * @throws IllegalArgumentException if {@code V < 0}
         */
        public EdgeWeightedDigraph(int V) {
            if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
            this.V = V;
            this.E = 0;
            this.indegree = new int[V];
            adj = (Bag<DirectedEdge>[]) new Bag[V];
            for (int v = 0; v < V; v++)
                adj[v] = new Bag<DirectedEdge>();
        }

        /**
         * Initializes a random edge-weighted digraph with {@code V} vertices and <em>E</em> edges.
         *
         * @param  V the number of vertices
         * @param  E the number of edges
         * @throws IllegalArgumentException if {@code V < 0}
         * @throws IllegalArgumentException if {@code E < 0}
         */
        /*
        public EdgeWeightedDigraph(int V, int E) {
            this(V);
            if (E < 0) throw new IllegalArgumentException("Number of edges in a Digraph must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = StdRandom.uniformInt(V);
                int w = StdRandom.uniformInt(V);
                double weight = 0.01 * StdRandom.uniformInt(100);
                DirectedEdge e = new DirectedEdge(v, w, weight);
                addEdge(e);
            }
        }*/

        /**
         * Initializes an edge-weighted digraph from the specified input stream.
         * The format is the number of vertices <em>V</em>,
         * followed by the number of edges <em>E</em>,
         * followed by <em>E</em> pairs of vertices and edge weights,
         * with each entry separated by whitespace.
         *
         * @param  in the input stream
         * @throws IllegalArgumentException if {@code in} is {@code null}
         * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
         * @throws IllegalArgumentException if the number of vertices or edges is negative
         */
        /*
        public EdgeWeightedDigraph(In in) {
            if (in == null) throw new IllegalArgumentException("argument is null");
            try {
                this.V = in.readInt();
                if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be non-negative");
                indegree = new int[V];
                adj = (Bag<DirectedEdge>[]) new Bag[V];
                for (int v = 0; v < V; v++) {
                    adj[v] = new Bag<DirectedEdge>();
                }

                int E = in.readInt();
                if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
                for (int i = 0; i < E; i++) {
                    int v = in.readInt();
                    int w = in.readInt();
                    validateVertex(v);
                    validateVertex(w);
                    double weight = in.readDouble();
                    addEdge(new DirectedEdge(v, w, weight));
                }
            }
            catch (NoSuchElementException e) {
                throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
            }
        }*/

        /**
         * Initializes a new edge-weighted digraph that is a deep copy of {@code G}.
         *
         * @param  G the edge-weighted digraph to copy
         */
        public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
            this(G.V());
            this.E = G.E();
            for (int v = 0; v < G.V(); v++)
                this.indegree[v] = G.indegree(v);
            for (int v = 0; v < G.V(); v++) {
                // reverse so that adjacency list is in same order as original
                Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
                for (DirectedEdge e : G.adj[v]) {
                    reverse.push(e);
                }
                for (DirectedEdge e : reverse) {
                    adj[v].add(e);
                }
            }
        }

        /**
         * Returns the number of vertices in this edge-weighted digraph.
         *
         * @return the number of vertices in this edge-weighted digraph
         */
        public int V() {
            return V;
        }

        /**
         * Returns the number of edges in this edge-weighted digraph.
         *
         * @return the number of edges in this edge-weighted digraph
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
         * Adds the directed edge {@code e} to this edge-weighted digraph.
         *
         * @param  e the edge
         * @throws IllegalArgumentException unless endpoints of edge are between {@code 0}
         *         and {@code V-1}
         */
        public void addEdge(DirectedEdge e) {
            int v = e.from();
            int w = e.to();
            validateVertex(v);
            validateVertex(w);
            adj[v].add(e);
            indegree[w]++;
            E++;
        }


        /**
         * Returns the directed edges incident from vertex {@code v}.
         *
         * @param  v the vertex
         * @return the directed edges incident from vertex {@code v} as an Iterable
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<DirectedEdge> adj(int v) {
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
         * Returns all directed edges in this edge-weighted digraph.
         * To iterate over the edges in this edge-weighted digraph, use foreach notation:
         * {@code for (DirectedEdge e : G.edges())}.
         *
         * @return all edges in this edge-weighted digraph, as an iterable
         */
        public Iterable<DirectedEdge> edges() {
            Bag<DirectedEdge> list = new Bag<DirectedEdge>();
            for (int v = 0; v < V; v++) {
                for (DirectedEdge e : adj(v)) {
                    list.add(e);
                }
            }
            return list;
        }

        /**
         * Returns a string representation of this edge-weighted digraph.
         *
         * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
         *         followed by the <em>V</em> adjacency lists of edges
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V + " " + E + NEWLINE);
            for (int v = 0; v < V; v++) {
                s.append(v + ": ");
                for (DirectedEdge e : adj[v]) {
                    s.append(e + "  ");
                }
                s.append(NEWLINE);
            }
            return s.toString();
        }

        /**
         * Returns a string representation of this edge-weighted digraph in DOT format,
         * suitable for visualization with Graphviz.
         *
         * To visualize the graph, install Graphviz (e.g., "brew install graphviz").
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
         * @return a string representation of this edge-weighted digraph in DOT format
         */
        public String toDot() {
            StringBuilder s = new StringBuilder();
            s.append("digraph {" + NEWLINE);
            s.append("node[shape=circle, style=filled, fixedsize=true, width=0.3, fontsize=\"10pt\"]" + NEWLINE);
            s.append("edge[arrowhead=normal, fontsize=\"9pt\"]" + NEWLINE);
            for (int v = 0; v < V; v++) {
                for (DirectedEdge e : adj[v]) {
                    int w = e.to();
                    s.append(v + " -> " + w + " [label=\"" + e.weight() + "\"]" + NEWLINE);
                }
            }
            s.append("}" + NEWLINE);
            return s.toString();
        }


    }




}

//make a edge weigthed digraph with people DATA TYPE
//a person is a vertex (has a name and an amount of skeptiscm)
//a relation is the edges (direction from one to the other, weight is the amount of skeptiscm)

//DATA STRUCTURE
//method to run through all connections / filling but only the amount of times the days span
//final return should be the unique amount of people the flooding reached