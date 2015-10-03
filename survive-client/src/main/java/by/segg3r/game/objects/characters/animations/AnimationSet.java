package by.segg3r.game.objects.characters.animations;

import java.util.HashMap;
import java.util.Map;

import by.segg3r.game.objects.Direction;
import by.segg3r.game.objects.animations.GameAnimation;

public class AnimationSet {

	private Map<Direction, GameAnimation> animations;
	
	public AnimationSet(GameAnimation left, GameAnimation right, GameAnimation top,
			GameAnimation down) {
		super();
		this.animations = new HashMap<Direction, GameAnimation>();
		this.animations.put(Direction.LEFT, left);
		this.animations.put(Direction.RIGHT, right);
		this.animations.put(Direction.TOP, top);
		this.animations.put(Direction.DOWN, down);
	}
	
	public AnimationSet(Map<Direction, GameAnimation> animations) {
		super();
		this.animations = animations;
	}
	
	public GameAnimation getCurrentAnimation(double direction) {
		return animations.get(Direction.fromAngle(direction));
	}

	public GameAnimation getAnimation(Direction direction) {
		return animations.get(direction);
	}

}
