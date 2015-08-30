package by.segg3r.game.rooms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.game.objects.GameObject;
import by.segg3r.game.objects.GameObjectFactory;
import by.segg3r.game.objects.iface.Layer;
import by.segg3r.game.objects.iface.Renderable;
import by.segg3r.game.objects.iface.Updatable;
import by.segg3r.game.objects.prefabs.Prefab;

public class RoomTest {

	private Room room;
	private GameObjectFactory gameObjectFactory;

	@BeforeMethod
	public void init() {
		gameObjectFactory = mock(GameObjectFactory.class);
		room = new Room(gameObjectFactory);
	}

	@Test(description = "should add renderable to the room")
	public void addRenderable() {
		assertEquals(0, room.getRenderables().size());
		Renderable background = mock(Renderable.class);
		room.addRenderable(Layer.BACKGROUND, background);
		assertEquals(1, room.getRenderables().size());
	}

	@Test(description = "should order renderables by layer when added to a room")
	public void addRenderablesOnDifferentLayers() {
		assertEquals(0, room.getRenderables().size());
		Renderable background = mock(Renderable.class);
		Renderable surface = mock(Renderable.class);
		Renderable object = mock(Renderable.class);
		Renderable ui = mock(Renderable.class);
		room.addRenderable(Layer.BACKGROUND, background);
		room.addRenderable(Layer.SURFACE, surface);
		room.addRenderable(Layer.OBJECT, object);
		room.addRenderable(Layer.UI, ui);

		assertEquals(4, room.getRenderables().size());
		assertEquals(1, room.getRenderables(Layer.BACKGROUND).size());
		assertEquals(1, room.getRenderables(Layer.SURFACE).size());
		assertEquals(1, room.getRenderables(Layer.OBJECT).size());
		assertEquals(1, room.getRenderables(Layer.UI).size());

		assertEquals(0, room.getRenderables().indexOf(background));
		assertEquals(1, room.getRenderables().indexOf(surface));
		assertEquals(2, room.getRenderables().indexOf(object));
		assertEquals(3, room.getRenderables().indexOf(ui));
	}

	@Test(description = "should add updatable to a room")
	public void addUpdatable() {
		assertEquals(0, room.getUpdatables().size());
		Updatable updatable = mock(Updatable.class);
		room.addUpdatable(updatable);
		assertEquals(1, room.getUpdatables().size());
	}

	@Test(description = "should render renderable only once when render triggered")
	public void shouldRenderOnlyOnce() {
		assertEquals(0, room.getRenderables().size());
		Renderable background = mock(Renderable.class);
		room.addRenderable(Layer.BACKGROUND, background);
		room.addRenderable(Layer.BACKGROUND, background);
		assertEquals(1, room.getRenderables().size());
	}

	@Test(description = "should update updatable only once when update triggered")
	public void shouldUpdateOnlyOnce() {
		assertEquals(0, room.getUpdatables().size());
		Updatable updatable = mock(Updatable.class);
		room.addUpdatable(updatable);
		room.addUpdatable(updatable);
		assertEquals(1, room.getUpdatables().size());
	}

	@Test(description = "should render all renderables in room")
	public void shouldRenderAllRenderables() throws SlickException {
		int amount = 50;
		List<Renderable> renderables = new ArrayList<Renderable>();
		for (int i = 0; i < amount; i++) {
			Renderable renderable = mock(Renderable.class);
			renderables.add(renderable);
		}
		renderables.forEach(renderable -> room.addRenderable(Layer.UI,
				renderable));
		GameContainer gc = mock(GameContainer.class);
		Graphics g = mock(Graphics.class);
		room.render(gc, g);
		for (int i = 0; i < amount; i++) {
			verify(renderables.get(i), times(1)).render(g);
		}
	}

	@Test(description = "should update all updatables in room")
	public void shouldUpdateAllUpdatables() throws SlickException {
		int amount = 50;
		List<Updatable> updatables = new ArrayList<Updatable>();
		for (int i = 0; i < amount; i++) {
			Updatable updatable = mock(Updatable.class);
			updatables.add(updatable);
		}
		updatables.forEach(updatable -> room.addUpdatable(updatable));
		room.update(mock(GameContainer.class), 700);
		for (int i = 0; i < amount; i++) {
			verify(updatables.get(i), times(1)).update(eq(.7));
		}
	}

	@Test(description = "should add game object to the room")
	public void addGameObject() {
		GameObject gameObject = mock(GameObject.class);
		room.addGameObject(gameObject);

		assertTrue(room.getRenderables().contains(gameObject));
		assertTrue(room.getUpdatables().contains(gameObject));
	}

	@Test(description = "should add game object instantiated from prefab factory")
	public void addGameObjectFromPrefab() throws SlickException {
		Prefab<?, ?> prefab = mock(Prefab.class);
		GameObject gameObject = mock(GameObject.class);

		room = spy(room);

		doNothing().when(room).addGameObject(any(GameObject.class));
		when(
				gameObjectFactory.instantiate(eq(prefab), eq(room), eq(300.),
						eq(200.))).thenReturn(gameObject);

		room.addGameObject(prefab, 300., 200.);
		verify(room, times(1)).addGameObject(eq(gameObject));
	}

}
