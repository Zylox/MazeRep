package a.maze;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Vector2f;

public class Maze {

	private Cell[][] maze;
	private int width;
	private int height;
	private static final int[] directions = { 1, 2, 3, 4 };
	// private static final ArrayList directions = new ArrayList();
	private static Random ran = new Random();

	public Maze(int x, int y) {
		width = x;
		height = y;
		generateMaze(x, y);
		maze[width - 2][height - 2].setStatus(3);
	}
	//didnt work
	//hope this doesnt get erased
	
	//comment
	
	private void generateMaze(int x, int y) {
		maze = new Cell[x][y];

		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				maze[i][j] = new Cell(new Vector2f(i, j));
			}
		}

		int r = ran.nextInt(height);
		while (r % 2 == 0) {
			r = ran.nextInt(height);
		}
		// Generate random c
		int c = ran.nextInt(width);
		while (c % 2 == 0) {
			c = ran.nextInt(width);
		}

		generate(c, r);
	}

	private int[] generateRandDirec() {
		ArrayList directions = new ArrayList();
		for (int i = 1; i < 5; i++) {
			directions.add(i);
		}

		int[] randDirects = new int[directions.size()];
		for (int i = 0; directions.size() != 0; i++) {
			int j = ran.nextInt(directions.size());
			randDirects[i] = (int) directions.get(j);
			directions.remove(j);
		}

		for (int i = 0; i < randDirects.length; i++) {
			System.out.println(randDirects[i]);
		}

		return randDirects;
	}

	public void destroyWalls(int x) {
		for (int i = 0; i <= x; i++) {
			int w = 1 + ran.nextInt(width - 2);
			int h = 1 + ran.nextInt(height - 2);
			
			if (maze[w][h].getStatus() != 0 && maze[w][h].getStatus() != 3) {
				maze[w][h].setStatus(0);
			}

		}


	}

	private void generate(int x, int y) {
		int[] ranDirect = generateRandDirec();

		for (int i = 0; i < ranDirect.length; i++) {
			switch (ranDirect[i]) {
			case 1:

				if (y - 2 <= 0)
					continue;
				if (maze[x][y - 2].getStatus() != 0) {
					maze[x][y - 2].setStatus(0);
					maze[x][y - 1].setStatus(0);
					generate(x, y - 2);
				}
				break;
			case 2:

				if (x + 2 >= width - 1) {
					continue;
				}
				if (maze[x + 2][y].getStatus() != 0) {
					maze[x + 2][y].setStatus(0);
					maze[x + 1][y].setStatus(0);
					generate(x + 2, y);
				}
				break;
			case 3:
				if (y + 2 >= height - 1)
					continue;
				if (maze[x][y + 2].getStatus() != 0) {
					maze[x][y + 2].setStatus(0);
					maze[x][y + 1].setStatus(0);
					generate(x, y + 2);
				}
				break;

			case 4:
				if (x - 2 <= 0)
					continue;
				if (maze[x - 2][y].getStatus() != 0) {
					maze[x - 2][y].setStatus(0);
					maze[x - 1][y].setStatus(0);
					generate(x - 2, y);
				}
				break;
			}
		}
	}

	public void search() {

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				maze[i][j].setPrevious(null);

				if (maze[i][j].getStatus() != 1 && maze[i][j].getStatus() != 3) {
					maze[i][j].setStatus(0);
				}
			}
		}
		maze[width - 2][height - 2].setStatus(3);

		ArrayList<Cell> temp = new ArrayList<Cell>();
		temp.add(maze[1][1]);
		Vector2f end = breathsFirstSearch(temp);
		System.out.println("returned successfully");
		if(end == null){
			return;
		}
		for (int i = 0; end != null; i++) {
			
			
			System.out.println(i);
			maze[(int) end.x][(int) end.y].setStatus(2);
			if (end.x == 1 && end.y == 1) {
				System.out.println("ITS AT THE FUCKIGN END");
				break;
			}
			end = maze[(int) end.x][(int) end.y].getPrevious();
		}
	}

	public Vector2f breathsFirstSearch(ArrayList<Cell> stack) {
		if (stack == null) {
			return null;
		}

		ArrayList<Cell> newStack = new ArrayList<Cell>();
		Cell cell = null;
		boolean end = false;
		do {
			if (stack.size() == 0) {
				return null;
			}
			while (stack.size() != 0) {
				cell = stack.get(0);
				stack.remove(0);
				Vector2f pos = cell.getPosition();
				System.out.println(pos.x + " " + pos.y);
				if(cell.getStatus() == 3){
					end = true;
					break;
				}

				// Right
				if (maze[(int) pos.x][(int) (pos.y + 1)].getPrevious() == null
						&& maze[(int) pos.x][(int) (pos.y + 1)].getStatus() != 1) {
					System.out.println("right");
					maze[(int) pos.x][(int) (pos.y + 1)].setPrevious(pos);
	
					newStack.add(maze[(int) pos.x][(int) (pos.y + 1)]);
				}
				// Down
				if (maze[(int) (pos.x + 1)][(int) (pos.y)].getPrevious() == null
						&& maze[(int) (pos.x + 1)][(int) (pos.y)].getStatus() != 1) {
					System.out.println("down");
					maze[(int) (pos.x + 1)][(int) (pos.y)].setPrevious(pos);
			
					newStack.add(maze[(int) (pos.x + 1)][(int) (pos.y)]);
				}
				// Left
				if (maze[(int) (pos.x)][(int) (pos.y - 1)].getPrevious() == null
						&& maze[(int) (pos.x)][(int) (pos.y - 1)].getStatus() != 1) {
					System.out.println("left");
					maze[(int) (pos.x)][(int) (pos.y - 1)].setPrevious(pos);
		
					newStack.add(maze[(int) (pos.x)][(int) (pos.y - 1)]);
				}
				// Up
				if (maze[(int) (pos.x - 1)][(int) (pos.y)].getPrevious() == null
						&& maze[(int) (pos.x - 1)][(int) (pos.y)].getStatus() != 1) {
					System.out.println("up");
					maze[(int) (pos.x - 1)][(int) (pos.y)].setPrevious(pos);

					newStack.add(maze[(int) (pos.x - 1)][(int) (pos.y)]);
				}
				cell.setStatus(4);
			}
			stack = newStack;
			newStack = new ArrayList<Cell>();
		} while (end != true);
		System.out.println("Victory");
		return cell.getPosition();
	}

	public int getCell(int x, int y) {
		return maze[x][y].getStatus();
	}
	
	public void setCellStatus(int x, int y, int status){
		maze[x][y].setStatus(status);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private class Cell {
		private int status;
		private Vector2f position;
		private Vector2f previous;

		public Cell(Vector2f position) {
			this.position = position;
			previous = null;
			status = 1;
		}

		private void setStatus(int status) {
			this.status = status;
		}

		private int getStatus() {
			return status;
		}

		private Vector2f getPosition() {
			return position;
		}

		private Vector2f getPrevious() {
			return previous;
		}

		private void setPrevious(Vector2f previous) {
			this.previous = previous;
		}
	}
}
