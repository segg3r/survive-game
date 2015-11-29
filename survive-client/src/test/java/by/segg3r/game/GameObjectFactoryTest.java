package by.segg3r.game;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;
import by.segg3r.game.objects.ClientGameObject;
import by.segg3r.game.objects.ClientGameObjectFactory;
import by.segg3r.game.objects.prefabs.ImageHolder;
import by.segg3r.game.objects.prefabs.Prefab;

public class GameObjectFactoryTest {

	private ImageHolder imageHolder;
	private ClientGameObjectFactory gameObjectFactory;

	@BeforeMethod
	public void init() {
		imageHolder = new ImageHolder();
		gameObjectFactory = new ClientGameObjectFactory();
		gameObjectFactory.setImageHolder(imageHolder);
	}

	@SuppressWarnings("unchecked")
	@Test(description = "should set position of newly created game object")
	public void shouldRenderCurrentRoom() throws SlickException {
		GameObject gameObject = new GameObject(1L);
		gameObject.setPosition(20., 50.);
		gameObject.setDestination(30., 40.);

		ClientGameObject clientGameObject = new ClientGameObject(gameObject);
		Prefab<ClientGameObject, ?> prefab = mock(Prefab.class);

		when(prefab.instantiate(eq(gameObject), eq(imageHolder))).thenReturn(
				clientGameObject);

		gameObjectFactory.instantiate(prefab, gameObject);
		Position position = clientGameObject.getPosition();
		Position destination = clientGameObject.getDestination();
		assertEquals(position.getX(), 20.);
		assertEquals(position.getY(), 50.);
		assertEquals(destination.getX(), 30.);
		assertEquals(destination.getY(), 40.);
	}
}
