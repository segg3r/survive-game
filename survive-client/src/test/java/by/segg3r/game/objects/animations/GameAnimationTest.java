package by.segg3r.game.objects.animations;

import static org.mockito.Mockito.*;

import org.newdawn.slick.Animation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.data.Position;

public class GameAnimationTest {

	private Animation animation;
	private GameAnimation gameAnimation;
	
	@BeforeMethod
	public void init() {
		animation = mock(Animation.class);
		gameAnimation = new GameAnimation(animation, new Position(10., 20.));
	}
	
	@Test(description = "should draw animation with offset")
	public void testOffset() {
		gameAnimation.draw(40.f, 50.f);
		
		verify(animation, times(1)).draw(eq(30.f), eq(30.f));
	}
	
	@Test(description = "should update animation")
	public void testUpdateAnimation() {
		gameAnimation.update(200L);
		
		verify(animation, times(1)).update(eq(200L));
	}
	
	@Test(description = "should set current frame")
	public void testSetCurrentFrame() {
		gameAnimation.setCurrentFrame(5);
		
		verify(animation, times(1)).setCurrentFrame(eq(5));
	}
	
}
