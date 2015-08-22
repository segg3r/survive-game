package by.segg3r.game;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.springframework.stereotype.Component;

import by.segg3r.game.rooms.Room;

@Component
public class Game extends BasicGame {

	private Room currentRoom;

	public Game() {
		super("Title");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		this.currentRoom = new Room();
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
	
}
