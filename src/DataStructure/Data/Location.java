package DataStructure.Data;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class Location implements Serializable, Comparable<Location> {

    Point2D location;
    int[][] path;
//    int maxNPC;

    //    private int height = 0;
//    private int width = 0;
    private int surfaceArea = 0;
    private String name = "";
    private Point2D size;

    public Location(int[][]location,String name, Point2D size) {
        this.path = location;
        this.name = name;
        this.size = size;
    }

//    public void setPath(int[][] path) {
//        this.path = path;
//    }

    public int[][] getPath() {
        return path;
    }

    public Point2D getSize(){
        return this.size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getSurfaceArea() {
//        return this.surfaceArea;
//    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Location o) {
        return this.name.compareTo(o.name);
    }
}

