package a.maze;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends BasicGameState {

	public static final int TILE_SIZE = 16;

	private int id;
	private Image background;
	private Image tile;
	private Maze maze;
	private Input input;
	private boolean showAll;

	public Main(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		background = new Image("res/background.png");
		tile = new Image("res/Tile.png");
		input = container.getInput();
		showAll = false;

		maze = new Maze(51, 39);
		maze.search();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw();

		for (int j = 0; j < maze.getHeight(); j++) {
			for (int i = 0; i < maze.getWidth(); i++) {
				if (maze.getCell(i, j) == 1) {
					tile.draw(i * TILE_SIZE, j * TILE_SIZE);
				}
				if (maze.getCell(i, j) == 3) {
					tile.draw(i * TILE_SIZE, j * TILE_SIZE, Color.green);
				}
				if (maze.getCell(i, j) == 2) {
					tile.draw(i * TILE_SIZE, j * TILE_SIZE, Color.cyan);
				}
				if (maze.getCell(i, j) == 4 && showAll == true) {
					tile.draw(i * TILE_SIZE, j * TILE_SIZE, Color.pink);
				}

			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (input.getMouseX() < container.getWidth()
					&& input.getMouseX() > 0
					&& input.getMouseY() < container.getHeight()
					&& input.getMouseY() > 0) {
				int x = (int) Math.floor(input.getMouseX() / TILE_SIZE);
				int y = (int) Math.floor(input.getMouseY() / TILE_SIZE);
				if (x != 0 && x != 50 && y != 0 && y != 38) {
					maze.setCellStatus(x, y, 0);
				}
			}
		}

		if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {

			if (input.getMouseX() < container.getWidth()
					&& input.getMouseX() > 0
					&& input.getMouseY() < container.getHeight()
					&& input.getMouseY() > 0) {
				int x = (int) Math.floor(input.getMouseX() / TILE_SIZE);
				int y = (int) Math.floor(input.getMouseY() / TILE_SIZE);
				if (maze.getCell(x, y) != 3) {
					maze.setCellStatus(x, y, 1);
				}
			}
		}

		if (input.isKeyPressed(Input.KEY_Q)) {
			maze = new Maze(51, 39);
			maze.search();
		}
		if (input.isKeyDown(Input.KEY_W)) {
			maze.destroyWalls(10);
			maze.search();
		}
		if (input.isKeyPressed(Input.KEY_E)) {
			maze.search();
		}
		if (input.isKeyPressed(Input.KEY_R)) {
			showAll = !showAll;
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
