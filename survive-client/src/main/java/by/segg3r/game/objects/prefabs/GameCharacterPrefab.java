package by.segg3r.game.objects.prefabs;

import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.AnimationSet;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;
import by.segg3r.game.objects.prefabs.options.GameCharacterPrefabAnimationOptions;
import by.segg3r.game.rooms.Room;

public class GameCharacterPrefab extends Prefab<GameCharacter, GameCharacterPrefabAnimationOptions> {

	public GameCharacterPrefab(GameCharacterPrefabAnimationOptions animationOptions) {
		super(animationOptions);
	}
	
	public GameCharacter instantiate(ImageHolder imageHolder, Room room, double x, double y) throws SlickException {
		GameCharacterPrefabAnimationOptions animationOptions = getAnimationOptions();
		int duration = animationOptions.getDuration();
		
		String bodyFileName = animationOptions.getBodyFileName();
		AnimationSet bodyAnimationSet = imageHolder.getGameCharacterAnimationSet(bodyFileName, duration);
		List<AnimationSet> animationSets = Arrays.asList(bodyAnimationSet);
		
		GameCharacterAnimation animation = new GameCharacterAnimation(animationSets);		
		GameCharacter gameCharacter = new GameCharacter(room, animation);
		return gameCharacter;		
	}
	
}
