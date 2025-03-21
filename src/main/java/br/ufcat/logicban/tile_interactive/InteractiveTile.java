package br.ufcat.logicban.tile_interactive;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.main.GamePanel;

public class InteractiveTile extends Entity {

	GamePanel gp;
	public boolean destructible = false;
	public int estadoLogico;
	public boolean placaConectada;
	
	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	
	public InteractiveTile(GamePanel gp) {
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