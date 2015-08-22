package by.segg3r.game;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
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

import by.segg3r.game.objects.Layer;
import by.segg3r.game.objects.Renderable;
import by.segg3r.game.objects.Updatable;
import by.segg3r.game.rooms.Room;

public class RoomTest {

	private Room room;

	@BeforeMethod
	public void init() {
		room = new Room();
	}

	@Test(description = "should add renderable to the room")
	public void addRenderable() {
		assertEquals(0, room.getRenderables().size());
		Renderable background = new Renderable() {
			@Override
			public void render(Graphics g) throws SlickException {
				return;
			}

			@Override
			public Layer getLayer() {
				return Layer.BACKGROUND;
			}

		};
		room.addRenderable(background);
		assertEquals(1, room.getRenderables().size());
	}

	@Test(description = "should order renderables by layer when added to a room")
	public void addRenderablesOnDifferentLayers() {
		assertEquals(0, room.getRenderables().size());
		Renderable background = new Renderable() {
			@Override
			public void render(Graphics g) throws SlickException {
				return;
			}

			@Override
			public Layer getLayer() {
				return Layer.BACKGROUND;
			}

		};

		Renderable surface = new Renderable() {
			@Override
			public void render(Graphics g) throws SlickException {
				return;
			}

			@Override
			public Layer getLayer() {
				return Layer.SURFACE;
			}

		};

		Renderable object = new Renderable() {
			@Override
			public void render(Graphics g) throws SlickException {
				return;
			}

			@Override
			public Layer getLayer() {
				return Layer.OBJECT;
			}

		};
		
		Renderable ui = new Renderable() {
			@Override
			public void render(Graphics g) throws SlickException {
				return;
			}

			@Override
			public Layer getLayer() {
				return Layer.UI;
			}

		};
		room.addRenderable(background);
		room.addRenderable(surface);
		room.addRenderable(object);
		room.addRenderable(ui);
		
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
		Updatable updatable = new Updatable() {
			@Override
			public void update(double delta) throws SlickException {
				return;
			}
		};
		room.addUpdatable(updatable);
		assertEquals(1, room.getUpdatables().size());
	}
	
	@Test(description = "should render renderable only once when render triggered")
	public void shouldRenderOnlyOnce() {
		assertEquals(0, room.getRenderables().size());
		Renderable background = new Renderable() {
			@Override
			public void render(Graphics g) throws SlickException {
				return;
			}

			@Override
			public Layer getLayer() {
				return Layer.BACKGROUND;
			}

		};
		room.addRenderable(background);
		room.addRenderable(background);
		assertEquals(1, room.getRenderables().size());
	}
	
	@Test(description = "should update updatable only once when update triggered")
	public void shouldUpdateOnlyOnce() {
		assertEquals(0, room.getUpdatables().size());
		Updatable updatable = new Updatable() {
			@Override
			public void update(double delta) throws SlickException {
				return;
			}
		};
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
			when(renderable.getLayer()).thenReturn(Layer.UI);
			renderables.add(renderable);
		}
		renderables.forEach(renderable -> room.addRenderable(renderable));
		GameContainer gc = mock(GameContainer.class);
		Graphics g = mock(Graphics.class);
		room.render(gc, g);
		for (int i = 0; i < amount; i++) {
			verify(renderables.get(i), times(1)).render(g);
		}
	}
	
	@Test(description = "should update all updatables in room")
	public void shouldUpdateAllUpdatables()	throws SlickException {
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

}
