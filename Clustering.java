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

    public Clustering(Graph g)
    {
        this.graph = g;
        this.clusters = new LinkedList<>();
    }

    //Driver function for Kruskal's Algorithm and the generation of minimum spanning trees
    public void kruskalMST()
    {
        //Queue of all the edges, which will sort them by weight
        LinkedList<Edge> queue = new LinkedList<>();
        //Add the Edges from the Graph to the queue
        for(int i = 0; i < this.graph.getVertexCount(); i++)
        {
            queue.add(this.graph.getVertices().get(i).getEdge());
        }
        Collections.sort(queue);
        int [] parent = new int[graph.getVertexCount()];
        //Start the algorithm with makeSet()
        this.makeSet(parent);
        //Lits of all the edges in the minimum spanning tree
        LinkedList<Edge> mst = new LinkedList<>();
        //Any edges that are not added to the minimum spanning tree are stored here
        LinkedList<Edge> leftOverEdges = new LinkedList<>();
        int index = 0;
        while(index < this.graph.getVertexCount() - 1)
        {
            //this was added as a result of if statement before x and y
            if(queue.isEmpty()) break;
            //Grab the first edge from the queue and capture it in a temporary Edge object
            Edge temp = queue.removeFirst();
            //Had to include this check other wise had index issues with my parent array
            //I think the issue is that array index starts at 0 where as the ID's of the Hotspots start at 1
            if(temp.getDestination().getID() == this.graph.getVertexCount() || temp.getSource().getID() == this.graph.getVertexCount())
            {
                leftOverEdges.add(temp);
            }
            else
            {
                //Capture the results of findSet on the source of the edge and the destination of the edge
                int x = this.findSet(parent, temp.getSource().getID());
                int y = this.findSet(parent, temp.getDestination().getID());
                //If x & y are not equal then add the edge to our minimum spanning tree, increment the index and perform a union on x & y
                if (x != y)
                {
                    mst.add(temp);
                    index++;
                    unionSets(parent, x, y);
                }
            }
        }
        //Generate Clusters
        this.createCluster(mst);
        this.createCluster(leftOverEdges);
    }

    //Creates a new Graph object using the list of edges passed in
    public void createCluster(LinkedList<Edge> edges)
    {
        Graph newCluster = new Graph();
        for(Edge e : edges)
        {
            newCluster.addVertex(e.getSource());
            newCluster.addEdge(e);
        }
        this.clusters.add(newCluster);
    }

    //Clustering Distance Functions

    //Preconditions: clusters.size >= 2
    //Postconditions: Returns the minimum distance between any two items belonging to two different clusters
    public double calcInterCD()
    {
        assert this.clusters.size() >= 2 : "2 clusters are required to calculate the inter-clustering distance";
        //This is way too many for loops, couldn't figure out how to use disjoint sets for this calculation
        for(Graph g : this.clusters)
        {
            for(Graph c : this.clusters)
            {
                //if they are the same graph then break becaue do not want to do a comparison
                if(g == c) continue;
                for(Edge e1 : g.getEdges())
                {
                    for(Edge e2 : c.getEdges())
                    {
                        //if the destination of e1 is the same as the source of the edge of e2 then thats the edge between clusters
                        if(e1.getDestination() == e2.getSource())
                        {
                            return e1.getWeight();
                        }
                    }
                }
            }
        }
        //if all else fails return 0
        return 0.0;
    }


    //Calculates the centroid of the cluster graph passed in
    //Preconditions:  g.vertexCount != 0 && g.edgeCount != 0
    //Postconditions: Calculates the average point in the cluster
    //  The x-coordinate of the centroid is the average of the 𝑥-coordinates of all the points in the cluster; similarly, the 𝑦-coordinate of the centroid
    //      is the average of the 𝑦-coordinates of all the points in the cluster.
    public void calcCentroid(Graph g)
    {
        //Ensure that the graph passed is actually a cluster
        assert this.clusters.contains(g);
        System.out.printf("\nStation %d:\n", this.clusters.indexOf(g)+1);
        double x = 0, y = 0;
        double[] centroid = new double[2];
        //Add up all the x & y coordinates for every vertex in g
        for(Hotspot v : g.getVertices())
        {
            x += v.getX();
            y += v.getY();
        }
        //Divide the sum by the number of vertices
        centroid[0] = x / g.getVertexCount();
        centroid[1] = y / g.getVertexCount();
        System.out.printf("Coordinates: (%.2f, %.2f)\n", centroid[0], centroid[1]);
        System.out.print("Hotspots: {");
        for(int i = 0; i < g.getVertexCount(); i++)
        {
            if(g.getVertex(i) == g.getVertices().getLast())
            {
                System.out.printf("%d}\n", g.getVertex(i).getID());
            }
            else
            {
                System.out.print(g.getVertex(i).getID());
                System.out.print(",");
            }
        }
    }

    //Disjoint Set Operational Functions

    //Preconditions:  None
    //Postconditions: The vertex at index i is assigned a reference to itself
    public void makeSet(int [] parent)
    {
        for(int i = 0; i < this.graph.getVertexCount(); i++)
        {
            parent[i] = i;
        }
    }

    //Preconditions:  None
    //Postconditions: Returns the root of the tree to which i belongs
    public int findSet(int [] parent, int i)
    {
        while(i != parent[i])
        {
            i = parent[i];
        }
        return i;
    }

    //Preconditions:  None
    //Postconditions: Combines the root node of two trees by making one root a child of the other
    public void mergeTrees(int [] parent, int i, int j) {parent[i] = j;}

    //Preconditions:
    //Postconditions: Constructs a tree that represents the union of sets i and j, assuming that i and j belong to different sets
    public void unionSets(int [] parent, int i, int j)
    {
        int x = this.findSet(parent, i);
        int y = this.findSet(parent, j);
        //this.mergeTrees(parent, x, y);
        parent[y] = parent[x];
    }

    //Setters

    //Preconditions:  None
    //Postconditions: Assigns the value of g to the private member variable graph
    public void setGraph(Graph g) {this.graph = g;}

    //Preconditions:  None
    //Postconditions: Assigns the value of c to the private member variable clusters
    public void setClusters(LinkedList<Graph> c) {this.clusters = c;}

    //Getters

    //Preconditions:  graph != null
    //Postconditions: Returns the original Graph object that was passed in to be clustered
    public Graph getGraph()
    {
        assert this.graph != null;
        return this.graph;
    }


    //Preconditions:  clusters.size() != 0
    //Postconditions: Returns a list of Graph objects that are representing the clusters
    public LinkedList<Graph> getClusters()
    {
        assert this.clusters.size() != 0 : "There are no clusters";
        return this.clusters;
    }

}
