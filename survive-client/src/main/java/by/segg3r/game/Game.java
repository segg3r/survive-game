package by.segg3r.game;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.game.actions.GameObjectMoveInputAction;
import by.segg3r.game.input.InputHandler;
import by.segg3r.game.input.processors.MouseInputProcessor;
import by.segg3r.game.objects.GameObjectFactory;
import by.segg3r.game.objects.characters.GameCharacter;
import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.objects.prefabs.GameCharacterPrefab;
import by.segg3r.game.objects.prefabs.options.GameCharacterPrefabAnimationOptions;
import by.segg3r.game.rooms.Room;
import by.segg3r.messaging.Connection;

@Component
public class Game extends BasicGame {

	@Autowired
	private GameObjectFactory gameObjectFactory;
	@Autowired
	private InputHandler inputHandler;
	@Autowired
	private Connection connection;

	private Room currentRoom;
	private GameCharacter playerCharacter;

	public Game() {
		super("Title");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		this.currentRoom = new Room(gameObjectFactory);

		Map<AnimationPart, String> files = new HashMap<AnimationPart, String>();
		files.put(AnimationPart.BODY, "001");
		files.put(AnimationPart.FACE, "001");
		files.put(AnimationPart.HAIRS, "001");
		files.put(AnimationPart.ARMOR, "001");
		GameCharacterPrefabAnimationOptions animationOptions = new GameCharacterPrefabAnimationOptions(
				files);
		GameCharacterPrefab prefab = new GameCharacterPrefab(animationOptions);

		playerCharacter = currentRoom.addGameObject(prefab, 500, 500);
		playerCharacter.setDestination(20, 20);

		addInputProcessors();
	}

	public void addInputProcessors() {
		inputHandler.addInputProcessor(
				new MouseInputProcessor(Input.MOUSE_LEFT_BUTTON,
						new GameObjectMoveInputAction(playerCharacter)
				)
		);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.currentRoom.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		inputHandler.handleInput(gc.getInput());
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			playerCharacter.setDestination(gc.getInput().getMouseX(), gc
					.getInput().getMouseY());
		}

		this.currentRoom.update(gc, delta);
	}

	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
	}

	public GameObjectFactory getGameObjectFactory() {
		return gameObjectFactory;
	}

	public void setGameObjectFactory(GameObjectFactory gameObjectFactory) {
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

}
