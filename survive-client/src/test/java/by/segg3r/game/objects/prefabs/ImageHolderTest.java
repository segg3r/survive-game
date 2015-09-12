package by.segg3r.game.objects.prefabs;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.objects.characters.animations.AnimationSet;
import by.segg3r.game.objects.prefabs.options.PrefabAnimationOptions;

public class ImageHolderTest {

	private ImageHolder imageHolder;

	@BeforeMethod
	public void init() {
		imageHolder = new ImageHolder();
	}

	@Test(description = "should return cached sprite sheet if exists")
	public void testCachedSpriteSheet() throws SlickException {
		String fileName = "sheet.png";
		imageHolder = spy(imageHolder);

		SpriteSheet cached = mock(SpriteSheet.class);
		SpriteSheet loaded = mock(SpriteSheet.class);
		PrefabAnimationOptions<?> animationOptions = mock(PrefabAnimationOptions.class);
		doReturn(cached).when(imageHolder).getCachedCharacterSpriteSheet(
				eq(fileName));
		doReturn(loaded).when(imageHolder).loadCharacterSpriteSheet(
				eq(fileName), eq(animationOptions));
		doNothing().when(imageHolder).cacheCharacterSpriteSheet(eq(fileName),
				eq(loaded));

		SpriteSheet result = imageHolder.getGameCharacterSpriteSheet(fileName, animationOptions);
		assertEquals(result, cached);
		verify(imageHolder, never()).cacheCharacterSpriteSheet(eq(fileName),
				eq(loaded));
	}

	@Test(description = "should return loaded sprite sheet and cache it if cached not exists")
	public void testLoadedSpriteSheet() throws SlickException {
		String fileName = "sheet.png";
		imageHolder = spy(imageHolder);

		SpriteSheet loaded = mock(SpriteSheet.class);
		PrefabAnimationOptions<?> animationOptions = mock(PrefabAnimationOptions.class);
		doReturn(null).when(imageHolder).getCachedCharacterSpriteSheet(
				eq(fileName));
		doReturn(loaded).when(imageHolder).loadCharacterSpriteSheet(
				eq(fileName), eq(animationOptions));
		doNothing().when(imageHolder).cacheCharacterSpriteSheet(eq(fileName),
				eq(loaded));

		SpriteSheet result = imageHolder.getGameCharacterSpriteSheet(fileName, animationOptions);
		assertEquals(result, loaded);
		verify(imageHolder, times(1)).cacheCharacterSpriteSheet(eq(fileName),
				eq(loaded));
	}

	@Test(description = "should check image holder caching")
	public void testCaching() {
		String fileName = "sheet.png";
		SpriteSheet spriteSheet = mock(SpriteSheet.class);

		assertNull(imageHolder.getCachedCharacterSpriteSheet(fileName));
		imageHolder.cacheCharacterSpriteSheet(fileName, spriteSheet);
		assertEquals(imageHolder.getCachedCharacterSpriteSheet(fileName),
				spriteSheet);
	}

	@Test(description = "should correctly get images from sprite sheet")
	public void testGetImagesFromSpriteSheet() throws SlickException {
		SpriteSheet spriteSheet = mock(SpriteSheet.class);
		PrefabAnimationOptions<?> animationOptions = mock(PrefabAnimationOptions.class);

		List<Image> images = new ArrayList<Image>();
		for (int i = 0; i < 12; i++) {
			images.add(mock(Image.class));
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				when(spriteSheet.getSprite(j, i)).thenReturn(
						images.get(i * 3 + j));
			}
		}

		String fileName = "sheet.png";
		int duration = 20;
		imageHolder = spy(imageHolder);
		doReturn(spriteSheet).when(imageHolder).getGameCharacterSpriteSheet(
				eq(fileName), eq(animationOptions));
		when(animationOptions.getDuration()).thenReturn(duration);
		
		AnimationSet result = imageHolder.getGameCharacterAnimationSet(fileName, animationOptions);
		
		Animation top = result.getTop();
		assertEquals(top.getImage(0), images.get(0));
		assertEquals(top.getImage(1), images.get(1));
		assertEquals(top.getImage(2), images.get(2));
		assertEquals(top.getImage(3), images.get(1));
		assertEquals(top.getDuration(0), duration);
		
		Animation right = result.getRight();
		assertEquals(right.getImage(0), images.get(3));
		assertEquals(right.getImage(1), images.get(4));
		assertEquals(right.getImage(2), images.get(5));
		assertEquals(right.getImage(3), images.get(4));
		assertEquals(right.getDuration(0), duration);
		
		Animation down = result.getDown();
		assertEquals(down.getImage(0), images.get(6));
		assertEquals(down.getImage(1), images.get(7));
		assertEquals(down.getImage(2), images.get(8));
		assertEquals(down.getImage(3), images.get(7));
		assertEquals(down.getDuration(0), duration);
		
		Animation left = result.getLeft();
		assertEquals(left.getImage(0), images.get(9));
		assertEquals(left.getImage(1), images.get(10));
		assertEquals(left.getImage(2), images.get(11));
		assertEquals(left.getImage(3), images.get(10));
		assertEquals(left.getDuration(0), duration);
	}
}
