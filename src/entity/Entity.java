package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {
	GamePanel gp;

	// Variáveis
	public int worldX, worldY;
	public int speed;
	public Color color;

	// Texturas
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage image;
	public String name;
	public String direction = "down";

	// Sprites animations
	public int spriteCounter = 0;
	public int spriteNum = 1;

	// Colisão
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Área sólida padrão para todas as entidades
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public boolean collision = false;
	public boolean collisionEndWorld = false;
	public Entity linkedEntity;
	
	// NPC
	public int actionLockCounter = 0;

	// TYPE
	public int type; // 0 = player, 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 0;
	public final int type_pickaxe = 0;

	// Debug
	public static boolean debugModeOn = false;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {
	}

	public void move(String direction) {

	}

	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkPlayer(this);
		gp.cChecker.checkEntity(this, gp.npc);
		//gp.cChecker.checkEntity(this, gp.iTile);
		// boolean contactPlayer = gp.cChecker.checkPlayer(this);

		// gp.cChecker.checkEntity(this, gp.monster);
	}

	public void update() {
		setAction();
		checkCollision();

		// Se colisao false, player pode mover
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
	}

	public BufferedImage setup(String imagePath, int width, int height, boolean isBtn) {
		if (!isBtn) {
			UtilityTool uTool = new UtilityTool();
			BufferedImage image = null;
			try {
				image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
				image = uTool.scaleImage(image, width, height);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		} else {
			UtilityTool uTool = new UtilityTool();
			BufferedImage image = null;
			try {
				image = ImageIO.read(getClass().getResourceAsStream(imagePath));
				image = uTool.scaleImage(image, width, height);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		}
	}

	public BufferedImage setup(String imagePath, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		switch (direction) {
		case "up":
			image = (spriteNum == 1) ? up1 : up2;
			break;
		case "down":
			image = (spriteNum == 1) ? down1 : down2;
			break;
		case "left":
			image = (spriteNum == 1) ? left1 : left2;
			break;
		case "right":
			image = (spriteNum == 1) ? right1 : right2;
			break;
		}
		g2.drawImage(image, worldX, worldY, null);
		
		
		if (debugModeOn) {
			// Debug área de colisão
			g2.setColor(color);
			g2.setStroke(new BasicStroke(2));
			g2.drawRect(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
			if (solidArea.width == 0 && solidArea.height == 0) {
			    int x = worldX + solidArea.x + 3;
			    int y = worldY + solidArea.y + 3;
			    int size = gp.tileSize - 6; // Ajuste do tamanho

			    // Desenha o retângulo
			    g2.drawRect(x, y, size, size);

			    // Desenha um "X" dentro do retângulo
			    g2.drawLine(x, y, x + size, y + size); // Linha diagonal \
			    g2.drawLine(x + size, y, x, y + size); // Linha diagonal /
			}

		}
	}
}
