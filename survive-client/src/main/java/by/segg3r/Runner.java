package by.segg3r;

import org.newdawn.slick.SlickException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.config.ClientConfig;
import by.segg3r.config.GameResourceConfig;
import by.segg3r.config.HandlersConfig;
import by.segg3r.game.SurviveGameContainer;
import by.segg3r.messaging.connection.Connection;
import by.segg3r.util.LoggerUtil;

public final class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		LoggerUtil.initializeLogger("client.log");
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				HandlersConfig.class, ClientConfig.class,
				GameResourceConfig.class)) {
			SurviveGameContainer gc = ctx
					.getBean(SurviveGameContainer.class);
			Connection connection = ctx.getBean(Connection.class);
			gc.setConnection(connection);
			gc.start();
			ctx.close();
		} catch (SlickException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
