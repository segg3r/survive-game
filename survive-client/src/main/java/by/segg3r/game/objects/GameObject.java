package by.segg3r.game.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.iface.Renderable;
import by.segg3r.game.objects.iface.Updatable;

public class GameObject implements Renderable, Updatable {

	private double x;
	private double y;
	private double speed;
	private double direction;

	public GameObject() {
		super();
	}

	@Override
	public void update(double delta) throws SlickException {
		updatePosition(delta);
	}

	private void updatePosition(double delta) {
		x += delta * speed * Math.cos(direction);
		y += delta * speed * Math.sin(direction);
	}

	@Override
	public void render(Graphics g) throws SlickException {
		return;
	}

	public void setPosition(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

}
