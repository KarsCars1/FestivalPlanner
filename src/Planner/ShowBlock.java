package Planner;

import DataStructure.Data.Show;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ShowBlock {
    private Rectangle2D.Double block;
    private Point2D.Double position;

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


    public ShowBlock(Show show) {
        this.show = show;
        position = new Point2D.Double(this.show.getBeginTime().getMinute() / 100 + 110, 110);
        block = new Rectangle2D.Double((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0)+100, 110, (((this.show.getEndTime().getHour()*100) + (this.show.getEndTime().getMinute()/60.0*100.0))-((this.show.getBeginTime().getHour()*100) + (this.show.getBeginTime().getMinute()/60.0*100.0))) , 100);
        System.out.println(this.show.getBeginTime().getMinute() / 100 + 100 - this.show.getEndTime().getMinute() / 100 + 100);
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
}
