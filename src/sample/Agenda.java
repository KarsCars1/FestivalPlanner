package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;


public class Agenda extends Canvas {

    private FXGraphics2D graphics = new FXGraphics2D(this.getGraphicsContext2D());
    private LinkedList<ShowBlock> shows = new LinkedList<>();

    private boolean clickedOnBlock = false;

    public Point2D position = new Point2D.Double(0, 0);
    public Point2D oldPosition;


    public Agenda() {
    }

    //todo make proper base for agenda
    public void drawAgendaBase() {
        graphics.setClip(null);
        this.graphics.setBackground(Color.white);
        this.graphics.clearRect(0, 0, (int)getWidth(), (int)getHeight());


        graphics.setColor(Color.gray);
        graphics.fill(new Rectangle2D.Double(5, 5, getWidth(), 100));
        graphics.fill(new Rectangle2D.Double(5, 5, 100, getHeight()));


        graphics.setColor(Color.black);
        this.graphics.setStroke(new BasicStroke(10));
        graphics.draw(new Rectangle2D.Double(5, 5, getWidth(), 100));
        graphics.draw(new Rectangle2D.Double(5, 5, 100, getHeight()));

        Font font = new Font("Dialog", Font.PLAIN, 20);
        GlyphVector agendaText = font.createGlyphVector(graphics.getFontRenderContext(), "12:00                12:30                13:00                13:30                14:00                14:30                15:00                15:30                16:00                16:30                17:00                17:30");

        AffineTransform transform = new AffineTransform();
        transform.translate(200, 50);
        Shape text = transform.createTransformedShape(agendaText.getOutline());
        graphics.fill(text);

        //graphics.setClip(new Rectangle2D.Double(100, 100, 100, 100));
    }

    public void addShow() {
        this.shows.add(new ShowBlock());
    }

    public void moveOnMouse(double X, double Y) {
        //move the block you clicked on
        if(clickedOnBlock) {
            position = new Point2D.Double(X, Y);
            int i = 0;
            for (ShowBlock show : shows) {
                i++;
                if (show.getBlock().contains(position)) {
                    show.setPosition(new Point2D.Double(calculateX(show), calculateY(show)));
                    //put the show on the front so that it's drawn on top of the rest
                    shows.addFirst(show);
                    shows.remove(i);
                    break;
                }
            }
            oldPosition = position;

            drawShows();
        }
    }

    public void drawShows() {
        drawAgendaBase();

        //draw the shows
        graphics.setColor(Color.black);
        for (int i = shows.size() - 1; i >= 0; i--) {
            graphics.draw(shows.get(i).getBlock());
        }
    }

    private double calculateX(ShowBlock show) {
        return show.getPosition().getX() + position.getX() - oldPosition.getX();
    }

    private double calculateY(ShowBlock show) {
        return show.getPosition().getY() + position.getY() - oldPosition.getY();
    }

    public void mousePressed(MouseEvent e) {
        position = new Point2D.Double(e.getX(), e.getY());
        //only move a block if you originally clicked on a block
        for (ShowBlock show : shows) {
            if (show.getBlock().contains(position)) {
                clickedOnBlock = true;
                break;
            }
        }

        oldPosition = new Point2D.Double(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        clickedOnBlock = false;
        oldPosition = null;
    }
}
