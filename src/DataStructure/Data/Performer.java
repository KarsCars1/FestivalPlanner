package DataStructure.Data;

import java.io.Serializable;

public class Performer implements Serializable {
    protected String performerName;
    private double popularity;
    
    public Performer(String performerName, double popularity) {
        this.popularity = popularity/100.0;
        this.performerName = performerName;
    }



    public String getPerformerName() {
        return performerName;
    }

    public double getPopularity(){
    return popularity * 100;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public void setPopularity(double popularity){
        this.popularity = popularity/100.0;
    }
}
