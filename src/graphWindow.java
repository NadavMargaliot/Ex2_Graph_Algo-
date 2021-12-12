import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class graphWindow extends JFrame implements ActionListener {
    myPanel panel;
    DirectedWeightedGraph graph;
    DirectedWeightedGraphAlgorithms algo;
    Dimension screenSize;
    JLabel label;
    JLabel miniLabel;
    JFrame miniFrame;
    JPanel miniPanel;
    JMenuBar mb;
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
    JButton shortestPathButton;
    JButton addNodeForTSP;
    JButton doneTSP;
    JTextField saveLoadFromUser;
    JTextField aFromUser;
    JTextField bFromUser;
    JTextField cFromUser;
    double textValue1;
    double textValue2;
    double textValue3;
    int width;
    int height;
    double minX;
    double maxX;
    double minY;
    double maxY;
    java.util.List<NodeData> tspList = new ArrayList<>();
    java.util.List<NodeData> shortestWay = new ArrayList<>();

    public class myPanel extends JPanel {
        ArrayList<NodeData> nodes = new ArrayList<>();
        ArrayList<EdgeData> edges = new ArrayList<>();
        ArrayList<NodeData> shortest = new ArrayList<>();
        Iterator<NodeData> nodeItr;
        Iterator<EdgeData> edgeItr;
        int centerNode;

        public myPanel() {
            super();
            this.centerNode = -1;
            this.nodeItr = algo.getGraph().nodeIter();
            this.edgeItr = algo.getGraph().edgeIter();
            while (nodeItr.hasNext()) {
                nodes.add(nodeItr.next());
            }
            while (edgeItr.hasNext()) {
                edges.add(edgeItr.next());
            }
        }

        public void makeShortestWay(ArrayList<NodeData> shortestWay) {
            this.shortest = new ArrayList<>(shortestWay.size());
            while (!shortestWay.isEmpty()) {
                this.shortest.add(shortestWay.get(0));
                shortestWay.remove(0);
            }
        }

        public void update() {
            this.nodeItr = graph.nodeIter();
            this.edgeItr = graph.edgeIter();
            this.nodes = new ArrayList<>();
            this.edges = new ArrayList<>();
            while (nodeItr.hasNext()) {
                this.nodes.add(nodeItr.next());
            }
            while (edgeItr.hasNext()) {
                this.edges.add(edgeItr.next());
            }
            while (!this.shortest.isEmpty()) {
                this.shortest.remove(0);
            }
            this.centerNode = -1;
        }


        private NodeData validKey(int key) {
            for (NodeData node : this.nodes) {
                if (node.getKey() == key)
                    return node;
            }
            return null;
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (NodeData temp : this.nodes) {
                int xPixel = (int) scaleX(temp.getLocation().x()) + 10;
                int yPixel = (int) scaleY(temp.getLocation().y()) + 40;
                if (temp.getKey() == this.centerNode) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillOval(xPixel, yPixel, 15, 15);
                g.setColor(Color.blue);
                g.drawString("" + temp.getKey(), xPixel, yPixel + 25);
            }
            for (EdgeData temp : this.edges) {
                NodeData currFrom = validKey(temp.getSrc());
                NodeData currTo = validKey(temp.getDest());
                assert currFrom != null;
                double xFrom = scaleX(currFrom.getLocation().x()) + 10;
                double yFrom = scaleY(currFrom.getLocation().y()) + 40;
                assert currTo != null;
                double xTo = scaleX(currTo.getLocation().x()) + 10;
                double yTo = scaleY(currTo.getLocation().y()) + 40;
                double weight = temp.getWeight();
                String weightStr = String.format("%.3f",weight);
                if (this.shortest.contains(currFrom) && this.shortest.contains(currTo)) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.MAGENTA);
                }
                drawArrowLine(g, (int) (xFrom + 7), (int) (yFrom + 7), (int) (xTo + 7), (int) (yTo + 7), 5, 5);
                g.setColor(Color.black);
                g.setFont(new Font("Ariel" , Font.BOLD,9));
                g.drawString(weightStr, (int)((xFrom * 0.3) + (xTo * 0.7)),  (int)((yFrom * 0.3) + (yTo * 0.7)));
            }
        }

        private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
            int dx = x2 - x1, dy = y2 - y1;
            double D = Math.sqrt(dx * dx + dy * dy);
            double xm = D - d, xn = xm, ym = h, yn = -h, x;
            double sin = dy / D, cos = dx / D;
            x = xm * cos - ym * sin + x1;
            ym = xm * sin + ym * cos + y1;
            xm = x;
            x = xn * cos - yn * sin + x1;
            yn = xn * sin + yn * cos + y1;
            xn = x;
            int[] xpoints = {x2, (int) xm, (int) xn};
            int[] ypoints = {y2, (int) ym, (int) yn};
            g.drawLine(x1, y1, x2, y2);
            g.fillPolygon(xpoints, ypoints, 3);
        }
    }


    public graphWindow(DirectedWeightedGraphAlgorithms algorithm) throws HeadlessException {
        super();
        this.miniLabel = new JLabel();
        this.miniPanel = new JPanel();
        this.miniFrame = new JFrame();
        this.label = new JLabel("Click the menu bar to choose function");
        this.label.setBounds(width / 2,height,width/2,height);
        this.miniFrame.setSize(200, 100);
        this.algo = algorithm;
        this.graph = algo.getGraph();
        this.miniPanel.setSize(400, 200);
        this.miniFrame.setSize(500, 200);
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = this.screenSize.width / 2;
        this.height = this.screenSize.height / 2;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width + 50, height + 150);
        this.setTitle("Directed Weighted Graph");
        this.minX = minX();
        this.minY = minY();
        this.maxX = maxX();
        this.maxY = maxY();
        createMenu();
        this.panel = new myPanel();
        this.panel.add(this.label);
        this.add(panel);
        this.setVisible(true);
    }

    private double minX() {
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double x = Double.POSITIVE_INFINITY;
        while (nodeDataIterator.hasNext()) {
            NodeData curr = nodeDataIterator.next();
            if (curr.getLocation().x() < x) {
                x = curr.getLocation().x();
            }
        }
        return x;
    }

    private double maxX() {
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double x = Double.NEGATIVE_INFINITY;
        while (nodeDataIterator.hasNext()) {
            NodeData curr = nodeDataIterator.next();
            if (curr.getLocation().x() > x) {
                x = curr.getLocation().x();
            }
        }
        return x;
    }

    private double minY() {
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double y = Double.POSITIVE_INFINITY;
        while (nodeDataIterator.hasNext()) {
            NodeData curr = nodeDataIterator.next();
            if (curr.getLocation().y() < y) {
                y = curr.getLocation().y();
            }
        }
        return y;
    }

    private double maxY() {
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double y = Double.NEGATIVE_INFINITY;
        while (nodeDataIterator.hasNext()) {
            NodeData curr = nodeDataIterator.next();
            if (curr.getLocation().y() > y) {
                y = curr.getLocation().y();
            }
        }
        return y;
    }

    private double scaleX(double x) {
        return this.width * (x - this.minX) / (this.maxX - this.minX);
    }

    private double scaleY(double y) {
        return this.height * (this.maxY - y) / (this.maxY - this.minY);
    }

    private void createMenu() {
        this.mb = new JMenuBar();
        this.saveLoad = new JMenu("Save/Load");
        this.saveLoad.addActionListener(this);
        this.saveLoad.setVerticalAlignment(JMenu.TOP);
        this.load = new JMenuItem("Load from JSON");
        this.load.addActionListener(this);
        this.save = new JMenuItem("Save to JSON");
        this.save.addActionListener(this);
        this.saveLoad.add(load);
        this.saveLoad.add(save);
        this.mb.add(saveLoad);

        this.editGraph = new JMenu("Edit graph");
        this.editGraph.addActionListener(this);
        this.editGraph.setVerticalAlignment(JMenu.TOP);
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
        this.mb.add(editGraph);

        this.algorithm = new JMenu("Algorithm");
        this.algorithm.addActionListener(this);
        this.algorithm.setVerticalAlignment(JMenu.TOP);
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
        this.mb.add(algorithm);
        addButtons();
        this.setJMenuBar(mb);
    }

    private void addButtons() {
        this.saveLoadFromUser = new JTextField(30);
        this.aFromUser = new JTextField(3);
        this.bFromUser = new JTextField(3);
        this.cFromUser = new JTextField(3);

        this.saveButton = new JButton("Save");
        this.saveButton.setSize(50, 30);
        this.saveButton.setBounds(165, 110, 65, 30);
        this.saveButton.addActionListener(this);

        this.loadButton = new JButton("Load");
        this.loadButton.setSize(50, 30);
        this.loadButton.setBounds(165, 110, 65, 30);
        this.loadButton.addActionListener(this);

        this.addNodeButton = new JButton("Add Node");
        this.addNodeButton.setSize(50, 30);
        this.addNodeButton.setBounds(165, 110, 65, 30);
        this.addNodeButton.addActionListener(this);

        this.removeNodeButton = new JButton("Remove Node");
        this.removeNodeButton.setSize(50, 30);
        this.removeNodeButton.setBounds(165, 110, 65, 30);
        this.removeNodeButton.addActionListener(this);

        this.addEdgeButton = new JButton("Add Edge");
        this.addEdgeButton.setSize(50, 30);
        this.addEdgeButton.setBounds(165, 110, 65, 30);
        this.addEdgeButton.addActionListener(this);

        this.removeEdgeButton = new JButton("Remove Edge");
        this.removeEdgeButton.setSize(50, 30);
        this.removeEdgeButton.setBounds(165, 110, 65, 30);
        this.removeEdgeButton.addActionListener(this);

        this.shortestPathButton = new JButton("Shortest Path");
        this.shortestPathButton.setSize(50, 30);
        this.shortestPathButton.setBounds(165, 110, 65, 30);
        this.shortestPathButton.addActionListener(this);

        this.addNodeForTSP = new JButton("Add Node");
        this.addNodeForTSP.addActionListener(this);
        this.doneTSP = new JButton("Done");
        this.doneTSP.addActionListener(this);
    }

    //     all the different actions
    @Override
    public void actionPerformed(ActionEvent e) {
//         the save action
        if (e.getSource() == this.save) {
            this.miniPanel.removeAll();
            saveLoadFromUser.setText("");
            this.miniLabel.setBounds(120, 30, 400, 30);
            this.miniLabel.setText("Enter full file location");
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(saveLoadFromUser);
            this.miniPanel.add(saveButton);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setVisible(true);
//             the save button action
        } else if (e.getSource() == saveButton) {
            this.miniFrame.dispose();
            String g1 = saveLoadFromUser.getText();
            this.algo.save(g1);
//             the load action
        } else if (e.getSource() == this.load) {
            this.miniPanel.removeAll();
            saveLoadFromUser.setText("");
            this.miniLabel.setText("Enter the location of the graph");
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(saveLoadFromUser);
            this.miniPanel.add(loadButton);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setVisible(true);
//             the load button action
        } else if (e.getSource() == this.loadButton) {
            this.miniFrame.dispose();
            String g1 = saveLoadFromUser.getText();
            this.algo.load(g1);
            this.graph = this.algo.getGraph();
            this.algo.init(this.graph);
            this.label.setText("Graph has been loaded");
            this.panel.add(label);
            this.panel.update();
            this.panel.repaint();
            this.add(panel);
//             the add node action
        } else if (e.getSource() == this.addNode) {
            this.miniPanel.removeAll();
            aFromUser.setText("");
            bFromUser.setText("");
            this.miniLabel.setText("Enter x and y");
            this.algo.init(this.graph);
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(aFromUser);
            this.miniPanel.add(bFromUser);
            this.miniPanel.add(addNodeButton);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setVisible(true);

//             the add Node button action
        } else if (e.getSource() == addNodeButton) {
            textValue1 = Double.parseDouble(aFromUser.getText());
            textValue2 = Double.parseDouble(bFromUser.getText());
            GeoLocation temp = new myGeo(textValue1, textValue2, 1);
            NodeData tempNode = new myNode(this.graph.nodeSize(), temp);
            this.graph.addNode(tempNode);
            this.miniFrame.dispose();
            this.label.setText("Node " + tempNode.getKey() + " was added succesfully!");
            this.panel.add(label);
            panel.update();
            panel.repaint();
            this.add(panel);

//             the remove Node action
        } else if (e.getSource() == this.removeNode) {
            if (this.graph != null) {
                this.miniPanel.removeAll();
                this.miniLabel.setText("Enter the key number of node to remove:");
                this.aFromUser.setText("");
                this.miniPanel.add(miniLabel);
                this.miniPanel.add(aFromUser);
                this.miniPanel.add(this.removeNodeButton);
                this.miniFrame.add(this.miniPanel);
                this.miniFrame.setLocationRelativeTo(this);
                this.miniFrame.setVisible(true);
            }

//             the remove Node button action
        } else if (e.getSource() == this.removeNodeButton) {
            textValue1 = Integer.parseInt(aFromUser.getText());
            NodeData removed = this.graph.removeNode((int) textValue1);
            if (removed == null) {
                label.setText("No such key");
                panel.add(label);
                this.miniFrame.dispose();
                this.add(panel);
            } else {
                this.graph.edgeSize();
                this.miniFrame.dispose();
                label.setText("Vertex was removed");
                panel.update();
                panel.repaint();
                this.add(panel);
                System.out.println(this.graph.nodeSize());
            }

//             the add Edge action
        } else if (e.getSource() == this.addEdge) {
            if (this.graph != null) {
                this.miniPanel.removeAll();
                this.aFromUser.setText("");
                this.bFromUser.setText("");
                this.cFromUser.setText("");
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

//             the add Edge button action
        } else if (e.getSource() == this.addEdgeButton) {
            this.textValue1 = Integer.parseInt(aFromUser.getText());
            this.textValue2 = Integer.parseInt(bFromUser.getText());
            this.textValue3 = Double.parseDouble(cFromUser.getText());
            this.graph.connect((int) textValue1, (int) textValue2, textValue3);
            this.miniFrame.dispose();
            this.miniPanel.removeAll();
            this.label.setText("Edge has been added!");
            this.panel.add(label);
            this.panel.update();
            this.panel.repaint();
//            this.add(panel);

//             the remove Edge action
        } else if (e.getSource() == this.removeEdge) {
            this.miniPanel.removeAll();
            this.aFromUser.setText("");
            this.bFromUser.setText("");
            this.miniLabel.setText("Enter SRC and DEST to remove the EDGE:");
            this.miniPanel.add(this.miniLabel);
            this.miniPanel.add(aFromUser);
            this.miniPanel.add(bFromUser);
            this.miniPanel.add(this.removeEdgeButton);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);

//             the remove Edge button action
        } else if (e.getSource() == this.removeEdgeButton) {
            this.miniPanel.removeAll();
            this.textValue1 = Integer.parseInt(aFromUser.getText());
            this.textValue2 = Integer.parseInt(bFromUser.getText());
            if (this.graph.getEdge((int) textValue1, (int) textValue2) == null) {
                this.miniFrame.dispose();
                this.label.setText("There is no Edge between those two nodes");
                this.panel.add(this.label);
                this.add(panel);
            } else {
                this.miniFrame.dispose();
                this.graph.removeEdge((int) textValue1, (int) textValue2);
                this.label.setText("The EDGE has been removed");
                this.panel.add(this.label);
                panel.update();
                panel.repaint();
                this.add(panel);
            }

//             the is connected action
        } else if (e.getSource() == this.isConnected) {
            this.panel.update();
            this.miniPanel.removeAll();
            if (this.algo.isConnected()) {
                this.label.setText("The graph is connected!");
            } else {
                this.label.setText("The graph is NOT connected!");
            }
            this.panel.add(label);
            this.add(panel);

//             the center action
        } else if (e.getSource() == this.center) {
            this.miniPanel.removeAll();
            this.panel.update();
            NodeData tmpNode = this.algo.center();
            if (tmpNode != null) {
                this.panel.centerNode = this.algo.center().getKey();
                this.label.setText("The center source number is " + this.panel.centerNode);
            } else {
                if (!this.algo.isConnected()) {
                    this.label.setText("The graph is not connected so there is not a center");
                } else {
                    this.label.setText("The graph is empty");
                }
            }
            this.panel.add(label);
            this.panel.repaint();
            this.add(panel);

//             the shortest path action
        } else if (e.getSource() == this.shortestPath) {
            this.miniPanel.removeAll();
            aFromUser.setText("");
            bFromUser.setText("");
            this.miniLabel.setText("Enter first SRC and than DEST to find the shortest path and the distance:");
            this.algo.init(this.graph);
            this.miniPanel.add(shortestPathButton);
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(aFromUser);
            this.miniPanel.add(bFromUser);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);

//             the shortest path button action
        } else if (e.getSource() == this.shortestPathButton) {
            this.miniFrame.dispose();
            textValue1 = Integer.parseInt(aFromUser.getText());
            textValue2 = Integer.parseInt(bFromUser.getText());
            if (this.algo.shortestPath((int) textValue1, (int) textValue2) == null) {
                this.label.setText("There is no path between " + textValue1 + " and " + textValue2);
                this.panel.add(label);
                this.panel.update();
                this.panel.repaint();
            } else {
                this.shortestWay = this.algo.shortestPath((int) textValue1, (int) textValue2);
                StringBuilder res = new StringBuilder();
                for (NodeData nodeData : this.shortestWay) {
                    res.append(nodeData.getKey()).append(" ");
                }
                this.panel.makeShortestWay((ArrayList<NodeData>) this.shortestWay);
                double distance = this.algo.shortestPathDist((int) textValue1, (int) textValue2);
                this.label.setText("The shortest path between " + textValue1 + " and " + textValue2 + " is " +
                        res + "\n and the distance is " + distance);
                this.panel.add(label);

                this.panel.repaint();
            }
            this.add(panel);
//             the TSP button action
        } else if (e.getSource() == this.tsp) {
            this.miniPanel.removeAll();
            this.miniLabel.setText("Add cities to the TSP. Click done when you had enough");
            this.aFromUser.setText("");
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(aFromUser);
            this.miniPanel.add(addNodeForTSP);
            this.miniPanel.add(this.doneTSP);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.addNodeForTSP) {
            textValue1 = Integer.parseInt(aFromUser.getText());
            NodeData tmp = this.graph.getNode((int) textValue1);
            if (!tspList.contains(tmp) && tmp != null) {
                this.miniLabel.setText("Great! You can continue adding cities, or click Done");
                tspList.add(tmp);
            } else {
                this.miniLabel.setText("Please add a valid node or click Done");
            }
            StringBuilder res = new StringBuilder();
            for (NodeData nodeData : tspList) {
                res.append(nodeData.getKey()).append(" ");
            }
            System.out.println(res);
            this.aFromUser.setText("");
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
        } else if (e.getSource() == this.doneTSP) {
            ArrayList<NodeData> resultTsp = (ArrayList<NodeData>) this.algo.tsp(this.tspList);
            StringBuilder res = new StringBuilder();
            for (NodeData nodeData : resultTsp) {
                res.append(nodeData.getKey()).append(" ");
            }

            while (!this.tspList.isEmpty()) {
                this.tspList.remove(0);
            }
            this.miniFrame.dispose();
            this.label.setText("TSP - " + res);
            this.panel.add(label);
            this.panel.update();
            this.panel.repaint();
            this.add(panel);
        }
    }

    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
        try {
            algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G3.json");
            new graphWindow(algo);
        } catch (Exception e) {
            System.out.println("fail");
        }


    }

}
