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

import by.segg3r.data.GameObject;
import by.segg3r.game.CommonConstants.Time;
import by.segg3r.game.exception.GameObjectNotFoundException;
import by.segg3r.game.objects.ClientGameObject;
import by.segg3r.game.objects.ClientGameObjectFactory;
import by.segg3r.game.objects.iface.Layer;
import by.segg3r.game.objects.iface.Renderable;
import by.segg3r.game.objects.iface.Updatable;
import by.segg3r.game.objects.prefabs.Prefab;

public class Room {

	private ClientGameObjectFactory gameObjectFactory;

	private Map<Long, ClientGameObject> gameObjects = new HashMap<Long, ClientGameObject>();
	private Map<Layer, Set<Renderable>> renderables = new HashMap<Layer, Set<Renderable>>();
	private Set<Updatable> updatables = new HashSet<Updatable>();

	public Room(ClientGameObjectFactory gameObjectFactory) {
		super();
		this.gameObjectFactory = gameObjectFactory;
	}

	public <T extends ClientGameObject> T addGameObject(Prefab<T, ?> prefab,
			GameObject gameObject) throws SlickException {
		T result = gameObjectFactory.instantiate(prefab, gameObject);
		addGameObject(result);
		return result;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Renderable renderable : this.getRenderables()) {
			renderable.render(g);
		}
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		for (Updatable updatable : this.getUpdatables()) {
			updatable.update((double) delta / Time.ONE_SECOND);
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

	public void addGameObject(ClientGameObject gameObject) {
		this.addRenderable(Layer.OBJECT, gameObject);
		this.addUpdatable(gameObject);
		this.gameObjects.put(gameObject.getId(), gameObject);
	}

	public ClientGameObject findGameObject(long objectId)
			throws GameObjectNotFoundException {
		ClientGameObject result = gameObjects.get(objectId);
		if (result == null) {
			throw new GameObjectNotFoundException(
					"Could not find object with id " + objectId);
		}
		return result;
	}

	public void removeGameObject(long objectId) {
		ClientGameObject object = gameObjects.get(objectId);
		if (object != null) {
			updatables.remove(object);
			for (Set<Renderable> layerRenderables : renderables.values()) {
				layerRenderables.remove(object);
			}
		}
		
		gameObjects.remove(objectId);
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

	public Map<Long, ClientGameObject> getGameObjects() {
		return gameObjects;
	}

}
