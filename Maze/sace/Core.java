package a.maze;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Core extends StateBasedGame{

	public static final int MAIN = 1;
	
	private Main main;
	
	public Core(){
		super("WIP");
		main = new Main(MAIN);
	}
	
	public static void main(String[] args) {
		
		try{
			AppGameContainer app = new AppGameContainer(new Core());
			
			app.setDisplayMode(816,624, false);
			app.setAlwaysRender(true);
			app.start();
			
		}catch(SlickException ex){
			ex.printStackTrace();
		}

	}
	
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(main);
	}

}