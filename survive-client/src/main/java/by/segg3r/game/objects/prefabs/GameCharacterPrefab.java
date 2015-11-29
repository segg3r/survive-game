package by.segg3r.game.objects.prefabs;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import by.segg3r.data.GameObject;
import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.objects.characters.animations.AnimationSet;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;
import by.segg3r.game.objects.prefabs.options.GameCharacterPrefabAnimationOptions;

public class GameCharacterPrefab extends
		Prefab<GameCharacter, GameCharacterPrefabAnimationOptions> {

	public GameCharacterPrefab(
			GameCharacterPrefabAnimationOptions animationOptions) {
		super(animationOptions);
	}

	public GameCharacter instantiate(GameObject gameObject, ImageHolder imageHolder)
			throws SlickException {
		GameCharacterPrefabAnimationOptions animationOptions = getAnimationOptions();

		GameCharacterAnimation animation = buildAnimation(imageHolder,
				animationOptions);
		GameCharacter gameCharacter = new GameCharacter(gameObject, animation);
		return gameCharacter;
	}

	private GameCharacterAnimation buildAnimation(ImageHolder imageHolder,
			GameCharacterPrefabAnimationOptions animationOptions)
			throws SlickException {
		Map<AnimationPart, AnimationSet> animationSets = new HashMap<AnimationPart, AnimationSet>();
		for (AnimationPart animationPart : AnimationPart.values()) {
			animationSets.put(animationPart, imageHolder
					.getGameCharacterAnimationSet(animationPart,
							animationOptions));
		}
		GameCharacterAnimation animation = new GameCharacterAnimation(
				animationSets);
		return animation;
	}
}
