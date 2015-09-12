package by.segg3r.game.objects.prefabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.springframework.stereotype.Component;

import by.segg3r.game.objects.characters.animations.AnimationSet;
import by.segg3r.game.objects.prefabs.options.PrefabAnimationOptions;

@Component
public class ImageHolder {

	private static final int GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES = 3;
	private static final int GAME_CHARACTER_ANIMATION_SET_VERTICAL_IMAGES = 4;

	private Map<String, SpriteSheet> characterSpriteSheetCache = new HashMap<String, SpriteSheet>();

	public ImageHolder() {
		super();
	}

	public AnimationSet getGameCharacterAnimationSet(String fileName,
			PrefabAnimationOptions<?> animationOptions) throws SlickException {
		SpriteSheet spriteSheet = getGameCharacterSpriteSheet(fileName,
				animationOptions);
		Animation top = buildGameCharacterAnimation(spriteSheet, 0,
				animationOptions);
		Animation right = buildGameCharacterAnimation(spriteSheet, 1,
				animationOptions);
		Animation down = buildGameCharacterAnimation(spriteSheet, 2,
				animationOptions);
		Animation left = buildGameCharacterAnimation(spriteSheet, 3,
				animationOptions);

		AnimationSet animationSet = new AnimationSet(left, right, top, down);
		return animationSet;
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

}
