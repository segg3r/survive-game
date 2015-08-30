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

@Component
public class ImageHolder {

	private static final int GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES = 3;
	private static final int GAME_CHARACTER_ANIMATION_SET_VERTICAL_IMAGES = 4;

	private Map<String, SpriteSheet> characterSpriteSheetCache = new HashMap<String, SpriteSheet>();

	public ImageHolder() {
		super();
	}

	public AnimationSet getGameCharacterAnimationSet(String fileName,
			int duration) throws SlickException {
		SpriteSheet spriteSheet = getGameCharacterSpriteSheet(fileName);
		Animation top = buildGameCharacterAnimation(spriteSheet, 0, duration);
		Animation right = buildGameCharacterAnimation(spriteSheet, 1, duration);
		Animation down = buildGameCharacterAnimation(spriteSheet, 2, duration);
		Animation left = buildGameCharacterAnimation(spriteSheet, 3, duration);

		AnimationSet animationSet = new AnimationSet(left, right, top, down);
		return animationSet;
	}

	private Animation buildGameCharacterAnimation(SpriteSheet spriteSheet,
			int rowIndex, int duration) {
		List<Image> images = new ArrayList<Image>();
		for (int i = 0; i < GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES; i++) {
			Image image = spriteSheet.getSprite(i, rowIndex);
			images.add(image);
		}
		images.add(spriteSheet.getSprite(1, rowIndex));
		Image[] imageArray = images
				.toArray(new Image[GAME_CHARACTER_ANIMATION_SET_HORIZONTAL_IMAGES]);
		return new Animation(imageArray, duration);
	}

	public SpriteSheet getGameCharacterSpriteSheet(String fileName)
			throws SlickException {
		SpriteSheet result = getCachedCharacterSpriteSheet(fileName);
		if (result == null) {
			result = loadCharacterSpriteSheet(fileName);
			cacheCharacterSpriteSheet(fileName, result);
		}
		return result;
	}

	public SpriteSheet loadCharacterSpriteSheet(String fileName)
			throws SlickException {
		Image image = new Image(fileName);
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
