package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.ui.GamePanel;

public class OBJ_Flag extends Entity {
	
	public static final String objName = "Flag";
	public static final String RED = "red";
	public static final String YELLOW = "yellow";
	
	public OBJ_Flag(GamePanel gp, String cor) {
		super(gp);
		
		name = objName;
		down1 = setup("/assets/objects/flag_"+cor, gp.tileSize, gp.tileSize);
		color = Color.yellow;
	}
}
