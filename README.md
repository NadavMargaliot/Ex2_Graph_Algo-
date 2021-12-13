# Ex2_Graph_Algo
![Weighted-directed-graph-i-i-iR-i](https://user-images.githubusercontent.com/84914845/145703597-5c8905ff-a6cb-422b-b1e6-bdb10a2ac2ca.png)

This project is about directed weighted graphs and algorithms.
The main task of this project is to run algorithms on a graph and visual it with GUI.
We built a menu that the user can run a bunch of cool algorithms on the graph and can see it on the screen.
The user can also do other stuff like add node or removing one and more.

## The classes in this project:
### MyNode:
Representing a node in the graph.
### MyEdge:
Representing a edge bewteen two nodes in the graph.
### MyGeo:
Representing a point3d of the node.
### DWGraph:
Representing a graph with those methods:
#### Get Node.
#### Get Edge.
#### Add Node.
#### Connect.
#### Node Iterator.
#### Edge Iterator.
#### Remove Node.
#### Remove Edge.
### DWGraphAlgorithms:
Representing all the algorithms that will run on the graph with those methods:
#### Is connected.
#### Shortest path distance - Dijkstra.
#### Shortest path nodes - Dijkstra.
#### Center.
#### TSP.
#### Save to JSON.
#### Load from JSON.
### GraphWindow:
#### Representing the Gui.
### G1 graph in our GUI:
![G1_regular](https://user-images.githubusercontent.com/84914845/145716216-b1a5f08a-d0ea-4abb-bfc9-b52d25b2afbc.jpg)
### Example of Shortest Path on G2 graph:
#### The Shortest Path colored green and the distance is written with the nodes way.
![G2_shortest_path](https://user-images.githubusercontent.com/84914845/145716249-97e5b30a-7525-4066-8cb7-1613c0acc96c.jpg)
### Example of Center on G3 graph:
#### The center node colred red (40).
![G3_center](https://user-images.githubusercontent.com/84914845/145716265-80942b21-cf4d-4a66-a9f8-a698281aa321.jpg)

## Navigate the menu:
When the user will run the program a pop up menu will appear.
At the menu bar the user will find 3 buttons:
Save/Load , Edit Graph , Algorithm.
### Save/Load:
#### Save - The program let the user to save a current graph to a JSON file.
#### Load - The program let the user to load a graph from a JSON file.
### Edit Graph:
#### Add Node - The program let the user to add a node by x and y.
#### Remove Node- - The program let the userto remove a node by inserting the node key.
Each edge that is related to the current node will be deleted.
#### Add Edge - The program let the user to add an edge by inserting two keys of nodes and weight.
#### Remove Edge - The program let the user to remove an edge by inserting two keys of node.
### Algorithm:
#### Is Connected - The program let the user to know if the graph is connected or not.
#### Center - The program let the user to see the center node if the graph is connected.
#### Shortest Path - The program let the user to see the shortest path between two nodes and the distance.
The shortest path will be colored green.
#### TSP - The program let the user to know the TSP by inserting list of nodes.

### Algorithm Run Time:
![algorithms_run_time](https://user-images.githubusercontent.com/84914845/145717845-48549a5f-5ee1-4324-85f4-dade33766c96.png)

### Running The Program With Terminal / CMD:
Download the project.
Open the Terminal / CMD in this directory:
/Users/IdeaProjects/Ex2_Graph_Algo/out/artifacts/Ex2_Graph_Algo_jar
java -jar Ex2_Graph_Algo_jar filePath

![terminalAlgo](https://user-images.githubusercontent.com/84914845/145723802-bf6b210b-e1aa-4395-adce-d1f28b0aceb5.jpg)








