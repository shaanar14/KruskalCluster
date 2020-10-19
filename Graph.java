/*
    COMP2230 Coding Assignment
    Author: Shaan Arora C3236359
    Graph.java
    Using Edge and Hotspot objects to represent a connect graph and display the weights of all edges in a matrix
 */

import java.util.LinkedList;

public class Graph
{
    //The number of vertices in a graph
    private final int vertexCount;
    //The number of edges in a graph
    private final int edgeCount;
    //Collection of all vertices in a graph
    private LinkedList<Hotspot> vertices;
    //Collection of all edges in a graph
    private LinkedList<Edge> edges;
    //StringBuilder so we can build the output as we connect edges
    private final StringBuilder output;

    //Default Constructor
    public Graph()
    {
        this.vertexCount = 0;
        this.edgeCount = 0;
        this.vertices = new LinkedList<>();
        this.edges = new LinkedList<>();
        this.output = new StringBuilder();
    }

    //Parameter Constructor
    public Graph(LinkedList<Hotspot> v)
    {
        this.vertices = v;
        this.vertexCount = this.vertices.size();
        this.edges = new LinkedList<>();
        this.output = new StringBuilder();
        //Creates Edge object which connects out vertices together
        this.connectEdges();
        //Now that we connect the vertices and create edges we can set the number of edges
        this.edgeCount = this.edges.size();
    }

    //Preconditions:  vertices.size() !=0 and edges.size() !=0
    //Postconditions: Creates Edge objects to connect the Hotspot objects which are acting as our vertices
    private void connectEdges()
    {
        //Ensure that the we do have vertices and edges for the graph
        assert (this.vertices.size() != 0) || (this.edges.size() != 0) : "No vertices or edges";
        //Anytime you see -4 or -5 in String.format() thats for padding and the - is for left alignment
        for(int i = 0; i < this.vertices.size(); i++)
        {
            for(int j = 0; j < this.vertices.size(); j++)
            {
                //if j has completed a loop then we want to add a new line character to our output to achieve the desired format
                if(j == 0) this.output.append("\n");
                //If i and j are the same we skip because we do not want an a vertex to have an edge connected to itself
                //  more specifically we do not want cycles
                if(j == i)
                {
                    //since we do not want an Edge connect a vertex to itself I simply just add a 0 for the weight to the output
                    this.output.append(String.format("%-5d", 0));
                    //continue the loop
                    continue;
                }
                //Create a new Edge object with its source being the vertex at index i and the destiantion at index j of the list of verticies
                //The weight of the edge is automatically calculated when a new Edge object is created
                Edge e = new Edge(this.getVertex(i), this.getVertex(j));
                //Now for that vertex at index i in the list of verticies which is the source of the edge, create a reference to that edge
                this.getVertex(i).setEdge(e);
                //check if the weight of the edge is a whole number
                if(e.getWeight() % 1 == 0)
                {
                    //remove the decimal values since its a whole number to make output tidier e.g. if weight = 5.00, the output will just be 5
                    this.output.append(String.format("%-5d", (int)e.getWeight()));
                }
                else{this.output.append(String.format("%-4.2f ", e.getWeight()));}
                //Add the newly created edge to the list of edges
                this.edges.add(e);
            }
        }
    }

    //Getters

    //Preconditions:  Graph has been declared and initalized
    //Postconditions: Returns the number of verticies in the graph, can return 0
    public int getVertexCount() {return this.vertexCount;}

    //Preconditions:  Graph has been declared and initialized
    //Postconditions: Returns the number of edges in the graph, can return 0
    public int getEdgeCount() {return this.edgeCount;}

    //Preconditions:  The parameter constructor on a Graph object has been used
    //Postconditions: Returns a LinkedList of Edge objects which represents a list of all edges in the graph
    public LinkedList<Edge> getEdges()
    {
        assert this.edgeCount > 0 && this.edges != null;
        return this.edges;
    }

    //Wrapper function to make things neater
    //takes an integer and returns the Hotspot object at that index in vertices
    public Hotspot getVertex(int i)
    {
        //make sure we do not have an empty list of vertices
        assert this.vertices.size() != 0;
        return this.vertices.get(i);
    }

    //Preconditions:  vertices != null and edges != null
    //Postconditions: Displays the weight graph of vertices, regardless if they are connected by an edge
    @Override
    public String toString()
    {
        //Make sure we at least have two verticies and at least one edge
        assert this.vertices.size() > 2 || this.edges.size() > 1;
        return this.output.toString();
    }
}
