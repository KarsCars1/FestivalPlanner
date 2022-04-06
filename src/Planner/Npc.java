package Planner;

import DataStructure.Data.Location;
import DataStructure.Data.Show;
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
    private boolean atStage = false;
    private Point2D position;
    private Point2D startPosition;
    private double angle;
    private ArrayList<BufferedImage> sprites;
    private double speed;
    private double frame;
    private Point2D target;
    private Point2D smallTarget;
    private int[][] pathfinding;
    private Location location;
    private Show currentShow;
    private int[][] beginEntrance;

    //Constructor
    public Npc(Point2D position, double angle, int[][] beginEntrance) throws IOException {

        BufferedImage fullImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Npc_template.png"));

        this.position = position;
        this.startPosition = position;
        this.target = position;
        this.smallTarget = new Point2D.Double(position.getX() / 16, position.getY() / 16);
        this.angle = angle;
        this.beginEntrance = beginEntrance;

        //Give npc's a random speed
        this.speed = 3 + 3 * Math.random();
        this.target = position;
        this.frame = Math.random() * 8;

        //get the sprites out of the image
        this.sprites = new ArrayList<>();
        int width = fullImage.getWidth() / 10;
        int height = fullImage.getHeight();
        for (int x = 0; x < 10; x++) {
            this.sprites.add(fullImage.getSubimage(x * width, 0, width, height));
        }
    }

    public int[][] getPathfinding() {
        return this.pathfinding;
    }

    //set where the npc needs to go
    public void setPathfinding(Location location) {
        if (location.getPath() != null) {
            this.pathfinding = location.getPath();
            this.location = location;
            this.atStage = false;
        }

    }

    //set where the npc needs to go
    public void setPathfinding(int[][] location) {
        if (location != null) {
            this.pathfinding = location;
            this.location = null;
            this.atStage = false;
        } else {
            System.out.println("error");
        }

    }

    //sets the show the npc is going to
    public void setCurrentShow(Show currentShow) {
        this.currentShow = currentShow;
    }

    //update the npc
    public void update(LocalTime time) {
        this.frame++;
        //checks if the show ended
        if (currentShow != null && currentShow.getEndTime().isBefore(time)) {
            setPathfinding(beginEntrance);
            this.currentShow = null;
        }
        //check if the next tile has been reached and if the stage has been reached
        if (this.target.distanceSq(this.position) < 32) {
            if (this.atStage) {
                this.target = new Point2D.Double(new Random().nextInt((int) this.location.getSize().getX() - 32),
                        new Random().nextInt((int) this.location.getSize().getY() - 32));


                this.target.setLocation(this.target.getX() - this.location.getSize().getX() / 2 + this.smallTarget.getX() * 16,
                        16 + this.target.getY() - this.location.getSize().getY() / 2 + this.smallTarget.getY() * 16);
            } else {
                getNewTarget();
            }
        }

        //check if the next target is the stage
        if (this.pathfinding != null && this.pathfinding[(int) this.smallTarget.getX() - 1][(int) this.smallTarget.getY() - 1] == 0 && !atStage) {
            this.atStage = true;
        }

        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(), this.target.getX() - this.position.getX());
        double rotation = targetAngle - this.angle;
        while (rotation < -Math.PI) {
            rotation += 2 * Math.PI;
        }
        while (rotation > Math.PI) {
            rotation -= 2 * Math.PI;
        }


        if (rotation < -rotationSpeed) {
            this.angle -= rotationSpeed;
        } else if (rotation > rotationSpeed) {
            this.angle += rotationSpeed;
        } else {
            this.angle = targetAngle;
        }


        this.position = new Point2D.Double(this.position.getX() + this.speed * Math.cos(this.angle), this.position.getY() + this.speed * Math.sin(this.angle));

    }

    //get the new target to the tile with the smallest distance
    private void getNewTarget() {
        if (this.pathfinding != null) {

            int x = (int) this.smallTarget.getX();
            int y = (int) this.smallTarget.getY();
            Point2D lowest = this.smallTarget;
            int lowestNumber = 9999;
            if (this.pathfinding[x - 1][y - 1] == 0) {
            } else {

                for (int x1 = -1; x1 < 2; x1++) {
                    for (int y1 = -1; y1 < 2; y1++) {
                        if (!(x + x1 < 0 || x + x1 > 100 || y + y1 < 0 || y + y1 > 100)) {
                            int newNumber = this.pathfinding[x + x1 - 1][y + y1 - 1];
                            if (newNumber < lowestNumber) {
                                lowestNumber = newNumber;
                                lowest = new Point2D.Double(x + x1, y + y1);
                            }
                        }
                    }
                }
                this.smallTarget = lowest;
                this.target = new Point2D.Double((this.smallTarget.getX()) * 16, (this.smallTarget.getY()) * 16);
            }

        }
    }

    //draw the npc at the correct location
    public void draw(FXGraphics2D graphics) {
        if (notAtStart()) {
            int centerX = this.sprites.get(0).getWidth() / 2;
            int centerY = this.sprites.get(0).getHeight() / 2;
            AffineTransform tx = new AffineTransform();
            tx.translate(this.position.getX() - centerX, this.position.getY() - centerY);
            tx.rotate(this.angle, centerX, centerY);

            graphics.drawImage(this.sprites.get((int) Math.floor(this.frame) % this.sprites.size()), tx, null);

            graphics.setColor(Color.white);
        }
    }

    //checks if the npc is not at the entrance
    private boolean notAtStart() {
        return !(startPosition.getX() / 16 == smallTarget.getX() && startPosition.getY() / 16 == smallTarget.getY());
    }

    //reset the npc's pathfinding
    public void reset() {
        this.position = startPosition;
        this.target = startPosition;
        this.atStage = false;
        this.smallTarget = new Point2D.Double(startPosition.getX() / 16, startPosition.getY() / 16);
        this.pathfinding = null;
    }
}

