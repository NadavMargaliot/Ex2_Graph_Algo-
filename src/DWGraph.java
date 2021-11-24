import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph implements DirectedWeightedGraph {
    private HashMap<Integer, myNode> vertices;
    private HashMap<Integer, HashMap<Integer , myEdge>> edges;
    private int MC = 0;

    public DWGraph(){
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

//    public DWGraph(Collections verts , Collections edges){
//        for(myNode node : verts){
//            this.vertices.put(node.getKey() , node);
//        }
////        for(myEdge edge: edges){
////            this.edges.put()
////        }
//    }

    @Override
    public NodeData getNode(int key) {
        if (this.vertices.containsKey(key)){
            return vertices.get(key);
        }
        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if(src == dest || (vertices.get(src) == null || vertices.get(dest) == null)){
            return null;
        }
        if(edges.containsKey(src)){
            if(edges.get(src).containsKey(dest)){
                return edges.get(src).get(dest);
            }
        }
        return null;


    }

    @Override
    public void addNode(NodeData n) {
        if(!this.vertices.containsKey(n.getKey())){
            this.vertices.put(n.getKey() , (myNode)n);
            MC++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {


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
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
