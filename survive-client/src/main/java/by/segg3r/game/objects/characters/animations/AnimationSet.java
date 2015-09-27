package by.segg3r.game.objects.characters.animations;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;

import by.segg3r.game.objects.Direction;

public class AnimationSet {

	private Map<Direction, Animation> animations;
	
	public AnimationSet(Animation left, Animation right, Animation top,
			Animation down) {
		super();
		this.animations = new HashMap<Direction, Animation>();
		this.animations.put(Direction.LEFT, left);
		this.animations.put(Direction.RIGHT, right);
		this.animations.put(Direction.TOP, top);
		this.animations.put(Direction.DOWN, down);
	}
	
	public AnimationSet(Map<Direction, Animation> animations) {
		super();
		this.animations = animations;
	}
	
	public Animation getCurrentAnimation(double direction) {
		return animations.get(Direction.fromAngle(direction));
	}

	public Animation getAnimation(Direction direction) {
		return animations.get(direction);
	}

}
