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
    JTextField saveLoadFromUser;
    JTextField aFromUser;
    JTextField bFromUser;
    JTextField cFromUser;
    int textValue1;
    int textValue2;
    double textValue3;
    double xForGeo;
    double yForGeo;
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

//        public void setNew() {
//            this.nodeItr = graph.nodeIter();
//            this.edgeItr = graph.edgeIter();
//            this.nodes = new ArrayList<>();
//            this.edges = new ArrayList<>();
//
//            while (nodeItr.hasNext()) {
//                this.nodes.add(nodeItr.next());
//            }
//            while (edgeItr.hasNext()) {
//                this.edges.add(edgeItr.next());
//            }
//        }


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
        loadText.setBounds(50, 50, 100, 20);
        loadLabel.setBounds(50, 100, 50, 20);

        this.miniLabel = new JLabel();
        this.miniPanel = new JPanel();
        this.miniFrame = new JFrame();
        this.miniFrame.setSize(600, 300);

//        this.srcFromUser = new JTextField(3);
//        this.destFromUser = new JTextField(3);
        this.saveLoadFromUser = new JTextField(30);
//        this.loadFromUser = new JTextField(30);
        this.aFromUser = new JTextField(3);
        this.bFromUser = new JTextField(3);
        this.cFromUser = new JTextField(3);


        this.saveButton = new JButton();
        this.saveButton.setText("Save");
        this.saveButton.setSize(50, 30);
        this.saveButton.setBounds(165, 110, 65, 30);
        this.saveButton.addActionListener(this);

        this.loadButton = new JButton();
        this.loadButton.setText("Load");
        this.loadButton.setSize(50, 30);
        this.loadButton.setBounds(165, 110, 65, 30);
        this.loadButton.addActionListener(this);

        this.addNodeButton = new JButton();
        this.addNodeButton.setSize(50, 30);
        this.addNodeButton.setBounds(165, 110, 65, 30);
        this.addNodeButton.addActionListener(this);

        this.removeNodeButton = new JButton();
        this.removeNodeButton.setSize(50, 30);
        this.removeNodeButton.setBounds(165, 110, 65, 30);
        this.removeNodeButton.addActionListener(this);

        this.addEdgeButton = new JButton();
        this.addEdgeButton.setSize(50, 30);
        this.addEdgeButton.setBounds(165, 110, 65, 30);
        this.addEdgeButton.addActionListener(this);

        this.removeEdgeButton = new JButton();
        this.removeEdgeButton.setSize(50, 30);
        this.removeEdgeButton.setBounds(165, 110, 65, 30);
        this.removeEdgeButton.addActionListener(this);

        this.isConnectedButton = new JButton();
        this.isConnectedButton.setText("Done");
        this.isConnectedButton.setSize(50, 30);
        this.isConnectedButton.setBounds(165, 110, 65, 30);
        this.isConnectedButton.addActionListener(this);

        this.shortestPathButton = new JButton();
        this.shortestPathButton.setText("Shortest Distance Way");
        this.shortestPathButton.setSize(50, 30);
        this.shortestPathButton.setBounds(165, 110, 65, 30);
        this.shortestPathButton.addActionListener(this);

        this.shortestPathDistButton = new JButton();
        this.shortestPathDistButton.setText("Shortest Distance");
        this.shortestPathDistButton.setSize(50, 30);
        this.shortestPathDistButton.setBounds(165, 110, 65, 30);
        this.shortestPathDistButton.addActionListener(this);


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
            saveLoadFromUser.setText("");
            this.miniLabel.setBounds(120, 30, 400, 30);
            this.miniLabel.setText("Enter full file location");
            this.miniPanel.add(saveButton);
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(saveLoadFromUser);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == saveButton) {
            this.miniFrame.dispose();
            String g1 = saveLoadFromUser.getText();
            this.algo.save(g1);
        } else if (e.getSource() == this.addNode) {
            this.miniPanel.removeAll();
            aFromUser.setText("");
            bFromUser.setText("");
            addNodeButton.setText("Enter location");
            aFromUser.setSize(60, 30);
            aFromUser.setBounds(130, 60, 60, 20);
            bFromUser.setSize(60, 30);
            bFromUser.setBounds(190, 60, 60, 20);
            this.algo.init(this.graph);
            this.miniPanel.add(addNodeButton);
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(aFromUser);
            this.miniPanel.add(bFromUser);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == addNodeButton) {
            miniPanel.removeAll();
            this.xForGeo = Double.parseDouble(this.aFromUser.getText());
            this.yForGeo = Double.parseDouble(this.bFromUser.getText());
            NodeData node = new myNode();
            node.setLocation(new myGeo((double) xForGeo, (double) yForGeo, (double) 0));
            this.graph.addNode(node);
            this.miniFrame.dispose();
            System.out.println(node.getKey());
            this.miniLabel.setText("Node " + node.getKey() + " was added succesfully!");
            this.miniPanel.add(miniLabel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.removeNode) {
            if (this.graph != null) {
                this.miniPanel.removeAll();
                this.miniLabel.setText("Enter the key number of node to remove:");
                this.aFromUser.setText("");
//                this.keyForNode = Integer.parseInt(keyFromUser.getText());
                this.removeNodeButton.setText("Remove");
                this.miniPanel.add(this.removeNodeButton);
//                this.graph.removeNode(keyForNode);
                this.miniPanel.add(miniLabel);
                this.miniPanel.add(aFromUser);
                this.miniFrame.add(this.miniPanel);
                this.miniFrame.setLocationRelativeTo(this);
                this.miniFrame.setVisible(true);
            }
        } else if (e.getSource() == this.removeNodeButton) {
            this.miniPanel.removeAll();
            this.textValue1 = Integer.parseInt(this.aFromUser.getText());
            if (this.graph.getNode(textValue1) == null) {
                this.miniLabel.setText("There is no such node");
            } else {
                this.miniLabel.setText(("Node " + textValue1 + " was removed"));
                this.graph.removeNode(textValue1);
            }
            this.miniPanel.add(this.miniLabel);
            this.add(miniPanel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setSize(400, 200);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);

        } else if (e.getSource() == this.addEdge) {
            if (this.graph != null) {
                this.miniPanel.removeAll();
                this.aFromUser.setText("");
                this.bFromUser.setText("");
                this.cFromUser.setText("");
                addEdgeButton.setText("Add Edge");
                this.miniLabel.setText("Enter SRC , DEST and WEIGHT for your new EDGE:");
                this.algo.init(this.graph);
                this.miniPanel.add(this.miniLabel);
                this.miniPanel.add(this.aFromUser);
                this.miniPanel.add(this.bFromUser);
                this.miniPanel.add(this.cFromUser);
                this.miniPanel.add(this.addEdgeButton);
                this.miniFrame.add(this.miniPanel);
                this.miniFrame.setLocationRelativeTo(this);
                this.miniFrame.setVisible(true);
            }
        } else if (e.getSource() == this.addEdgeButton) {
            this.textValue1 = Integer.parseInt(aFromUser.getText());
            this.textValue2 = Integer.parseInt(bFromUser.getText());
            this.textValue3 = Double.parseDouble(cFromUser.getText());
            this.graph.connect(textValue1,textValue2,textValue3);
            this.miniFrame.dispose();
            this.miniPanel.removeAll();
            this.miniLabel.setText("Edge has been added!");
            this.miniPanel.add(miniLabel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.removeEdge) {

        } else if (e.getSource() == this.removeEdgeButton) {

        } else if (e.getSource() == this.isConnected) {
            this.miniPanel.removeAll();
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
            aFromUser.setText("");
            bFromUser.setText("");
            this.miniLabel.setText("Enter first SRC and than DEST to find shortest path:");
            this.miniLabel.setBounds(100, 30, 400, 20);
            this.algo.init(this.graph);
            this.miniPanel.add(shortestPathButton);
            this.miniPanel.add(shortestPathDistButton);
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(aFromUser);
            this.miniPanel.add(bFromUser);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.shortestPathDistButton) {
            this.miniFrame.dispose();
            textValue1 = Integer.parseInt(aFromUser.getText());
            textValue2 = Integer.parseInt(bFromUser.getText());
            if (this.graph.getNode(textValue1) == null) {
                this.miniLabel.setText("there is no such src");
            }
            if (this.graph.getNode(textValue2) == null) {
                this.miniLabel.setText("there is no such dest");
            }
            this.miniPanel.removeAll();
            double distance = this.algo.shortestPathDist((int) textValue1, (int) textValue2);
            this.miniLabel.setText("The shortest Distance between Node " + textValue1
                    + " and Node " + textValue2 + " is " + distance);
            this.miniLabel.setBounds(100, 30, 400, 20);
            this.miniPanel.add(miniLabel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.shortestPathButton) {
            this.miniFrame.dispose();
            textValue1 = Integer.parseInt(aFromUser.getText());
            textValue2 = Integer.parseInt(bFromUser.getText());
            if (this.graph.getNode(textValue1) == null) {
                this.miniLabel.setText("there is no such src");
            }
            if (this.graph.getNode(textValue2) == null) {
                this.miniLabel.setText("there is no such dest");
            }
            if (this.algo.shortestPath(textValue1, textValue2) == null) {
                this.miniLabel.setText("there is no path between those two nodes");
            }
            this.miniPanel.removeAll();
            if (this.graph.getNode(textValue1) != null && this.graph.getNode(textValue2) != null &&
                    this.algo.shortestPath(textValue1, textValue2) != null) {
                java.util.List<NodeData> wayNodes = this.algo.shortestPath((int) textValue1, (int) textValue2);
                String txt = "The Shortest Way Is ";
                for (int i = 0; i < wayNodes.size(); i++) {
                    txt += wayNodes.get(i).getKey() + " ";
                }
                this.miniLabel.setText(txt);
            }
            this.miniPanel.add(miniLabel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        }
    }


    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
        algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
        new graphWindow(algo);
    }

}
