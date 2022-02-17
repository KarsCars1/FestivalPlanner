package DataStructure.Data;

import java.time.LocalDateTime;

public class Show {

    private String name;
    private Location location;
    private Performer performer;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

    public Show(String name, Location location, Performer performer, LocalDateTime beginTime, LocalDateTime endTime) {
        this.name = name;
        this.location = location;
        this.performer = performer;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Performer getPerformer() {
        return this.performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public String getLocation() {
        return this.name + ": " + this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
