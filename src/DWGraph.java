import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;

public class DWGraph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> vertices;
    private HashMap<Integer, HashMap<Integer, EdgeData>> neighbors;
    private int MC = 0;
    private int edgeSize = 0;
    private int nodeSize = 0;

    public DWGraph() {
        this.vertices = new HashMap<>();
        this.neighbors = new HashMap<>();
    }


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
            this.vertices.put(n.getKey(), n);
            this.neighbors.put(n.getKey(), new HashMap<>());
            this.MC++;
            this.nodeSize++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest && w > 0) {
            if (this.vertices.containsKey(src) && this.vertices.containsKey(dest)) {
                EdgeData newEdge = new myEdge(src, dest, w);
                this.neighbors.get(src).put(dest, newEdge);
                this.MC++;
                this.edgeSize++;
            }
        }


    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return this.vertices.values().iterator();
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
        if(!vertices.containsKey(key)){
            return null;
        }
        NodeData tmp = this.vertices.get(key);
        this.vertices.remove(key);
        this.nodeSize--;
        for(Integer k : this.neighbors.get(key).keySet()){
            this.neighbors.get(key).remove(k);
            this.edgeSize--;
        }

        return tmp;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if(!edgeBetween(src , dest)){
            return null;
        }
        EdgeData tmp = this.neighbors.get(src).get(dest);
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
        return this.MC;
    }
}
