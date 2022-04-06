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

    public AgendaFollower(PerformerController performerController, ArrayList<Npc> npcs) {
        this.performerController = performerController;
        this.currentTime = getBeginTime();
        this.npcs = npcs;
        this.running = false;
    }

    //start the simulation 20 minutes before the first show
    public LocalTime getBeginTime() {
        if (this.performerController.getShows().size() > 0) {
            Show earliest = this.performerController.getShows().get(0);
            for (Show show : this.performerController.getShows()) {
                if (show.getBeginTime().compareTo(earliest.getBeginTime()) < 0) {
                    earliest = show;
                }
            }

            //dont start the previous day because that causes problems with npcs thinking the show has ended
            if (earliest.getBeginTime().minusMinutes(20).isBefore(earliest.getBeginTime())) {
                return earliest.getBeginTime().minusMinutes(20);
            }
        }

        return LocalTime.of(0, 00);


    }

    //return the current time in the simulation
    public LocalTime getCurrentTime() {
        return this.currentTime;
    }

    private LocalTime earlyStart = LocalTime.of(0, 16);

    //reset the early start
    public void reset(){
        this.earlyStart = LocalTime.of(0, 16);
    }

    //set the current time
    public void setCurrentTime(LocalTime newTime) {
        ArrayList<Show> shows = performerController.getShows();
        for (Show show : shows) {
            if (((show.getBeginTime().minusMinutes(15).compareTo(newTime) <= 0) &&
                    (show.getBeginTime().minusMinutes(15).compareTo(this.currentTime) >= 0)) ||
                    show.getBeginTime().isBefore(this.earlyStart)) {
                this.earlyStart = LocalTime.of(0, 0);
                for (Npc npc : this.npcs) {
                    if (show.getPerformer().getPopularity() > Math.random() * 100) {
                        System.out.println(npc);
                        npc.setPathfinding(show.getLocation());
                        npc.setCurrentShow(show);
                    }
                }
            }
        }

        this.currentTime = newTime;

    }

    //check if the simulation is running
    public boolean isRunning() {
        return this.running;
    }

    //turn on the simulation
    public void setRunning(boolean running) {
        this.running = running;
    }
}
