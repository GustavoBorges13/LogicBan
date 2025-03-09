package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

	GamePanel gp;
	public boolean destructible = false;
	public int estadoLogico;
	
	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	
	public void playSFX() {
		
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public void update() {
		
	}
}
