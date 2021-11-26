import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraphAlgorithmsTest {
    DWGraph g = new DWGraph();
    NodeData node1 = new myNode(1);
    NodeData node2 = new myNode(2);
    NodeData node3 = new myNode(3);


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
        DWGraph a = new DWGraph();
        a.addNode(node1);
        a.addNode(node2);
        a.addNode(node3);
        a.connect(node1.getKey() , node2.getKey() , 10);
        a.connect(node1.getKey() , node3.getKey() , 11);
        a.connect(node3.getKey() , node1.getKey() , 25);
        a.connect(node3.getKey() , node2.getKey() , 31);
        a.connect(node2.getKey() , node3.getKey() , 12);
        a.connect(node2.getKey() , node1.getKey() , 13);
        DWGraphAlgorithms graphAlgorithms = new DWGraphAlgorithms();
        graphAlgorithms.init(a);
        assertTrue(graphAlgorithms.isConnected());
    }

    @Test
    void shortestPathDist() {
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
        g.connect(a.getKey(),b.getKey() , 3);
        g.connect(a.getKey(),c.getKey(),2);
        g.connect(c.getKey(),b.getKey(),2);
        g.connect(c.getKey(),e.getKey(),3);
        g.connect(b.getKey(),d.getKey(),4);
        g.connect(b.getKey(),e.getKey(),1);
        g.connect(d.getKey(),f.getKey(),1);
        g.connect(e.getKey(),f.getKey(),2);
        DWGraphAlgorithms graphAlgorithms = new DWGraphAlgorithms();
        graphAlgorithms.init(g);
        assertEquals(6 , graphAlgorithms.shortestPathDist(a.getKey(),f.getKey()));
        assertEquals(3 , graphAlgorithms.shortestPathDist(a.getKey(),b.getKey()));
        assertEquals(4 , graphAlgorithms.shortestPathDist(a.getKey(),e.getKey()));
        assertEquals(-1 , graphAlgorithms.shortestPathDist(a.getKey(),h.getKey()));
        assertEquals(-1 , graphAlgorithms.shortestPathDist(b.getKey(),h.getKey()));
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}