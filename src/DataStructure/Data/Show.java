package DataStructure.Data;

import java.io.Serializable;
import java.time.LocalTime;

public class Show implements Serializable {

    private String name;
    private Location location;
    private Performer performer;
    private LocalTime beginTime;
    private LocalTime endTime;
    private double popularity;


    public Show(String name, Location location, Performer performer, LocalTime beginTime, LocalTime endTime, double popularity) {
        this.name = name;
        this.location = location;
        this.performer = performer;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.popularity = popularity;
    }

    public String getPerformerName() {
        return performer.getPerformerName();
    }

//    public String getLocationName() {
//        return location.getName();
//    }

    public String getName() {
        return name;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Performer getPerformer() {
        return this.performer;
    }

//    public void setPerformer(Performer performer) {
//        this.performer = performer;
//    }

    public Location getLocation() {
        return this.location;
    }

//    public void setLocation(Location location) {
//        this.location = location;
//    }

    @Override
    public String toString() {
        return "Show{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", performer=" + performer +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }

    public double getPopularity() {
        return this.popularity;
    }
}
