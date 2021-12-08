import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.GeoLocation;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


public class graphWindow extends JFrame implements ActionListener{
    ArrayList<GeoLocation> locationsList = new ArrayList<>();
    DirectedWeightedGraphAlgorithms algo;
    JMenuBar mb;
    JMenu menu;
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
    JPanel panel;


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

        createMenuBar();

//       loadButton();





//        ActionListener loadListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JButton button = new JButton("Load");
//                JLabel label = new JLabel("Enter path to load");
//                JTextField textField = new JTextField();
//                textField.setBounds(50,50,100,20);
//                button.setBounds(50,150,50,20);
//                label.setBounds(50,100,50,20);
//                load.add(button);
//                load.add(label);
//                load.add(textField);
//            }
//        };

        this.setVisible(true);

    }

    private void createMenuBar(){

        this.mb = new JMenuBar();
        this.menu = new JMenu("Menu");
        this.menu.setVerticalAlignment(JMenu.TOP);
        this.mb.add(menu);
        this.add(mb);
        this.saveLoad = new JMenu("Save/Load");
        this.saveLoad.addActionListener(this);
        this.saveLoad.setVerticalAlignment(JMenu.TOP);
         this.load = new JMenuItem("Load");
        this.load.addActionListener(this);
        this.save = new JMenuItem("Save");
        this.save.addActionListener(this);
        this.editGraph = new JMenu("Edit graph");
        this.editGraph.addActionListener(this);
        this.algorithm = new JMenu("Algorithm");
        this.algorithm.addActionListener(this);
        this.menu.add(saveLoad);
        this.saveLoad.add(load);
        this.saveLoad.add(save);
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
        this.isConnected = new JMenuItem("Is Connected");
        this.isConnected.addActionListener(this);
        this.shortestPath = new JMenuItem("Shorted Path");
        this.shortestPath.addActionListener(this);
        this.center = new JMenuItem("Center");
        this.center.addActionListener(this);
        this.tsp = new JMenuItem("Tsp");
        this.tsp.addActionListener(this);
        this.algorithm.add(isConnected);
        this.algorithm.add(shortestPath);
        this.algorithm.add(center);
        this.algorithm.add(tsp);
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.loadButton = new JButton("Load");
        JTextField loadText = new JTextField();
        JLabel loadLabel = new JLabel("Enter path to load:");
        loadButton.setBounds(50,150,50,20);
        loadText.setBounds(50,50,100,20);
        loadLabel.setBounds(50,100,50,20);
        panel.add(loadButton);
        panel.add(loadText);
        panel.add(loadLabel);

        this.add(panel);



//    private void loadButton(){
//        JButton button = new JButton("Load");
//        button.addActionListener(this);
//        JLabel label = new JLabel("Enter path to load");
//        JTextField textField = new JTextField();
//        textField.setBounds(50,50,100,20);
//        button.setBounds(50,150,50,20);
//        label.setBounds(50,100,50,20);
//        this.add(button);
//        this.add(label);
//        this.add(textField);
//
//        ActionListener load = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getSource() == button){
//                    System.out.println("poo");
//                }
//            }
//        };

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.loadButton ) {
            System.out.println("poo");
        }

    };





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
