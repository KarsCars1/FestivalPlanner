package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;


public class Agenda extends Canvas {

    private FXGraphics2D graphics = new FXGraphics2D(this.getGraphicsContext2D());
    private LinkedList<ShowBlock> shows = new LinkedList<>();

    public Point2D position = new Point2D.Double(0,0);
    public Point2D oldPosition;


    public Agenda() {

    }

    public void testDraw() {
        this.graphics.setBackground(Color.white);
        this.graphics.clearRect(0,0,1920,1080);
        this.graphics.setStroke(new BasicStroke(20));
//        this.graphics.draw(new Rectangle2D.Double(position.getX()-50, position.getY()-50, 100, 100));

    }

    public void drawAgendaBase(){
        graphics.setColor(Color.black);
        this.graphics.setBackground(Color.white);
        this.graphics.clearRect(0,0,1920,1080);
        this.graphics.setStroke(new BasicStroke(20));
        for (int j = 0; j < 100; j++) {
            for(int i = 0; i < 100; i++){
                graphics.draw(new Rectangle2D.Double(i*100, j*100, 100, 100));

            }
        }
    }

    public void addShow(){
        this.shows.add(new ShowBlock());
    }

    public void moveOnMouse(double X, double Y) {
        position = new Point2D.Double(X, Y);
        int i = 0;
        for(ShowBlock show : shows){
            i++;
            if (show.getBlock().contains(position)) {
                //System.out.println("mouseX " + position.getX() + " mouseY " + position.getY() + " blokX " + blok.getX() + " blokY " + blok.getY());
                show.setPosition(new Point2D.Double(calculateX(show), calculateY(show)));

                System.out.println(calculateX(show) + " x, y " + calculateY(show));
                shows.addFirst(show);
                shows.remove(i);
                break;
            }
        }
        oldPosition = position;

        drawShows();
    }

    public void drawShows() {
        drawAgendaBase();
        graphics.setColor(Color.gray);
        for (int i = shows.size()-1; i >= 0; i--) {
            graphics.fill(shows.get(i).getBlock());
        }
    }

    private double calculateX(ShowBlock show) {
        return show.getPosition().getX() + position.getX() - oldPosition.getX();
    }

    private double calculateY(ShowBlock show) {

        return show.getPosition().getY() + position.getY() - oldPosition.getY();
    }

    public void mousePressed(MouseEvent e) {
        oldPosition = new Point2D.Double(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        oldPosition = null;
    }
}
