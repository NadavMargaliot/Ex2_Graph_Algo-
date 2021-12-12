import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraphAlgorithmsTest {
    String G1 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json";
    String G2 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G2.json";
    String G3 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G3.json";
    String G1000 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G4_1000.json";
    String G10000 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G5_10000.json";
    String G100000 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G6_100000.json";
    DWGraphAlgorithms g1 = new DWGraphAlgorithms();
    DWGraphAlgorithms g2 = new DWGraphAlgorithms();
    DWGraphAlgorithms g3 = new DWGraphAlgorithms();
    DWGraphAlgorithms g1000 = new DWGraphAlgorithms();
    DWGraphAlgorithms g10000 = new DWGraphAlgorithms();
    DWGraphAlgorithms g100000 = new DWGraphAlgorithms();
    DWGraph g = new DWGraph();
    NodeData node1 = new myNode(1);
    NodeData node2 = new myNode(2);
    NodeData node3 = new myNode(3);
    NodeData node4 = new myNode(4);
    NodeData node5 = new myNode(5);
    NodeData node6 = new myNode(6);


    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
        DWGraphAlgorithms a = new DWGraphAlgorithms();

    }

    @Test
    void isConnected() {
        g1.load(G1);
        g2.load(G2);
        g3.load(G3);
//        g1000.load(G1000);
//        g10000.load(G10000);
//        g100000.load(G100000);
        DWGraph gr = new DWGraph();
        gr.addNode(node1);
        gr.addNode(node2);
        gr.addNode(node3);
        gr.connect(node1.getKey(), node2.getKey(), 10);
        gr.connect(node1.getKey(), node3.getKey(), 11);
        gr.connect(node3.getKey(), node1.getKey(), 25);
        gr.connect(node3.getKey(), node2.getKey(), 31);
        gr.connect(node2.getKey(), node3.getKey(), 12);
        gr.connect(node2.getKey(), node1.getKey(), 13);
        DWGraphAlgorithms graphAlgorithms = new DWGraphAlgorithms();
        graphAlgorithms.init(gr);
        assertTrue(graphAlgorithms.isConnected());
        assertTrue(g1.isConnected());
        assertTrue(g2.isConnected());
        assertTrue(g3.isConnected());
//        assertTrue(g1000.isConnected());
//       assertTrue(g10000.isConnected());
//        assertTrue(g100000.isConnected());


        NodeData a = new myNode(1);
        NodeData b = new myNode(2);
        NodeData c = new myNode(3);
        NodeData d = new myNode(4);
        NodeData e = new myNode(5);
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        g.connect(a.getKey(), b.getKey(), 1);
        g.connect(b.getKey(), c.getKey(), 1);
        g.connect(c.getKey(), d.getKey(), 1);
        g.connect(d.getKey(), e.getKey(), 1);
        g.connect(e.getKey(), a.getKey(), 1);
        g.connect(a.getKey(), d.getKey(), 1);
        DWGraphAlgorithms graphAlgorithms2 = new DWGraphAlgorithms();
        graphAlgorithms2.init(g);
        assertTrue(graphAlgorithms2.isConnected());
        g.removeEdge(e.getKey(), a.getKey());
        assertFalse(graphAlgorithms2.isConnected());


    }

    @Test
    void shortestPathDist() {
        //       g1000.load(G1000);
        //       g10000.load(G10000);
//        g100000.load(G100000);
//
//        g100000.shortestPathDist(0,99999);

        NodeData a = new myNode(1);
        NodeData b = new myNode(2);
        NodeData c = new myNode(3);
        NodeData d = new myNode(4);
        NodeData e = new myNode(5);
        NodeData f = new myNode(6);
        NodeData h = new myNode(8);
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        g.addNode(f);
        g.addNode(h);
        g.connect(a.getKey(), b.getKey(), 3);
        g.connect(a.getKey(), c.getKey(), 2);
        g.connect(c.getKey(), b.getKey(), 2);
        g.connect(c.getKey(), e.getKey(), 3);
        g.connect(b.getKey(), d.getKey(), 4);
        g.connect(b.getKey(), e.getKey(), 1);
        g.connect(d.getKey(), f.getKey(), 1);
        g.connect(e.getKey(), f.getKey(), 2);
        DWGraphAlgorithms graphAlgorithms = new DWGraphAlgorithms();
        graphAlgorithms.init(g);
        assertEquals(6, graphAlgorithms.shortestPathDist(a.getKey(), f.getKey()));
        assertEquals(3, graphAlgorithms.shortestPathDist(a.getKey(), b.getKey()));
        assertEquals(4, graphAlgorithms.shortestPathDist(a.getKey(), e.getKey()));
        assertEquals(-1, graphAlgorithms.shortestPathDist(a.getKey(), h.getKey()));
        assertEquals(-1, graphAlgorithms.shortestPathDist(b.getKey(), h.getKey()));


        DirectedWeightedGraph rr = new DWGraph();
        DirectedWeightedGraphAlgorithms test = new DWGraphAlgorithms();
        test.init(rr);
        NodeData q = new myNode(0);
        NodeData w = new myNode(1);
        NodeData r = new myNode(2);
        NodeData t = new myNode(3);
        test.getGraph().addNode(q);
        test.getGraph().addNode(w);
        test.getGraph().addNode(r);
        test.getGraph().addNode(t);
        test.getGraph().connect(0, 1, 3.0);
        test.getGraph().connect(0, 3, 7.0);
        test.getGraph().connect(1, 0, 8.0);
        test.getGraph().connect(1, 2, 2.0);
        test.getGraph().connect(2, 0, 5.0);
        test.getGraph().connect(2, 3, 1.0);
        test.getGraph().connect(3, 0, 2.0);
        assertEquals(7.0, test.shortestPathDist(3, 2));
    }

    @Test
    void shortestPath() {

//        g1000.load(G1000);
//        g10000.load(G10000);
//        g100000.load(G100000);
//
//        g100000.shortestPath(0, 99999);


        NodeData a = new myNode(1);
        NodeData b = new myNode(2);
        NodeData c = new myNode(3);
        NodeData d = new myNode(4);
        NodeData e = new myNode(5);
        NodeData f = new myNode(6);
        NodeData h = new myNode(8);
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        g.addNode(f);
        g.addNode(h);
        g.connect(a.getKey(), b.getKey(), 3);
        g.connect(a.getKey(), c.getKey(), 2);
        g.connect(c.getKey(), b.getKey(), 2);
        g.connect(c.getKey(), e.getKey(), 3);
        g.connect(b.getKey(), d.getKey(), 4);
        g.connect(b.getKey(), e.getKey(), 1);
        g.connect(d.getKey(), f.getKey(), 1);
        g.connect(e.getKey(), f.getKey(), 2);
        DWGraphAlgorithms graphAlgorithms = new DWGraphAlgorithms();
        graphAlgorithms.init(g);
        ArrayList<NodeData> expected1 = new ArrayList<>();
        expected1.add(a);
        expected1.add(b);
        expected1.add(e);
        expected1.add(f);
        ArrayList<NodeData> expected2 = new ArrayList<>();
        expected2.add(c);
        expected2.add(e);
        expected2.add(f);
        assertEquals(expected1, graphAlgorithms.shortestPath(a.getKey(), f.getKey()));
        assertEquals(expected2, graphAlgorithms.shortestPath((c.getKey()), f.getKey()));
        assertNull(graphAlgorithms.shortestPath(a.getKey(), h.getKey()));
    }

    @Test
    void center() {
        DirectedWeightedGraphAlgorithms algo1 = new DWGraphAlgorithms();
        algo1.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
        System.out.println("G1 " + algo1.center().getKey());
        DirectedWeightedGraphAlgorithms algo2 = new DWGraphAlgorithms();
        algo2.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G2.json");
        System.out.println("G2 " + algo2.center().getKey());
        DirectedWeightedGraphAlgorithms algo3 = new DWGraphAlgorithms();
        algo3.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G3.json");
        System.out.println("G3 " + algo3.center().getKey());

//        g1000.load(G1000);
////        g10000.load(G10000);
////        g100000.load(G100000);
//        g1000.center();


    }

    @Test
    void tsp() {

//        g1000.load(G1000);
//        ArrayList<NodeData> nodes = new ArrayList<>();
//        while (g1000.getGraph().nodeIter().hasNext()){
//            nodes.add(g1000.getGraph().nodeIter().next());
//        }
//        g1000.tsp(nodes);


        DWGraph g = new DWGraph();
        NodeData a = new myNode(1);
        NodeData b = new myNode(2);
        NodeData c = new myNode(3);
        NodeData d = new myNode(4);
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.connect(a.getKey(), b.getKey(), 3);
        g.connect(a.getKey(), c.getKey(), 2);
        g.connect(c.getKey(), b.getKey(), 2);
        g.connect(c.getKey(), a.getKey(), 3);
        g.connect(b.getKey(), d.getKey(), 4);
        g.connect(d.getKey(), c.getKey(), 9);
        g.connect(d.getKey(), b.getKey(), 4);
        g.connect(b.getKey(), c.getKey(), 2);
        List<NodeData> aa = new ArrayList<>();
        aa.add(d);
        aa.add(b);
        aa.add(c);
        aa.add(a);
        DWGraphAlgorithms graphAlgorithms = new DWGraphAlgorithms();
        graphAlgorithms.init(g);
        aa = graphAlgorithms.tsp(aa);
        List<NodeData> bb = List.of(new NodeData[]{a, c, b, d});
        assertEquals(aa, bb);
    }

    @Test
    void save() {
        DirectedWeightedGraph graph = new DWGraph();
        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.connect(1, 2, 2);
        graph.connect(2, 1, 2);
        graph.connect(2, 3, 3);
        graph.connect(3, 2, 3);
        graph.connect(3, 4, 1);
        graph.connect(4, 3, 1);
        graph.connect(3, 5, 4);
        graph.connect(5, 3, 4);
        graph.connect(5, 4, 4);
        graph.connect(4, 5, 4);
        graph.connect(3, 6, 4);
        graph.connect(6, 3, 4);
        algo.init(graph);
        algo.save("a.txt");


    }

    @Test
    void load() {
        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
        algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
        assertEquals(17, algo.getGraph().nodeSize());
        assertEquals(35.21007339305892, algo.getGraph().getNode(3).getLocation().x());
    }
}