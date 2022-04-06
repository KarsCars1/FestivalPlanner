package DataStructure.Data;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class Location implements Serializable, Comparable<Location> {

    Point2D location;
    int[][] path;
    private String name = "";
    private Point2D size;

    public Location(int[][]location,String name, Point2D size) {
        this.path = location;
        this.name = name;
        this.size = size;
    }

    public int[][] getPath() {
        return this.path;
    }

    public Point2D getSize(){
        return this.size;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Location o) {
        return this.name.compareTo(o.name);
    }
}

