package by.segg3r.game.objects.characters.animations;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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
		animationSets.put(AnimationPart.FACE, mock(AnimationSet.class));
		gameCharacter = mock(GameCharacter.class);
		gameCharacterAnimation = new GameCharacterAnimation(animationSets);
	}

	@Test(description = "should update each animation set on game character animation update"
			+ " if character speed greater than 0")
	public void shouldUpdate() {
		when(gameCharacter.getSpeed()).thenReturn(1.);
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
		verify(mockedCurrentAnimation, never()).setCurrentFrame(eq(1));
	}
	
	@Test(description = "should not update animation sets if game character is not moving")
	public void shouldNotUpdate() {
		when(gameCharacter.getSpeed()).thenReturn(0.);
		when(gameCharacter.getDirection()).thenReturn(Math.PI / 2);

		for (AnimationPart animationPart : AnimationPart.values()) {
			AnimationSet animationSet = animationSets.get(animationPart);
			if (animationSet != null) {
				when(animationSet.getCurrentAnimation(eq(Math.PI / 2)))
						.thenReturn(mockedCurrentAnimation);
			}
		}
		gameCharacterAnimation.update(gameCharacter, 500L);
		verify(mockedCurrentAnimation, never()).update(anyLong());
		verify(mockedCurrentAnimation, times(2)).setCurrentFrame(eq(1));
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
