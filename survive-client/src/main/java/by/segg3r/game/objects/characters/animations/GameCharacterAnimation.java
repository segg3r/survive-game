package by.segg3r.game.objects.characters.animations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;

import by.segg3r.game.objects.characters.GameCharacter;

public class GameCharacterAnimation {

	private Map<AnimationPart, AnimationSet> animationSets;

	public GameCharacterAnimation(Map<AnimationPart, AnimationSet> animationSets) {
		super();
		this.animationSets = animationSets;
	}

	public void update(GameCharacter gameCharacter, long delta) {
		List<Animation> currentAnimations = getCurrentAnimations(gameCharacter);
		for (Animation currentAnimation : currentAnimations) {
			currentAnimation.update(delta);
		}
	}

	public void draw(GameCharacter gameCharacter, float x, float y) {
		List<Animation> currentAnimations = getCurrentAnimations(gameCharacter);
		for (Animation currentAnimation : currentAnimations) {
			currentAnimation.draw(x, y);
		}
	}

	private List<Animation> getCurrentAnimations(GameCharacter gameCharacter) {
		List<Animation> result = new ArrayList<Animation>();

		double direction = gameCharacter.getDirection();
		for (AnimationPart animationPart : AnimationPart.values()) {
			AnimationSet animationSet = getAnimationSet(animationPart);
			if (animationSet != null) {
				Animation currentAnimation = animationSet.getCurrentAnimation(direction);
				result.add(currentAnimation);
			}	
		}
		return result;
	}

	public AnimationSet getAnimationSet(AnimationPart animationPart) {
		return animationSets.get(animationPart);
	}

}
