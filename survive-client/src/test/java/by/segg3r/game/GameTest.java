package by.segg3r.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import by.segg3r.game.rooms.Room;

public class GameTest {

	private Room room;
	private Game game;
	
	@BeforeMethod
	public void init() {
		room = mock(Room.class);
		game = new Game();
		game.setCurrentRoom(room);
	}
	
	@Test(description = "should render current room")
	public void shouldRenderCurrentRoom() throws SlickException {
		GameContainer gc = mock(GameContainer.class);
		Graphics g = mock(Graphics.class);
		
		game.render(gc, g);
		verify(room, times(1)).render(eq(gc), eq(g));
	}
	
	@Test(description = "should update current room")
	public void shouldUpdateCurrentRoom() throws SlickException {
		GameContainer gc = mock(GameContainer.class);
		
		game.update(gc, 500);
		verify(room, times(1)).update(eq(gc), eq(500));
	}
	
}
