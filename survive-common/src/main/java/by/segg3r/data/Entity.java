package by.segg3r.data;

import java.io.Serializable;

public class Entity implements Serializable {

	private static final long serialVersionUID = -8911811326918788912L;

	private long id;

	public Entity() {

	}

	public Entity(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
