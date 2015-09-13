package by.segg3r;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.game.config.AppConfig;
import by.segg3r.game.config.GameResourceConfig;

public final class Runner {

	private Runner() {};
	
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class, GameResourceConfig.class)) {
			AppGameContainer appGameContainer = ctx.getBean(AppGameContainer.class);
			appGameContainer.start();

			ctx.close();
		} catch (SlickException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
