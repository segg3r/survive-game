package by.segg3r.game;

import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import by.segg3r.game.objects.GameObject;
import by.segg3r.game.objects.GameObjectFactory;
import by.segg3r.game.objects.prefabs.ImageHolder;
import by.segg3r.game.objects.prefabs.Prefab;
import by.segg3r.game.rooms.Room;

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
		Room room = mock(Room.class);
		GameObject gameObject = new GameObject(room);
		Prefab<?, ?> prefab = mock(Prefab.class);
		
		when(prefab.instantiate(any(ImageHolder.class), any(Room.class)))
			.thenReturn(gameObject);
		
		gameObjectFactory.instantiate(prefab, room, 20., 50.);
		assertEquals(gameObject.getX(), 20.);
		assertEquals(gameObject.getY(), 50.);
	}

}
