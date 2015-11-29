package by.segg3r.game.objects.characters;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;
import by.segg3r.game.objects.ClientGameObject;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;

public class GameCharacter extends ClientGameObject {

	private static final long serialVersionUID = 2119859119724098451L;

	private static final double DEFAULT_MOVEMENT_SPEED = 60.;
	
	private GameCharacterAnimation gameCharacterAnimation;

	public GameCharacter(GameObject gameObject, GameCharacterAnimation gameCharacterAnimation) {
		super(gameObject);
		setMovementSpeed(DEFAULT_MOVEMENT_SPEED);
		this.gameCharacterAnimation = gameCharacterAnimation;
	}

	@Override
	public void update(double delta) throws SlickException {
		super.update(delta);
		gameCharacterAnimation.update(this, (long) (delta * 1000));
	}

	@Override
	public void render(Graphics g) throws SlickException {
		super.render(g);
		Position position = getPosition();
		gameCharacterAnimation.draw(this, (float) position.getX(), (float) position.getY());
	}

	public GameCharacterAnimation getGameCharacterAnimation() {
		return gameCharacterAnimation;
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
