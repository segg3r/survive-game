package by.segg3r.game.objects.characters;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.GameObject;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;
import by.segg3r.game.rooms.Room;

public class GameCharacter extends GameObject {

	private GameCharacterAnimation gameCharacterAnimation;

	public GameCharacter(Room room,
			GameCharacterAnimation gameCharacterAnimation) {
		super(room);
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
		gameCharacterAnimation.draw(this, (float) getX(), (float) getY());
	}

	public GameCharacterAnimation getGameCharacterAnimation() {
		return gameCharacterAnimation;
	}

}
