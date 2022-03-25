package Planner;

import DataStructure.Data.Show;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ShowBlock {
    private Rectangle2D.Double block;
    private Point2D.Double position;
    private String text;
    private int row;

    public String getShowName() {
        return show.getName();
    }
    public String getShowPerformer(){
        return show.getPerformerName();
    }
    public String getShowTime(){
        return show.getBeginTime() + " - " + show.getEndTime();
    }

    private Show show;


    public ShowBlock(Show show, int row) {
        this.row = row;
        this.show = show;
        position = new Point2D.Double((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0)+100, (((this.show.getEndTime().getHour()*100) + (this.show.getEndTime().getMinute()/60.0*100.0))-((this.show.getBeginTime().getHour()*100))));
        block = new Rectangle2D.Double((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0)+100, 100 + 100*row, (((this.show.getEndTime().getHour()*100) + (this.show.getEndTime().getMinute()/60.0*100.0))-((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0))) , 100);
        System.out.println(this.show.getBeginTime().getMinute() / 100 + 100 - this.show.getEndTime().getMinute() / 100 + 100);
        this.text = "Show: " + this.show.getName() + "\n" +  "Perfomer: " + this.show.getPerformerName() + "\n" + this.show.getBeginTime();

        System.out.println(row);

    }

    public Rectangle2D.Double getBlock() {
        System.out.println(block.x + "," + block.y);
        return block;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
        block.setRect(position.getX(), position.getY(), block.getWidth(), block.getHeight());
    }

    public void draw(FXGraphics2D graphics) {
        System.out.println(position.x + " " + position.y);
        Font font = new Font(Font.MONOSPACED, 5, 16);
        graphics.setColor(Color.CYAN);
        graphics.fill(block);
        graphics.setColor(Color.black);

        AffineTransform transform = new AffineTransform();
        transform.translate(block.x+5, block.y+20);
        GlyphVector vector = font.createGlyphVector(graphics.getFontRenderContext(), "Show: " + this.show.getName());
        Shape a = vector.getOutline();

        a = transform.createTransformedShape(a);
        graphics.fill(a);

        transform.translate(0, 20);
        vector = font.createGlyphVector(graphics.getFontRenderContext(), "Perfomer: " + this.show.getPerformerName());
        a = vector.getOutline();

        a = transform.createTransformedShape(a);
        graphics.fill(a);


        transform.translate(0, 20);
        vector = font.createGlyphVector(graphics.getFontRenderContext(), this.show.getBeginTime() + " - " + this.show.getEndTime());
        a = vector.getOutline();


        a = transform.createTransformedShape(a);
        graphics.fill(a);

        graphics.draw(block);



    }
}
