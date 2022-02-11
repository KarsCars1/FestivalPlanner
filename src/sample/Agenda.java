package sample;

import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;


public class Agenda extends Canvas {

    private FXGraphics2D graphics = new FXGraphics2D(this.getGraphicsContext2D());


    public Agenda() {

    }

    public void testDraw() {
        graphics.setColor(Color.BLACK);
//        graphics.translate(600/2, 400/2);
//        graphics.draw(new Line2D.Double(-250, 0, 250, 0));
//        graphics.draw(new Line2D.Double(0, 250, 0, -250));
        graphics.draw(new Rectangle2D.Double(100, 100, 200,200));

    }

    public void drawAgendaBase(){

    }

    public void addShow(){

    }
}
