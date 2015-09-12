package by.segg3r.game.objects.prefabs;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.newdawn.slick.SlickException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
		//arrange
		Map<AnimationPart, String> files = new HashMap<AnimationPart, String>();
		files.put(AnimationPart.BODY, "body_part.png");
		files.put(AnimationPart.ARMOR, "armor_part.png");
		GameCharacterPrefabAnimationOptions animationOptions = new GameCharacterPrefabAnimationOptions(
				files);
		GameCharacterPrefab prefab = new GameCharacterPrefab(animationOptions);
		
		AnimationSet body = mock(AnimationSet.class);
		AnimationSet armor = mock(AnimationSet.class);
		
		when(imageHolder.getGameCharacterAnimationSet(eq("body_part.png"), eq(animationOptions)))
			.thenReturn(body);
		when(imageHolder.getGameCharacterAnimationSet(eq("armor_part.png"), eq(animationOptions)))
			.thenReturn(armor);
		
		//act
		GameCharacter gameCharacter = prefab.instantiate(imageHolder, room);
	
		//assert
		GameCharacterAnimation animation = gameCharacter.getGameCharacterAnimation();
		assertEquals(animation.getAnimationSet(AnimationPart.BODY), body);
		assertEquals(animation.getAnimationSet(AnimationPart.ARMOR), armor);
		assertNull(animation.getAnimationSet(AnimationPart.FACE));
	}

}
