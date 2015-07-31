package by.segg3r.game.config;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import by.segg3r.game.Game;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
@PropertySource("classpath:app.properties")
public class AppConfig {

	@Value("${window.width}")
	private int windowWidth;
	@Value("${window.height}")
	private int windowHeight;
	
	@Bean
	public AppGameContainer appGameContainer(Game game) throws SlickException {
		AppGameContainer appGameContainer = new AppGameContainer(game);
		appGameContainer.setDisplayMode(windowWidth, windowHeight, false);
		return appGameContainer;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
