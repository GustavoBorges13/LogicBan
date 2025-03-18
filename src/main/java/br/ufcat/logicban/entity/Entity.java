package br.ufcat.logicban.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import br.ufcat.logicban.tile_interactive.IT_MetalPlate;
import br.ufcat.logicban.ui.GamePanel;
import br.ufcat.logicban.util.UtilityTool;

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
	
	
	public String walkType;
	// WIRES
//    public int wireSetId;       // Identificador do conjunto de fios
//    public boolean isHead = false;      // Indica se é a "cabeça" do fio
//    public boolean isTail = false;      // Indica se é a "cauda" do fio
	public String tipo = "";

	// TYPE
	public int type; // 0 = player, 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 0;
	public final int type_pickaxe = 0;
	
	public IT_MetalPlate lastPlate = null;
	public int doorIndex;
	
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
		gp.cChecker.checkEntity(this, gp.iTile);
		// gp.cChecker.checkEntity(this, gp.wire);
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
			if (solidArea.width == 0 && solidArea.height == 0 && name.equals("Metal Plate")) {
				int x = worldX + solidArea.x + 3;
				int y = worldY + solidArea.y + 3;
				int size = gp.tileSize - 6; // Ajuste do tamanho

				// Desenha o retângulo
				g2.drawRect(x - 25, y - 25, size, size);

				// Desenha um "X" dentro do retângulo
				g2.drawLine(x - 25, y - 25, x + size - 25, y + size - 25); // Linha diagonal \
				g2.drawLine(x + size - 25, y - 25, x - 25, y + size - 25); // Linha diagonal /
			}
		}
	}

	public void detectPlate() {
        IT_MetalPlate currentPlate = null; // Placa atual sob a entidade (se houver)

        // Verifica se a entidade está em cima de uma placa
        for (int i = 0; i < gp.iTile[gp.currentMap].length; i++) {
            if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null
                    && gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
                IT_MetalPlate plate = (IT_MetalPlate) gp.iTile[gp.currentMap][i];

                // Calcula a distância entre a entidade e a placa
                int xDistance = Math.abs(worldX - plate.worldX);
                int yDistance = Math.abs(worldY - plate.worldY);
                int distance = Math.max(xDistance, yDistance);

                // Se a entidade estiver perto da placa
                if (distance < 27) {
                    // Ativa a placa
                    plate.estadoLogico = 1;
                    currentPlate = plate; // Define a placa atual

                    // Se for uma nova placa, toca o som
                    if (plate != lastPlate) {
                        if (!plate.soundPlayed) {
                            gp.playSFX(3);
                            plate.soundPlayed = true;
                        }
                    }
                }
                //System.out.println("X: "+ xDistance +" Y: "+yDistance + " plateX:"+ plate.worldX+ " plateY:"+plate.worldY+ " worldX: "+worldX+ " worldY: "+ worldY);
            }
        }

        // Desativa a placa anterior se a entidade não estiver mais sobre ela
        if (lastPlate != null && lastPlate != currentPlate) {
            lastPlate.estadoLogico = 0;
            lastPlate.soundPlayed = false; // Permite tocar novamente quando voltar
        }

        lastPlate = currentPlate; // Atualiza a última placa
    }
}
