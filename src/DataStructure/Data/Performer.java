package DataStructure.Data;

import java.io.Serializable;

//the performer class is a collection of every object that can be planned in the agenda module, every performer has a name and a populartiy value
//the popularity is a percentage of how much people will probably go to the concert
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
        int convertedPopularity =popularity;
        return convertedPopularity;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public void setPopularity(int popularity){
        this.popularity = popularity;
    }
}
