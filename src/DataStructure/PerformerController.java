package DataStructure;

import DataStructure.Data.Artist;
import DataStructure.Data.Band;
import DataStructure.Data.Performer;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class PerformerController {
    private ArrayList<Performer> performers = new ArrayList<>();
    private ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<Band> bands = new ArrayList<>();

    public void addArtist(String performerName) {
        if (!performerAlreadyExists(performerName) && !artistAlreadyExists(performerName)) {
            Artist newArtist = new Artist(performerName);
            performers.add(newArtist);
            artists.add(newArtist);
        } else if (artistAlreadyExists(performerName)&&!performerAlreadyExists(performerName)) {
            for (Artist artist : artists) {
                if (artist.getPerformerName().equals(performerName)){
                    performers.add(artist);
                    System.out.println("added bandmember to artistlist: "+ performerName);
                }
            }
        } else {
            System.out.println("Cannot add duplicates (" + performerName + ")");
        }
    }

    public ArrayList<Performer> getPerformers() {
        return this.performers;
    }

    public void addBandMember(String performerName) {
        Artist newArtist = new Artist(performerName);
        artists.add(newArtist);
    }

    public boolean artistAlreadyExists(String performerName) {
        for (Artist artist : artists) {
            if (artist.getName().equals(performerName)) {
                return true;
            }
        }
        return false;
    }

    public boolean performerAlreadyExists(String performerName) {
        for (Performer performer : performers) {
            if (performer.getPerformerName().equals(performerName)) {
                return true;
            }
        }
        return false;
    }

    public void addBandMembers(ListView<String> listView, String bandName) {
        Band thisBand = null;
        for (Band band : bands) {
            if (band.getPerformerName().equals(bandName)) {
                thisBand = band;
            }
        }
        for (String item : listView.getItems()) {
            Artist thisArtist = new Artist(item);
            if (!artistAlreadyExists(item)) {
                artists.add(thisArtist);
            }
            if (!thisBand.getMembers().contains(thisArtist)) {
                thisBand.addMember(thisArtist);
            }
        }
    }

    public void updateList(ListView<String> listView) {
        listView.getItems().clear();
        for (Performer performer : performers) {
            listView.getItems().add(performer.getPerformerName());
        }
    }

    public void addBand(String performerName) {
        Band newBand = new Band(performerName);
        performers.add(newBand);
        bands.add(newBand);
    }

    public void updatePerformer() {

    }
}
