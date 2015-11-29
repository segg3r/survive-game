package by.segg3r.game.objects;

import static org.testng.Assert.assertEquals;

import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;

public class ClientGameObjectTest {

	private GameObject gameObject;
	private ClientGameObject clientGameObject;

	@BeforeMethod
	public void init() {
		gameObject = new GameObject(1L);
		clientGameObject = new ClientGameObject(gameObject);
	}

	@Test(description = "should correctly update position depending on direction")
	public void shouldCorrectlyUpdatePositionDependingOnDirection() throws SlickException {
		Position position = clientGameObject.getPosition();
		clientGameObject.setSpeed(10.);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(50., 0.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(50., -50.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), -10. / Math.sqrt(2.), 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(0., -50.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 0., 0.001);
		assertEquals(position.getY(), -10., 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(-50., -50);
		clientGameObject.update(1.);
		assertEquals(position.getX(), -10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), -10. / Math.sqrt(2.), 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(-50., 0);
		clientGameObject.update(1.);
		assertEquals(position.getX(), -10., 0.001);
		assertEquals(position.getY(), 0, 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(-50., 50.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), -10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), 10. / Math.sqrt(2.), 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(0, 50.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 0, 0.001);
		assertEquals(position.getY(), 10, 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.setDestination(50., 50.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 10. / Math.sqrt(2.), 0.001);
		assertEquals(position.getY(), 10. / Math.sqrt(2.), 0.001);
	}
	
	@Test(description = "should correctly update position depending on speed")
	public void shouldCorrectlyUpdatePositionDependingOnSpeed() throws SlickException {
		Position position = clientGameObject.getPosition();
		clientGameObject.setDestination(100., 0.);	
		
		clientGameObject.setSpeed(10.);
		clientGameObject.setPosition(0., 0.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		clientGameObject.setSpeed(20.);
		clientGameObject.setPosition(0., 0.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 20., 0.001);
		assertEquals(position.getY(), 0., 0.001);
	}
	
	@Test(description = "should correctly update position depending on update delta")
	public void shouldCorrectlyUpdatePositionDependingOnUpdateDelta() throws SlickException {
		Position position = clientGameObject.getPosition();
		clientGameObject.setSpeed(10.);
		clientGameObject.setDestination(100., 0.);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.update(1.);
		assertEquals(position.getX(), 10., 0.001);
		assertEquals(position.getY(), 0., 0.001);
		
		clientGameObject.setPosition(0., 0.);
		clientGameObject.update(.5);
		assertEquals(position.getX(), 5., 0.001);
		assertEquals(position.getY(), 0., 0.001);
	}
	
	@Test(description = "should set speed equal to movement speed if not close to destination")
	public void testMovementSpeedWhenNotCloseToDestination() throws SlickException {
		Position position = clientGameObject.getPosition();
		position.set(0., 0.);
		clientGameObject.setSpeed(0.);
		clientGameObject.setMovementSpeed(10.);
		clientGameObject.setDestination(6., 0.);
		
		clientGameObject.update(.5);
		assertEquals(clientGameObject.getSpeed(), clientGameObject.getMovementSpeed());
	}
	
	@Test(description = "should stop moving and set to destination if close to it")
	public void testMovementSpeedAndPositionWhenCloseToDestination() throws SlickException {
		Position position = clientGameObject.getPosition();
		position.set(0., 0.);
		clientGameObject.setSpeed(10.);
		clientGameObject.setMovementSpeed(10.);
		clientGameObject.setDestination(4., 0.);
		
		clientGameObject.update(.5);
		assertEquals(clientGameObject.getSpeed(), 0.);
		assertEquals(position.getX(), 4.);
		assertEquals(position.getY(), 0.);
	}
	
	@Test(description = "should set direction to destination if not close to it")
	public void testDiractionWhenNotCloseToDestination() throws SlickException {
		Position position = clientGameObject.getPosition();
		position.set(0., 0.);
		clientGameObject.setDirection(0.);
		clientGameObject.setSpeed(10.);
		clientGameObject.setDestination(200., 200.);
		
		clientGameObject.update(1.);
		assertEquals(clientGameObject.getDirection(), Math.PI * 7. / 4., 0.001);
	}

}
