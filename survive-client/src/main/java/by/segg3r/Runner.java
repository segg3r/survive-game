package by.segg3r;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import by.segg3r.game.SurviveGame;

public class Runner {

	public static void main(String[] args) {
		try {
			SurviveGame game = new SurviveGame("Title");
			AppGameContainer appGameContainer = new AppGameContainer(game);
			appGameContainer.setDisplayMode(380, 560, false);

			appGameContainer.start();
		} catch (SlickException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
