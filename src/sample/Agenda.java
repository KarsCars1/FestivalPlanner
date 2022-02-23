package sample;

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
import java.util.LinkedList;


public class Agenda extends Canvas {

    public Point2D position = new Point2D.Double(0, 0);
    public Point2D oldPosition;
    private FXGraphics2D graphics = new FXGraphics2D(this.getGraphicsContext2D());
    private LinkedList<ShowBlock> shows = new LinkedList<>();
    private boolean clickedOnBlock = false;


    public Agenda() {
    }

    //todo make proper base for agenda
    public void drawAgendaBase() {
        System.out.println("hallo");//        graphics.setClip(null);
        this.graphics.setBackground(Color.white);
        this.graphics.clearRect(0, 0, (int) getWidth(), (int) getHeight());


        graphics.setColor(Color.gray);
        graphics.fill(new Rectangle2D.Double(5, 5, 90, getHeight()));


        graphics.setColor(Color.black);
        this.graphics.setStroke(new BasicStroke(10));
        graphics.draw(new Rectangle2D.Double(5, 5, 90, getHeight()));
        graphics.setColor(Color.gray);
        graphics.fill(new Rectangle2D.Double(5, 5, getWidth(), 100));
        graphics.setColor(Color.black);
        graphics.draw(new Rectangle2D.Double(5, 5, getWidth(), 100));

        this.graphics.setStroke(new BasicStroke(2));
        Font font = new Font("Dialog", Font.PLAIN, 20);
        GlyphVector agendaText;
        for (int i = 0; i < 25; i++) {
            if (i < 10) {
                agendaText = font.createGlyphVector(graphics.getFontRenderContext(), "0" + i + ":00");
            } else {
                agendaText = font.createGlyphVector(graphics.getFontRenderContext(), i + ":00");
            }

            AffineTransform transform = new AffineTransform();
            transform.translate(i * 100 + 75, 60);
            Shape text = transform.createTransformedShape(agendaText.getOutline());
            graphics.fill(text);

            graphics.draw(new Line2D.Double(i * 100 + 100, 105, i * 100 + 100, getHeight()));
        }


        //graphics.setClip(new Rectangle2D.Double(100, 100, 100, 100));
    }

    public void addShowBlock(Show show) {
        if (!this.shows.contains(show)) {
            this.shows.add(new ShowBlock(show));
        }
        drawShows();
    }

    public void moveOnMouse(double X, double Y) {
        //move the block you clicked on
        if (clickedOnBlock) {
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


        graphics.setColor(Color.green);
        for (ShowBlock show : shows) {

            Rectangle.Double rectangle = show.getBlock();
            System.out.println(rectangle.toString());
            graphics.fill(rectangle);
        }
//        for (int i = shows.size() - 1; i >= 0; i--) {
//            System.out.println("??");
//            graphics.fill(shows.get(i).getBlock());
//
//        }
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
