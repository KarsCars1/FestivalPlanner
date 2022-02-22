package sample;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ShowBlock {
    private Rectangle2D.Double block;
    private Point2D.Double position;


    public ShowBlock() {
        position = new Point2D.Double(200, 0);
        block = new Rectangle2D.Double(200, 0, 100,100);
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
