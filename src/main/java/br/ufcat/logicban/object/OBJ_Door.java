package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.ui.GamePanel;

public class OBJ_Door extends Entity {
	
	public static final String objName = "Door";
	
	public OBJ_Door(GamePanel gp) {
		
		super(gp);

		name = objName;
		down1 = setup("/assets/objects/door", gp.tileSize, gp.tileSize);
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		color = Color.red;
	}
}
