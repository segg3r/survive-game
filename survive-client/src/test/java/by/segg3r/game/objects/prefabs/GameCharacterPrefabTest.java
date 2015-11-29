package by.segg3r.game.objects.prefabs;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.newdawn.slick.SlickException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.data.GameObject;
import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.objects.characters.animations.AnimationSet;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;
import by.segg3r.game.objects.prefabs.options.GameCharacterPrefabAnimationOptions;
import by.segg3r.game.rooms.Room;

public class GameCharacterPrefabTest {

	@Mock
	private ImageHolder imageHolder;
	@Mock
	private Room room;

	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void afterMethod() {
		reset(imageHolder, room);
	}

	@Test(description = "should correctly instantiate animation sets")
	public void testInstantiateAnimationSets() throws SlickException {
		// arrange
		GameCharacterPrefabAnimationOptions animationOptions = mock(GameCharacterPrefabAnimationOptions.class);
		GameCharacterPrefab prefab = new GameCharacterPrefab(animationOptions);

		AnimationSet body = mock(AnimationSet.class);
		AnimationSet armor = mock(AnimationSet.class);

		when(
				imageHolder.getGameCharacterAnimationSet(
						eq(AnimationPart.BODY), eq(animationOptions)))
				.thenReturn(body);
		when(
				imageHolder.getGameCharacterAnimationSet(
						eq(AnimationPart.ARMOR), eq(animationOptions)))
				.thenReturn(armor);
		when(
				imageHolder.getGameCharacterAnimationSet(
						eq(AnimationPart.HAIRS), eq(animationOptions)))
				.thenReturn(null);

		// act
		GameObject gameObject = new GameObject(1L);
		GameCharacter gameCharacter = prefab.instantiate(gameObject, imageHolder);

		// assert
		GameCharacterAnimation animation = gameCharacter
				.getGameCharacterAnimation();
		assertEquals(animation.getAnimationSet(AnimationPart.BODY), body);
		assertEquals(animation.getAnimationSet(AnimationPart.ARMOR), armor);
		assertNull(animation.getAnimationSet(AnimationPart.FACE));
	}

}
