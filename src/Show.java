public class Show {

    private Location location;
    private String name;

    public Show(String name, Location location) {
        this.location = location;
        this.name = name;
    }

    public String getLocation() {
        return this.name + ": " + this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
