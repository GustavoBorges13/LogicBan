package object;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
	
	public static final String objName = "Key";
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		
		name = objName;
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		color = Color.red;
	}
}
