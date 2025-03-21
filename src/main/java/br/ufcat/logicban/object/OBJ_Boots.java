package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.main.GamePanel;

public class OBJ_Boots extends Entity {
	
	public static final String objName = "Boots";
	
	public OBJ_Boots(GamePanel gp) {
		
		super(gp);

		name = objName;
		down1 = setup("/assets/objects/boots", gp.tileSize, gp.tileSize);
		
		color = Color.red;
	}
}
