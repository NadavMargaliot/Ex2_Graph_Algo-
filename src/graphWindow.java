import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    JTextField saveLoadFromUser;
    JTextField aFromUser;
    JTextField bFromUser;
    JTextField cFromUser;
    int textValue1;
    int textValue2;
    double textValue3;
    double xForGeo;
    double yForGeo;
    int width;
    int height;
    double minX;
    double maxX;
    double minY;
    double maxY;
    ArrayList<NodeData> locationsList = new ArrayList<>();

    public class myPanel extends JPanel {
        ArrayList<NodeData> nodes = new ArrayList<>();
        ArrayList<EdgeData> edges = new ArrayList<>();
        Iterator<NodeData> nodeItr;
        Iterator<EdgeData> edgeItr;

        public myPanel() {
            super();
            this.nodeItr = algo.getGraph().nodeIter();
            this.edgeItr = algo.getGraph().edgeIter();
            while (nodeItr.hasNext()) {
                nodes.add(nodeItr.next());
            }
            while (edgeItr.hasNext()) {
                edges.add(edgeItr.next());
            }
        }
        public void setNew(){
            this.nodeItr = graph.nodeIter();
            this.edgeItr =graph.edgeIter();
            this.nodes = new ArrayList<>();
            this.edges = new ArrayList<>();
            while (nodeItr.hasNext()){
                this.nodes.add( nodeItr.next());
            }
            while (edgeItr.hasNext()){
                this.edges.add((EdgeData) edgeItr.next());
            }
//            while (!this.shortest.isEmpty()){
//                this.shortest.remove(0);
//            }

        }


        private NodeData NodeLocation(int key){
            for(int i=0; i<this.nodes.size();i++){
                if(this.nodes.get(i).getKey() == key)
                    return nodes.get(i);
            }
            return null;
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < this.nodes.size(); i++) {
                NodeData temp = this.nodes.get(i);
                int xPixel = (int) (temp.getLocation().x());
                int yPixel = (int) (temp.getLocation().y());
                g.setColor(Color.BLUE);
                g.fillOval(xPixel, yPixel, 22, 22);
                g.setColor(Color.BLACK);
                g.drawString("Key:" + temp.getKey(), xPixel - 10, yPixel + 35);
            }
            for (int i = 0; i < this.edges.size(); i++) {
                EdgeData temp = this.edges.get(i);
                NodeData currFrom = NodeLocation(temp.getSrc());
                NodeData currTo = NodeLocation(temp.getDest());
                double xFrom = currFrom.getLocation().x();
                double yFrom = currFrom.getLocation().y();
                double xTo = currTo.getLocation().x();
                double yTo = currTo.getLocation().y();
                g.setColor(Color.RED);
                g.drawLine((int) (xFrom + 12), (int) (yFrom + 12), (int) (xTo + 12), (int) (yTo + 12));
                drawArrowLine(g,(int) (xFrom + 12), (int) (yFrom + 12), (int) (xTo + 12), (int) (yTo + 12),(int)(Math.abs(xFrom-xTo)),(int)(Math.abs(yFrom-yTo)));
            }
        }

        private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
            int dx = x2 - x1, dy = y2 - y1;
            double D = Math.sqrt(dx*dx + dy*dy);
            double xm = D - d, xn = xm, ym = h, yn = -h, x;
            double sin = dy / D, cos = dx / D;

            x = xm*cos - ym*sin + x1;
            ym = xm*sin + ym*cos + y1;
            xm = x;

            x = xn*cos - yn*sin + x1;
            yn = xn*sin + yn*cos + y1;
            xn = x;

            int[] xpoints = {x2, (int) xm, (int) xn};
            int[] ypoints = {y2, (int) ym, (int) yn};

            g.drawLine(x1, y1, x2, y2);
            g.fillPolygon(xpoints, ypoints, 3);
        }


