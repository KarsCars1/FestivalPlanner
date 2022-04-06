package Simulation;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


/**
 * Created by johan on 15-2-2017.
 */
public class Camera {
    private Point2D centerPoint = new Point2D.Double(0,0);
    private double zoom = 1;
    private double rotation = 0;
    private Point2D lastMousePos;
    private Canvas canvas;
    private Resizable resizable;
    private FXGraphics2D g2d;

    public Camera(Canvas canvas, Resizable resizable, FXGraphics2D g2d) {
        this.canvas = canvas;
        this.resizable = resizable;
        this.g2d = g2d;

        //check where you clicked
        canvas.setOnMousePressed(e -> {
            this.lastMousePos = new Point2D.Double(e.getX(), e.getY());
        });

        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnScroll(e-> mouseScroll(e));
    }

    //return the current transform
    public AffineTransform getTransform()  {
        AffineTransform tx = new AffineTransform();
        tx.scale(this.zoom, this.zoom);
        tx.translate(this.centerPoint.getX(), this.centerPoint.getY());
        tx.rotate(this.rotation);
        return tx;
    }

    //react to a dragging mouse
    public void mouseDragged(MouseEvent e) {
        if(e.getButton() == MouseButton.PRIMARY) {
            this.centerPoint = new Point2D.Double(
                    this.centerPoint.getX() - (this.lastMousePos.getX() - e.getX()) / this.zoom,
                    this.centerPoint.getY() - (this.lastMousePos.getY() - e.getY()) / this.zoom
            );
            lastMousePos = new Point2D.Double(e.getX(), e.getY());

        }
    }

    //change the zoom on scroll
    public void mouseScroll(ScrollEvent e) {
        this.zoom *= (1 + e.getDeltaY()/250.0f);

    }
}
