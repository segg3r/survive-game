package by.segg3r.game;

import static org.testng.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.input.InputHandler;
import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;
import by.segg3r.game.rooms.Room;

public class GameTest {

	private InputHandler inputHandler;
	private GameCharacterAnimation gameCharacterAnimation;
	private GameCharacter playerCharacter;
	private Room room;
	private Input input;
	private GameContainer gc;
	private Graphics g;
	private Game game;

	@BeforeMethod
	public void init() {
		game = new Game();
		
		gameCharacterAnimation = mock(GameCharacterAnimation.class);
		playerCharacter = new GameCharacter(gameCharacterAnimation);
		game.setPlayerCharacter(playerCharacter);
		
		room = mock(Room.class);
		game.setCurrentRoom(room);
			
		g = mock(Graphics.class);
		
		input = mock(Input.class);
		gc = mock(GameContainer.class);
		when(gc.getInput()).thenReturn(input);
		
		inputHandler = new InputHandler();
		game.setInputHandler(inputHandler);
		game.addInputProcessors();
	}

	@Test(description = "should render current room")
	public void shouldRenderCurrentRoom() throws SlickException {
		game.render(gc, g);
		verify(room, times(1)).render(eq(gc), eq(g));
	}

	@Test(description = "should update current room")
	public void shouldUpdateCurrentRoom() throws SlickException {
		game.update(gc, 500);
		verify(room, times(1)).update(eq(gc), eq(500));
	}

	@Test(description = "should set character destination on mouse click")
	public void shouldMoveCharacterOnMouseClick() throws SlickException {
		when(input.isMousePressed(eq(Input.MOUSE_LEFT_BUTTON)))
				.thenReturn(true);
		when(input.getMouseX()).thenReturn(20);
		when(input.getMouseY()).thenReturn(50);
		when(gc.getInput()).thenReturn(input);
		
		game.update(gc, 1000);
		assertEquals(playerCharacter.getDestination().getX(), 20.);
		assertEquals(playerCharacter.getDestination().getY(), 50.);
	}

}
