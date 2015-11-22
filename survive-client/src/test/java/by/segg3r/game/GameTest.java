package by.segg3r.game;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.client.ClientMessageHandler;
import by.segg3r.game.input.InputHandler;
import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.GameCharacterAnimation;
import by.segg3r.game.rooms.Room;
import by.segg3r.messaging.Connection;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.messaging.exception.MessageSendingException;

public class GameTest {

	private InputHandler inputHandler;
	private GameCharacterAnimation gameCharacterAnimation;
	private GameCharacter playerCharacter;
	private Room room;
	private Input input;
	private GameContainer gc;
	private Graphics g;
	private Queue<ClientMessageHandler<?>> messageQueue;
	private Connection connection;
	private SurviveGame game;

	@BeforeMethod
	public void init() {
		game = new SurviveGame();

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

		messageQueue = new LinkedList<ClientMessageHandler<?>>();
		game.setMessageQueue(messageQueue);

		connection = mock(Connection.class);
		game.setConnection(connection);
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

	@Test(description = "should send response messages handled from queue")
	public void shouldSendResponseMessagesHandledFromQueue()
			throws MessageHandlingException, SlickException,
			MessageSendingException {
		ClientMessageHandler<?> clientMessageHandler = mock(ClientMessageHandler.class);
		messageQueue.add(clientMessageHandler);
		Message message = new Message() {
		};
		when(clientMessageHandler.handleClientMessage(eq(game)))
				.thenReturn(Arrays.asList(message, message));

		game.update(gc, 1000);

		verify(connection, times(2)).sendMessage(eq(message));
	}

}
