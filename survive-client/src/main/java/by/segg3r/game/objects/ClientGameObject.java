package by.segg3r.game.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;
import by.segg3r.game.objects.iface.Renderable;
import by.segg3r.game.objects.iface.Updatable;
import by.segg3r.game.util.GameMath;

public class ClientGameObject extends GameObject implements Renderable,
		Updatable {

	private static final long serialVersionUID = -7873531130690055791L;

	public ClientGameObject(GameObject from) {
		super(from.getId());
		Position position = from.getPosition();
		setPosition(position.getX(), position.getY());

		Position destination = from.getDestination();
		setDestination(destination.getX(), destination.getY());
		
		setMovementSpeed(from.getMovementSpeed());
		setSpeed(from.getSpeed());
		setDirection(from.getDirection());
	}

	@Override
	public void update(double delta) throws SlickException {
		updatePosition(delta);
	}

	private void updatePosition(double delta) {
		double distanceToDestination = GameMath.distanceBetween(getPosition(),
				getDestination());
		if (distanceToDestination > getSpeed() * delta) {
			if (getSpeed() == 0) {
				setSpeed(getMovementSpeed());
			}
			setDirection(GameMath.directionBetween(getPosition(), getDestination()));
		} else {
			getPosition().set(getDestination());
			setSpeed(0);
		}

		Position position = getPosition();
		double speed = getSpeed();
		double direction = getDirection();
		
		position.setX(position.getX() + delta * speed * Math.cos(direction));
		position.setY(position.getY() - delta * speed * Math.sin(direction));
	}

	@Override
	public void render(Graphics g) throws SlickException {
		return;
	}

}
