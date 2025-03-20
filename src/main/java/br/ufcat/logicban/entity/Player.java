package br.ufcat.logicban.entity;

import java.awt.Color;
import java.awt.Rectangle;

import br.ufcat.logicban.object.OBJ_Boots;
import br.ufcat.logicban.object.OBJ_Chest;
import br.ufcat.logicban.object.OBJ_Door;
import br.ufcat.logicban.object.OBJ_Door_Iron;
import br.ufcat.logicban.object.OBJ_Key;
import br.ufcat.logicban.tile_interactive.IT_MetalPlate;
import br.ufcat.logicban.tile_interactive.InteractiveTile;
import br.ufcat.logicban.ui.GamePanel;
import br.ufcat.logicban.ui.KeyHandler;

public class Player extends Entity {
	KeyHandler keyH;
	public int hasKey;
	int standCounter;
	public final String smoothWalk = "Smooth-Walk";
	public final String stepWalk = "Step-by-Step";
	public int speedAux = 3;
	public int speedMultiplicator = 0;

	// flags
	public boolean allRocksOn = false;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);

		this.keyH = keyH;
		walkType = stepWalk;

		color = Color.magenta;

		// setDefaultValues();
		getPlayerImage();

	}

	public void setDefaultValues() {
		standCounter = 0;
		hasKey = 0;
		worldX = gp.eHandler.newWorldX;
		worldY = gp.eHandler.newWorldY;
		speed = gp.eHandler.oldSpeed; // movimento de 48 pixels por vez
		direction = "down";
		color = Color.magenta;
	}

	public void getPlayerImage() {
		String name = "robot";
		up1 = setup("/assets/player/" + name + "_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/assets/player/" + name + "_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/assets/player/" + name + "_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/assets/player/" + name + "_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/assets/player/" + name + "_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/assets/player/" + name + "_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/assets/player/" + name + "_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/assets/player/" + name + "_right_2", gp.tileSize, gp.tileSize);

	}

	public void update() {
		if (walkType.equals(smoothWalk)) {
			solidArea.x = 8;
			solidArea.y = 12;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			solidArea.width = 32;
			solidArea.height = 32;

			if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
					|| keyH.rightPressed == true) {
				if (keyH.upPressed == true) {
					direction = "up";
				} else if (keyH.downPressed == true) {
					direction = "down";
				} else if (keyH.leftPressed == true) {
					direction = "left";
				} else if (keyH.rightPressed == true) {
					direction = "right";
				}

				// CHECK TILE COLLISION
				collisionOn = false;
				collisionEndWorld = false;
				gp.cChecker.checkTile(this);

				// CHECK END WORLD COLLISION
				gp.cChecker.checkEnd(this);

				// CHECK OBJECT COLLISION
				int objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);

				// CHECK NPC COLLISION
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);

				int iTileIndex = gp.cChecker.checkObject(this, true);
				interactInteractiveTile(iTileIndex);

				// CHECK INTERACTIVE TILE COLISION
				gp.cChecker.checkEntity(this, gp.iTile);

				// CHECK EVENT
				gp.eHandler.checkEvent();

				// IF COLLISION IS FALSE, PLAYER CAN MOVE
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

				// trocador de imagens/ sprrites
				spriteCounter++;
				if (spriteCounter > 12) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			} else {
				standCounter++;
				if (standCounter == 20) {
					spriteNum = 1;
					standCounter = 0;
				}
			}
		} else if (walkType.equals(stepWalk)) {

			solidArea.x = 0;
			solidArea.y = 0;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			solidArea.width = 48;
			solidArea.height = 48;

			speed = gp.tileSize;
			// Verifica se alguma tecla foi pressionada neste frame
			if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
					|| keyH.rightPressed == true) {

				if (keyH.upPressed == true) {
					direction = "up";
				} else if (keyH.downPressed == true) {
					direction = "down";
				} else if (keyH.leftPressed == true) {
					direction = "left";
				} else if (keyH.rightPressed == true) {
					direction = "right";
				}

				// CHECK TILE COLLISION
				collisionOn = false;
				collisionEndWorld = false;
				gp.cChecker.checkEnd(this);
				gp.cChecker.checkTile(this);

				// CHECK OBJECT COLLISION
				int objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);

				// CHECK NPC COLLISION
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);
				
				// CHECK INTERACTIVE TILE COLISION
				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
				interactInteractiveTile(iTileIndex);

				// CHECK EVENT
				gp.eHandler.checkEvent();

				// IF COLLISION IS FALSE, PLAYER CAN MOVE
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

				// Atualiza animação
				spriteNum = (spriteNum == 1) ? 2 : 1;
			}
			keyH.resetJustPressed();
		}
		detectPlate(); // Detecta se o player está em cima de uma placa
	}

	public void pickUpObject(int i) {

		// Se o index for 999 significa que o player nao tocou em nenhum objeto
		// caso contrario exclui o item no mapa (pegar o item)
		if (i != 999) {

			// gp.obj[i] = null; // excluir da tela

			String objectName = gp.obj[gp.currentMap][i].name;
			//System.out.println(objectName);
			
			switch (objectName) {
			case OBJ_Key.objName:
				gp.playSFX(1);
				hasKey++;
				gp.obj[gp.currentMap][i] = null;
				System.out.println("Chave: " + hasKey); // debug em terminal
				gp.ui.showMessage("Voce pegou uma chave!"); // debug em UI
				break;
			case OBJ_Door.objName:

				OBJ_Door targetDoor = (OBJ_Door) gp.obj[gp.currentMap][i];
				//System.out.println(targetDoor.messageShown);
				if (targetDoor.messageShown) {
					// nada a fazer
				} else {
					gp.ui.showMessage("Voce precisa acerta a combinação logica!");
				}
//				if (hasKey > 0) {
//					gp.playSFX(3);
//					gp.obj[gp.currentMap][i] = null;
//					hasKey--;
//					System.out.println("Porta: " + hasKey); // debug em terminal
//					gp.ui.showMessage("Você abriu a porta!"); // debug em UI
//				} else {
////					gp.ui.showMessage("Voce precisa acerta a combinação logica!");
//				}
				break;

			case OBJ_Door_Iron.objName:
				OBJ_Door_Iron targetIronDoor = (OBJ_Door_Iron) gp.obj[gp.currentMap][i];
				//System.out.println(targetIronDoor.messageShown);
				if (targetIronDoor.messageShown) {
					// nada a fazer
				} else {
					gp.ui.showMessage("Voce precisa acerta a combinação logica!");
				}
//				if (hasKey > 0) {
//					gp.playSFX(3);
//					gp.obj[gp.currentMap][i] = null;
//					hasKey--;
//					System.out.println("Porta: " + hasKey); // debug em terminal
//					gp.ui.showMessage("Você abriu a porta!"); // debug em UI
//				} else {
//					// if()
////					gp.ui.showMessage("Voce precisa acerta a combinação logica!");
//				}
				break;
//			case OBJ_Door_Iron.objName:
//				if (allRocksOn == true) {
//					gp.ui.showMessage("Você abriu a porta!"); // debug em UI
//				} else {
//					gp.ui.showMessage("Voce precisa acerta a combinação logica!");
//				}
//				break;
			case OBJ_Boots.objName:

				gp.playSFX(2);
				if (walkType.equals(smoothWalk)) {
					speedMultiplicator += 1;
					speed += 2;
					// gp.eHandler.teleport(1, 3,8);
				} else {
					speed = gp.tileSize;
					speedAux = 2 + 3;
				}
				gp.obj[gp.currentMap][i] = null;
				gp.ui.showMessage("Speed UP!");
				break;
			case OBJ_Chest.objName:
//				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSFX(4);
				gp.ui.levelFinished = true;
				gp.gameState = gp.nextPhaseState;
				break;
			}
		}
	}

	public void interactNPC(int i) {
		if (walkType.equals(smoothWalk)) {
			if (i != 999) {
				// Obtém a entidade que o player está interagindo
				gp.npc[gp.currentMap][i].move(direction);
			}
		} else if (walkType.equals(stepWalk)) {
			if (i != 999) {
				Entity targetNPC = gp.npc[gp.currentMap][i];

				// Verifica se o NPC é outra caixa
				if (targetNPC instanceof NPC_Box) {
					NPC_Box box = (NPC_Box) targetNPC;

					// Salvar a posição original do player e da caixa
					int boxOldWorldX = box.worldX;
					int boxOldWorldY = box.worldY;

					// Tenta mover a outra caixa na mesma direção
					box.move(direction);

					// Se a outra caixa não puder ser movida (colisão)m, marca a colisão
					if (box.collisionOn) {
						box.worldX = boxOldWorldX;
						box.worldY = boxOldWorldY;
						collisionOn = true;
					} else {
						// Troca a posição do player com a posição original da caixa
						worldX = boxOldWorldX;
						worldY = boxOldWorldY;
					}

				} else {
					// Lógica para interagir com outros tipos de NPCs
					collisionOn = true; // Impede o movimento se for outro tipo de NPC
				}
			}
		}

	}

	public void interactInteractiveTile(int i) {

		if (i != 999) {
			//System.out.println("colisao com iteratictive tile");
		}
	}
}
