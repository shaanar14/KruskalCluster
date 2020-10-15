KruskalCluster


Implementation of Kruskal's algorithm.


Use Kruskal’s algorithm for finding a minimum spanning tree to find an optimal clustering (clustering that maximizes the inter-clustering distance) of the given set of n points in a two-dimensional plane, for the given number k of clusters.


This program suggests the number of fire stations to maximize the ratio of distances between hotspots handled by different fire stations and those handled by the same fire station.



Reads in a CSV file  that should look like this:


1,1.0,1.0
2,2.0,2.0
3,3.0,5.0
4,7.0,8.0
5,8.0,7.0


and a number which will represent the number of temporary fire stations.


The first column in the file will be the ID for a Hotspot object and the decimal numbers that follow it will become the x and y coordinate, respecitvelly.


A Hotspot object represents a vertex in a graph and an Edge object represents an edge that connects two Hotspots/vertices.





