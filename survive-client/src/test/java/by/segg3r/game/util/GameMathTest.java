package by.segg3r.game.util;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import by.segg3r.game.objects.Position;

public class GameMathTest {

	@Test(description = "should correctly calculate distance between points")
	public void testDistanceBetween() {
		assertEquals(GameMath.distanceBetween(new Position(0., 4.),
				new Position(0., 20.)), 16.);
		assertEquals(GameMath.distanceBetween(new Position(20., 0.),
				new Position(25., 0.)), 5.);
		assertEquals(GameMath.distanceBetween(new Position(2., 4.),
				new Position(3., 5.)), Math.sqrt(2.));
		assertEquals(GameMath.distanceBetween(new Position(-5., -3.),
				new Position(-2, 1)), 5.);
	}
	
	@Test(description = "should correctly calculate direction between points")
	public void testDirectionBetween() {
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(1., 0.)),
				0.);
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(-1., 0.)),
				Math.PI);
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(0., 1.)),
				Math.PI * 3. / 2.);
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(0., -1.)),
				Math.PI / 2.);
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(1., -1.)),
				Math.PI / 4.);
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(1., 1.)),
				Math.PI * 7. / 4.);
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(-1., -1.)),
				Math.PI * 3. / 4.);
		assertEquals(GameMath.directionBetween(new Position(0., 0.), new Position(-1., 1.)),
				Math.PI * 5. / 4.);
	}

}
