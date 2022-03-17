package Planner;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class SimulatorScene extends StandardScene {
    FXGraphics2D graphics;
    ResizableCanvas canvas;

    public SimulatorScene(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.setCenter(canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());

        scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Simulation");
        stage.show();
        draw(graphics);
    }

    private void draw(FXGraphics2D graphics) {

    }
}
