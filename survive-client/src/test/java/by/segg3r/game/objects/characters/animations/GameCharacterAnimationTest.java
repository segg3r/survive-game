package by.segg3r.game.objects.characters.animations;

import static org.mockito.Mockito.*;
//import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.objects.characters.GameCharacter;

public class GameCharacterAnimationTest {

	private static final int TESTABLE_ANIMATION_SETS_COUNT = 10;

	private GameCharacterAnimation gameCharacterAnimation;
	private GameCharacter gameCharacter;
	private List<AnimationSet> animationSets;
	private Animation mockedCurrentAnimation;

	@BeforeMethod
	public void init() {
		mockedCurrentAnimation = mock(Animation.class);

		animationSets = new ArrayList<AnimationSet>();
		for (int i = 0; i < TESTABLE_ANIMATION_SETS_COUNT; i++) {
			animationSets.add(mock(AnimationSet.class));
		}
		gameCharacter = mock(GameCharacter.class);
		gameCharacterAnimation = new GameCharacterAnimation(animationSets);
	}

	@Test(description = "should update each animation set on game character animation update")
	public void shouldUpdate() {
		when(gameCharacter.getDirection()).thenReturn(Math.PI / 2);

		for (AnimationSet animationSet : animationSets) {
			when(animationSet.getCurrentAnimation(eq(Math.PI / 2))).thenReturn(
					mockedCurrentAnimation);
		}

		gameCharacterAnimation.update(gameCharacter, 500L);
		verify(mockedCurrentAnimation, times(TESTABLE_ANIMATION_SETS_COUNT))
				.update(eq(500L));
	}
	
	@Test(description = "should render each animation set on game character animation render")
	public void shouldRender() {
		when(gameCharacter.getDirection()).thenReturn(Math.PI / 2);

		for (AnimationSet animationSet : animationSets) {
			when(animationSet.getCurrentAnimation(eq(Math.PI / 2))).thenReturn(
					mockedCurrentAnimation);
		}

		gameCharacterAnimation.draw(gameCharacter, 20.f, 30.f);
		verify(mockedCurrentAnimation, times(TESTABLE_ANIMATION_SETS_COUNT))
				.draw(eq(20.f), eq(30.f));
	}

}
