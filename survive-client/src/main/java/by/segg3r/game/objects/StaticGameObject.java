package by.segg3r.game.objects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.rooms.Room;

public class StaticGameObject extends GameObject {

	private Animation animation;
	
	public StaticGameObject(Room room, Animation animation) {
		super(room);
		this.animation = animation;
	}
	
	@Override
	public void update(double delta) throws SlickException {
		super.update(delta);
		animation.update((long) (delta * 1000));
	}
	
	@Override
	public void render(Graphics g) throws SlickException {
		super.render(g);
		animation.draw((float) getX(), (float) getY());
	}

}
