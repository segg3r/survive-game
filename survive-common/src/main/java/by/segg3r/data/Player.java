package by.segg3r.data;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1575763117546575124L;

	private static long currentId = 0;

	private static synchronized long newId() {
		return ++currentId;
	}

	private long id;
	private Position position;

	public Player() {
		this(0., 0.);
	}

	public Player(double x, double y) {
		this(new Position(x, y));
	}

	public Player(Position position) {
		this.id = newId();
		this.position = position;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setX(double x) {
		position.setX(x);
	}

	public void setY(double y) {
		position.setY(y);
	}

	public void setPosition(double x, double y) {
		position.set(x, y);
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}

}
