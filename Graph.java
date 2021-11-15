package cpcs324_project;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author Batol,Sarah,Joud 
 */
public class Graph {

    /**
     * variable to take the number of vertices from the main method depend on user choice
     */
    int vertices;

    /**
     * variable to take the number edges from the main method depend on user choice
     */
    int edges;

    /**
     * a linked list that save every vertex adjacent 
     */
    LinkedList<Edge>[] adjacencylist;
    
    
  

    /**
     * constructor 
     * @param vertices = number of vertices 
     * @param edges = number of edges 
     */
    Graph(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
        adjacencylist = new LinkedList[vertices];
        //initialize adjacency lists for all the vertices
        for (int i = 0; i < vertices; i++) {
            adjacencylist[i] = new LinkedList<>();
        }
    }

    /**
     * this method is used in make_graph() method to add a new edge to the graph
     * @param source source vertex
     * @param destination destination vertex
     * @param weight weight of the edge  
     */
    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencylist[source].addFirst(edge);

        edge = new Edge(destination, source, weight);
        adjacencylist[destination].addFirst(edge); //for undirected graph

    }


    /**
     * Prim's Algorithm using Priority Queue : calculate the taken time to apply Prim's Algorithm using Priority Queue on different graphs 
     */
    public void PrimPriorityQueue() {
        //start time
        double StartTime = System.currentTimeMillis();
        boolean[] MinSpanTree = new boolean[vertices];
        ResultSet[] resultSet = new ResultSet[vertices];
        int[] key = new int[vertices];  //keys are used to store the key to know whether priority queue update is required

        //Initialize all the keys to infinity and
        //initialize resultSet for all the vertices
        for (int i = 0; i < vertices; i++) {
            key[i] = Integer.MAX_VALUE;
            resultSet[i] = new ResultSet();
        }

        //Initialize priority queue
        //override the comparator to do the sorting based keys
        PriorityQueue<Pair<Integer, Integer>> priorityQueue = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> point1, Pair<Integer, Integer> point2) {
                //sort using key values
                int key1 = point1.getKey();
                int key2 = point2.getKey();
                return key1 - key2;
            }
        });

        //create the pair for for the first index, 0 key 0 index
        key[0] = 0;
        Pair<Integer, Integer> point0 = new Pair<>(key[0], 0);
        //add it to pq
        priorityQueue.offer(point0);
        resultSet[0] = new ResultSet();
        resultSet[0].parent = -1;

        //while priority queue is not empty
        while (!priorityQueue.isEmpty()) {
            //extract the min
            Pair<Integer, Integer> extractedMinPair = priorityQueue.poll();

            //extracted vertex
            int extractedVertex = extractedMinPair.getValue();
            MinSpanTree[extractedVertex] = true;

            //iterate through all the adjacent vertices and update the keys
            LinkedList<Edge> list = adjacencylist[extractedVertex];
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                //only if edge destination is not present in mst
                if (MinSpanTree[edge.destination] == false) {
                    int destination = edge.destination;
                    int newKey = edge.weight;
                    //check if updated key < existing key, if yes, update if
                    if (key[destination] > newKey) {
                        //add it to the priority queue
                        Pair<Integer, Integer> NewPair = new Pair<>(newKey, destination);
                        priorityQueue.offer(NewPair);
                        //update the resultSet for destination vertex
                        resultSet[destination].parent = extractedVertex;
                        resultSet[destination].weight = newKey;
                        //update the key[]
                        key[destination] = newKey;
                    }
                }
            }
        }
        //finish time of the algorithm
        double FinishTime = System.currentTimeMillis();
        //print the total time consumed by the algorithm
        System.out.println("Total runtime of Prim's Algorithm (Usin PQ): " + (FinishTime - StartTime) + " ms.");
        //print mst
        PrintMinSpanTree(resultSet);
    }

    /**
     * Prim's Algorithm using Min heap : calculate the taken time to apply Prim's Algorithm using Min heap on different graphs 
     */
    public void PrimMinHeap() {
        //start time
        double StartTime = System.currentTimeMillis();
        boolean[] inHeap = new boolean[vertices];
        ResultSet[] resultSet = new ResultSet[vertices];
        //keys[] used to store the key to know whether min hea update is required
        int[] key = new int[vertices];
        //create heapNode for all the vertices
        HeapNode[] heapNodes = new HeapNode[vertices];
        for (int i = 0; i < vertices; i++) {
            heapNodes[i] = new HeapNode();
            heapNodes[i].vertex = i;
            heapNodes[i].key = Integer.MAX_VALUE;
            resultSet[i] = new ResultSet();
            resultSet[i].parent = -1;
            inHeap[i] = true;
            key[i] = Integer.MAX_VALUE;
        }

        //decrease the key for the first index
        heapNodes[0].key = 0;

        //add all the vertices to the MinHeap
        MinHeap minHeap = new MinHeap(vertices);
        //add all the vertices to priority queue
        for (int i = 0; i < vertices; i++) {
            minHeap.insert(heapNodes[i]);
        }

        //while minHeap is not empty
        while (!minHeap.isEmpty()) {
            //extract the min
            HeapNode extractedMinNode = minHeap.extractMin();

            //extracted vertex
            int extractedVertex = extractedMinNode.vertex;
            inHeap[extractedVertex] = false;

            //iterate through all the adjacent vertices
            LinkedList<Edge> list = adjacencylist[extractedVertex];
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                //only if edge destination is present in heap
                if (inHeap[edge.destination]) {
                    int destination = edge.destination;
                    int newKey = edge.weight;
                    //check if updated key < existing key, if yes, update if
                    if (key[destination] > newKey) {
                        decreaseKey(minHeap, newKey, destination);
                        //update the parent node for destination
                        resultSet[destination].parent = extractedVertex;
                        resultSet[destination].weight = newKey;
                        key[destination] = newKey;
                    }
                }
            }
        }
        //finish time of the algorithm
        double FinishTime = System.currentTimeMillis();
        //print the total time consumed by the algorithm
        System.out.println("Total runtime of Prim's Algorithm (Usin Min Heap): " + (FinishTime - StartTime) + " ms.");
        //print mst
        PrintMinSpanTree(resultSet);
    }

    public Graph() {
    }

    /**
     * remove the min value from the heap 
     * @param minHeap = All min heap values 
     * @param newKey = Index
     * @param vertex = wanted vertex
     */
    public void decreaseKey(MinHeap minHeap, int newKey, int vertex) {

        //get the index which key's needs a decrease;
        int index = minHeap.indexes[vertex];

        //get the node and update its value
        HeapNode node = minHeap.MinHeapArr[index];
        node.key = newKey;
        minHeap.bubbleUp(index);
    }

    /**
     * calculate and display the minimum spanning tree cost and display it , called in prim's algorithm
     * @param resultSet
     */
    public void PrintMinSpanTree(ResultSet[] resultSet) {
        int total_min_weight = 0;
        System.out.println("Minimum Spanning Tree: ");
        for (int i = 1; i < vertices; i++) {
            total_min_weight += resultSet[i].weight;
        }
        System.out.println("Total cost: " + total_min_weight);
    }


    /**
     * Kruskal's Algorithm : calculate the taken time to apply kurskal's algorithm on different graph 
     */
    public void kruskal() {
        // start time
        double StartTime = System.currentTimeMillis();
        LinkedList<Edge>[] allEdges = adjacencylist.clone(); // modified data type from ArrayList to LinkedList
        PriorityQueue<Edge> priorityQueueVar = new PriorityQueue<>(edges, Comparator.comparingInt(o -> o.weight));

        //add all the edges to priority queue, //sort the edges on weights
        for (int i = 0; i < allEdges.length; i++) {
            for (int j = 0; j < allEdges[i].size(); j++) {
                priorityQueueVar.add(allEdges[i].get(j));
            }
        }
        //create a parent []
        int[] parent = new int[vertices];

        //makeset
        makeSet(parent);

        LinkedList<Edge> MinSpanTree = new LinkedList<>();

        //process vertices - 1 edges
        int index = 0;
        while (index < vertices - 1 && !priorityQueueVar.isEmpty()) {
            Edge edge = priorityQueueVar.remove();
            //check if adding this edge creates a cycle
            int x_set = find(parent, edge.source);
            int y_set = find(parent, edge.destination);

            if (x_set == y_set) {
                //ignore, will create cycle
            } else {
                //add it to our final result
                MinSpanTree.add(edge);
                index++;
                union(parent, x_set, y_set);
            }
        }

        //finish time of the algorithm
        double FinishTime = System.currentTimeMillis();
        //print the total time consumed by the algorithm
        System.out.println("Total runtime of Kruskal's Algorithm: " + (FinishTime - StartTime) + " ms.");
        printGraph(MinSpanTree);
    }


    /**
     * Make set- creating a new element with a parent pointer to itself.
     * @param parent = chain of parent pointers
     */
    public void makeSet(int[] parent) {
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
        }
    }

    /**
     * chain of parent pointers from x upwards through the tree until an element is reached whose parent is itself
     * @param parent = chain of parent pointers
     * @param vertex = wanted vertex
     * @return the searched vertex 
     */
    public int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            return find(parent, parent[vertex]);
        };
        return vertex;
    }

    /**
     * but the connected component of the graph together 
     * @param parent = chain of parent pointers
     * @param x = parent
     * @param y = child
     */
   public void union(int[] parent, int x, int y) {
        int x_set_parent = find(parent, x);
        int y_set_parent = find(parent, y);
        //make x as parent of y
        parent[y_set_parent] = x_set_parent;
    } 

    /**
     * calculate all edges weight to print the min spanning tree cost , called in kruskal's algorithm
     * @param edgeList = all edges in the graph to get the weight from 
     */
    public void printGraph(LinkedList<Edge> edgeList) {
        int cost = 0;
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            cost += edge.weight;
        }
        System.out.println("Minimum Spanning Tree Cost = " + cost);
    }

    //----------------------------------------------------------------------

    /**
     * randomly generate graph with given int weight the range is from (0,20)
     * @param graph = data structure made of vertices and edges 
     */
    public void make_graph(Graph graph) {
        // instance of Random class
        Random random = new Random();
        // ensure that all vertices are connected
        for (int i = 0; i < vertices - 1; i++) {
            int RandomNum = random.nextInt(10) + 1;
            addEdge(i, i + 1, RandomNum);

        }

        // generate random graph with the remaining edges
        int remaning = edges - (vertices - 1);

        for (int i = 0; i < remaning; i++) {
            int source = random.nextInt(graph.vertices);
            int Destination = random.nextInt(graph.vertices);
            if (Destination == source || isConnected(source, Destination, graph.adjacencylist)) { // to avoid self loops and duplicate edges
                i--;
                continue;
            }
            // generate random weights in range 0 to 20
            int weight = random.nextInt(20) + 1;
            // add edge to the graph
            addEdge(source, Destination, weight);
        }
    }

    /**
     *checks if the edge is already existed and connected
     * @param Source
     * @param Destination
     * @param allEdges
     * @return
     */
    public boolean isConnected(int Source, int Destination, LinkedList<Edge>[] allEdges) {
        for (LinkedList<Edge> i : allEdges) {
            for (Edge edge : i) {
                if ((edge.source == Source && edge.destination == Destination) || (edge.source == Destination && edge.destination == Source)) {
                    return true;
                }
            }
        }
        return false;
    }
}
