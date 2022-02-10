package DataStructure.Data;

public class Show {

    private String name;
    private Location location;
    private Performer performer;

    public Show(String name, Location location, Performer performer) {
        this.name = name;
        this.location = location;
        this.performer = performer;
    }

    public Performer getPerformer(){
        return this.performer;
    }

    public Performer setPerformer(Performer performer){
        this.performer = performer;

        return this.performer;
    }

    public String getLocation() {
        return this.name + ": " + this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
