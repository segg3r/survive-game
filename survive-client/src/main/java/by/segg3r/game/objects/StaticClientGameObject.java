package by.segg3r.game.objects;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;
import by.segg3r.game.CommonConstants.Time;

public class StaticClientGameObject extends ClientGameObject {

	private static final long serialVersionUID = -3918585342704804098L;

	private Animation animation;

	public StaticClientGameObject(GameObject gameObject, Animation animation) {
		super(gameObject);
		this.animation = animation;
	}

	@Override
	public void update(double delta) throws SlickException {
		super.update(delta);
		animation.update((long) (delta * Time.ONE_SECOND));
	}

	@Override
	public void render(Graphics g) throws SlickException {
		super.render(g);
		Position position = getPosition();
		animation.draw((float) position.getX(), (float) position.getY());
	}

	private void writeObject(ObjectOutputStream stream)
			throws IOException {
		throw new NotSerializableException(getClass().getName());
	}

	private void readObject(ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		throw new NotSerializableException(getClass().getName());
	}


}
