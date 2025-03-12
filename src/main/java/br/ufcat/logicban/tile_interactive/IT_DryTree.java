package br.ufcat.logicban.tile_interactive;

import br.ufcat.logicban.ui.GamePanel;

public class IT_DryTree extends InteractiveTile{
	
	GamePanel gp;
	
	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/assets/tiles_interactive/drytree", gp.tileSize, gp.tileSize);
		destructible = true;
		
	}
	
}
