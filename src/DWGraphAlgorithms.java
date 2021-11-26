import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.util.Iterator;
import java.util.List;

public class DWGraphAlgorithms implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;


    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph copied = new DWGraph();
        // adding the vertices to the graph
        Iterator<NodeData> nodes = this.graph.nodeIter();
        while (nodes.hasNext()) {
            copied.addNode(nodes.next());
        }
        // adding the edges to the graph and connect them
        Iterator<EdgeData> edges = this.graph.edgeIter();
        while (nodes.hasNext()) {
            int currNode = nodes.next().getKey();
            while (edges.hasNext()) {
                EdgeData currEdge = edges.next();
                copied.connect(currNode, currEdge.getDest(), currEdge.getWeight());
            }
        }
        return copied;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
