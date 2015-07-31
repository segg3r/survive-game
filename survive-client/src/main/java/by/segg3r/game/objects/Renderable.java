package by.segg3r.game.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface Renderable {

	void render(Graphics g) throws SlickException;
	
	Layer getLayer();
	
}
