package Simulation;

import Planner.GuiCallback;
import Planner.StandardScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SimulatorScene extends StandardScene implements Resizable {
    private final Color backgroundColor = new Color(44, 139, 42, 255);
    FXGraphics2D graphics;
    ResizableCanvas canvas;
    AffineTransform transform = new AffineTransform();
    private SimulationMap map;
    private int count = 0;
    private Camera camera;
    private double time;
    private double fps;

    public SimulatorScene() throws Exception {

        time = 0;
        fps = 30;
        BorderPane borderPane = new BorderPane();

        canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.setCenter(canvas);
        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        map = new SimulationMap("TileMap1.json", new Pathfinding(), graphics);
        canvas.setOnMouseDragged(e -> {
            camera.mouseDragged(e);
            graphics.setTransform(camera.getTransform());
        });

        canvas.setOnScroll(e -> {
            camera.mouseScroll(e);
            graphics.setTransform(camera.getTransform());
            draw(graphics);
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

    public void update(double deltaTime) {
        time += deltaTime;
        if (time >= 1.0 / fps) {
            time = 0;
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
        map.draw(graphics);
    }
}
