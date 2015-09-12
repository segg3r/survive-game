package by.segg3r.game.objects;

import static org.testng.Assert.assertEquals;

import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GameObjectTest {

	private GameObject gameObject;

	@BeforeMethod
	public void init() {
		gameObject = new GameObject();
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
