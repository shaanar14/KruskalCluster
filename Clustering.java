/*
    COMP2230 Coding Assignment
    Author: Shaan Arora C3236359
    Clustering.java
    Takes a Graph object and performs Kruskal's algorithm on that graph to produce minimum spanning trees.
    These minimum spanning trees are stored as their own seperate Graph object and the clustering concepts will be applied to them
 */

import java.util.*;

public class Clustering
{
    //The Graph object that we will cluster
    private Graph graph;
    //LinkedList of clusters, somewhere to store the final output
    private LinkedList<Graph> clusters;
    //List of all verticies
    private LinkedList<Hotspot> parent;

    public Clustering(Graph g)
    {
        this.graph = g;
        this.clusters = new LinkedList<>();
        this.parent = new LinkedList<>();
    }

    public void kruskalMST()
    {
        //Queue of all the edges, which will sort them by weight
        LinkedList<Edge> queue = new LinkedList<>();
        //Add the Edges from the Graph to the queue
        for(int i = 0; i < this.graph.getEdgeCount(); i++)
        {
            queue.add(this.graph.getEdges().get(i));
        }
        Collections.sort(queue);
        //Start the algorithm with makeSet()
        this.makeSet();
        LinkedList<Edge> mst = new LinkedList<>();
        int index = 0;
        while(index < this.graph.getVertexCount())
        {
            Edge temp = queue.removeFirst();
            int x = this.findSet(temp.getSource().getID());
            int y = this.findSet(temp.getDestination().getID());
            if (x == y)
            {
                //ignore do not want to make cycle
            }
            mst.add(temp);
            index++;
            unionSets(x, y);
            //ignore, otherwise we will create a cycle
        }
        for(Edge e : mst) System.out.println(e.getSource());
    }

    //Disjoint Set Operational Functions

    //Preconditions:  None
    //Postconditions: The vertex at index i is assigned a reference to itself
    public void makeSet()
    {
        for(int i = 1; i <= this.graph.getVertexCount(); i++)
        {
            Hotspot temp = new Hotspot(i);
            this.parent.add(temp);
        }
    }

    //Preconditions:  None
    //Postconditions: Returns the root of the tree to which i belongs
    public int findSet(int i)
    {
        if(i == this.parent.size())
        {
            return i;
        }
        if(this.parent.get(i).getID() != i)
        {
            return this.findSet(this.parent.get(i).getID());
        }
        return i;
    }

    //Preconditions:  None
    //Postconditions: Combines the root node of two trees by making one root a child of the other
    public void mergeTrees(int i, int j)
    {
        this.parent.get(i).setID(this.parent.get(j).getID());
    }

    //Preconditions:
    //Postconditions: Constructs a tree that represents the union of sets i and j, assuming that i and j belong to different sets
    public void unionSets(int i, int j)
    {
        this.mergeTrees(this.findSet(i), this.findSet(j));
    }

}
