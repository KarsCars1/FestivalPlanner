package Simulation;

import DataStructure.Data.Show;
import DataStructure.PerformerController;
import Planner.Npc;

import java.time.LocalTime;
import java.util.ArrayList;

public class AgendaFollower {
    private LocalTime currentTime;
    private PerformerController performerController;
    private ArrayList<Npc> npcs;

    public AgendaFollower(PerformerController performerController, ArrayList<Npc> npcs){
        this.performerController = performerController;
        this.currentTime = LocalTime.of(14, 00);
        this.npcs = npcs;
    }

    public LocalTime getCurrentTime(){
        return this.currentTime;
    }

    public void setCurrentTime(LocalTime newTime){
        ArrayList<Show> shows = performerController.getShows();
        System.out.println(newTime.toString());
        for (Show show: shows) {
            if(show.getBeginTime().compareTo(newTime) <= 0 && show.getBeginTime().compareTo(this.currentTime) >= 0){
                for (Npc npc : this.npcs) {
                    if (show.getPopularity() > Math.random()){
                        npc.setPathfinding(show.getLocation().getPath());
                    }
                }
            }
        }

        this.currentTime = newTime;

    }
}