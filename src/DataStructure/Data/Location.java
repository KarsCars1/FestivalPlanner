package DataStructure.Data;
import java.awt.geom.Point2D;
import java.io.Serializable;

// the Location class is to define where the different stages are, a location Object contains a size, a name and a Path.

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

//The path variable is implemented for the npc to be able to locate the location
    public int[][] getPath() {
        return this.path;
    }

//With the size an Npc is able to determine what part of the map is part of the location they are in, this way the npc will know where they can move around
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

