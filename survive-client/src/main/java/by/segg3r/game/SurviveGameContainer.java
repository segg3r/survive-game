package by.segg3r.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

import by.segg3r.messaging.Connection;

public class SurviveGameContainer extends AppGameContainer {

	private Connection connection;

	public SurviveGameContainer(Game game) throws SlickException {
		super(game);
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

}
