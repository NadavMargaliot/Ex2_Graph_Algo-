import api.GeoLocation;
import api.NodeData;

public class myNode implements NodeData {
    private static int id = 0;
    private int key;
    private GeoLocation location;
    private String info;
    private int tag;

    public myNode(){
        this.key = id++;
        this.info = null;
        this.tag = 0;

    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return null;
    }

    @Override
    public void setLocation(GeoLocation p) {

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
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
