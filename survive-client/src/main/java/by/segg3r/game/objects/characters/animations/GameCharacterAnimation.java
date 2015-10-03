package by.segg3r.game.objects.characters.animations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.segg3r.game.objects.animations.GameAnimation;
import by.segg3r.game.objects.characters.GameCharacter;

public class GameCharacterAnimation {

	private static final int STANDING_IMAGE_INDEX = 1;
	
	private Map<AnimationPart, AnimationSet> animationSets;

	public GameCharacterAnimation(Map<AnimationPart, AnimationSet> animationSets) {
		super();
		this.animationSets = animationSets;
	}

	public void update(GameCharacter gameCharacter, long delta) {
		List<GameAnimation> currentAnimations = getCurrentAnimations(gameCharacter);
		if (gameCharacter.getSpeed() > 0) {
			for (GameAnimation currentAnimation : currentAnimations) {
				currentAnimation.update(delta);
			}
		} else {
			for (GameAnimation currentAnimation : currentAnimations) {
				currentAnimation.setCurrentFrame(STANDING_IMAGE_INDEX);
			}
		}
	}

	public void draw(GameCharacter gameCharacter, float x, float y) {
		List<GameAnimation> currentAnimations = getCurrentAnimations(gameCharacter);
		for (GameAnimation currentAnimation : currentAnimations) {
			currentAnimation.draw(x, y);
		}
	}

	private List<GameAnimation> getCurrentAnimations(GameCharacter gameCharacter) {
		List<GameAnimation> result = new ArrayList<GameAnimation>();

		double direction = gameCharacter.getDirection();
		for (AnimationPart animationPart : AnimationPart.values()) {
			AnimationSet animationSet = getAnimationSet(animationPart);
			if (animationSet != null) {
				GameAnimation currentAnimation = animationSet.getCurrentAnimation(direction);
				result.add(currentAnimation);
			}	
		}
		return result;
	}

	public AnimationSet getAnimationSet(AnimationPart animationPart) {
		return animationSets.get(animationPart);
	}

}
