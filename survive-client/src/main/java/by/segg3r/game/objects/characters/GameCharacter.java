package by.segg3r.game.objects.characters;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.GameObject;
import by.segg3r.game.objects.Position;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;

public class GameCharacter extends GameObject {

	private static final double DEFAULT_MOVEMENT_SPEED = 60.;
	
	private GameCharacterAnimation gameCharacterAnimation;

	public GameCharacter(GameCharacterAnimation gameCharacterAnimation) {
		super();
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

}
