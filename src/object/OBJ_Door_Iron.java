package object;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity {
	public static final String objName = "Door Iron";

	public OBJ_Door_Iron(GamePanel gp) {

		super(gp);

		name = objName;
		down1 = setup("/objects/door_iron", gp.tileSize, gp.tileSize);
		collision = true;

		solidArea.x = 0;
		solidArea.y = 16 - 12;
		solidArea.width = 48;
		solidArea.height = 38 + 4;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		color = Color.red;
	}
}
