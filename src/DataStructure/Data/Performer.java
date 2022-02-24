package DataStructure.Data;

import java.io.Serializable;

public class Performer implements Serializable {
    protected String performerName;

    public Performer(String performerName) {
        this.performerName = performerName;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }
}
