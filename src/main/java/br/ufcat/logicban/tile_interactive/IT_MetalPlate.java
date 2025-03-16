package br.ufcat.logicban.tile_interactive;

import java.awt.Color;

import br.ufcat.logicban.ui.GamePanel;

public class IT_MetalPlate extends InteractiveTile{


	GamePanel gp;
	public static final String itName = "Metal Plate";
	public boolean soundPlayed = false;
	
	public IT_MetalPlate(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		name = itName;
		down1 = setup("/assets/tiles_interactive/metalplate", gp.tileSize, gp.tileSize);
		//destructible = true;
		
		solidArea.x = 25;
		solidArea.y = 25;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		color = Color.red;
		estadoLogico = 0;
		
	}

}