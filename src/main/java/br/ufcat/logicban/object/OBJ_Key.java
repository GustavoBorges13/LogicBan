package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.main.GamePanel;

public class OBJ_Key extends Entity {
	
	public static final String objName = "Key";
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		
		name = objName;
		down1 = setup("/assets/objects/key", gp.tileSize, gp.tileSize);
		color = Color.red;
	}
}
