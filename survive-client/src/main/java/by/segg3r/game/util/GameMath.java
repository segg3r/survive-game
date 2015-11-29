package by.segg3r.game.util;

import by.segg3r.data.Position;

public final class GameMath {

	private GameMath() {};
	
	public static double distanceBetween(Position p1, Position p2) {
		double dx = p2.getX() - p1.getX();
		double dy = p2.getY() - p1.getY();
		
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public static double directionBetween(Position p1, Position p2) {
		double atan = Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX());
		if (atan > 0) {
			return Math.PI * 2 - atan;
		} else if (atan < 0) {
			return -atan;
		} else {
			return 0.;
		}
	}
	
}
