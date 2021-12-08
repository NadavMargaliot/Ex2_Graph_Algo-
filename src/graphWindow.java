import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.GeoLocation;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


public class graphWindow extends JFrame implements ActionListener, MouseListener , MouseMotionListener {
    ArrayList<GeoLocation> locationsList = new ArrayList<>();
    DirectedWeightedGraphAlgorithms algo;


    public graphWindow(DirectedWeightedGraphAlgorithms algorithm) throws HeadlessException{
        this.algo = algorithm;
        Iterator<NodeData> itr = this.algo.getGraph().nodeIter();
        while (itr.hasNext()){
            NodeData tmp = itr.next();
            this.locationsList.add(tmp.getLocation());
        }
//        this.add(new myPanel());
        initialize();
    }

    private void initialize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(screenSize.width / 2, screenSize.height / 2);
        this.setTitle("Directed Weighted Graph");
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        mb.add(menu);
        this.add(mb);
        JMenu saveLoad = new JMenu("Save/Load");
        saveLoad.addActionListener(this);
        JMenu editGraph = new JMenu("Edit graph");
        editGraph.addActionListener(this);
        JMenu algorithm = new JMenu("Algorithm");
        algorithm.addActionListener(this);
        menu.add(saveLoad);
        menu.add(editGraph);
        menu.add(algorithm);
        JMenuItem removeNode = new JMenuItem("Remove Node");
        removeNode.addActionListener(this);
        JMenuItem addNode = new JMenuItem("Add Node");
        addNode.addActionListener(this);
        JMenuItem removeEdge = new JMenuItem("Remove Edge");
        removeEdge.addActionListener(this);
        JMenuItem addEdge = new JMenuItem("Add Edge");
        addEdge.addActionListener(this);
        editGraph.add(removeNode);
        editGraph.add(addNode);
        editGraph.add(removeEdge);
        editGraph.add(addEdge);
        JMenuItem isConnected = new JMenuItem("Is Connected");
        isConnected.addActionListener(this);

        JMenu shortestPath = new JMenu("Shorted Path");
        shortestPath.addActionListener(this);
        JMenuItem shortestPathDist = new JMenuItem("Shortest Path Distance");
        shortestPathDist.addActionListener(this);
        JMenuItem shortestPath2 = new JMenuItem("ShortestPath");
        shortestPath2.addActionListener(this);
        shortestPath.add(shortestPathDist);
        shortestPath.add(shortestPath2);

        JMenuItem center = new JMenuItem("Center");
        center.addActionListener(this);
        JMenuItem tsp = new JMenuItem("Tsp");
        tsp.addActionListener(this);
        algorithm.add(isConnected);
        algorithm.add(shortestPath);
        algorithm.add(center);
        algorithm.add(tsp);


        this.setVisible(true);




    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

//    public static void main(String[] args) {
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        JFrame j = new JFrame();
//        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        j.setSize(screenSize.width/2, screenSize.height/2);
//        ImageIcon img = new ImageIcon("/Users/adielbenmeir/Desktop/dwg/DWGPIC");
//        j.setIconImage(img.getImage());
//        j.setTitle("Directed Weighted Graph");
//        j.setVisible(true);
//    }


//    public graphWindow(DirectedWeightedGraphAlgorithms algorithm){
//        this.algo = algorithm;
//        DirectedWeightedGraph graph = algo.getGraph();
//        Iterator<NodeData> nodes = graph.nodeIter();
//        while (nodes.hasNext()) {
//            NodeData curr = nodes.next();
//            GeoLocation tmp = curr.getLocation();
//            this.locationsList.add(tmp);
//        }
//    }
    public class myPanel extends JPanel {
//        @Override
        ArrayList<GeoLocation> locations = new ArrayList<>();

        public myPanel(){
            DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
            algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
            DirectedWeightedGraph graph = algo.getGraph();
            Iterator<NodeData> itr = graph.nodeIter();
            while (itr.hasNext()){
                NodeData tmp = itr.next();
                this.locations.add(tmp.getLocation());
            }

//            GeoLocation a = new myGeo(10,100,0);
//            GeoLocation b = new myGeo(20,150,0);
//            GeoLocation c = new myGeo(30,200,0);
////            this.locations.add(a);
////            this.locations.add(b);
//            this.locations.add(c);

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            ArrayList<GeoLocation> arr = new ArrayList<>();
            DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
            algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
            DirectedWeightedGraph graph = algo.getGraph();
            Iterator<NodeData> itr = graph.nodeIter();
            while (itr.hasNext()){
                NodeData tmp = itr.next();
                arr.add(tmp.getLocation());
            }
            for(int i = 0; i < arr.size(); i++){
                g.setColor(Color.BLACK);
                g.fillOval((int)arr.get(i).x() ,(int)arr.get(i).y(),10,10 );
            }
//            for(GeoLocation geo : locations){
//                g.setColor(Color.BLACK);
//                g.fillOval((int)geo.x(),(int)geo.y(),10,10);
//            }



        }
    }


    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
        algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
        new graphWindow(algo);
    }

}
