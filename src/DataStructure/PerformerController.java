package DataStructure;

import DataStructure.Data.*;
import javafx.scene.control.ListView;

import java.time.LocalTime;
import java.util.ArrayList;

public class PerformerController {
    private ArrayList<Performer> performers = new ArrayList<>();
    private ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<Band> bands = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<Show> shows = new ArrayList<>();
    //private EditArtistScene editArtist = new EditArtistScene();

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public ArrayList<Band> getBands() {
        return bands;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    public void addArtist(String performerName) {
        if (!performerAlreadyExists(performerName) && !artistAlreadyExists(performerName)) {
            Artist newArtist = new Artist(performerName);
            performers.add(newArtist);
            artists.add(newArtist);
        } else if (artistAlreadyExists(performerName) && !performerAlreadyExists(performerName)) {
            for (Artist artist : artists) {
                if (artist.getPerformerName().equals(performerName)) {
                    performers.add(artist);
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

    public boolean bandAlreadyExists(String performerName) {
        for (Band band : bands) {
            if (band.getPerformerName().equals(performerName)) {
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
    public void addLocation(String name){
        for (Location location : locations) {
            if (location.getName().equals(name)) {
                return;
            }
        }
        locations.add(new Location(name));
        System.out.println(locations.toString());
    }

    public void addShow(String name, String location, String performer, LocalTime beginTime, LocalTime endTime) {
        for (Show show : shows) {
            if (show.getName().equals(name)) {
                return;
            }
        }
        Performer performer1 = null;
        for (Performer performer2 : performers) {
            if (performer2.getPerformerName().equals(performer)) {
                performer1 = performer2;
            }
        }
        Location location1 = null;
        for (Location location2 : locations) {
            if (location2.getName().equals(location)) {
                location1 = location2;
            }
        }
        Show newShow = new Show(name, location1, performer1, beginTime, endTime);
        shows.add(newShow);
        System.out.println(newShow.toString());
    }

    public Show getShow(String name) {
        for (Show show : shows) {
            if (show.getName().equals(name)) {
                return show;
            }
        }

        return null;
    }

    public void removeShow(Show show) {
        shows.remove(show);
    }

    public void updateList(ListView<String> listView) {
        listView.getItems().clear();
        for (Performer performer : performers) {
            listView.getItems().add(performer.getPerformerName());
        }
    }

    public void addBand(String performerName) {
        if (!performerAlreadyExists(performerName) && !bandAlreadyExists(performerName)) {
            Band newBand = new Band(performerName);
            performers.add(newBand);
            bands.add(newBand);
        } else if (bandAlreadyExists(performerName) && !performerAlreadyExists(performerName)) {
            for (Band artist : bands) {
                if (artist.getPerformerName().equals(performerName)) {
                    performers.add(artist);
                    System.out.println(performerName);
                }
            }
        } else {
            System.out.println("Cannot add duplicates (" + performerName + ")");
        }
    }

    public void editPerformer(String performerName) {
        for (Performer performer: performers) {
            if(performerName == performer.getPerformerName()){
                if (bands.contains(performerName)){
                    //getEditBand;
                } else {
                    //editArtist.getScene();
                }
                break;
            }
        }
    }

    public void removePerformer(String performerName) {
        for (int i = 0; i < performers.size(); i++) {
            Performer performer = performers.get(i);
            if (performer.getPerformerName().equals(performerName)) {
                performers.remove(i);
            }
        }
    }

    public ArrayList<String> getPerformersString() {
        ArrayList<String> names = new ArrayList<>();
        for (Performer performer : this.performers) {
            names.add(performer.getPerformerName());
        }
        return names;
    }

    public ArrayList<String> getLocationsString() {
        ArrayList<String> names = new ArrayList<>();
        for (Location location : this.locations) {
            names.add(location.getName());
        }
        return names;
    }
}
