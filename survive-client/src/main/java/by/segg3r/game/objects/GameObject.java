package by.segg3r.game.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.iface.Renderable;
import by.segg3r.game.objects.iface.Updatable;
import by.segg3r.game.util.GameMath;

public class GameObject implements Renderable, Updatable {

	private Position position;
	private Position destination;
	private double movementSpeed;
	private double speed;
	private double direction;

	public GameObject() {
		super();
		this.position = new Position();
		this.destination = new Position();
	}

	@Override
	public void update(double delta) throws SlickException {
		updatePosition(delta);
	}

	private void updatePosition(double delta) {
		double distanceToDestination = GameMath.distanceBetween(position,
				destination);
		if (distanceToDestination > speed * delta) {
			if (speed == 0) {
				speed = movementSpeed;
			}
			direction = GameMath.directionBetween(position, destination);
		} else {
			position.set(destination.getX(), destination.getY());
			speed = 0;
		}

		position.setX(position.getX() + delta * speed * Math.cos(direction));
		position.setY(position.getY() - delta * speed * Math.sin(direction));
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

	public Position getDestination() {
		return destination;
	}

	public void setDestination(double x, double y) {
		destination.set(x, y);
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

	public double getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(double movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
}
