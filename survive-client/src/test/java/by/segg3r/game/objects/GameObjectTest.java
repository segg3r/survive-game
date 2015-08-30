package by.segg3r.game.objects;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.rooms.Room;

public class GameObjectTest {

	private GameObject gameObject;
	private Room room;

	@BeforeMethod
	public void init() {
		room = mock(Room.class);
		gameObject = new GameObject(room);
	}

	@Test(description = "should correctly update position")
	public void shouldCorrectlyUpdatePosition() throws SlickException {
		gameObject.setPosition(200., 300.);
		gameObject.setDirection(Math.PI);
		gameObject.setSpeed(20.);

		gameObject.update(.5);

		assertEquals(gameObject.getX(), 190.);
		assertEquals(gameObject.getY(), 300.);
	}

}
