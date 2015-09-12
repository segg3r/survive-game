package by.segg3r.game.objects.characters.animations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
//import static org.testng.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.objects.characters.GameCharacter;

public class GameCharacterAnimationTest {

	private GameCharacterAnimation gameCharacterAnimation;
	private GameCharacter gameCharacter;
	private Map<AnimationPart, AnimationSet> animationSets;
	private Animation mockedCurrentAnimation;

	@BeforeMethod
	public void init() {
		mockedCurrentAnimation = mock(Animation.class);

		animationSets = new HashMap<AnimationPart, AnimationSet>();
		animationSets.put(AnimationPart.BODY, mock(AnimationSet.class));
		animationSets.put(AnimationPart.HEAD, mock(AnimationSet.class));
		gameCharacter = mock(GameCharacter.class);
		gameCharacterAnimation = new GameCharacterAnimation(animationSets);
	}

	@Test(description = "should update each animation set on game character animation update")
	public void shouldUpdate() {
		when(gameCharacter.getDirection()).thenReturn(Math.PI / 2);

		for (AnimationPart animationPart : AnimationPart.values()) {
			AnimationSet animationSet = animationSets.get(animationPart);
			if (animationSet != null) {
				when(animationSet.getCurrentAnimation(eq(Math.PI / 2)))
						.thenReturn(mockedCurrentAnimation);
			}
		}

		gameCharacterAnimation.update(gameCharacter, 500L);
		verify(mockedCurrentAnimation, times(2)).update(eq(500L));
	}

	@Test(description = "should render each animation set on game character animation render")
	public void shouldRender() {
		when(gameCharacter.getDirection()).thenReturn(Math.PI / 2);

		for (AnimationPart animationPart : AnimationPart.values()) {
			AnimationSet animationSet = animationSets.get(animationPart);
			if (animationSet != null) {
				when(animationSet.getCurrentAnimation(eq(Math.PI / 2)))
						.thenReturn(mockedCurrentAnimation);
			}

		}

		gameCharacterAnimation.draw(gameCharacter, 20.f, 30.f);
		verify(mockedCurrentAnimation, times(2)).draw(eq(20.f), eq(30.f));
	}

}
