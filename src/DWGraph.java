import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;

public class DWGraph implements DirectedWeightedGraph {
    private HashMap<Integer, myNode> vertices;
    private HashMap<Integer, HashMap<Integer, myEdge>> neighbors;
    private int MC = 0;
    private int edgeSize = 0;
    private int nodeSize = 0;

    public DWGraph() {
        this.vertices = new HashMap<>();
        this.neighbors = new HashMap<>();
    }

//    public DWGraph(HashMap<Integer, myNode> vertices , HashMap<Integer, HashMap<Integer , myEdge>> edges){
//        for(Map.Entry<Integer , myNode> entry : vertices.entrySet()){
//            this.vertices.put(entry.getKey(), entry.getValue());
//        }
//        for(Map.Entry<Integer,HashMap<Integer , myEdge>> entry : edges){
////            this.edges.put(entry.getKey() , entry.getValue());
//        }


//        for(myNode node : verts){
//            this.vertices.put(node.getKey() , node);
//        }
////        for(myEdge edge: edges){
//////            this.edges.put()
//////        }
//    }

    @Override
    public NodeData getNode(int key) {
        if (this.vertices.containsKey(key)) {
            return vertices.get(key);
        }
        return null;
    }

    public boolean edgeBetween(int src, int dest) {
        if (this.neighbors.get(src) == null || this.neighbors.get(src).get(dest) == null) {
            return false;
        }
        return true;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (src == dest || (vertices.get(src) == null || vertices.get(dest) == null)) {
            return null;
        }
        if (neighbors.containsKey(src)) {
            if (neighbors.get(src).containsKey(dest)) {
                return neighbors.get(src).get(dest);
            }
        }
        return null;


    }

    @Override
    public void addNode(NodeData n) {
        if (!this.vertices.containsKey(n.getKey())) {
            this.vertices.put(n.getKey(), (myNode) n);
            this.neighbors.put(n.getKey(), new HashMap<>());
            this.MC++;
            this.nodeSize++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest && w > 0) {
            if (this.vertices.containsKey(src) && this.vertices.containsKey(dest)) {
                myEdge newEdge = new myEdge(src, dest, w);
                this.neighbors.get(src).put(dest, newEdge);
                this.MC++;
                this.edgeSize++;
            }
        }


    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if(!edgeBetween(src , dest)){
            return null;
        }
        myEdge tmp = this.neighbors.get(src).get(dest);
        this.neighbors.get(src).remove(dest);
        this.edgeSize--;
        MC++;
        return tmp;
    }

    @Override
    public int nodeSize() {
        return this.nodeSize;
    }

    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
