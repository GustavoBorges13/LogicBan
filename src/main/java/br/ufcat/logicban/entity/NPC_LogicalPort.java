package br.ufcat.logicban.entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import br.ufcat.logicban.object.OBJ_Door_Iron;
import br.ufcat.logicban.tile_interactive.IT_MetalPlate;
import br.ufcat.logicban.tile_interactive.InteractiveTile;
import br.ufcat.logicban.ui.GamePanel;

public class NPC_LogicalPort extends Entity {

	public static final String npcName = "Porta Logica";

	public String walkType = gp.player.walkType;
	public ArrayList<InteractiveTile> plateList = new ArrayList<InteractiveTile>();
	public ArrayList<Entity> boxList = new ArrayList<Entity>();
	
	public NPC_LogicalPort(GamePanel gp, String tipo, String direction) {
		super(gp);
		this.tipo = tipo;
		this.direction = direction;
		name = npcName;
		direction = "down";
		speed = 3;
		
		solidArea = new Rectangle();
		solidArea.x = 6;
		solidArea.y = 8;
		solidArea.width = 36;
		solidArea.height = 38;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		color = Color.black;
		
		getImage(tipo);
	}

	public void getImage(String tipo) {

		switch (direction) {
		case "left":
			down1 = setup("/assets/wires_connections/" + tipo + "_left", gp.tileSize, gp.tileSize);
			break;
		case "right":
			down1 = setup("/assets/wires_connections/" + tipo + "_right", gp.tileSize, gp.tileSize);
			break;
		case "up":
			down1 = setup("/assets/wires_connections/" + tipo + "_up", gp.tileSize, gp.tileSize);
			break;
		case "down":
			down1 = setup("/assets/wires_connections/" + tipo + "_down", gp.tileSize, gp.tileSize);
			break;
		}
	}

	public void setAction() {
	}

	public void update() {
	}

	public void move(String d) {

		this.direction = d;

		// CHECK TILE COLLISION
		checkCollision();
		
//		if (collisionOn == false) {
//			switch (direction) {
//			case "up":
//				worldY -= speed;
//				break;
//			case "down":
//				worldY += speed;
//				break;
//			case "left":
//				worldX -= speed;
//				break;
//			case "right":
//				worldX += speed;
//				break;
//			}
//		}
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
					gp.npc[gp.currentMap][i].name.equals(NPC_LogicalPort.npcName)) {
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
					gp.ui.showMessage("Voce abriu a porta!");
					gp.obj[gp.currentMap][i] = null;
					gp.playSFX(7);
				}
			}
		}
	}
}
