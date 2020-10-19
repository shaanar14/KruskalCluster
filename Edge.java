/*
    COMP2230 Coding Assignment
    Author: Shaan Arora C3236359
    Edge.java
    Objects of this class represent weighted edges of a graph
 */

public class Edge implements Comparable<Edge>
{
    //The source vertex of the edge which will be a Hotspot object
    private Hotspot source;
    //The desination vertex of an edge which will be a Hotspot object
    private Hotspot destination;
    //The weight of an edge which I will use the Euclidean distance formula to calculate
    private double weight;

    //Default constructor
    public Edge()
    {
        this.source = new Hotspot();
        this.destination = new Hotspot();
        this.weight = 0.0;
    }

    public Edge(Hotspot s, Hotspot d)
    {
        this.source = s;
        this.destination = d;
        //calculate the distance between the source Hotspot and the destination Hotspot
        this.weight = this.calcWeight();

    }

    //Helper function to calculate the distance between the source and destination of an edge which is used as the weight
    private double calcWeight()
    {
        //ensure that the current Edge object does have a source and destination
        assert (this.source != null) && (this.destination != null) : "no source or destination";
        //x1, y1 will be source and x2 and y2 will be destination
        //sub calculation for (x2 - x1) squared
        double resultX = (this.destination.getX() - this.source.getX()) * (this.destination.getX() - this.source.getX());
        //sub calculation for (y2 - y1) squared
        double resultY = (this.destination.getY() - this.source.getY()) * (this.destination.getY() - this.source.getY());
        //final calculation of the formula
        double result =  Math.sqrt(resultX + resultY);
        //rounding to two decimal places
        return Math.round(result * 100.0) / 100.0;
    }

    //Setters
    public void setSource(Hotspot s) {this.source = s;}

    public void setDestination(Hotspot d) {this.destination = d;}

    public void setWeight(double w) {this.weight = w;}

    //Getters

    //Preconditions:  None
    //Postconditions: Returns the source of an edge which will be a Hotspot object
    public Hotspot getSource() {return this.source;}

    //Preconditions:  None
    //Postconditions: Returns the destination of an edge which will be a Hotspot object
    public Hotspot getDestination() {return this.destination;}

    //Preconditions:  None
    //Postconditions: Returns the weight of the current Edge object
    public double getWeight() {return this.weight;}

    //  if(edge1.weight < edge2.weight) return 1
    //  if(edge1.weight > edge2.weight) return -1
    //  if(edge1.weight == edge2.weight) return 0
    @Override
    public int compareTo(Edge e)
    {
        if(this.getWeight() < e.getWeight())
        {
            return -1;
        }
        if(this.getWeight() == e.getWeight())
        {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder();
        String o = String.format("Edge: %d  %d  %.2f\n", this.getSource().getID(), this.getDestination().getID(), this.getWeight());
        output.append(o);
        return output.toString();
    }
}
