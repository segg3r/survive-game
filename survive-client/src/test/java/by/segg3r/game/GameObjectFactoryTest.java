package by.segg3r.game;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.objects.GameObject;
import by.segg3r.game.objects.GameObjectFactory;
import by.segg3r.game.objects.prefabs.ImageHolder;
import by.segg3r.game.objects.prefabs.Prefab;

public class GameObjectFactoryTest {

	private ImageHolder imageHolder;
	private GameObjectFactory gameObjectFactory;

	@BeforeMethod
	public void init() {
		imageHolder = new ImageHolder();
		gameObjectFactory = new GameObjectFactory();
		gameObjectFactory.setImageHolder(imageHolder);
	}

	@Test(description = "should set position of newly created game object")
	public void shouldRenderCurrentRoom() throws SlickException {
		GameObject gameObject = new GameObject();
		Prefab<?, ?> prefab = mock(Prefab.class);

		when(prefab.instantiate(any(ImageHolder.class))).thenReturn(gameObject);

		gameObjectFactory.instantiate(prefab, 20., 50.);
		assertEquals(gameObject.getX(), 20.);
		assertEquals(gameObject.getY(), 50.);
	}
}
