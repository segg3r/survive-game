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

import by.segg3r.game.objects.GameObject;
import by.segg3r.game.objects.GameObjectFactory;
import by.segg3r.game.objects.iface.Layer;
import by.segg3r.game.objects.iface.Renderable;
import by.segg3r.game.objects.iface.Updatable;
import by.segg3r.game.objects.prefabs.Prefab;

public class Room {

	private GameObjectFactory gameObjectFactory;
	
	private Map<Layer, Set<Renderable>> renderables = new HashMap<Layer, Set<Renderable>>();
	private Set<Updatable> updatables = new HashSet<Updatable>();

	public Room(GameObjectFactory gameObjectFactory) {
		super();
		this.gameObjectFactory = gameObjectFactory;
	}
	
	public void addGameObject(Prefab<?, ?> prefab, double x, double y) throws SlickException {
		GameObject gameObject = gameObjectFactory.instantiate(prefab, this, x, y);
		addGameObject(gameObject);
	}

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

	public void addRenderable(Layer layer, Renderable renderable) {
		Set<Renderable> layerRenderables = renderables.get(layer);
		if (layerRenderables == null) {
			layerRenderables = new HashSet<Renderable>();
			renderables.put(layer, layerRenderables);
		}
		layerRenderables.add(renderable);
	}

	public List<Renderable> getRenderables() {
		List<Renderable> result = new ArrayList<Renderable>();
		// should order by layer
		for (Layer layer : Layer.values()) {
			List<Renderable> layerRenderables = getRenderables(layer);
			if (layerRenderables != null) {
				result.addAll(layerRenderables);
			}
		}
		return result;
	}

	public void addGameObject(GameObject gameObject) {
		this.addRenderable(Layer.OBJECT, gameObject);
		this.addUpdatable(gameObject);
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
