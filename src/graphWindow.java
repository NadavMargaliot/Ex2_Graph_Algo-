import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


public class graphWindow extends JFrame implements ActionListener {
    DirectedWeightedGraph graph;
    DirectedWeightedGraphAlgorithms algo;
    Dimension screenSize;
    JLabel label;
    JLabel miniLabel;
    JFrame miniFrame;
    JPanel miniPanel;
    JMenuBar menu;
    JMenu editGraph;
    JMenu algorithm;
    JMenu saveLoad;
    JMenuItem save;
    JMenuItem load;
    JMenuItem removeNode;
    JMenuItem addNode;
    JMenuItem removeEdge;
    JMenuItem addEdge;
    JMenuItem isConnected;
    JMenuItem shortestPath;
    JMenuItem center;
    JMenuItem tsp;
    JButton loadButton;
    JButton saveButton;
    JButton addNodeButton;
    JButton removeNodeButton;
    JButton addEdgeButton;
    JButton removeEdgeButton;
    JButton isConnectedButton;
    JButton shortestPathButton;
    JButton shortestPathDistButton;
    JPanel panel;
    JTextField srcFromUser;
    JTextField destFromUser;
    JTextField saveFromUser;
    JTextField loadFromUser;
    int srcForShortest;
    int destForShortest;
    ArrayList<NodeData> locationsList = new ArrayList<>();

    public class myPanel extends JPanel {
        ArrayList<NodeData> nodes = new ArrayList<>();
        ArrayList<EdgeData> edges = new ArrayList<>();
        Iterator<NodeData> nodeItr;
        Iterator<EdgeData> edgeItr;

        public myPanel() {
            super();
            this.nodeItr = graph.nodeIter();
            this.edgeItr = graph.edgeIter();
            while (nodeItr.hasNext()) {
                nodes.add(nodeItr.next());
            }
            while (edgeItr.hasNext()) {
                edges.add(edgeItr.next());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < nodes.size(); i++) {
                NodeData curr = nodes.get(i);
                int x = (int) curr.getLocation().x();
                int y = (int) curr.getLocation().y();
                g.setColor(Color.BLACK);
                g.fillOval(x, y, 20, 20);
                g.drawString("Key" + curr.getKey(), x - 5, y - 5);
            }
        }
    }


    public graphWindow(DirectedWeightedGraphAlgorithms algorithm) throws HeadlessException {
        super();
        JFrame frame = new JFrame();
        this.algo = algorithm;
        Iterator<NodeData> itr = this.algo.getGraph().nodeIter();
        while (itr.hasNext()) {
            NodeData tmp = itr.next();
            this.locationsList.add(tmp);
        }
//        this.add(new myPanel());
        initialize();
    }

    private void initialize() {

        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(this.screenSize.width / 2, this.screenSize.height / 2);
        this.setTitle("Directed Weighted Graph");
        String G1 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json";
        this.algo.load(G1);
        this.graph = algo.getGraph();

        createMenuBar();
        this.setVisible(true);

    }

