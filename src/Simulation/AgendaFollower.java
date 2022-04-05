package Simulation;
import DataStructure.Data.Show;
import DataStructure.PerformerController;
import Planner.Npc;

import java.time.LocalTime;
import java.util.ArrayList;

public class AgendaFollower {
    private boolean running;
    private LocalTime currentTime;
    private PerformerController performerController;
    private ArrayList<Npc> npcs;

    public AgendaFollower(PerformerController performerController, ArrayList<Npc> npcs){
        this.performerController = performerController;
        this.currentTime = getBeginTime();
        this.npcs = npcs;
        this.running = false;
    }

    public LocalTime getBeginTime(){
        if(performerController.getShows().size() > 0){
            Show earliest = performerController.getShows().get(0);
            for (Show show : performerController.getShows()) {
                if(show.getBeginTime().compareTo(earliest.getBeginTime()) < 0){
                    earliest = show;
                }
            }
            return earliest.getBeginTime().minusMinutes(20);
        }
        else {
            return LocalTime.of(0, 00);
        }

    }

    public LocalTime getCurrentTime(){
        return this.currentTime;
    }

    public void setCurrentTime(LocalTime newTime){
        ArrayList<Show> shows = performerController.getShows();

        //System.out.println(newTime.toString());
        for (Show show: shows) {
            if(show.getBeginTime().minusMinutes(15).compareTo(newTime) <= 0 && show.getBeginTime().minusMinutes(15).compareTo(this.currentTime) >= 0){
                for (Npc npc : this.npcs) {
                    System.out.println(show.getPerformer().getPopularity());
                    if (show.getPerformer().getPopularity() > Math.random()*100){
                        npc.setPathfinding(show.getLocation());
                    }
                }
            }
        }

        this.currentTime = newTime;

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
