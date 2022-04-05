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

    private Show show;


    public ShowBlock(Show show, int row) {
        this.row = row;
        this.show = show;
        position = new Point2D.Double((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0)+100, (((this.show.getEndTime().getHour()*100) + (this.show.getEndTime().getMinute()/60.0*100.0))-((this.show.getBeginTime().getHour()*100))));
        block = new Rectangle2D.Double((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0)+100, 100 + 100*row, (((this.show.getEndTime().getHour()*100) + (this.show.getEndTime().getMinute()/60.0*100.0))-((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0))) , 100);
        this.text = "Show: " + this.show.getName() + "\n" +  "Perfomer: " + this.show.getPerformerName() + "\n" + this.show.getBeginTime();


    }

    //draw the block on the right spot with cropped text
    public void draw(FXGraphics2D graphics) {
        Font font = new Font(Font.MONOSPACED, 5, 16);
        graphics.setColor(Color.CYAN);
        graphics.fill(block);
        graphics.setColor(Color.black);

        AffineTransform transform = new AffineTransform();
        transform.translate(block.x+5, block.y+20);
        GlyphVector vector;

        //draw the shows name within the block
        if(("Show: " + this.show.getName()).length() > this.block.width/10){
            vector = font.createGlyphVector(graphics.getFontRenderContext(), ("Show: " + this.show.getName()).substring(0, (int)this.block.width/10));

        }  else {
            vector = font.createGlyphVector(graphics.getFontRenderContext(), "Show: " + this.show.getName());
        }
        Shape a = vector.getOutline();

        a = transform.createTransformedShape(a);
        graphics.fill(a);

        //draw the performer's name withing the block
        transform.translate(0, 20);
        if(("By: " + this.show.getPerformerName()).length() > this.block.width/10){
            vector = font.createGlyphVector(graphics.getFontRenderContext(), ("By: " + this.show.getPerformerName()).substring(0, (int)this.block.width/10));

        }  else {
            vector = font.createGlyphVector(graphics.getFontRenderContext(), "By: " + this.show.getPerformerName());
        }
        a = vector.getOutline();

        a = transform.createTransformedShape(a);
        graphics.fill(a);

        //draw the show's time within the block
        transform.translate(0, 20);
        if((this.show.getBeginTime() + " - " + this.show.getEndTime()).length() > this.block.width/10){
            vector = font.createGlyphVector(graphics.getFontRenderContext(), (this.show.getBeginTime() + " - " + this.show.getEndTime()).substring(0, (int)this.block.width/10));

        }  else {
            vector = font.createGlyphVector(graphics.getFontRenderContext(), this.show.getBeginTime() + " - " + this.show.getEndTime());
        }
        a = vector.getOutline();


        a = transform.createTransformedShape(a);
        graphics.fill(a);

        graphics.draw(block);



    }
}
