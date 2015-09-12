package by.segg3r.game;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.game.objects.GameObjectFactory;
import by.segg3r.game.objects.characters.animations.AnimationPart;
import by.segg3r.game.objects.prefabs.GameCharacterPrefab;
import by.segg3r.game.objects.prefabs.options.GameCharacterPrefabAnimationOptions;
import by.segg3r.game.rooms.Room;

@Component
public class Game extends BasicGame {

	@Autowired
	private GameObjectFactory gameObjectFactory;

	private Room currentRoom;

	public Game() {
		super("Title");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		this.currentRoom = new Room(gameObjectFactory);

		Map<AnimationPart, String> files = new HashMap<AnimationPart, String>();
		files.put(AnimationPart.BODY, "img/characters/image_body.png");
		GameCharacterPrefabAnimationOptions animationOptions = new GameCharacterPrefabAnimationOptions(
				files);
		GameCharacterPrefab prefab = new GameCharacterPrefab(animationOptions);

		currentRoom.addGameObject(prefab, 50, 50);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.currentRoom.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
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

}
