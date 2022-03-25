package Simulation;

import DataStructure.PerformerController;
import Planner.Npc;
import Planner.StandardScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

public class SimulatorScene extends StandardScene implements Resizable {
    private final Color backgroundColor = new Color(44, 139, 42, 255);
    FXGraphics2D graphics;
    ResizableCanvas canvas;
    AffineTransform transform = new AffineTransform();
    ArrayList<Npc> npcs;
    double timer;
    private SimulationMap map;
    private int count = 0;
    private Camera camera;
    private double time;
    private double fps;
    private AgendaFollower agendaFollower;

    public SimulatorScene(PerformerController performerController) throws Exception {
        time = 0;
        fps = 60;
        BorderPane borderPane = new BorderPane();
        map = new SimulationMap("TileMap1.json", new Pathfinding(), graphics, performerController);
        canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.setCenter(canvas);
        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        init(performerController);
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


        scene = new Scene(borderPane);
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
            }
        }.start();
    }

    public void init(PerformerController performerController) throws IOException {
        this.npcs = new ArrayList<>();
        while (this.npcs.size() < 500) {
            Npc npc = new Npc(new Point2D.Double(832, 1600), 0);
            this.npcs.add(npc);
            //npc.setPathfinding(performerController.getLocations().get(new Random().nextInt(performerController.getLocations().size())).getPath());
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
            agendaFollower.setCurrentTime(agendaFollower.getCurrentTime().plusSeconds((long) (deltaTime * 180)));
            draw(graphics);

        }
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int) canvas.getHeight() * 2, (int) canvas.getWidth() * 2);
        transform.setTransform(camera.getTransform());
        //System.out.println(transform.getScaleX() + " " + transform.getScaleY() + " " + transform.getTranslateX() + " " + transform.getTranslateY());
        graphics.setTransform(transform);
        graphics.setBackground(backgroundColor);
        map.draw(graphics, canvas.getHeight(), canvas.getWidth());
        if (!npcs.isEmpty()) {
            for (Npc npc : npcs) {
                npc.draw(graphics);
            }
        }
    }
}


//    private final Color backgroundColor = new Color(44, 139, 42, 255);
//    FXGraphics2D graphics;
//    ResizableCanvas canvas;
//    AffineTransform transform = new AffineTransform();
//    private SimulationMap map;
//    private int count = 0;
//    private Camera camera;
//    private double time;
//    private double fps;
//
//    public SimulatorScene() throws Exception {
//
//        time = 0;
//        fps = 30;
//        BorderPane borderPane = new BorderPane();
//
//        canvas = new ResizableCanvas(g -> draw(g), borderPane);
//        borderPane.setCenter(canvas);
//        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
//        map = new SimulationMap("TileMap1.json", new Pathfinding(), graphics);
//        canvas.setOnMouseDragged(e -> {
//            camera.mouseDragged(e);
//            graphics.setTransform(camera.getTransform());
//        });
//
//        canvas.setOnScroll(e -> {
//            camera.mouseScroll(e);
//            graphics.setTransform(camera.getTransform());
//            draw(graphics);
//        });
//
//        camera = new Camera(canvas, this, graphics);
//
//
//        scene = new Scene(borderPane);
//
//        new AnimationTimer() {
//            long last = -1;
//
//            @Override
//            public void handle(long now) {
//                if (last == -1)
//                    last = now;
//                update((now - last) / 1000000000.0);
//                last = now;
//            }
//        }.start();
//    }
//
//    public void update(double deltaTime) {
//        time += deltaTime;
//        if (time >= 1.0 / fps) {
//            time = 0;
//            draw(graphics);
//        }
//    }
//
//    public void draw(FXGraphics2D graphics) {
//        graphics.setTransform(new AffineTransform());
//        graphics.clearRect(0, 0, (int) canvas.getHeight() * 2, (int) canvas.getWidth() * 2);
//        transform.setTransform(camera.getTransform());
//        //System.out.println(transform.getScaleX() + " " + transform.getScaleY() + " " + transform.getTranslateX() + " " + transform.getTranslateY());
//        graphics.setTransform(transform);
//        graphics.setBackground(backgroundColor);
//        map.draw(graphics);
//    }
