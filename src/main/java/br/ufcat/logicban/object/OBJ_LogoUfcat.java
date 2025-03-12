package br.ufcat.logicban.object;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.ui.GamePanel;

public class OBJ_LogoUfcat extends Entity {
	
	public static final String objName = "Logo Ufcat";
	
	public OBJ_LogoUfcat(GamePanel gp) {
		super(gp);
		
		name = objName;
		down1 = setup("/assets/logos/ufcat",(int)(1165/2.5),(int)(455/2.5));
		
	}
}
