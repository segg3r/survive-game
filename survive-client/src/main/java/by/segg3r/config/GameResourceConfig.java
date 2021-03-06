package by.segg3r.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.util.pathresolver.PathResolver;
import by.segg3r.game.util.pathresolver.PatternPathResolver;

@Configuration
public class GameResourceConfig {

	private PathResolver imageResolver = new PatternPathResolver(
			ClientConfig.RESOURCES_FOLDER + "/img/${path}");
	private PathResolver characterImageResolver = new PatternPathResolver(
			"characters/${path}", imageResolver);

	@Bean(name = "animationPartPathResolvers")
	public Map<AnimationPart, PathResolver> getAnimationPartPathResolvers() {
		Map<AnimationPart, PathResolver> result = new HashMap<AnimationPart, PathResolver>();

		for (AnimationPart animationPart : AnimationPart.values()) {
			String folderName = animationPart.toString().toLowerCase(
					ClientConfig.DEFAULT_LOCALE);
			result.put(animationPart, new PatternPathResolver(folderName
					+ "/${path}.png", characterImageResolver));
		}

		return result;
	}

}
