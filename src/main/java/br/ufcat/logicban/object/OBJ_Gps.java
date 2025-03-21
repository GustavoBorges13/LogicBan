package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.main.GamePanel;

public class OBJ_Gps extends Entity {
	
	public static final String objName = "Gps";
	
	public OBJ_Gps(GamePanel gp) {
		super(gp);
		
		name = objName;
		down1 = setup("/assets/objects/gps", gp.tileSize, gp.tileSize);
		color = Color.red;
	}
}
