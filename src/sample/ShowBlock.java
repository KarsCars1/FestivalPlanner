package sample;

import DataStructure.Data.Show;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ShowBlock {
    private Rectangle2D.Double block;
    private Point2D.Double position;
    private Show show;


    public ShowBlock(Show show) {
        this.show = show;
        position = new Point2D.Double(this.show.getBeginTime().getMinute()/100+100, 100);
        block = new Rectangle2D.Double(this.show.getBeginTime().getMinute()/100+100, 100, this.show.getBeginTime().getMinute()/100+100-this.show.getEndTime().getMinute()/100+100,100);
        System.out.println(this.show.getBeginTime().getMinute()/100+100-this.show.getEndTime().getMinute()/100+100);
    }

    public Rectangle2D.Double getBlock() {
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
