package DataStructure.Data;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Location implements Serializable {

    Point2D location;
    int[][]path;

    private int height = 0;
    private int width = 0;
    private int surfaceArea = 0;
    private String name = "";

    public Location(String name, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.name = name;
        this.surfaceArea = this.height * this.width;
    }

    public Location(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public int getSurfaceArea() {
        return this.surfaceArea;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
