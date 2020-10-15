/*
    COMP2230 Programming Assignment
    Author: Shaan Arora C3236359
    Hotspot.java
    This class and objects of this class will act as vertices for a graph
 */
public class Hotspot
{
    //Private Member variables
    //ID of the hotspot which comes from the first number in every line of a valid input file
    private int id;
    //x coordinate of the hotspot
    private double x;
    //y coordinate of the hotspot
    private double y;
    //Every Hotspot/vertex needs to know which edge its connected to
    private Edge edge;


    //Default Constructor
    public Hotspot()
    {
        this.id = 0;
        this.x = 0.0;
        this.y = 0.0;
        this.edge = null;
    }

    //Parameter Constructor
    public Hotspot(int i, double x, double y, Edge e)
    {
        assert i >= 0 && x >=0 && y >= 0;
        this.id = i;
        this.x = x;
        this.y = y;
        this.edge = e;
    }

    //Setters

    //Preconditions:  None
    //Postconditions: Assigns the value of i to the member variable id
    public void setID(int i) {this.id = i;}

    //Preconditions:  None
    //Postconditions: Assigns the value of x to the member variable x
    public void setX(double x) {this.x = x;}

    //Preconditions:  None
    //Postconditions: Assigns the value of y to the member variable y
    public void setY(double y) {this.y = y;}

    //Preconditions:  None
    //Postconditions: Assigns the value of e to the member variable edge
    public void setEdge(Edge e) {this.edge = e;}

    //Getters

    //Preconditions:  None
    //Postconditions: Returns the ID of the current Hotspot object
    public int getID(){return this.id;}

    //Preconditions:  None
    //Postconditions: Returns the x coordinate of the current Hotspot object
    public double getX(){return this.x;}

    //Preconditions:  None
    //Postconditions: Returns the x coordinate of the current Hotspot object
    public double getY(){return this.y;}

    //Preconditions:  None
    //Postconditions: Returns the edge the current Hotspot object is connected to
    public Edge getEdge(){return this.edge;}

    @Override
    public String toString()
    {
        //HS for hotspot
        return String.format("HS: %d X: %.2f Y: %.2f", this.getID(), this.getX(), this.getY());
    }
}
