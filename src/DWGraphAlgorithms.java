import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import org.w3c.dom.Node;

import java.util.*;

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
        int nodeSize = this.graph.nodeSize();
        int edgeSize = this.graph.edgeSize();
        return (nodeSize * (nodeSize - 1)) == edgeSize;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        Iterator<EdgeData> checkingIsTherePath = this.graph.edgeIter();
        boolean isTherePath = false;
        while (checkingIsTherePath.hasNext()) {
            EdgeData tmpEdge = checkingIsTherePath.next();
            if (tmpEdge.getDest() == dest) {
                isTherePath = true;
                break;
            }
        }
        if (!isTherePath) {
            return -1;
        }
        // each node weight is infinity
        this.graph.getNode(src).setWeight(0);
        Comparator<NodeData> lessWeight = new Comparator<>() {
            @Override
            public int compare(NodeData node1, NodeData node2) {
                return Double.compare(node1.getWeight(), node2.getWeight());
            }
        };
        PriorityQueue<NodeData> sp = new PriorityQueue<>(lessWeight);
        sp.add(this.graph.getNode(src));
        while (!sp.isEmpty()) {
            NodeData currNode = sp.poll();
            if (currNode.getInfo() != "Visited") {
                currNode.setInfo("Visited");
            }
            Iterator<EdgeData> currNodeIter = this.graph.edgeIter(currNode.getKey());
            while (currNodeIter.hasNext()) {
                EdgeData tmpEdge = currNodeIter.next();
                if (this.graph.getNode(tmpEdge.getDest()).getInfo() != "Visited") {
                    if (currNode.getWeight() + tmpEdge.getWeight() < this.graph.getNode(tmpEdge.getDest()).getWeight()) {
                        this.graph.getNode(tmpEdge.getDest()).setWeight(currNode.getWeight() + tmpEdge.getWeight());
                        sp.add(this.graph.getNode(tmpEdge.getDest()));
                    }
                }
            }
        }
        double shortestPath = this.graph.getNode(dest).getWeight();
        Iterator<NodeData> nodeIter = this.graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData currNode = nodeIter.next();
            currNode.setWeight(Double.POSITIVE_INFINITY);
            currNode.setInfo("Null");
        }
        return shortestPath;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (shortestPathDist(src, dest) == -1) {
            return null;
        }
        // each node weight is infinity
        this.graph.getNode(src).setWeight(0);
        Comparator<NodeData> lessWeight = new Comparator<>() {
            @Override
            public int compare(NodeData node1, NodeData node2) {
                return Double.compare(node1.getWeight(), node2.getWeight());
            }
        };
        HashMap<NodeData, NodeData> path = new HashMap<>();
        PriorityQueue<NodeData> sp = new PriorityQueue<>(lessWeight);
        sp.add(this.graph.getNode(src));
        while (!sp.isEmpty()) {
            NodeData currNode = sp.poll();
            if (!currNode.getInfo().equals("Visited")) {
                currNode.setInfo("Visited");
            }
            Iterator<EdgeData> currNodeIter = this.graph.edgeIter(currNode.getKey());
            while (currNodeIter.hasNext()) {
                EdgeData tmpEdge = currNodeIter.next();
                if (!this.graph.getNode(tmpEdge.getDest()).getInfo().equals("Visited")) {
                    if (currNode.getWeight() + tmpEdge.getWeight() < this.graph.getNode(tmpEdge.getDest()).getWeight()) {
                        this.graph.getNode(tmpEdge.getDest()).setWeight(currNode.getWeight() + tmpEdge.getWeight());
                        path.put(this.graph.getNode(tmpEdge.getDest()), currNode);
                        sp.add(this.graph.getNode(tmpEdge.getDest()));
                    }
                }
            }
        }
        List<NodeData> reversePath = new LinkedList<>();
        NodeData curr = this.graph.getNode(dest);
        reversePath.add(curr);
        while (!reversePath.contains(this.graph.getNode(src))) {
            NodeData back = path.get(curr);
            reversePath.add(back);
            curr = back;
        }
        ArrayList<NodeData> resultPath = new ArrayList<>();
        for (int i = reversePath.size() - 1; i >= 0; i--) {
            resultPath.add(reversePath.get(i));
        }
        return resultPath;
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