    private void createMenuBar() {

        this.menu = new JMenuBar();
        this.add(menu);
        this.saveLoad = new JMenu("Save/Load");
        this.saveLoad.addActionListener(this);
        this.saveLoad.setVerticalAlignment(JMenu.TOP);
        this.load = new JMenuItem("Load from JSON");
        this.load.addActionListener(this);
        this.save = new JMenuItem("Save to JSON");
        this.save.addActionListener(this);
        this.editGraph = new JMenu("Edit graph");
        this.editGraph.addActionListener(this);
        this.editGraph.setVerticalAlignment(JMenu.TOP);
        this.algorithm = new JMenu("Algorithm");
        this.algorithm.addActionListener(this);
        this.saveLoad.add(load);
        this.saveLoad.add(save);
        this.menu.add(saveLoad);
        this.menu.add(editGraph);
        this.menu.add(algorithm);
        this.removeNode = new JMenuItem("Remove Node");
        this.removeNode.addActionListener(this);
        this.addNode = new JMenuItem("Add Node");
        this.addNode.addActionListener(this);
        this.removeEdge = new JMenuItem("Remove Edge");
        this.removeEdge.addActionListener(this);
        this.addEdge = new JMenuItem("Add Edge");
        this.addEdge.addActionListener(this);
        this.editGraph.add(removeNode);
        this.editGraph.add(addNode);
        this.editGraph.add(removeEdge);
        this.editGraph.add(addEdge);
        this.isConnected = new JMenuItem("Is Connected?");
        this.isConnected.addActionListener(this);
        this.shortestPath = new JMenuItem("Shorted Path");
        this.shortestPath.addActionListener(this);
        this.center = new JMenuItem("Center");
        this.center.addActionListener(this);
        this.tsp = new JMenuItem("TSP");
        this.tsp.addActionListener(this);
        this.algorithm.add(isConnected);
        this.algorithm.add(shortestPath);
        this.algorithm.add(center);
        this.algorithm.add(tsp);
        this.algorithm.setVerticalAlignment(JMenu.TOP);
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.loadButton = new JButton("Load");
        JTextField loadText = new JTextField();
        JLabel loadLabel = new JLabel("Enter path to load:");
        this.loadButton.setBounds(50, 150, 50, 20);
        loadText.setBounds(50, 50, 100, 20);
        loadLabel.setBounds(50, 100, 50, 20);

        this.miniLabel = new JLabel();
        this.miniPanel = new JPanel();
        this.miniFrame = new JFrame();
        this.miniFrame.setSize(600, 300);

        this.srcFromUser = new JTextField(3);
        this.destFromUser = new JTextField(3);
        this.saveFromUser = new JTextField(30);
        this.loadFromUser = new JTextField(30);




        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setSize(50, 30);
        saveButton.setBounds(165, 110, 65, 30);
        saveButton.addActionListener(this);

        loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.setSize(50, 30);
        loadButton.setBounds(165, 110, 65, 30);
        loadButton.addActionListener(this);

        addNodeButton = new JButton();
        addNodeButton.setSize(50, 30);
        addNodeButton.setBounds(165, 110, 65, 30);
        addNodeButton.addActionListener(this);

        removeNodeButton = new JButton();
        removeNodeButton.setSize(50, 30);
        removeNodeButton.setBounds(165, 110, 65, 30);
        removeNodeButton.addActionListener(this);

        addEdgeButton = new JButton();
        addEdgeButton.setSize(50, 30);
        addEdgeButton.setBounds(165, 110, 65, 30);
        addEdgeButton.addActionListener(this);

        removeEdgeButton = new JButton();
        removeEdgeButton.setSize(50, 30);
        removeEdgeButton.setBounds(165, 110, 65, 30);
        removeEdgeButton.addActionListener(this);

        isConnectedButton = new JButton();
        isConnectedButton.setText("Done");
        isConnectedButton.setSize(50, 30);
        isConnectedButton.setBounds(165, 110, 65, 30);
        isConnectedButton.addActionListener(this);

        shortestPathButton = new JButton();
        shortestPathButton.setText("Shortest Distance Way");
        shortestPathButton.setSize(50, 30);
        shortestPathButton.setBounds(165, 110, 65, 30);
        shortestPathButton.addActionListener(this);

        shortestPathDistButton = new JButton();
        shortestPathDistButton.setText("Shortest Distance");
        shortestPathDistButton.setSize(50, 30);
        shortestPathDistButton.setBounds(165, 110, 65, 30);
        shortestPathDistButton.addActionListener(this);


//        this.label = new JLabel("Click the menu bar to choose function");
//        this.label.setBounds(30, 400, (this.screenSize.width / 2) - 30, (this.screenSize.height / 2) - 270);
//        this.panel.add(label);
//


//        panel.add(loadButton);
//        panel.add(loadText);
//        panel.add(loadLabel);
//
//        this.add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.save) {
            this.miniPanel.removeAll();
            saveFromUser.setText("");
            this.miniLabel.setBounds(120,30,400,30);
            this.miniLabel.setText("Enter full file location");
            this.miniPanel.add(saveButton);
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(saveFromUser);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == saveButton){
            this.miniFrame.dispose();
            String g1 = saveFromUser.getText();
            this.algo.save(g1);
        } else if (e.getSource() == this.isConnected) {
            this.miniPanel.removeAll();
//            this.miniPanel.add(isConnectedButton);
            this.miniLabel.setBounds(120, 50, 200, 50);
            if (this.algo.isConnected()) {
                this.miniLabel.setText("The graph is connected!");
            } else {
                this.miniLabel.setText("The graph is NOT connected!");
            }
            this.miniPanel.add(miniLabel);
            this.add(miniPanel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setSize(400, 200);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.center) {
            this.miniPanel.removeAll();
            this.miniLabel.setBounds(120, 50, 200, 50);
            this.miniLabel.setText("The center source number is " + this.algo.center().getKey());
            this.miniPanel.add(miniLabel);
            this.add(miniPanel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setSize(400, 200);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.shortestPath) {
            this.miniPanel.removeAll();
            srcFromUser.setText("");
            destFromUser.setText("");
            this.miniLabel.setText("Enter first SRC and than DEST to find shortest path:");
            this.miniLabel.setBounds(100, 30, 400, 20);
            this.algo.init(this.graph);
            this.miniPanel.add(shortestPathButton);
            this.miniPanel.add(shortestPathDistButton);
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(srcFromUser);
            this.miniPanel.add(destFromUser);
            this.miniFrame.add(this.miniPanel);

//            this.srcForShortest = Integer.parseInt(this.srcFromUser.getText());
//            this.destForShortest = Integer.parseInt(this.destFromUser.getText());
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.shortestPathDistButton) {
//            this.srcForShortest = Integer.parseInt(this.srcFromUser.getText());
//            this.destForShortest = Integer.parseInt(this.destFromUser.getText());
            this.miniFrame.dispose();
            this.miniPanel.removeAll();
            this.miniLabel.setText("The shortest Distance is " +
                    this.algo.shortestPathDist(srcForShortest,destForShortest));
            this.miniLabel.setBounds(100, 30, 400, 20);
            this.miniPanel.add(miniLabel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        }

    }

    ;


//    public class myPanel extends JPanel {
//        //        @Override
//        ArrayList<GeoLocation> locations = new ArrayList<>();
//
//        public myPanel() {
//            DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
//            algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
//            DirectedWeightedGraph graph = algo.getGraph();
//            Iterator<NodeData> itr = graph.nodeIter();
//            while (itr.hasNext()) {
//                NodeData tmp = itr.next();
//                this.locations.add(tmp.getLocation());
//            }
//
////            GeoLocation a = new myGeo(10,100,0);
////            GeoLocation b = new myGeo(20,150,0);
////            GeoLocation c = new myGeo(30,200,0);
//////            this.locations.add(a);
//////            this.locations.add(b);
////            this.locations.add(c);
//
//        }
//
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            ArrayList<GeoLocation> arr = new ArrayList<>();
//            DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
//            algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
//            DirectedWeightedGraph graph = algo.getGraph();
//            Iterator<NodeData> itr = graph.nodeIter();
//            while (itr.hasNext()) {
//                NodeData tmp = itr.next();
//                arr.add(tmp.getLocation());
//            }
//            for (int i = 0; i < arr.size(); i++) {
//                g.setColor(Color.BLACK);
//                g.fillOval((int) arr.get(i).x(), (int) arr.get(i).y(), 10, 10);
//            }
////            for(GeoLocation geo : locations){
////                g.setColor(Color.BLACK);
////                g.fillOval((int)geo.x(),(int)geo.y(),10,10);
////            }
//
//
//        }
//    }


    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
        algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
        new graphWindow(algo);
    }

}
