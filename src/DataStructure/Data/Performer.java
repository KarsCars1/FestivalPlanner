package DataStructure.Data;

import java.io.Serializable;

public class Performer implements Serializable {
    protected String performerName;
    private int popularity;

    public Performer(String performerName, int popularity) {
        this.popularity = popularity;
        this.performerName = performerName;
    }



    public String getPerformerName() {
        return performerName;
    }

    public int getPopularity(){
        int convertedPopularity = (int) (popularity);
        return convertedPopularity;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public void setPopularity(int popularity){
        this.popularity = popularity;
    }
}
