package Simulation;
import DataStructure.Data.Performer;
import DataStructure.Data.Show;
import DataStructure.PerformerController;
import Planner.Npc;
import Planner.StandardScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class SimulatorScene extends StandardScene implements Resizable {
    private final Color BACKGROUND_COLOR = new Color(44, 139, 42, 255);
    FXGraphics2D graphics;
    ResizableCanvas canvas;
    AffineTransform transform = new AffineTransform();
    ArrayList<Npc> npcs;
    double timer;
    private SimulationMap map;
    private Camera camera;
    private double time;
    private double fps;
    private AgendaFollower agendaFollower;
    private PerformerController performerController;
    private AnimationTimer animationTimer;

    public SimulatorScene(PerformerController performerController) throws Exception {
        this.time = 0;
        this.fps = 60;
        BorderPane borderPane = new BorderPane();
        this.map = new SimulationMap("TileMap1.json", new Pathfinding(), performerController);
        this.canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.setCenter(this.canvas);
        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        init();
        this.performerController = performerController;
        this.agendaFollower = new AgendaFollower(performerController, this.npcs);

        this.camera = new Camera(this.canvas, this, this.graphics);


        Label timer = new Label("00:00:00");
        borderPane.getChildren().add(timer);

        this.scene = new Scene(borderPane);

        this.animationTimer = new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                timer.setText(agendaFollower.getCurrentTime().toString().substring(0,5));
                update((now - last) / 1000000000.0);
                last = now;
            }
        };
    }

    //create the amount of npc's you want to use
    public void init() throws IOException {
        this.npcs = new ArrayList<>();
        while (this.npcs.size() < 1000) {
            Npc npc = new Npc(new Point2D.Double(832, 1600), 0);
            this.npcs.add(npc);
        }

        this.timer = 0;
    }

    //update all npc's and simulation time on a timer
    public void update(double deltaTime) {
        this.time += deltaTime;
        if (this.time >= 1.0 / this.fps) {
            this.time = 0;
            for (Npc npc : this.npcs) {
                npc.update();
            }
            LocalTime newTime = this.agendaFollower.getCurrentTime().plusSeconds((long) (1));
            this.agendaFollower.setCurrentTime(newTime);
            draw(this.graphics);

        }
    }

    //get the animation timer
    public AnimationTimer getAnimationTimer(){
        return this.animationTimer;
    }

    //draw the map and all npc's
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int) this.canvas.getHeight() * 2, (int) this.canvas.getWidth() * 2);

        //transform with the camera
        this.transform.setTransform(this.camera.getTransform());
        graphics.setTransform(this.transform);

        //set the background green like the map
        graphics.setBackground(BACKGROUND_COLOR);

        //draw the map
        this.map.draw(graphics);

        //draw the timer
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        graphics.drawString(this.agendaFollower.getCurrentTime().toString(), 30, 30);

        //draw the npc's
        if (!this.npcs.isEmpty()) {
            for (Npc npc : this.npcs) {
                npc.draw(graphics);
            }
        }
    }

    //get the agenda follower
    public AgendaFollower getAgendaFollower() {
        return this.agendaFollower;
    }

}