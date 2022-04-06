package DataStructure;

import DataStructure.Data.*;
import javafx.scene.control.ListView;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class PerformerController implements Serializable {
    private ArrayList<Performer> performers = new ArrayList<>();
    private ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<Band> bands = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<Show> shows = new ArrayList<>();

    //return artists
    public ArrayList<Artist> getArtists() {
        return this.artists;
    }

    //return bands
    public ArrayList<Band> getBands() {
        return this.bands;
    }

    //return locations
    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    //return shows
    public ArrayList<Show> getShows() {
        return this.shows;
    }

    //add an artist if its name isn't used
    public void addArtist(String performerName, int popularity) {
        if (!performerAlreadyExists(performerName) && !artistAlreadyExists(performerName)) {
            Artist newArtist = new Artist(performerName, popularity);
            this.performers.add(newArtist);
            artists.add(newArtist);
        } else if (artistAlreadyExists(performerName) && !performerAlreadyExists(performerName)) {
            for (Artist artist : this.artists) {
                if (artist.getPerformerName().equals(performerName)) {
                    this.performers.add(artist);
                }
            }
        } else {
            System.out.println("Cannot add duplicates (" + performerName + ")");
        }
    }

    //check if the given name is used
    public boolean artistAlreadyExists(String performerName) {
        for (Artist artist : this.artists) {
            if (artist.getName().equals(performerName)) {
                return true;
            }
        }
        return false;
    }

    //check if the given bandName is used
    public boolean bandAlreadyExists(String performerName) {
        for (Band band : this.bands) {
            if (band.getPerformerName().equals(performerName)) {
                return true;
            }
        }
        return false;
    }

    //check if the given name is used
    public boolean performerAlreadyExists(String performerName) {
        for (Performer performer : this.performers) {
            if (performer.getPerformerName().equals(performerName)) {
                return true;
            }
        }
        return false;
    }

    //add members to a given band
    public void addBandMembers(ListView<String> listView, String bandName) {
        Band thisBand = null;
        for (Band band : this.bands) {
            if (band.getPerformerName().equals(bandName)) {
                thisBand = band;
            }
        }
        for (String item : listView.getItems()) {
            Artist thisArtist = new Artist(item);
            if (!artistAlreadyExists(item)) {
                this.artists.add(thisArtist);
            }
            if (!thisBand.getMembers().contains(thisArtist)) {
                thisBand.addMember(thisArtist);
            }
        }
    }

    //add a location
    public void addLocation(int[][] path, String name, Point2D size) {
        this.locations.add(new Location(path, name, size));
    }

    //add a show
    public void addShow(String name, String location, String performer, LocalTime beginTime, LocalTime endTime) {
        for (Show show : this.shows) {
            //check if the show's name is used
            if (show.getName().equals(name)) {
                return;
            }

            //control if the time is already occupied at the given stage
            if (((beginTime.isAfter(show.getBeginTime()) && beginTime.isBefore(show.getEndTime())
                    || (endTime.isAfter(show.getBeginTime()) && endTime.isBefore(show.getEndTime()))
                    || (beginTime.compareTo(show.getBeginTime()) == 0)
                    || (endTime.compareTo(show.getEndTime()) == 0))
                    && location.equals(show.getLocation().getName()))) {
                System.out.println("2 shows cant be at the same time on the same stage!");
                return;
            }
        }
        Performer performer1 = null;
        for (Performer performer2 : this.performers) {
            if (performer2.getPerformerName().equals(performer)) {
                performer1 = performer2;
            }
        }
        Location location1 = null;
        for (Location location2 : this.locations) {
            if (location2.getName().equals(location)) {
                location1 = location2;
            }
        }
        Show newShow = new Show(name, location1, performer1, beginTime, endTime);
        this.shows.add(newShow);
    }

    //get the given show by name
    public Show getShow(String name) {
        for (Show show : this.shows) {
            if (show.getName().equals(name)) {
                return show;
            }
        }
        return null;
    }

    //remove the given show
    public void removeShow(Show show) {
        this.shows.remove(show);
    }

    //update the given list
    public void updateList(ListView<String> listView) {
        listView.getItems().clear();
        for (Performer performer : this.performers) {
            listView.getItems().add(performer.getPerformerName());
        }
    }

    //add a band if it doesnt already exist
    public void addBand(String performerName, int popularity) {
        if (!performerAlreadyExists(performerName) && !bandAlreadyExists(performerName)) {
            Band newBand = new Band(performerName, popularity);
            this.performers.add(newBand);
            this.bands.add(newBand);
        } else if (bandAlreadyExists(performerName) && !performerAlreadyExists(performerName)) {
            for (Band artist : this.bands) {
                if (artist.getPerformerName().equals(performerName)) {
                    performers.add(artist);
                }
            }
        } else {
            System.out.println("Cannot add duplicates (" + performerName + ")");
        }
    }

    //remove the given performer by name
    public void removePerformer(String performerName) {
        for (int i = 0; i < this.performers.size(); i++) {
            Performer performer = this.performers.get(i);
            if (performer.getPerformerName().equals(performerName)) {
                this.performers.remove(i);
            }
        }
    }

    //get all performers' names
    public ArrayList<String> getPerformersString() {
        ArrayList<String> names = new ArrayList<>();
        for (Performer performer : this.performers) {
            names.add(performer.getPerformerName());
        }
        return names;
    }

    //get all location's names
    public ArrayList<String> getLocationsString() {
        ArrayList<String> names = new ArrayList<>();
        for (Location location : this.locations) {
            names.add(location.getName());
        }
        return names;
    }

    //change this performerController to the given performerController
    public void loadFrom(PerformerController newPerformerController) {
        this.performers = newPerformerController.performers;
        this.artists = newPerformerController.artists;
        this.bands = newPerformerController.bands;
        this.locations = newPerformerController.locations;
        this.shows = newPerformerController.shows;
    }
}
