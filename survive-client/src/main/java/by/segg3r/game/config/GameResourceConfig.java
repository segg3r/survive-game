package by.segg3r.game.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import by.segg3r.game.objects.Direction;
import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.util.pathresolver.PathResolver;
import by.segg3r.game.util.pathresolver.PatternPathResolver;

@Configuration
public class GameResourceConfig {

	private PathResolver imageResolver = new PatternPathResolver(
			AppConfig.RESOURCES_FOLDER + "/img/${path}");
	private PathResolver characterImageResolver = new PatternPathResolver(
			"characters/${path}", imageResolver);

	@Bean(name = "animationPartPathResolvers")
	public Map<AnimationPart, PathResolver> getAnimationPartPathResolvers() {
		Map<AnimationPart, PathResolver> result = new HashMap<AnimationPart, PathResolver>();

		for (AnimationPart animationPart : AnimationPart.values()) {
			String folderName = animationPart.toString().toLowerCase(
					AppConfig.DEFAULT_LOCALE);
			result.put(animationPart, new PatternPathResolver(folderName
					+ "/${path}.png", characterImageResolver));
		}

		return result;
	}

	@Bean(name = "directionImageRows")
	public Map<Direction, Integer> getDirectionImageRows() {
		Map<Direction, Integer> result = new HashMap<Direction, Integer>();

		result.put(Direction.TOP, 0);
		result.put(Direction.RIGHT, 1);
		result.put(Direction.DOWN, 2);
		result.put(Direction.LEFT, 3);

		return result;
	}
}
