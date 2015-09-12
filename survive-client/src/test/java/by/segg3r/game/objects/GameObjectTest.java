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

	@Test(description = "should correctly update position depending on direction")
	public void shouldCorrectlyUpdatePositionDependingOnDirection() throws SlickException {
		Position position = gameObject.getPosition();
		gameObject.setSpeed(10.);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(0.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(Math.PI / 4.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), -10. / Math.sqrt(2.), 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(Math.PI / 2.);
		gameObject.update(1.);
		assertEquals(position.getX(), 0., 0.001);
		assertEquals(position.getY(), -10., 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(Math.PI * 3. / 4.);
		gameObject.update(1.);
		assertEquals(position.getX(), -10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), -10. / Math.sqrt(2.), 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(Math.PI);
		gameObject.update(1.);
		assertEquals(position.getX(), -10., 0.001);
		assertEquals(position.getY(), 0, 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(Math.PI * 5. / 4.);
		gameObject.update(1.);
		assertEquals(position.getX(), -10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), 10. / Math.sqrt(2.), 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(Math.PI * 3. / 2.);
		gameObject.update(1.);
		assertEquals(position.getX(), 0, 0.001);
		assertEquals(position.getY(), 10, 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDirection(Math.PI * 7. / 4.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), 10. / Math.sqrt(2.), 0.001);
	}
	
	@Test(description = "should correctly update position depending on speed")
	public void shouldCorrectlyUpdatePositionDependingOnSpeed() throws SlickException {
		Position position = gameObject.getPosition();
		gameObject.setDirection(0.);		
		
		gameObject.setSpeed(10.);
		gameObject.setPosition(0., 0.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		gameObject.setSpeed(20.);
		gameObject.setPosition(0., 0.);
		gameObject.update(1.);
		assertEquals(position.getX(), 20., 0.001);
		assertEquals(position.getY(), 0., 0.001);
	}
	
	@Test(description = "should correctly update position depending on update delta")
	public void shouldCorrectlyUpdatePositionDependingOnUpdateDelta() throws SlickException {
		Position position = gameObject.getPosition();
		gameObject.setSpeed(10.);
		gameObject.setDirection(0.);
		
		gameObject.setPosition(0., 0.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.update(.5);
		assertEquals(position.getX(), 5., 0.001);
		assertEquals(position.getY(), 0., 0.001);
	}

}