//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
////            scaleGraph();
//            for (int i = 0; i < this.nodes.size(); i++) {
//                NodeData curr = this.nodes.get(i);
//                int x = (int) scaleX(curr.getLocation().x());
//                int y = (int) scaleY(curr.getLocation().y());
//                g.setColor(Color.RED);
//                g.fillOval(x, y, 10, 10);
//                g.setColor(Color.BLACK);
//                g.drawString("Key" + curr.getKey(), x - 5, y - 5);
//            }
//            System.out.println(this.nodes.size());
//        }
    }


    public graphWindow(DirectedWeightedGraphAlgorithms algorithm) throws HeadlessException {
        super();
        this.algo = algorithm;
        this.graph = algo.getGraph();
        this.panel = new myPanel();

        Iterator<NodeData> itr = this.algo.getGraph().nodeIter();
        while (itr.hasNext()) {
            NodeData tmp = itr.next();
            this.locationsList.add(tmp);
        }
        this.panel.setNew();
        this.panel.repaint();
        this.add(panel);
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = this.screenSize.width / 2;
        this.height = this.screenSize.height / 2;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setTitle("Directed Weighted Graph");
        String G1 = "/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json";
        this.algo.load(G1);
        this.graph = this.algo.getGraph();
        this.minX = minX();
        this.minY = minY();
        this.maxX = maxX();
        this.maxY = maxY();
        createMenuBar();
        this.setVisible(true);




    }

    private double minX(){
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double x = Double.POSITIVE_INFINITY;
        while(nodeDataIterator.hasNext()){
            NodeData curr = nodeDataIterator.next();
            if(curr.getLocation().x() < x){
                x = curr.getLocation().x();
            }
        }
        return x;
    }
    private double maxX(){
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double x = Double.NEGATIVE_INFINITY;
        while(nodeDataIterator.hasNext()){
            NodeData curr = nodeDataIterator.next();
            if(curr.getLocation().x() > x){
                x = curr.getLocation().x();
            }
        }
        return x;
    }
    private double minY(){
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double y = Double.POSITIVE_INFINITY;
        while(nodeDataIterator.hasNext()){
            NodeData curr = nodeDataIterator.next();
            if(curr.getLocation().y() < y){
                y = curr.getLocation().y();
            }
        }
        return y;
    }
    private double maxY(){
        Iterator<NodeData> nodeDataIterator = this.graph.nodeIter();
        double y = Double.NEGATIVE_INFINITY;
        while(nodeDataIterator.hasNext()){
            NodeData curr = nodeDataIterator.next();
            if(curr.getLocation().y() > y){
                y = curr.getLocation().y();
            }
        }
        return y;
    }
    private double scaleX(double x){
        return this.width * (x - this.minX) / (this.maxX - this.minX);
    }
    private double scaleY(double y){
        return this.height * (this.maxY - y) / (this.maxY - this.minY);
    }
    private double factorX(double w){
        return w * this.width / Math.abs(this.maxX - this.minX);
    }
    private double factorY(double h){
        return h * this.height / Math.abs(this.maxY - this.minY);
    }
    private double userX(double x){
        return this.minX + x * (this.maxX-this.minX) / this.width;
    }
    private double userY(double y){
        return this.maxY - y *(this.maxY - this.minY) / this.height;
    }
    private void scaleGraph(){
        Iterator<NodeData> iterator = this.graph.nodeIter();
        while (iterator.hasNext()){
            NodeData currNode = iterator.next();
            GeoLocation currGeo = currNode.getLocation();
            double newX = (double) (this.width-100) * ((double) currGeo.x() - this.minX) / (this.maxX - this.minX) + 30;
            double newY = (double) (this.height-200) * ((double) currGeo.y() - this.minY) / (this.maxY - this.minY) + 30;
            GeoLocation newGeo = new myGeo(newX,newY,0);
            currNode.setLocation(newGeo);
        }

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
//        this.panel = new JPanel();
//        this.panel.setLayout(null);
        this.loadButton = new JButton("Load");
        JTextField loadText = new JTextField();
        JLabel loadLabel = new JLabel("Enter path to load:");
        loadText.setBounds(50, 50, 100, 20);
        loadLabel.setBounds(50, 100, 50, 20);

        this.miniLabel = new JLabel();
        this.miniPanel = new JPanel();
        this.miniFrame = new JFrame();
        this.miniFrame.setSize(600, 300);

        this.saveLoadFromUser = new JTextField(30);
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
        this.addNodeButton.setText("Add Node");
        this.addNodeButton.setSize(50, 30);
        this.addNodeButton.setBounds(165, 110, 65, 30);
        this.addNodeButton.addActionListener(this);

        this.removeNodeButton = new JButton();
        this.removeNodeButton.setText("Remove Node");
        this.removeNodeButton.setSize(50, 30);
        this.removeNodeButton.setBounds(165, 110, 65, 30);
        this.removeNodeButton.addActionListener(this);

        this.addEdgeButton = new JButton();
        this.addEdgeButton.setText("Add Edge");
        this.addEdgeButton.setSize(50, 30);
        this.addEdgeButton.setBounds(165, 110, 65, 30);
        this.addEdgeButton.addActionListener(this);

        this.removeEdgeButton = new JButton();
        this.removeEdgeButton.setText("Remove Edge");
        this.removeEdgeButton.setSize(50, 30);
        this.removeEdgeButton.setBounds(165, 110, 65, 30);
        this.removeEdgeButton.addActionListener(this);

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
//        panel.add(loadButton);
//        panel.add(loadText);
//        panel.add(loadLabel);
//        this.add(panel);

    }

    /**
     all the different actions
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         the save action
         */
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
            /**
             the save button action
             */
        } else if (e.getSource() == saveButton) {
            this.miniFrame.dispose();
            String g1 = saveLoadFromUser.getText();
            this.algo.save(g1);
            /**
             the add Node action
             */
        }else if(e.getSource() == this.load){
            this.miniPanel.removeAll();
            saveLoadFromUser.setText("");
            this.miniLabel.setText("Enter the location of the graph");
            this.miniPanel.add(miniLabel);
            this.miniPanel.add(saveLoadFromUser);
            this.miniPanel.add(loadButton);
            this.miniFrame.add(this.miniPanel);
            this.miniFrame.setVisible(true);
        }else if(e.getSource() == this.loadButton){
            this.miniFrame.dispose();
            String g1 = aFromUser.getText();
            this.algo.load(g1);
            this.graph = this.algo.getGraph();
            panel.setNew();
            this.repaint();


//            this.miniPanel.removeAll();
//            this.miniFrame.dispose();
//            String txt = saveLoadFromUser.getText();
//            this.algo.load(txt);
//            this.graph = this.algo.getGraph();
//            this.miniPanel.add(new myPanel());
//            this.miniFrame.add(miniPanel);
//            this.miniFrame.add(this.miniPanel);
//            this.miniFrame.setVisible(true);
        }
        else if (e.getSource() == this.addNode) {
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
            /**
             the add Node button action
             */
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
            /**
             the remove Node action
             */
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
            /**
             the remove Node button action
             */
        } else if (e.getSource() == this.removeNodeButton) {
            this.miniPanel.removeAll();
            this.textValue1 = Integer.parseInt(this.aFromUser.getText());
            if (this.graph.getNode(textValue1) == null) {
                this.miniLabel.setText("There is no such node");
                this.miniFrame.dispose();
                this.miniPanel.add(this.miniLabel);
                this.miniFrame.add(miniPanel);
                this.miniFrame.setLocationRelativeTo(this);
                this.miniFrame.setVisible(true);
            } else {
                this.miniLabel.setText(("Node " + textValue1 + " was removed"));
                this.graph.removeNode(textValue1);
                this.miniFrame.dispose();
                this.miniPanel.add(this.miniLabel);
                this.miniFrame.add(miniPanel);
                this.miniFrame.setLocationRelativeTo(this);
                this.miniFrame.setVisible(true);
            }
            /**
             the add Edge action
             */
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
            /**
             the add Edge button action
             */
        } else if (e.getSource() == this.addEdgeButton) {
            this.textValue1 = Integer.parseInt(aFromUser.getText());
            this.textValue2 = Integer.parseInt(bFromUser.getText());
            this.textValue3 = Double.parseDouble(cFromUser.getText());
            this.graph.connect(textValue1, textValue2, textValue3);
            this.miniFrame.dispose();
            this.miniPanel.removeAll();
            this.miniLabel.setText("Edge has been added!");
            this.miniPanel.add(miniLabel);
            this.miniFrame.add(miniPanel);
            this.miniFrame.setLocationRelativeTo(this);
            this.miniFrame.setVisible(true);
            /**
             the remove Edge action
             */
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
            /**
             the remove Edge button action
             */
        } else if (e.getSource() == this.removeEdgeButton) {
            this.miniPanel.removeAll();
            this.textValue1 = Integer.parseInt(aFromUser.getText());
            this.textValue2 = Integer.parseInt(bFromUser.getText());
            if(this.graph.getEdge(textValue1,textValue2) == null){
                this.miniFrame.dispose();
                this.miniLabel.setText("There is no Edge between those two nodes");
                this.miniPanel.add(this.miniLabel);
                this.miniFrame.add(miniPanel);
                this.miniFrame.setLocationRelativeTo(this);
                this.miniFrame.setVisible(true);
            }else{
                this.miniFrame.dispose();
                this.graph.removeEdge(textValue1,textValue2);
                this.miniLabel.setText("The EDGE has been removed");
                this.miniPanel.add(this.miniLabel);
                this.miniFrame.add(miniPanel);
                this.miniFrame.setLocationRelativeTo(this);
                this.miniFrame.setVisible(true);
            }
            /**
             the is connected action
             */
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
            /**
             the center action
             */
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
            /**
             the shortest path action
             */
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
            /**
             the shortest path distance button action
             */
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
            /**
             the shortest path button action
             */
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
            /**
             the TSP button action
             */
        }else if(e.getSource() == this.tsp){
//            this.miniPanel.removeAll();
//            if(this.algo.isConnected()){
//                java.util.List<NodeData> citiesNodes = this.algo.tsp();
//            }else{
//                this.miniLabel.setText("The graph is no connected so there is not a way to visit all cities.");
//            }
        }
    }


    public static void main(String[] args) {
//        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
//        algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
//        new graphWindow(algo);


        DirectedWeightedGraphAlgorithms algo = new DWGraphAlgorithms();
        try {
            algo.load("/Users/adielbenmeir/IdeaProjects/Ex2_Graph_Algo/data/G1.json");
            DWGraph graph = (DWGraph) algo.getGraph();
            new graphWindow(algo);
        }
        catch (Exception e){
            System.out.println("fail");
        }



    }

}
