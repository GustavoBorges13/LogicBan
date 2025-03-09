package entity;

import java.awt.Color;
import java.util.Random;

import main.GamePanel;

public class NPC_OldPlayer extends Entity {

	private int moveInterval = 80; // Intervalo de tempo entre movimentos (em frames)
	private int moveTimer = 0; // Contador de frames para controlar o intervalo
	private boolean isMoving = false; // Controla se o NPC está se movendo
	public String walkType = gp.player.walkType;
	
	public NPC_OldPlayer(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;

		getImage();
		color = Color.magenta;
	}

	public void getImage() {

		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);

	}

	public void setAction() {
		walkType = gp.player.walkType;
		if (walkType.equals("Smooth-Walk")) {

			speed = 1;
			actionLockCounter++;

			if (actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100) + 1;

				if (i <= 25) {
					direction = "up";
				}
				if (i < 25 && i <= 50) {
					direction = "down";
				}
				if (i > 50 && i <= 75) {
					direction = "left";
				}
				if (i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
		} else if (walkType.equals("Step-by-Step")) {
			speed = gp.tileSize;
			moveTimer++;

			// Só decide uma nova direção após o intervalo de tempo
			if (moveTimer >= moveInterval && !isMoving) {
				Random random = new Random();
				int i = random.nextInt(100) + 1;

				if (i <= 25) {
					direction = "up";
				} else if (i > 25 && i <= 50) {
					direction = "down";
				} else if (i > 50 && i <= 75) {
					direction = "left";
				} else if (i > 75 && i <= 100) {
					direction = "right";
				}

				// Verifica colisão antes de mover
				collisionOn = false;
				gp.cChecker.checkTile(this);
				gp.cChecker.checkObject(this, false);
				gp.cChecker.checkPlayer(this);

				if (!collisionOn) {
					isMoving = true; // Inicia o movimento
				}

				moveTimer = 0; // Reseta o contador de tempo
			}
		}
	}

	public void update() {
		walkType = gp.player.walkType;
		if (walkType.equals("Smooth-Walk")) {
			setAction();

			checkCollision();

			
			// Se colisão for falsa, permite o movimento
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

			// Trocador de imagens/sprites
			spriteCounter++;
			if (spriteCounter > 12) {
				spriteNum = (spriteNum == 1) ? 2 : 1;
				spriteCounter = 0;
			}

		} else if (walkType.equals("Step-by-Step")) {
			speed = gp.tileSize;
			setAction(); // Define a ação do NPC

			if (isMoving) {
				// Move um bloco inteiro
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

				isMoving = false; // Finaliza o movimento

				// Atualiza animação
				spriteNum = (spriteNum == 1) ? 2 : 1;
			}
		}
	}
}
