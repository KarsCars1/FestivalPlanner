package DataStructure.Data;

public class Location {

    private int xLocation = 0;
    private int yLocation = 0;
    private int height = 0;
    private int width = 0;
    private int surfaceArea = 0;
    private String name = "";

    public Location(String name, int x, int y, int width, int height) {
        this.xLocation = x;
        this.yLocation = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.surfaceArea = this.height * this.width;
    }

    public int getSurfaceArea(){
        return this.surfaceArea;
    }

    @Override
    public String toString() {
        return this.name + ": Coordinates (" + this.xLocation + ", " + this.yLocation + "): Surface area " + this.surfaceArea;
    }
}
