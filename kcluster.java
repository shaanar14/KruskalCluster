/*
    COMP2230 Programming Assignment
    Author: Shaan Arora C3236359
    kcluster.java
    Main driver class for the entire assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class kcluster
{
    public static void main(String[] args)
    {
        System.out.println("Hello and welcome to Kruskal’s Clustering!\n");
        //assert keyword wasn't working
        //throwing an exception felt too harsh of a program exit
        if(args.length != 2)
        {
            System.out.println("You have entered " + args.length +  " argument but 2 is required.");
            System.out.println("This program will exit so you can do so");
            System.exit(1);
        }
        //Store the file name passed in
        String fileName = args[0];
        //Number of temporary fire stations requested
        final int tempStations = Integer.parseInt(args[1]);
        //A String to store the file name stripped of ./
        String alteredFileName = "";
        if(fileName.startsWith("."))
        {
            //Strip the first two chars from the file name
            alteredFileName = fileName.substring(1);
        }
        //Store the current working directory plus the file name which will
        String dir = System.getProperty("user.dir") + alteredFileName;
        File file = new File(dir);
        try
        {
            Scanner scan = new Scanner(file);
            LinkedList<Hotspot> hotspots = new LinkedList<>();
            //Check for empty file
            if(!scan.hasNextLine())
            {
                System.out.println("Error empty file. Program stopping execution so this can be corrected");
                System.exit(1);
            }
            //Parse the input file
            while(scan.hasNextLine())
            {
                String line = scan.nextLine();
                //could use var which would just infer the type of cleanLine based on the return value of splt()
                String[] cleanLine = line.split(",");
                Hotspot temp = new Hotspot();
                for(String s : cleanLine)
                {
                    if(s.charAt(0) == '-')
                    {
                        System.out.println("Error negative number found: " + s);
                        System.out.println("Program stopping so this can be corrected");
                        System.exit(1);
                    }
                    //match for positive integer
                    if(s.matches("\\d"))
                    {
                        temp.setID(Integer.parseInt(s));
                    }
                    //regex; for positive digit followed by dot followed by positive digit
                    if(s.matches("(\\d)(\\.)(\\d)"))
                    {
                        double d = Double.parseDouble(s);
                        if(temp.getX() == 0.0 && d != 0.0)
                        {
                            temp.setX(d);
                        }
                        else if(temp.getY() == 0.0 && d != 0.0)
                        {
                            temp.setY(d);
                        }
                    }
                }
                //Add the temporary Hotspot object which is acting as a vertex of a graph to a temporary list
                hotspots.add(temp);
            }
            //Pass that temporary list of verticies to a Graph object
            Graph g = new Graph(hotspots);
            //Send the graph to be clustered
            Clustering clusters = new Clustering(g);
            System.out.println("The weighted graph of hotspots:");
            //Display the weighted adjacency matrix of the graph
            System.out.println(g);
            System.out.printf("\nThere are %d number of hotspots\n", g.getVertexCount());
            System.out.printf("You have requested %d temporary fire stations\n", tempStations);
            //Create minimum spanning trees and store them as clusters
            clusters.kruskalMST();
            //for each Graph object reprsenting the clusters, calculate the centroid of it which will also display
            clusters.getClusters().forEach(clusters::calcCentroid);
            //Inter-clustering distance
            System.out.printf("\nInter - Clustering distance: %.2f\n", clusters.calcInterCD());
            System.out.print("\nThank you for using Kruskal’s Clustering. Bye.\n");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("The file name you have entered is " + fileName + " but it doesnt exist.");
            System.out.println("This program will exit so you can do so");
            System.exit(1);
        }
    }
}
