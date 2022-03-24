package Planner;


import org.jfree.fx.FXGraphics2D;
import sun.nio.cs.ext.MacThai;


import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;


public class Npc {

	private BufferedImage fullImage;
	private Point2D position;
	private double angle;
	private ArrayList<BufferedImage> sprites;
	private double speed;
	private double frame;
	private Point2D target;
	private static double rotationSpeed = 0.1;


	public Npc(Point2D position, double angle) throws IOException {

		BufferedImage fullImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Npc_template.png"));

		this.position = position;
		this.angle = angle;
		this.speed = 2 + 3 * Math.random();
		this.target = position;
		this.frame = Math.random() * 8;

		this.sprites = new ArrayList<>();
		int width = fullImage.getWidth() / 10;
		int height = fullImage.getHeight();
		for (int x = 0; x < 10; x++) {
			this.sprites.add(fullImage.getSubimage(x * width, 0, width, height));
		}
	}

	public void update(ArrayList<Npc> npcs) {
		if (target.distanceSq(position) < 32)
			return;

		double targetAngle = Math.atan2(this.target.getY() - this.position.getY(), this.target.getX() - this.position.getX());
		double rotation = targetAngle - this.angle;
		while (rotation < -Math.PI) {
			rotation += 2 * Math.PI;
		}
		while (rotation > Math.PI) {
			rotation -= 2 * Math.PI;
		}

		double oldAngle = this.angle;
		if (rotation < -rotationSpeed) {
			this.angle -= rotationSpeed;
		} else if (rotation > rotationSpeed) {
			this.angle += rotationSpeed;
		} else {
			this.angle = targetAngle;
		}

		Point2D oldposition = this.position;

		this.position = new Point2D.Double(this.position.getX() + this.speed * Math.cos(this.angle), this.position.getY() + this.speed * Math.sin(this.angle));

	}


	public void draw(FXGraphics2D graphics) {
		int centerX = sprites.get(0).getWidth()/2;
		int centerY = sprites.get(0).getHeight()/2;
		AffineTransform tx = new AffineTransform();
		tx.translate(position.getX() - centerX, position.getY() - centerY);
		tx.rotate(angle + Math.PI/2, centerX, centerY);
		tx.translate(0, 20);


		graphics.drawImage(this.sprites.get((int)Math.floor(frame) % this.sprites.size()), tx, null);


		graphics.setColor(Color.white);
		graphics.draw(new Ellipse2D.Double(position.getX()-32, position.getY()-16, 25, 25));
		graphics.draw(new Line2D.Double(position, target));

	}

	public void setTarget(Point2D newTarget){
	this.target = newTarget;
	}

}