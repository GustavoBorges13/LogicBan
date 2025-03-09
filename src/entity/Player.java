package entity;

import java.awt.Color;
import java.awt.Rectangle;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	KeyHandler keyH;
	public int hasKey;
	int standCounter;
	public String walkType = "Smooth-Walk";
	public int speedAux;
	
	//flags
	public boolean allRocksOn = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);

		this.keyH = keyH;

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 12;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		color = Color.magenta;
		
		//setDefaultValues();
		getPlayerImage();
		
		
	}

	public void setDefaultValues() {
		
		standCounter = 0;
		hasKey = 0;
		worldX = gp.eHandler.newWorldX;
		worldY = gp.eHandler.newWorldY;
		speed = 3; // movimento de 48 pixels por vez
		speedAux = speed;
		direction = "down";
		color = Color.magenta;
	}

	public void getPlayerImage() {

		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);

	}

	public void update() {
	
		if (walkType.equals("Smooth-Walk")) {
			speed = speedAux;
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
		} else if (walkType.equals("Step-by-Step")) {
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
				gp.cChecker.checkTile(this);

				// CHECK OBJECT COLLISION
				int objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);

				// CHECK NPC COLLISION
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);

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

				// Atualiza animação
				spriteNum = (spriteNum == 1) ? 2 : 1;
			}
			keyH.resetJustPressed();
		}
	}

	public void pickUpObject(int i) {

		// Se o index for 999 significa que o player nao tocou em nenhum objeto
		// caso contrario exclui o item no mapa (pegar o item)
		if (i != 999) {

			// gp.obj[i] = null; // excluir da tela

			String objectName = gp.obj[gp.currentMap][i].name;

			switch (objectName) {
			case "Key":
				gp.playSFX(1);
				hasKey++;
				gp.obj[gp.currentMap][i] = null;
				System.out.println("Chave: " + hasKey); // debug em terminal
				gp.ui.showMessage("Voce pegou uma chave!"); // debug em UI
				break;
			case "Door":
				if (hasKey > 0) {
					gp.playSFX(3);
					gp.obj[gp.currentMap][i] = null;
					hasKey--;
					System.out.println("Porta: " + hasKey); // debug em terminal
					gp.ui.showMessage("Você abriu a porta!"); // debug em UI
				} else {
					gp.ui.showMessage("Voce precisa de uma chave!");
				}
				break;
			case "Door Iron":
				if (allRocksOn == true) {
					gp.ui.showMessage("Você abriu a porta!"); // debug em UI
				} else {
					gp.ui.showMessage("Voce precisa de três caixas nas placas");
				}
				break;
			case "Boots":
				gp.playSFX(2);
				if (walkType.equals("Smooth-Walk")) {
					speed += 2;
					speedAux = speed;
					// gp.eHandler.teleport(1, 3,8);
				} else {
					speed = gp.tileSize;
					speedAux = 2 + 3;
				}
				gp.obj[gp.currentMap][i] = null;
				gp.ui.showMessage("Speed UP!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSFX(4);
				break;
			}
		}
	}

	public void interactNPC(int i) {

		if (i != 999) {
			//System.out.println("Voce esta colidindo com NPC");

			// MOVER
			gp.npc[gp.currentMap][i].move(direction);
		}
	}
}
