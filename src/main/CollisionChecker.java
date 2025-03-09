package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {

		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1 = 0, tileNum2 = 0;

		// predicao de movimento
		try {
			switch (entity.direction) {
			case "up":
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow]; // verifica ponto esquerdo superior da area
																				// colisao
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow]; // verifica ponto direito superior da area
																				// colisao
				gp.eHandler.outTile = false;
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow]; // verifica ponto esquerdo superior da area
																				// colisao
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow]; // verifica ponto direito superior da area
																					// colisao
				gp.eHandler.outTile = false;
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow]; // verifica ponto esquerdo superior da area
																				// colisao
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow]; // verifica ponto direito superior da area
																				// colisao
				gp.eHandler.outTile = false;
				break;
			case "right":
				entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow]; // verifica ponto esquerdo superior da area
																				// colisao
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow]; // verifica ponto direito superior da area
																					// colisao
				gp.eHandler.outTile = false;
				break;
			}
		} catch (Exception e) {
			//entity.collisionOn = true;
			//gp.eHandler.outTile = true;
			//System.out.println("Tentou atravesar o fim do mundo");
		}
		if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
			entity.collisionOn = true;
			//System.out.println("Colidiu com paredes ou arvores, etc");
		}
		
	}
	
	public void checkEnd(Entity entity) {
		int deslocation = 0;
		
		if(entity.worldX - entity.speed < 0) {
			entity.worldX = deslocation;
			//entity.collisionOn = true;
			//entity.collisionEndWorld = true;
			//System.out.println("Colidiu com fim do mundo");
		}else if(entity.worldY - entity.speed < 0) {
			entity.worldY = deslocation;
			//entity.collisionOn = true;
			//entity.collisionEndWorld = true;
			//System.out.println("Colidiu com fim do mundo");
		}else if(entity.worldX + entity.speed > gp.screenWidth-gp.tileSize) {
			entity.worldX = entity.worldX;
			//entity.collisionOn = true;
			//entity.collisionEndWorld = true;
			//System.out.println("Colidiu com fim do mundo");
		}else if(entity.worldY + entity.speed > gp.screenHeight-gp.tileSize) {
			entity.worldY = entity.worldY;
			//entity.collisionOn = true;
			//entity.collisionEndWorld = true;
			//System.out.println("Colidiu com fim do mundo");
		}
	}

	// OBJECT COLISION
	public int checkObject(Entity entity, boolean player) {

		int index = 999;

		for (int i = 0; i < gp.obj[1].length; i++) {

			if (gp.obj[gp.currentMap][i] != null) {

				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object's solid area position
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

				switch (entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
					// System.out.println("UP collision!");
					if (gp.obj[gp.currentMap][i].collision == true) {
						entity.collisionOn = true;
						//System.out.println("Colisao com objeto!");
					}
					if (player == true) {
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	// NPC OR MONSTER colision
	public int checkEntity(Entity entity, Entity[][] target) {
		int index = 999;

		for (int i = 0; i < target[1].length; i++) {

			if (target[gp.currentMap][i] != null) {

				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object's solid area position
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

				switch (entity.direction) {
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
					if(target[gp.currentMap][i] != entity) {
						//System.out.println("Voce colidiu com ENTIDADE!");
						entity.collisionOn = true;
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		// Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		// Get the object's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		switch (entity.direction) {
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}
		
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			//System.out.println("Entidade colidiu com player");
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY; 
		
		return contactPlayer;
	}
}
