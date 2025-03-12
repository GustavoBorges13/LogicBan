package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.ui.GamePanel;

public class OBJ_Chest extends Entity {
	
	public static final String objName = "Chest";

	public OBJ_Chest(GamePanel gp) {
		
		super(gp);

		name = objName;
		down1 = setup("/assets/objects/chest", gp.tileSize, gp.tileSize);
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 8;
		solidArea.width = 45;
		solidArea.height = 38;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		color = Color.yellow;
	}
}
