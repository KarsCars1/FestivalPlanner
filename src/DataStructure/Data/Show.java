package DataStructure.Data;

import java.io.Serializable;
import java.time.LocalTime;

//the Show class is for Shows that get planned, they have a performer, a location and a beginning and ending time

public class Show implements Serializable {

    private String name;
    private Location location;
    private Performer performer;
    private LocalTime beginTime;
    private LocalTime endTime;



    public Show(String name, Location location, Performer performer, LocalTime beginTime, LocalTime endTime) {
        this.name = name;
        this.location = location;
        this.performer = performer;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public String getPerformerName() {
        return performer.getPerformerName();
    }

    public String getLocationName() {
        return this.location.getName();
    }

    public String getName() {
        return this.name;
    }

    public LocalTime getBeginTime() {
        return this.beginTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public Performer getPerformer() {
        return this.performer;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Show{" +
                "name='" + this.name + '\'' +
                ", location=" + this.location +
                ", performer=" + this.performer.getPerformerName() +
                ", beginTime=" + this.beginTime +
                ", endTime=" + this.endTime +
                '}';
    }
}
