package cpcs324_project;

/**
 *
 * @author Batol,Sarah,Joud 
 */
public class Edge {

    /**
     * source vertex
     */
    int source;

    /**
     * destination vertex
     */
    int destination;

    /**
     * edge weight
     */
    int weight;


    /**
     * constructor 
     * @param source = vertex
     * @param destination = vertex
     * @param weight = int value has a edge weight 
     */
    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String toString() {
        return source + "-" + destination + ": " + weight;
    }
}
