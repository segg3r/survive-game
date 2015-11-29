package by.segg3r.game.objects;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.data.GameObject;

public class StaticClientGameObjectTest {

	private GameObject gameObject;
	private StaticClientGameObject staticClientGameObject;
	private Animation animation;

	@BeforeMethod
	public void init() {
		animation = mock(Animation.class);
		gameObject = new GameObject(1L);
		staticClientGameObject = new StaticClientGameObject(gameObject, animation);
	}

	@Test(description = "should update current animation")
	public void shouldUpdateCurrentAnimation() throws SlickException {
		staticClientGameObject.update(.345);
		verify(animation, times(1)).update(eq(345L));
	}

	@Test(description = "should draw current animation")
	public void shouldRenderCurrentAnimation() throws SlickException {
		Graphics g = mock(Graphics.class);
		staticClientGameObject.setPosition(25.f, 45.f);
		staticClientGameObject.render(g);
		verify(animation, times(1)).draw(eq(25.f), eq(45.f));
	}

}
