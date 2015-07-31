package by.segg3r;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import by.segg3r.game.config.AppConfig;

public class Runner {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class)) {
			AppGameContainer appGameContainer = ctx.getBean(AppGameContainer.class);
			appGameContainer.start();

			ctx.close();
		} catch (SlickException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
