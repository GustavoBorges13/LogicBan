package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Logo extends Entity {
	
	public static final String objName = "Logo";
	
	public OBJ_Logo(GamePanel gp) {
		super(gp);
		
		name = objName;
		down1 = setup("/logos/Logo",(int)(1165/2.5),(int)(455/2.5));
		
	}
}
