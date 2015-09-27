package by.segg3r.game.objects.prefabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import by.segg3r.game.objects.Direction;
import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.objects.characters.animations.AnimationSet;
import by.segg3r.game.objects.prefabs.options.GameCharacterPrefabAnimationOptions;
import by.segg3r.game.objects.prefabs.options.PrefabAnimationOptions;
import by.segg3r.game.util.pathresolver.PathResolver;

@Component
public class ImageHolder {

	private static final int GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES = 3;
	private static final int GAME_CHARACTER_ANIMATION_SET_VERTICAL_IMAGES = 4;

	@Value("#{animationPartPathResolvers}")
	private Map<AnimationPart, PathResolver> animationPartPathResolvers;
	@Value("#{directionImageRows}")
	private Map<Direction, Integer> directionImageRows;

	private Map<String, SpriteSheet> characterSpriteSheetCache = new HashMap<String, SpriteSheet>();

	public ImageHolder() {
		super();
	}

	public AnimationSet getGameCharacterAnimationSet(
			AnimationPart animationPart,
			GameCharacterPrefabAnimationOptions animationOptions)
			throws SlickException {
		String partName = animationOptions.getAnimationPartName(animationPart);
		AnimationSet result = null;
		if (partName != null) {
			String fileName = animationPartPathResolvers.get(animationPart)
					.resolve(partName);
			SpriteSheet spriteSheet = getGameCharacterSpriteSheet(fileName,
					animationOptions);
			Map<Direction, Animation> animations = new HashMap<Direction, Animation>();
			for (Direction direction : Direction.values()) {
				animations.put(
						direction,
						buildGameCharacterAnimation(spriteSheet,
								directionImageRows.get(direction),
								animationOptions));
			}

			result = new AnimationSet(animations);
		}
		return result;
	}

	private Animation buildGameCharacterAnimation(SpriteSheet spriteSheet,
			int rowIndex, PrefabAnimationOptions<?> animationOptions) {
		List<Image> images = new ArrayList<Image>();
		for (int i = 0; i < GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES; i++) {
			Image image = spriteSheet.getSprite(i, rowIndex);
			images.add(image);
		}
		images.add(spriteSheet.getSprite(1, rowIndex));
		Image[] imageArray = images
				.toArray(new Image[GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES]);
		return new Animation(imageArray, animationOptions.getDuration());
	}

	public SpriteSheet getGameCharacterSpriteSheet(String fileName,
			PrefabAnimationOptions<?> animationOptions) throws SlickException {
		SpriteSheet result = getCachedCharacterSpriteSheet(fileName);
		if (result == null) {
			result = loadCharacterSpriteSheet(fileName, animationOptions);
			cacheCharacterSpriteSheet(fileName, result);
		}
		return result;
	}

	public SpriteSheet loadCharacterSpriteSheet(String fileName,
			PrefabAnimationOptions<?> animationOptions) throws SlickException {
		Image image = new Image(fileName, animationOptions.getBackgroundColor());
		return new SpriteSheet(image, image.getWidth()
				/ GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES,
				image.getHeight()
						/ GAME_CHARACTER_ANIMATION_SET_VERTICAL_IMAGES);
	}

	public void cacheCharacterSpriteSheet(String fileName,
			SpriteSheet spriteSheet) {
		characterSpriteSheetCache.put(fileName, spriteSheet);
	}

	public SpriteSheet getCachedCharacterSpriteSheet(String fileName) {
		return characterSpriteSheetCache.get(fileName);
	}

	public Map<AnimationPart, PathResolver> getAnimationPartPathResolvers() {
		return animationPartPathResolvers;
	}

	public void setAnimationPartPathResolvers(
			Map<AnimationPart, PathResolver> animationPartPathResolvers) {
		this.animationPartPathResolvers = animationPartPathResolvers;
	}

	public Map<Direction, Integer> getDirectionImageRows() {
		return directionImageRows;
	}

	public void setDirectionImageRows(Map<Direction, Integer> directionImageRows) {
		this.directionImageRows = directionImageRows;
	}

}
