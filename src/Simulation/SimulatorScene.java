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
    private final Color backgroundColor = new Color(44, 139, 42, 255);
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
        time = 0;
        fps = 60;
        BorderPane borderPane = new BorderPane();
        map = new SimulationMap("TileMap1.json", new Pathfinding(), performerController);
        canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.setCenter(canvas);
        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        init();
        this.performerController = performerController;
        this.agendaFollower = new AgendaFollower(performerController, npcs);

        canvas.setOnMouseDragged(e -> {
            camera.mouseDragged(e);
            graphics.setTransform(camera.getTransform());
        });

        canvas.setOnScroll(e -> {
            camera.mouseScroll(e);
            graphics.setTransform(camera.getTransform());
        });

        camera = new Camera(canvas, this, graphics);
        Label timer = new Label("00:00:00");
        borderPane.getChildren().add(timer);
        scene = new Scene(borderPane);
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

    public void init() throws IOException {
        this.npcs = new ArrayList<>();
        while (this.npcs.size() < 1000) {
            Npc npc = new Npc(new Point2D.Double(832, 1600), 0);
            this.npcs.add(npc);
        }

        timer = 0;
    }

    public void update(double deltaTime) {
        time += deltaTime;
        if (time >= 1.0 / fps) {
            time = 0;
            for (Npc npc : npcs) {
                npc.update();
            }
            LocalTime newTime = agendaFollower.getCurrentTime().plusSeconds((long) (1));
            agendaFollower.setCurrentTime(newTime);
            draw(graphics);

        }
    }

    public AnimationTimer getAnimationTimer(){
        return this.animationTimer;
    }

    public void searchShows(LocalTime oldTime, LocalTime newtime){
        for (Show show: this.performerController.getShows()) {
            if(show.getBeginTime().minusMinutes(15).compareTo(newtime) <= 0 && show.getBeginTime().minusMinutes(15).compareTo(oldTime) >= 0){
                for (Npc npc : this.npcs) {
                    npc.setPathfinding(show.getLocation());
                }
            }
        }
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int) canvas.getHeight() * 2, (int) canvas.getWidth() * 2);
        transform.setTransform(camera.getTransform());
        graphics.setTransform(transform);
        graphics.setBackground(backgroundColor);
        map.draw(graphics, canvas.getHeight(), canvas.getWidth());
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        graphics.drawString(agendaFollower.getCurrentTime().toString(), 30, 30);
        if (!npcs.isEmpty()) {
            for (Npc npc : npcs) {
                npc.draw(graphics);
            }
        }
    }

    public AgendaFollower getAgendaFollower() {
        return agendaFollower;
    }

}