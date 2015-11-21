package by.segg3r;

import org.apache.log4j.BasicConfigurator;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.client.Client;
import by.segg3r.game.config.ClientConfig;
import by.segg3r.game.config.GameResourceConfig;

public final class Runner {

	private Runner() {
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				ClientConfig.class, GameResourceConfig.class)) {
			Client client = ctx.getBean(Client.class);
			Thread clientThread = new Thread(client);
			clientThread.start();

			AppGameContainer appGameContainer = ctx
					.getBean(AppGameContainer.class);
			appGameContainer.start();

			ctx.close();
		} catch (SlickException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
