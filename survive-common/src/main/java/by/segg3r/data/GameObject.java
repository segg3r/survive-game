package by.segg3r.data;

public class GameObject extends Entity {

	private static final long serialVersionUID = 1686820238432868069L;

	private Position position;
	private Position destination;
	private double movementSpeed;
	private double speed;
	private double direction;

	public GameObject(long id) {
		super(id);
		this.position = new Position();
		this.destination = new Position();
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
