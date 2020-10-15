/*
    COMP2230 Programming Assignment
    Author: Shaan Arora C3236359
    Graph.java
    Stores a set of Hotspot objects which will act as verticies and a set of edges which will connect Hotspot objects
 */

import java.util.Arrays;
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
    //Weights between vertices even though some may not have edges connected to them
    //so if we have [1][2] that will have the distance between hotspot 1 and 2
    private double[][] distances;


    //Default Constructor
    public Graph()
    {
        this.vertexCount = 0;
        this.edgeCount = 0;
        this.vertices = new LinkedList<>();
        this.edges = new LinkedList<>();
    }

    //Parameter Constructor
    public Graph(LinkedList<Hotspot> v)
    {
        this.vertices = v;
        this.vertexCount = this.vertices.size();
        this.distances = new double[this.vertexCount][this.vertexCount];
        this.edges = new LinkedList<>();
        //Creates Edge object which connects out vertices together
        this.connectEdges();
        //Now that we connect the vertices and create edges we can set the number of edges
        this.edgeCount = this.edges.size();
    }

    public void calcDistances()
    {
        for(int i = 0; i < this.vertices.size(); i++)
        {
            this.distances[i][0] = this.getVertex(i).getID();
            for(int j = 0; j < this.vertices.size(); j++)
            {
                Hotspot iH, jH;
                iH = this.getVertex(i);
                jH = this.getVertex(j);
                this.distances[i][j] = this.calcDistance(iH, jH);
            }
        }
    }

    //Calculate the distance between two vertices/Hotspots regardless if an edge connects them
    private double calcDistance(Hotspot h1, Hotspot h2)
    {
        assert h1 != null && h2 != null;
        //x1, y1 will come from h1 and x2 and y2 will come from h2
        //sub calculation for (x2 - x1) squared
        double resultX = (h2.getX() - h1.getX()) * (h2.getX() - h1.getX());
        //sub calculation for (y2 - y1) squared
        double resultY = (h2.getY() - h1.getY()) * (h2.getY() - h1.getY());
        //final calculation of the formula
        double result =  Math.sqrt(resultX + resultY);
        //rounding to two decimal places
        return Math.round(result * 100.0) / 100.0;
    }

    //Preconditions:  vertices.size() !=0 and edges.size() !=0
    //Postconditions: Creates Edge objects to connect the Hotspot objects which are acting as our vertices
    private void connectEdges()
    {
        //Ensure that the we do have vertices and edges for the graph
        assert (this.vertices.size() != 0) || (this.edges.size() != 0) : "No vertices or edges";
        for(int i = 0; i < this.vertices.size(); i++)
        {
            //Create and connect edges to vertices and vertices to edges
            Edge e;
            if(i+1 == this.vertices.size())
            {
                e = new Edge(this.getVertex(i), this.getVertex(i-1));
            }
            else
            {
                e = new Edge(this.getVertex(i), this.getVertex(i+1));
            }
            this.edges.add(e);
            this.getVertex(i).setEdge(e);
        }
    }

    //Getters
    public int getVertexCount() {return this.vertexCount;}

    public int getEdgeCount() {return this.edgeCount;}

    public LinkedList<Edge> getEdges() {return this.edges;}

    public double[][] getDistances()
    {
        return this.distances;
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
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < this.vertices.size(); i++)
        {
            for(int j = 0; j < this.vertices.size(); j++)
            {
                String o = String.valueOf(this.distances[i][j]);
                //For formatting, if we have a decimal
                if(o.matches("\\d\\.[0]"))
                {
                    int parse = (int) this.distances[i][j];
                    String p = String.valueOf(parse);
                    p = String.format("%-4s", p);
                    output.append(p);
                }
                else
                {
                    output.append(o);
                }
                output.append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }
}
