package br.ufcat.logicban.tile_interactive;

import java.awt.Color;
import java.util.ArrayList;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.ui.GamePanel;

public class IT_RedWire extends Wire {
	GamePanel gp;
	public static final String itName = "Red";
	public String direction = "horizontal_down";

	public ArrayList<InteractiveTile> plateList = new ArrayList<InteractiveTile>();
	public ArrayList<Entity> wireList = new ArrayList<Entity>();	
	
	public IT_RedWire(GamePanel gp, int col, int row, String direction, int wireSetId) {
		super(gp, col, row);
		this.gp = gp;
		this.direction = direction;
		this.wireSetId = wireSetId;

		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;

		name = itName;

		carregarTextura();

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		color = Color.red;
	}

	@Override
	public void update() {
		detectPlateConnection();
	}

	public void carregarTextura() {
		switch (direction) {
		case "horizontal_up":
			down1 = setup("/assets/wires/" + itName + "_horizontal_up", gp.tileSize, gp.tileSize);
			break;
		case "horizontal_down":
			down1 = setup("/assets/wires/" + itName + "_horizontal_down", gp.tileSize, gp.tileSize);
			break;
		case "vertical_left":
			down1 = setup("/assets/wires/" + itName + "_vertical_left", gp.tileSize, gp.tileSize);
			break;
		case "vertical_right":
			down1 = setup("/assets/wires/" + itName + "_vertical_right", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_up1":
			down1 = setup("/assets/wires/" + itName + "_curve_left_up1", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_up2":
			down1 = setup("/assets/wires/" + itName + "_curve_left_up2", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_up1":
			down1 = setup("/assets/wires/" + itName + "_curve_right_up1", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_up2":
			down1 = setup("/assets/wires/" + itName + "_curve_right_up2", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_down1":
			down1 = setup("/assets/wires/" + itName + "_curve_left_down1", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_down2":
			down1 = setup("/assets/wires/" + itName + "_curve_left_down2", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_down1":
			down1 = setup("/assets/wires/" + itName + "_curve_right_down1", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_down2":
			down1 = setup("/assets/wires/" + itName + "_curve_right_down2", gp.tileSize, gp.tileSize);
			break;
		}
	}

	public void detectPlateConnection() {
		// Detecta placas de metal
		for (int i = 0; i < gp.iTile[1].length; i++) {
			if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null
					&& gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {

				InteractiveTile plate = gp.iTile[gp.currentMap][i];

				int xDistance = Math.abs(worldX - plate.worldX);
				int yDistance = Math.abs(worldY - plate.worldY);
				int distance = Math.max(xDistance, yDistance);
				//System.out.println("isHEAD: "+isHead+" isTAIL: "+isTail);
				if (distance < 50) { // Ajuste este valor conforme necessário

					if (isHead) {
						// Lógica para quando a cabeça está conectada à placa
						//System.out.println("Cabeça do fio " + wireSetId + " conectada à placa!");
						linkedEntity = plate;
						plate.placaConectada = true;
						//System.out.println(gp.iTile[gp.currentMap][i].estadoLogico);
						//gp.playSFX(3);
					} else if (isTail) {
						// Lógica para quando a cauda está conectada à placa
						System.out.println("Cauda do fio " + wireSetId + " conectada à placa!");
						linkedEntity = plate;
						plate.placaConectada = true;
						//gp.playSFX(3);
					}
				} else {
					if (isHead && linkedEntity == plate) {
						plate.placaConectada = false;
						linkedEntity = null;
					} else if (isTail && linkedEntity == plate) {
						plate.placaConectada = false;
						linkedEntity = null;
					}

				}
			}
		}
	}
}