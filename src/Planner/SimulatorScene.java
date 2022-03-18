package Planner;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class SimulatorScene extends StandardScene implements Resizable {
    FXGraphics2D graphics;
    ResizableCanvas canvas;
    AffineTransform transform = new AffineTransform();

    private SimulationMap map;
    private int count = 0;
    private Camera camera;
    private final Color backgroundColor = new Color(44, 139, 42, 255);

    public SimulatorScene(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        map = new SimulationMap("TileMap1.json");
        canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.setCenter(canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseDragged(e ->{
            camera.mouseDragged(e);
            graphics.setTransform(camera.getTransform());
        });

        canvas.setOnScroll(e ->{
            camera.mouseScroll(e);
            graphics.setTransform(camera.getTransform());
            draw(graphics);
        });

        camera = new Camera(canvas, this, graphics);

        scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Simulation");
        stage.show();
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int)canvas.getHeight()*2, (int)canvas.getWidth()*2);
        transform.setTransform(camera.getTransform());
        //System.out.println(transform.getScaleX() + " " + transform.getScaleY() + " " + transform.getTranslateX() + " " + transform.getTranslateY());
        graphics.setTransform(transform);
        graphics.setBackground(backgroundColor);
        map.draw(graphics);
    }
}
