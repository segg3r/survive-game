package by.segg3r.game.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.iface.Renderable;
import by.segg3r.game.objects.iface.Updatable;

public class GameObject implements Renderable, Updatable {

	private Position position;
	private double speed;
	private double direction;

	public GameObject() {
		super();
		this.position = new Position(0., 0.);
	}

	@Override
	public void update(double delta) throws SlickException {
		updatePosition(delta);
	}

	private void updatePosition(double delta) {
		position.setX(position.getX() + delta * speed * Math.cos(direction));
		position.setY(position.getY() + delta * speed * Math.sin(direction));
	}

	@Override
	public void render(Graphics g) throws SlickException {
		return;
	}

	public void setPosition(double x, double y) {
		position.set(x, y);
	}
	
	public Position getPosition() {
		return this.position;
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
