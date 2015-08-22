package by.segg3r.game.rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.Layer;
import by.segg3r.game.objects.Renderable;
import by.segg3r.game.objects.Updatable;

public class Room {

	private Map<Layer, Set<Renderable>> renderables = new HashMap<Layer, Set<Renderable>>();
	private Set<Updatable> updatables = new HashSet<Updatable>();
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Renderable renderable : this.getRenderables()) {
			renderable.render(g);
		}
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		for (Updatable updatable : this.getUpdatables()) {
			updatable.update((double) delta / 1000.);
		}
	}
	
	public void addRenderable(Renderable renderable) {
		Set<Renderable> layerRenderables = renderables.get(renderable
				.getLayer());
		if (layerRenderables == null) {
			layerRenderables = new HashSet<Renderable>();
			renderables.put(renderable.getLayer(), layerRenderables);
		}
		layerRenderables.add(renderable);
	}

	public List<Renderable> getRenderables() {
		List<Renderable> result = new ArrayList<Renderable>();
		// should sort by layer order
		for (Layer layer : Layer.values()) {
			List<Renderable> layerRenderables = getRenderables(layer);
			if (layerRenderables != null) {
				result.addAll(layerRenderables);
			}
		}
		return result;
	}
	
	public List<Renderable> getRenderables(Layer layer) {
		Set<Renderable> set = this.renderables.get(layer);
		return set == null ? null : new ArrayList<Renderable>(set);
	}

	public void addUpdatable(Updatable updatable) {
		this.updatables.add(updatable);
	}

	public List<Updatable> getUpdatables() {
		return new ArrayList<Updatable>(this.updatables);
	}

	
}
