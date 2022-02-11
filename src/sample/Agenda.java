package sample;

import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class Agenda extends Canvas {

    private FXGraphics2D graphics = new FXGraphics2D(this.getGraphicsContext2D());
    public Point2D position = new Point2D.Double(100,100);


    public Agenda() {

    }

    public void testDraw() {
        this.graphics.setBackground(Color.white);
        this.graphics.clearRect(0,0,1920,1080);
        this.graphics.setStroke(new BasicStroke(20));
        this.graphics.draw(new Rectangle2D.Double(position.getX()-50, position.getY()-50, 100, 100));

    }

    public void drawAgendaBase(){

    }

    public void addShow(){

    }

    public void moveOnMouse(double X, double Y) {
        position = new Point2D.Double(X, Y);
        this.testDraw();
    }
}
