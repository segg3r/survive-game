package by.segg3r.game.objects.animations;

import org.newdawn.slick.Animation;

import by.segg3r.game.objects.Position;

public class GameAnimation {

	private Animation animation;
	private Position offset;

	public GameAnimation(Animation animation, Position offset) {
		super();
		this.offset = offset;
		this.animation = animation;
	}

	public void update(long delta) {
		animation.update(delta);
	}

	public void setCurrentFrame(int index) {
		animation.setCurrentFrame(index);
	}

	public void draw(float x, float y) {
		animation.draw(x - (float) offset.getX(), y - (float) offset.getY());
	}

	public Position getOffset() {
		return offset;
	}

	public void setOffset(Position offset) {
		this.offset = offset;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

}
