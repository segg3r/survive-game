package by.segg3r.game.objects;

import static org.testng.Assert.assertEquals;

import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.data.Position;

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
		gameObject.setDestination(50., 0.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDestination(50., -50.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), -10. / Math.sqrt(2.), 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDestination(0., -50.);
		gameObject.update(1.);
		assertEquals(position.getX(), 0., 0.001);
		assertEquals(position.getY(), -10., 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDestination(-50., -50);
		gameObject.update(1.);
		assertEquals(position.getX(), -10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), -10. / Math.sqrt(2.), 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDestination(-50., 0);
		gameObject.update(1.);
		assertEquals(position.getX(), -10., 0.001);
		assertEquals(position.getY(), 0, 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDestination(-50., 50.);
		gameObject.update(1.);
		assertEquals(position.getX(), -10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), 10. / Math.sqrt(2.), 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDestination(0, 50.);
		gameObject.update(1.);
		assertEquals(position.getX(), 0, 0.001);
		assertEquals(position.getY(), 10, 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.setDestination(50., 50.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), 10. / Math.sqrt(2.), 0.001);
	}
	
	@Test(description = "should correctly update position depending on speed")
	public void shouldCorrectlyUpdatePositionDependingOnSpeed() throws SlickException {
		Position position = gameObject.getPosition();
		gameObject.setDestination(100., 0.);	
		
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
		gameObject.setDestination(100., 0.);
		
		gameObject.setPosition(0., 0.);
		gameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		gameObject.setPosition(0., 0.);
		gameObject.update(.5);
		assertEquals(position.getX(), 5., 0.001);
		assertEquals(position.getY(), 0., 0.001);
	}
	
	@Test(description = "should set speed equal to movement speed if not close to destination")
	public void testMovementSpeedWhenNotCloseToDestination() throws SlickException {
		Position position = gameObject.getPosition();
		position.set(0., 0.);
		gameObject.setSpeed(0.);
		gameObject.setMovementSpeed(10.);
		gameObject.setDestination(6., 0.);
		
		gameObject.update(.5);
		assertEquals(gameObject.getSpeed(), gameObject.getMovementSpeed());
	}
	
	@Test(description = "should stop moving and set to destination if close to it")
	public void testMovementSpeedAndPositionWhenCloseToDestination() throws SlickException {
		Position position = gameObject.getPosition();
		position.set(0., 0.);
		gameObject.setSpeed(10.);
		gameObject.setMovementSpeed(10.);
		gameObject.setDestination(4., 0.);
		
		gameObject.update(.5);
		assertEquals(gameObject.getSpeed(), 0.);
		assertEquals(position.getX(), 4.);
		assertEquals(position.getY(), 0.);
	}
	
	@Test(description = "should set direction to destination if not close to it")
	public void testDiractionWhenNotCloseToDestination() throws SlickException {
		Position position = gameObject.getPosition();
		position.set(0., 0.);
		gameObject.setDirection(0.);
		gameObject.setSpeed(10.);
		gameObject.setDestination(200., 200.);
		
		gameObject.update(1.);
		assertEquals(gameObject.getDirection(), Math.PI * 7. / 4., 0.001);
	}

}
