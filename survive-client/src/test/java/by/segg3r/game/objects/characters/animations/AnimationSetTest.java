package by.segg3r.game.objects.characters.animations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;

import org.newdawn.slick.Animation;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AnimationSetTest {

	private Animation left;
	private Animation right;
	private Animation top;
	private Animation down;
	private AnimationSet animationSet;

	@BeforeMethod
	public void init() {
		left = mock(Animation.class);
		right = mock(Animation.class);
		top = mock(Animation.class);
		down = mock(Animation.class);
		animationSet = new AnimationSet(left, right, top, down);
	}

	@AfterMethod
	public void resetMocks() {
		reset(left, right, top, down);
	}

	@Test(description = "should correctly return animation by direction")
	public void shouldCorrectlyReturnAnimationByDirection() {
		assertEquals(animationSet.getCurrentAnimation(0.), right);
		assertEquals(animationSet.getCurrentAnimation(Math.PI / 4.), top);
		assertEquals(animationSet.getCurrentAnimation(Math.PI / 2.), top);
		assertEquals(animationSet.getCurrentAnimation(Math.PI * 3. / 4.), left);
		assertEquals(animationSet.getCurrentAnimation(Math.PI), left);
		assertEquals(animationSet.getCurrentAnimation(Math.PI * 5. / 4.), down);
		assertEquals(animationSet.getCurrentAnimation(Math.PI * 3. / 2.), down);
		assertEquals(animationSet.getCurrentAnimation(Math.PI * 7. / 4.), right);
	}

}
