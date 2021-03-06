import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
        return this.neighbors.get(src) != null && this.neighbors.get(src).get(dest) != null;
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
        ArrayList<EdgeData> edgeDataArrayList = new ArrayList<>();
        Set<Integer> sourceSet = this.neighbors.keySet();
        Iterator<Integer> iterSource = sourceSet.iterator();
        for (Object i : sourceSet) {
            int tmpSource = iterSource.next();
            if (this.neighbors.get(tmpSource) != null) {
                Set<Integer> destSet = this.neighbors.get(tmpSource).keySet();
                Iterator<Integer> iterDest = destSet.iterator();
                for (Object j : destSet) {
                    int tmpDest = iterDest.next();
                    edgeDataArrayList.add(this.neighbors.get(tmpSource).get(tmpDest));
                }
            }
        }
        return edgeDataArrayList.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return this.neighbors.get(node_id).values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        if (!vertices.containsKey(key)) {
            return null;
        }
        NodeData tmp = this.vertices.get(key);
        this.vertices.remove(key);
        this.nodeSize--;
        this.MC++;
        int size = this.neighbors.get(key).size();
        this.neighbors.remove(key);
        this.edgeSize -= size;
        Set<Integer> sourceSet = this.neighbors.keySet();
        Iterator<Integer> it = sourceSet.iterator();
        for (Object i : sourceSet) {
            int representIt = it.next();
            if (edgeBetween(representIt, key)) {
                removeEdge(representIt, key);
            }
        }
        return tmp;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (!edgeBetween(src, dest)) {
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
