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
        this.currentTime = LocalTime.of(0, 00);
        this.npcs = npcs;
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
                    if (show.getPopularity() > Math.random() || npc.getPathfinding() != null){
                        npc.setPathfinding(show.getLocation().getPath());
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