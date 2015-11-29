package by.segg3r.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import by.segg3r.client.ClientMessageHandler;
import by.segg3r.data.GameObject;
import by.segg3r.game.actions.GameObjectMoveInputAction;
import by.segg3r.game.input.InputHandler;
import by.segg3r.game.input.processors.MouseInputProcessor;
import by.segg3r.game.objects.ClientGameObjectFactory;
import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.objects.prefabs.GameCharacterPrefab;
import by.segg3r.game.objects.prefabs.options.GameCharacterPrefabAnimationOptions;
import by.segg3r.game.rooms.Room;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.messaging.exception.MessageSendingException;

@Component
public class SurviveGame extends BasicGame {

	private static final Logger LOG = LogManager.getLogger(SurviveGame.class);

	@Autowired
	private ClientGameObjectFactory gameObjectFactory;
	@Autowired
	private InputHandler inputHandler;
	@Value("#{messageQueue}")
	private Queue<ClientMessageHandler<?>> messageQueue;

	private Connection connection;

	private Room currentRoom;
	private GameCharacter playerCharacter;

	public SurviveGame() {
		super("Title");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		if (!(gc instanceof SurviveGameContainer)) {
			throw new SlickException(
					"Wrong game container was passed in init method.");
		}
		SurviveGameContainer sgc = (SurviveGameContainer) gc;
		this.currentRoom = new Room(gameObjectFactory);
		this.connection = sgc.getConnection();
		new Thread(this.connection).start();
	}

	public void createPlayerCharacter(GameObject gameObject) throws SlickException {
		Map<AnimationPart, String> files = new HashMap<AnimationPart, String>();
		files.put(AnimationPart.BODY, "001");
		files.put(AnimationPart.FACE, "001");
		files.put(AnimationPart.HAIRS, "001");
		files.put(AnimationPart.ARMOR, "001");
		GameCharacterPrefabAnimationOptions animationOptions = new GameCharacterPrefabAnimationOptions(
				files);
		GameCharacterPrefab prefab = new GameCharacterPrefab(animationOptions);

		playerCharacter = currentRoom.addGameObject(prefab, gameObject);

		addInputProcessors();
	}

	public void addInputProcessors() {
		inputHandler.addInputProcessor(new MouseInputProcessor(
				Input.MOUSE_LEFT_BUTTON, new GameObjectMoveInputAction(
						playerCharacter)));
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.currentRoom.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		processMessages();
		inputHandler.handleInput(gc.getInput());
		this.currentRoom.update(gc, delta);
	}

	public void processMessages() {
		while (!messageQueue.isEmpty()) {
			ClientMessageHandler<?> message = messageQueue.poll();

			try {
				Collection<Message> response = message
						.handleClientMessage(this);

				for (Message responseMessage : response) {
					connection.sendMessage(responseMessage);
				}
			} catch (MessageHandlingException | MessageSendingException e) {
				LOG.error("Error handling incoming message", e);
			}
		}
	}

	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
	}

	public ClientGameObjectFactory getGameObjectFactory() {
		return gameObjectFactory;
	}

	public void setGameObjectFactory(ClientGameObjectFactory gameObjectFactory) {
		this.gameObjectFactory = gameObjectFactory;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public GameCharacter getPlayerCharacter() {
		return playerCharacter;
	}

	public void setPlayerCharacter(GameCharacter gameCharacter) {
		this.playerCharacter = gameCharacter;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	public void setMessageQueue(Queue<ClientMessageHandler<?>> messageQueue) {
		this.messageQueue = messageQueue;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
