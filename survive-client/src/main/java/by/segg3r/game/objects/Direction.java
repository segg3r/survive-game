package by.segg3r.game.objects;

public enum Direction {

	LEFT, RIGHT, TOP, DOWN;
	
	public static Direction fromAngle(double direction) {
		Direction result;
		if (direction < Math.PI / 4.) {
			result = Direction.RIGHT;
		} else if (direction < Math.PI * 3. / 4.) {
			result = Direction.TOP;
		} else if (direction < Math.PI * 5. / 4.) {
			result = Direction.LEFT;
		} else if (direction < Math.PI * 7. / 4.)  {
			result = Direction.DOWN;
		} else {
			result = Direction.RIGHT;
		}
		return result;
	}
	
}
