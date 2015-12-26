package by.segg3r.game.objects;

public enum Direction {

	LEFT(3), RIGHT(1), TOP(0), DOWN(2);

	private static final double BOTTOM_RIGHT = Math.PI * 7. / 4.;
	private static final double BOTTOM_LEFT = Math.PI * 5. / 4.;
	private static final double TOP_LEFT = Math.PI * 3. / 4.;
	private static final double TOP_RIGHT = Math.PI / 4.;

	private final int spriteRow;

	Direction(int spriteRow) {
		this.spriteRow = spriteRow;
	}

	public static Direction fromAngle(double direction) {
		Direction result;
		if (direction < TOP_RIGHT) {
			result = Direction.RIGHT;
		} else if (direction < TOP_LEFT) {
			result = Direction.TOP;
		} else if (direction < BOTTOM_LEFT) {
			result = Direction.LEFT;
		} else if (direction < BOTTOM_RIGHT) {
			result = Direction.DOWN;
		} else {
			result = Direction.RIGHT;
		}
		return result;
	}

	public int getSpriteRow() {
		return spriteRow;
	}

}
