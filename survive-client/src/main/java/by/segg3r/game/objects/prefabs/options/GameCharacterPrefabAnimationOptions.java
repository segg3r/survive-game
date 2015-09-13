package by.segg3r.game.objects.prefabs.options;

import java.util.Map;

import org.newdawn.slick.Color;

import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.AnimationPart;

public class GameCharacterPrefabAnimationOptions extends
		PrefabAnimationOptions<GameCharacter> {

	private static final int DEFAULT_DURATION = 500;
	private static final Color DEFAULT_BACKGROUND_COLOR = new Color(32, 156, 0);

	private Map<AnimationPart, String> animationPartNames;

	public GameCharacterPrefabAnimationOptions(
			Map<AnimationPart, String> animationPartFileNames) {
		this(animationPartFileNames, DEFAULT_DURATION, DEFAULT_BACKGROUND_COLOR);
	}

	public GameCharacterPrefabAnimationOptions(
			Map<AnimationPart, String> animationPartFileNames, int duration,
			Color backgroundColor) {
		super(duration, backgroundColor);
		this.animationPartNames = animationPartFileNames;
	}

	public String getAnimationPartName(AnimationPart animationPart) {
		return animationPartNames.get(animationPart);
	}

}
