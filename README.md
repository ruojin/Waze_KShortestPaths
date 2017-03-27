# Waze_KShortestPaths
## Introduction
This project is an interactive web application that displays California Road Network on a map that supports two types of queries, finding adjacent edges of certain intersection point and finding the three shortest paths between two intersection points.
## Implementation
For the frontend, the web UI, which displays California Road Network with visualized data of nodes and edges, is built with HTML, JavaScript and GoogleMaps API. 

For the backend, I implemented Dijkstra's and Yen's Algorithms to find the top-k shortest paths in a undirected weighted graph. The very shortest path is calculated by Dijkestra's and the next shortest path is always explored on the basis of the shortest paths that are shorter. 
## how build the website
Setup Tomcat v7.0 as local host.
The 
