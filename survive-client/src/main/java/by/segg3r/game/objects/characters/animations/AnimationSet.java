package by.segg3r.game.objects.characters.animations;

import org.newdawn.slick.Animation;

public class AnimationSet {

	private Animation left;
	private Animation right;
	private Animation top;
	private Animation down;

	public AnimationSet(Animation left, Animation right, Animation top,
			Animation down) {
		super();
		this.left = left;
		this.right = right;
		this.top = top;
		this.down = down;
	}
	
	public Animation getCurrentAnimation(double direction) {
		Animation currentAnimation;
		if (direction < Math.PI / 4.) {
			currentAnimation = right;
		} else if (direction < Math.PI * 3. / 4.) {
			currentAnimation = top;
		} else if (direction < Math.PI * 5. / 4.) {
			currentAnimation = left;
		} else if (direction < Math.PI * 7. / 4.)  {
			currentAnimation = down;
		} else {
			currentAnimation = right;
		}
		return currentAnimation;
	}

	public Animation getLeft() {
		return left;
	}

	public void setLeft(Animation left) {
		this.left = left;
	}

	public Animation getRight() {
		return right;
	}

	public void setRight(Animation right) {
		this.right = right;
	}

	public Animation getTop() {
		return top;
	}

	public void setTop(Animation top) {
		this.top = top;
	}

	public Animation getDown() {
		return down;
	}

	public void setDown(Animation down) {
		this.down = down;
	}

}
