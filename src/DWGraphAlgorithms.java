import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
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
        if (this.graph.nodeSize() == 0) {
            return true;
        }
        boolean regularGraph = true;
        boolean reverseGraph = true;
        Queue<NodeData> regularQueue = new LinkedList<>();
        Iterator<NodeData> nodes = this.graph.nodeIter();
        regularQueue.add(nodes.next());
        while (!regularQueue.isEmpty()) {
            NodeData regularCurrNode = regularQueue.poll();
            if (regularCurrNode.getInfo() != "Visited") {
                regularCurrNode.setInfo("Visited");
            }
            Iterator<EdgeData> edgeIter = this.graph.edgeIter(regularCurrNode.getKey());
            while (edgeIter.hasNext()) {
                EdgeData currEdge = edgeIter.next();
                if (this.graph.getNode(currEdge.getDest()).getInfo() != "Visited") {
                    regularQueue.add(this.graph.getNode(currEdge.getDest()));
                    this.graph.getNode(currEdge.getDest()).setInfo("Visited");
                }
            }
        }
        while (nodes.hasNext()) {
            NodeData tmp = nodes.next();
            if (tmp.getInfo() != "Visited") {
                regularGraph = false;
            }
            tmp.setInfo("");
        }
        DirectedWeightedGraph reverse = reverseGraph();
        Queue<NodeData> reverseQueue = new LinkedList<>();
        Iterator<NodeData> reverseNodes = reverse.nodeIter();
        reverseQueue.add(reverseNodes.next());
        while (!reverseQueue.isEmpty()) {
            NodeData reverseCurrNode = reverseQueue.poll();
            if (reverseCurrNode.getInfo() != "Visited") {
                reverseCurrNode.setInfo("Visited");
            }
            Iterator<EdgeData> reverseEdgeIter = reverse.edgeIter(reverseCurrNode.getKey());
            while (reverseEdgeIter.hasNext()) {
                EdgeData reverseCurrEdge = reverseEdgeIter.next();
                if (reverse.getNode(reverseCurrEdge.getDest()).getInfo() != "Visited") {
                    reverseQueue.add(reverse.getNode(reverseCurrEdge.getDest()));
                    reverse.getNode(reverseCurrEdge.getDest()).setInfo("Visited");
                }
            }
        }
        while (reverseNodes.hasNext()) {
            NodeData reverseTmp = reverseNodes.next();
            if (reverseTmp.getInfo() != "Visited") {
                reverseGraph = false;
                reverseTmp.setInfo("");
            }
        }
        return regularGraph && reverseGraph;
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
        if (!isConnected()) {
            return null;
        }
        double sumMin = Double.POSITIVE_INFINITY;
        double sum = 0;
        NodeData result = null;
        Iterator<NodeData> nodes = this.graph.nodeIter();
        Iterator<NodeData> goToNodes = this.graph.nodeIter();
        while (nodes.hasNext()) {
            NodeData currNode = nodes.next();
            while (goToNodes.hasNext()) {
                NodeData currGoToNode = goToNodes.next();
                sum += shortestPathDist(currNode.getKey(), currGoToNode.getKey());
            }
            if (sum < sumMin) {
                result = currNode;
                sumMin = sum;
            }
            goToNodes = this.graph.nodeIter();
        }
        return result;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override

    public boolean save(String file) {
        try {

            JsonObject edgesObject = new JsonObject();

            JsonArray Edges = new JsonArray();
            JsonObject nodeObject = new JsonObject();
            JsonArray Nodes = new JsonArray();
            JsonObject AllObj = new JsonObject();
            ArrayList<NodeData> nodeArray = new ArrayList<>();
            Iterator<NodeData> nodeIter = this.graph.nodeIter();
            while (nodeIter.hasNext()) {
                nodeArray.add(nodeIter.next());
            }
            for (NodeData i : nodeArray) {
                edgesObject = new JsonObject();
                Iterator<EdgeData> edgeNode = this.graph.edgeIter(i.getKey());
                ArrayList<EdgeData> edgeArray = new ArrayList<>();
                while (edgeNode.hasNext()) {
                    edgeArray.add(edgeNode.next());
                }
                for (EdgeData e : edgeArray) {
                    edgesObject.addProperty("src", e.getSrc());
                    edgesObject.addProperty("w", e.getWeight());
                    edgesObject.addProperty("dest", e.getDest());
                    JsonObject ob = edgesObject.deepCopy();
                    Edges.add(ob);

                }
            }
            AllObj.add("Edges", Edges);

            for (NodeData i : nodeArray) {
                nodeObject = new JsonObject();
                String s = "" + i.getLocation();
                nodeObject.addProperty("pos", s);
                nodeObject.addProperty("id", i.getKey());
                Nodes.add(nodeObject);
                s = "";
            }
            AllObj.add("Nodes", Nodes);
            try {
                PrintWriter pw = new PrintWriter("graph.json");
                pw.write(AllObj.toString());
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override

    public boolean load(String file) {
        try
        {
            DirectedWeightedGraph loaded = new DWGraph();

            JsonParser jsonParser = new JsonParser();
            FileReader reader = new FileReader(file);
            Object obj  = jsonParser.parse(reader);


            JsonObject vertex = new JsonObject();
            JsonObject edges = new JsonObject();
            JsonObject jsonObject = (JsonObject) obj;


            JsonArray vertexArray = jsonObject.getAsJsonArray("Nodes");

            for(int i=0; i<vertexArray.size(); i++)
            {
                vertex = vertexArray.get(i).getAsJsonObject();
                int n = vertex.get("id").getAsInt();
                String pos = vertex.get("pos").getAsString();
                NodeData node = new myNode(n);
                String [] s = pos.split(",");
                node.setLocation(new myGeo(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
                loaded.addNode(node);
            }

            JsonArray edgesArray = jsonObject.getAsJsonArray("Edges");
            for(int i=0; i<edgesArray.size(); i++)
            {
                edges = edgesArray.get(i).getAsJsonObject();
                int src = edges.get("src").getAsInt();
                int dest = edges.get("dest").getAsInt();
                double w = edges.get("w").getAsDouble();
                loaded.connect(src, dest, w);
            }
            this.graph= loaded;


        }
        catch(FileNotFoundException | JsonIOException e)
        {
            e.printStackTrace();
        }
        return true;
    }

    private DirectedWeightedGraph reverseGraph() {
        Iterator<NodeData> nodes = this.graph.nodeIter();
        DirectedWeightedGraph reverse = new DWGraph();
        while (nodes.hasNext()) {
            NodeData currNode = nodes.next();
            reverse.addNode(currNode);
        }
        Iterator<EdgeData> edges = this.graph.edgeIter();
        while (edges.hasNext()) {
            EdgeData currEdge = edges.next();
            reverse.connect(currEdge.getDest(), currEdge.getSrc(), currEdge.getWeight());
        }
        return reverse;
    }
}
