package Planner;

import DataStructure.Data.Location;
import DataStructure.Data.Show;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;


public class Agenda extends Canvas {
    private FXGraphics2D graphics = new FXGraphics2D(this.getGraphicsContext2D());
    private LinkedList<ShowBlock> shows = new LinkedList<>();
    private ArrayList<Location> locations;


    public Agenda(ArrayList<Location> locations) {
        this.locations = locations;
    }

    //draw the base of the agenda
    public void drawAgendaBase() {
        this.graphics.setBackground(Color.white);
        this.graphics.clearRect(0, 0, (int) getWidth(), (int) getHeight());


        this.graphics.setColor(Color.gray);
        this.graphics.fill(new Rectangle2D.Double(5, 5, 90, getHeight()));


        //draw the lines of the top and bottom row
        this.graphics.setColor(Color.black);
        this.graphics.setStroke(new BasicStroke(10));
        this.graphics.draw(new Rectangle2D.Double(5, 5, 90, getHeight()));
        this.graphics.setColor(Color.gray);
        this.graphics.fill(new Rectangle2D.Double(5, 5, getWidth(), 90));
        this.graphics.setColor(Color.black);
        this.graphics.draw(new Rectangle2D.Double(5, 5, getWidth(), 90));

        this.graphics.setStroke(new BasicStroke(2));
        Font font = new Font("Dialog", Font.PLAIN, 20);
        GlyphVector agendaText;

        //draw the time slots
        for (int i = 0; i < 25; i++) {
            if (i < 10) {
                agendaText = font.createGlyphVector(this.graphics.getFontRenderContext(), "0" + i + ":00");
            } else {
                agendaText = font.createGlyphVector(this.graphics.getFontRenderContext(), i + ":00");
            }

            AffineTransform transform = new AffineTransform();
            transform.translate(i * 100 + 75, 60);
            Shape text = transform.createTransformedShape(agendaText.getOutline());
            this.graphics.fill(text);

            this.graphics.draw(new Line2D.Double(i * 100 + 100, 95, i * 100 + 100, getHeight()));
        }


        //draw the location slots
        int i =0;
        for (Location location : this.locations) {
            AffineTransform transform = new AffineTransform();
            transform.translate(20, 150 + i*100);
            GlyphVector vector = font.createGlyphVector(this.graphics.getFontRenderContext(), location.getName());
            Shape a = vector.getOutline();

            a = transform.createTransformedShape(a);
            this.graphics.fill(a);
            i++;

            this.graphics.draw(new Line2D.Double(100, 100.0 + 100*i, 2500, 100.0 + 100*i));
        }
    }

    //adds a showblock
    public void addShowBlock(Show show, int stage) {
        if (!this.shows.contains(show)) {
            this.shows.add(new ShowBlock(show, stage));
        }
        drawShows();
    }

    //draws each show on the correct time slot and location slot with the correct length
    public void drawShows() {
        drawAgendaBase();
        //draw the shows


        for (ShowBlock show : this.shows) {
            this.graphics.setColor(Color.green);

            show.draw(this.graphics);
        }
    }

    public LinkedList<ShowBlock> getShows() {
        return this.shows;
    }

    //updates the shows
    public void update() {

        drawAgendaBase();
        drawShows();
    }
}
