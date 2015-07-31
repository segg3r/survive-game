package by.segg3r.game;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import by.segg3r.game.objects.Layer;
import by.segg3r.game.objects.Renderable;
import by.segg3r.game.objects.Updatable;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTest {

	private Game game;

	@Before
	public void init() {
		game = new Game();
		assertNotNull(game);
	}

	@Test
	public void addRenderable() {
		assertEquals(0, game.getRenderables().size());
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
		game.addRenderable(background);
		assertEquals(1, game.getRenderables().size());
	}

	@Test
	public void addRenderablesOnDifferentLayers() {
		assertEquals(0, game.getRenderables().size());
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
		game.addRenderable(background);
		game.addRenderable(surface);
		game.addRenderable(object);
		game.addRenderable(ui);
		
		assertEquals(4, game.getRenderables().size());
		assertEquals(1, game.getRenderables(Layer.BACKGROUND).size());
		assertEquals(1, game.getRenderables(Layer.SURFACE).size());
		assertEquals(1, game.getRenderables(Layer.OBJECT).size());
		assertEquals(1, game.getRenderables(Layer.UI).size());
		
		assertEquals(0, game.getRenderables().indexOf(background));
		assertEquals(1, game.getRenderables().indexOf(surface));
		assertEquals(2, game.getRenderables().indexOf(object));
		assertEquals(3, game.getRenderables().indexOf(ui));
	}

	@Test
	public void addUpdatable() {
		assertEquals(0, game.getUpdatables().size());
		Updatable updatable = new Updatable() {
			@Override
			public void update(double delta) throws SlickException {
				return;
			}
		};
		game.addUpdatable(updatable);
		assertEquals(1, game.getUpdatables().size());
	}
	
	@Test
	public void shouldRenderOnlyOnce() {
		assertEquals(0, game.getRenderables().size());
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
		game.addRenderable(background);
		game.addRenderable(background);
		assertEquals(1, game.getRenderables().size());
	}
	
	@Test
	public void shouldUpdateOnlyOnce() {
		assertEquals(0, game.getUpdatables().size());
		Updatable updatable = new Updatable() {
			@Override
			public void update(double delta) throws SlickException {
				return;
			}
		};
		game.addUpdatable(updatable);
		game.addUpdatable(updatable);
		assertEquals(1, game.getUpdatables().size());
	}
	
	@Test
	public void shouldRenderAllRenderables() throws SlickException {
		int amount = 50;
		List<Renderable> renderables = new ArrayList<Renderable>();
		for (int i = 0; i < amount; i++) {
			Renderable renderable = mock(Renderable.class);
			when(renderable.getLayer()).thenReturn(Layer.UI);
			renderables.add(renderable);
		}
		renderables.forEach(renderable -> game.addRenderable(renderable));
		GameContainer gc = mock(GameContainer.class);
		Graphics g = mock(Graphics.class);
		game.render(gc, g);
		for (int i = 0; i < amount; i++) {
			verify(renderables.get(i), times(1)).render(g);
		}
	}
	
	@Test
	public void shouldUpdateAllUpdatables()	throws SlickException {
		int amount = 50;
		List<Updatable> updatables = new ArrayList<Updatable>();
		for (int i = 0; i < amount; i++) {
			Updatable updatable = mock(Updatable.class);
			updatables.add(updatable);
		}
		updatables.forEach(updatable -> game.addUpdatable(updatable));
		game.update(mock(GameContainer.class), 700);
		for (int i = 0; i < amount; i++) {
			verify(updatables.get(i), times(1)).update(eq(.7));
		}
	}

}
