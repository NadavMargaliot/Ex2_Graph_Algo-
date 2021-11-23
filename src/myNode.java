import api.GeoLocation;
import api.NodeData;

import java.util.HashMap;

public class myNode implements NodeData {
    private static int id = 0;
    private int key;
    private GeoLocation location;
    private String info;
    private int tag;
    private HashMap<Integer, myNode> neighbors;
    private HashMap<Integer, myNode> edges;

    public myNode() {
        this.key = id++;
        this.info = null;
        this.tag = 0;
        this.location = new myGeo();
        this.neighbors = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public myNode(int key) {
        this.key = key;
        this.info = null;
        this.tag = 0;
        this.location = new myGeo();
        this.neighbors = new HashMap<>();
        this.edges = new HashMap<>();
    }
    public myNode (myNode other){
        if (other != null){
            this.key = other.key;
            this.info = other.info;
            this.tag = other.tag;
            this.location = other.location;
            this.neighbors = new HashMap<>();
            this.edges = new HashMap<>();
        }
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = p;

    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;

    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;

    }
}
