package by.segg3r.game.util.pathresolver;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class PatternPathResolverTest {

	@Test(description = "should resolve path by pattern without parent")
	public void testResolveWithoutParent() {
		assertEquals(
				new PatternPathResolver("res/img/${path}.png").resolve("001"),
				"res/img/001.png");
	}

	@Test(description = "should resolve path by pattern with parent")
	public void testResolveWithParent() {
		PathResolver imageResourceResolver = new PatternPathResolver(
				"res/img/${path}");
		PathResolver characterImageResourceResolver = new PatternPathResolver(
				"character/${path}.png", imageResourceResolver);
		assertEquals(characterImageResourceResolver.resolve("001"),
				"res/img/character/001.png");
	}

}
