package Planner;

import DataStructure.Data.Location;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Npc {

    private static double rotationSpeed = 1;
    //    private BufferedImage fullImage;
    private boolean atStage = false;
    private Point2D position;
    private double angle;
    private ArrayList<BufferedImage> sprites;
    private double speed;
    private double frame;
    private Point2D target;
    private Point2D smallTarget;
    private int[][] pathfinding;
    private Location location;

    public int[][] getPathfinding() {
        return pathfinding;
    }

    public Npc(Point2D position, double angle) throws IOException {

        BufferedImage fullImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Npc_template.png"));

        this.position = position;
        this.target = position;
        this.smallTarget = new Point2D.Double(position.getX() / 16, position.getY() / 16);
        this.angle = angle;
        this.speed = 3 + 3 * Math.random();
        this.target = position;
        this.frame = Math.random() * 8;

        this.sprites = new ArrayList<>();
        int width = fullImage.getWidth() / 10;
        int height = fullImage.getHeight();
        for (int x = 0; x < 10; x++) {
            this.sprites.add(fullImage.getSubimage(x * width, 0, width, height));
        }
    }

    public void setPathfinding(Location location) {
        if (location.getPath() != null) {
            this.pathfinding = location.getPath();
            this.location = location;
            atStage = false;
        }

    }

    public void update() {
        this.frame++;
        if (target.distanceSq(position) < 32) {
            getNewTarget();
        }

        if(this.pathfinding != null && this.pathfinding[(int) this.smallTarget.getX() - 1][(int) this.smallTarget.getY() - 1] == 0 && !atStage){
            atStage = true;
        }
        if(atStage){
            target = new Point2D.Double(new Random().nextInt((int) this.location.getSize().getX() - 32),
                    new Random().nextInt((int) this.location.getSize().getY() - 32));
            target.setLocation(16 + target.getX() - this.location.getSize().getX() / 2 + smallTarget.getX() * 16, 16 + target.getY() - this.location.getSize().getY() / 2 + smallTarget.getY() * 16);
            //rotationSpeed = Math.random() - 0.5;
        }
        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(), this.target.getX() - this.position.getX());
        double rotation = targetAngle - this.angle;
        while (rotation < -Math.PI) {
            rotation += 2 * Math.PI;
        }
        while (rotation > Math.PI) {
            rotation -= 2 * Math.PI;
        }

//        double oldAngle = this.angle;
        if (rotation < -rotationSpeed) {
            this.angle -= rotationSpeed;
        } else if (rotation > rotationSpeed) {
            this.angle += rotationSpeed;
        } else {
            this.angle = targetAngle;
        }

//        Point2D oldposition = this.position;

        this.position = new Point2D.Double(this.position.getX() + this.speed * Math.cos(this.angle), this.position.getY() + this.speed * Math.sin(this.angle));

    }

    private void getNewTarget() {
        if (pathfinding != null) {

            int x = (int) smallTarget.getX();
            int y = (int) smallTarget.getY();
            Point2D lowest = smallTarget;
            int lowestNumber = 9999;
            if (pathfinding[x - 1][y - 1] == 0) {
            } else {

                for (int x1 = -1; x1 < 2; x1++) {
                    for (int y1 = -1; y1 < 2; y1++) {
                        if (!(x + x1 < 0 || x + x1 > 100 || y + y1 < 0 || y + y1 > 100)) {
                            int newNumber = pathfinding[x + x1 - 1][y + y1 - 1];
                            if (newNumber < lowestNumber) {
                                lowestNumber = newNumber;
                                lowest = new Point2D.Double(x + x1, y + y1);
                            }
                        }
                    }
                }
                smallTarget = lowest;
                target = new Point2D.Double((smallTarget.getX()) * 16, (smallTarget.getY()) * 16);
            }

        }
    }

    public void draw(FXGraphics2D graphics) {
        int centerX = sprites.get(0).getWidth() / 2;
        int centerY = sprites.get(0).getHeight() / 2;
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - centerX, position.getY() - centerY);
        tx.rotate(angle, centerX, centerY);

        graphics.drawImage(this.sprites.get((int) Math.floor(frame) % this.sprites.size()), tx, null);

        graphics.setColor(Color.white);
//        graphics.draw(new Ellipse2D.Double(position.getX() - 12.5, position.getY() - 12.5, 25, 25));
//        graphics.draw(new Line2D.Double(position, target));

    }

//    public void setTarget(Point2D newTarget) {
//        this.target = newTarget;
//    }

}

