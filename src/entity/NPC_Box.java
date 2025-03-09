package entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

public class NPC_Box extends Entity {

	public static final String npcName = "Box";

	public String walkType = gp.player.walkType;
	public ArrayList<InteractiveTile> plateList = new ArrayList<InteractiveTile>();
	public ArrayList<Entity> boxList = new ArrayList<Entity>();
	
	public NPC_Box(GamePanel gp) {
		super(gp);

		name = npcName;
		direction = "down";
		speed = 3;

		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 9;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		color = Color.ORANGE;
		
		getImage();
	}

	public void getImage() {

		up1 = setup("/npc/box01", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/box01", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/box01", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/box01", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/box01", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/box01", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/box01", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/box01", gp.tileSize, gp.tileSize);

	}

	public void setAction() {
	}

	public void update() {
	}

	public void move(String d) {

		this.direction = d;

		// CHECK TILE COLLISION
		checkCollision();
		
		if (collisionOn == false) {
			switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		detectPlate();
	}

	public void detectPlate() {



		// Cria uma lista de placas
		for (int i = 0; i < gp.iTile[1].length; i++) {

			if (gp.iTile[gp.currentMap][i] != null && 
					gp.iTile[gp.currentMap][i].name != null && 
					gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
				plateList.add(gp.iTile[gp.currentMap][i]);
			}
		}

		// Cria uma lista de caixas
		for (int i = 0; i < gp.npc[1].length; i++) {

			if (gp.npc[gp.currentMap][i] != null && 
					gp.npc[gp.currentMap][i].name != null && 
					gp.npc[gp.currentMap][i].name.equals(NPC_Box.npcName)) {
				boxList.add(gp.npc[gp.currentMap][i]);
			}
		}

		int count = 0;

		// Scanea sempre as placas (detectar caixas)
		for (int i = 0; i < plateList.size(); i++) {

			int xDistance = Math.abs(worldX - plateList.get(i).worldX);
			int yDistance = Math.abs(worldY - plateList.get(i).worldY);
			int distance = Math.max(xDistance, yDistance);
			
			// altera 15 se quiser mexer na hitbox das placas
			if (distance < 15) {

				if (linkedEntity == null) {
					linkedEntity = plateList.get(i);
					plateList.get(i).estadoLogico = 1;
					
					gp.playSFX(3);
				}
			} else {
				if (linkedEntity == plateList.get(i)) {
					linkedEntity = null;
					plateList.get(i).estadoLogico = 0;
				}
			}
		}

		// Conta quantas caixas estao em cima de uma placa
		for (int i = 0; i < boxList.size(); i++) {

			// count the box on the plate
			if (boxList.get(i).linkedEntity != null) {
				count++;
			}
		}

		// Se todas as caixas estao sob a placa, a porta de ferro (iron door) abre
		if (count == boxList.size()) {
			for (int i = 0; i < gp.obj[1].length; i++) {

				if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
					gp.ui.showMessage("You opend the door!");
					gp.obj[gp.currentMap][i] = null;
					gp.playSFX(7);
				}
			}
		}
	}
}
