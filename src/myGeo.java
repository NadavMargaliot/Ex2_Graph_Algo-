import api.GeoLocation;

public class myGeo implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public myGeo(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // default constructor
    public myGeo() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double a = Math.pow(this.x - g.x(), 2);
        double b = Math.pow(this.y - g.y(), 2);
        double c = Math.pow(this.z - g.z(), 2);
        return Math.sqrt(a + b + c);
    }
}
