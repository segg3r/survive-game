package by.segg3r.game.objects.characters;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;

public class GameCharacterTest {

	private GameCharacterAnimation gameCharacterAnimation;
	private GameCharacter gameCharacter;

	@BeforeMethod
	public void init() {
		gameCharacterAnimation = mock(GameCharacterAnimation.class);
		gameCharacter = new GameCharacter(gameCharacterAnimation);
	}

	@Test(description = "should update animation on update")
	public void testUpdateAnimation() throws SlickException {
		gameCharacter.update(.3);
		verify(gameCharacterAnimation, times(1)).update(eq(gameCharacter),
				eq(300L));
	}

	@Test(description = "should render animation on render")
	public void testRenderAnimation() throws SlickException {
		Graphics g = mock(Graphics.class);
		gameCharacter.setPosition(10., 20.);

		gameCharacter.render(g);
		verify(gameCharacterAnimation, times(1)).draw(eq(gameCharacter),
				eq(10.f), eq(20.f));
	}

}
