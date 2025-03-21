package br.ufcat.logicban.main;

import br.ufcat.logicban.entity.NPC_Box;
import br.ufcat.logicban.entity.NPC_OldMan;
import br.ufcat.logicban.entity.NPC_OldPlayer;
import br.ufcat.logicban.object.OBJ_Boots;
import br.ufcat.logicban.object.OBJ_Chest;
import br.ufcat.logicban.object.OBJ_Door;
import br.ufcat.logicban.object.OBJ_Door_Iron;
import br.ufcat.logicban.object.OBJ_Flag;
import br.ufcat.logicban.object.OBJ_Key;
import br.ufcat.logicban.tile_interactive.IT_DryTree;
import br.ufcat.logicban.tile_interactive.IT_LogicalPort;
import br.ufcat.logicban.tile_interactive.IT_MetalPlate;
import br.ufcat.logicban.tile_interactive.IT_RedWire;
import br.ufcat.logicban.tile_interactive.IT_Wire;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {

		int mapNum = 0;
		int i = 0;
		// FASE 1
		gp.obj[mapNum][i] = new OBJ_Door(gp, 0, IT_LogicalPort.itName); // Porta de ferro controlada pela porta logica
																		// ID 0
		gp.obj[mapNum][i].worldX = 15 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Flag(gp, OBJ_Flag.RED); // Porta de ferro controlada pela porta logica ID 0
		gp.obj[mapNum][i].worldX = 16 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
		i++;
//		gp.obj[mapNum][i] = new OBJ_Chest(gp); // Porta de ferro 0 controlada pela porta AND com ID 0
//		gp.obj[mapNum][i].worldX = 16 * gp.tileSize;
//		gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
//		i++;

		// FASE 2
		mapNum = 1;
		gp.obj[mapNum][i] = new OBJ_Boots(gp);
		gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 4 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp, 3, IT_MetalPlate.itName); // Porta de ferro 1 controlada pela placa ID
																			// 3
		gp.obj[mapNum][i].worldX = 19 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 11 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Flag(gp, OBJ_Flag.YELLOW);
		gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 12 * gp.tileSize;
		i++;

		// FASE 3
		mapNum = 2;
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp, 1, IT_LogicalPort.itName); // Porta de ferro 2 controlada pela porta
																				// logica ID 1
		gp.obj[mapNum][i].worldX = 14 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp, 2, IT_LogicalPort.itName); // Porta de ferro 3 controlada pela placa
																				// ID 2
		gp.obj[mapNum][i].worldX = 20 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Flag(gp, OBJ_Flag.YELLOW);
		gp.obj[mapNum][i].worldX = 22 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;

		// FASE 4
		mapNum = 3;
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp, 8, IT_LogicalPort.itName); // Porta de ferro 3 controlada pela placa
		// ID 3
		gp.obj[mapNum][i].worldX = 18 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 6 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Flag(gp, OBJ_Flag.YELLOW);
		gp.obj[mapNum][i].worldX = 20 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 6 * gp.tileSize;
		i++;

		// FASE 5
		mapNum = 4;
		gp.obj[mapNum][i] = new OBJ_Flag(gp, OBJ_Flag.YELLOW);
		gp.obj[mapNum][i].worldX = 25 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;

	}

	public void setNPC() {

		int mapNum = 0;
		int i = 0;

		// FASE 1
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 11 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 6 * gp.tileSize;
		i++;
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 13 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 8 * gp.tileSize;
		i++;

		// FASE 2
		mapNum = 1;
//		gp.npc[mapNum][i] = new NPC_OldMan(gp);
//		gp.npc[mapNum][i].worldX = 14 * gp.tileSize;
//		gp.npc[mapNum][i].worldY = 11 * gp.tileSize;
//		i++;
//		gp.npc[mapNum][i] = new NPC_OldPlayer(gp);
//		gp.npc[mapNum][i].worldX = 18 * gp.tileSize;
//		gp.npc[mapNum][i].worldY = 5 * gp.tileSize;
//		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 11 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 9 * gp.tileSize;
		i++;

		// FASE 3
		mapNum = 2;
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 8 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 10 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 7 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = 4 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 10 * gp.tileSize;
		i++;

		// FASE 4
		mapNum = 3;
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 8 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 9 * gp.tileSize;
		i++;
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 10 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 9 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 12 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 9 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 14 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 9 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 16 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 9 * gp.tileSize;
		i++;

		// FASE 5
		mapNum = 4;

	}

	public void setInteractiveTile() {

		int mapNum = 0;
		int i = 0;

		// FASE 1
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 12, 5);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 11, 7);
		i++;

		IT_LogicalPort portaLogica = new IT_LogicalPort(gp, 15, 5, IT_LogicalPort.AND, "right", IT_LogicalPort.WHITE,
				0); // placa AND id = 0
		portaLogica.addPlateIndex(0);
		portaLogica.addPlateIndex(1);
		gp.iTile[mapNum][i] = portaLogica;
		i++;

		// FASE 2
		// gp.iTile[mapNum][i] = new IT_DryTree(gp, 9, 10);i++;
		mapNum = 1;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 12, 9);
		i++;

		// FASE 3
		mapNum = 2;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 9, 5);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 5);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 16, 9);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 18, 9);
		i++;

		portaLogica = new IT_LogicalPort(gp, 14, 5, IT_LogicalPort.OR, "down", IT_LogicalPort.WHITE, 1); // placa AND id
																											// // = 1
		portaLogica.addPlateIndex(0);
		portaLogica.addPlateIndex(1);
		gp.iTile[mapNum][i] = portaLogica;
		i++;
		portaLogica = new IT_LogicalPort(gp, 20, 9, IT_LogicalPort.AND, "up", IT_LogicalPort.WHITE, 2); // placa AND id
																										// // = 2
		portaLogica.addPlateIndex(2);
		portaLogica.addPlateIndex(3);
		gp.iTile[mapNum][i] = portaLogica;
		i++;

		// FASE 4
		mapNum = 3;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 10); // id 4
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 10, 10);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 12, 10);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 14, 10);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 16, 10);
		i++;

		portaLogica = new IT_LogicalPort(gp, 10, 11, IT_LogicalPort.NOT, "down", IT_LogicalPort.GRAY, 3);
		portaLogica.addPlateIndex(1);
		gp.iTile[mapNum][i] = portaLogica;
		i++;

		portaLogica = new IT_LogicalPort(gp, 14, 11, IT_LogicalPort.NOT, "down", IT_LogicalPort.GRAY, 4);
		portaLogica.addPlateIndex(3);
		gp.iTile[mapNum][i] = portaLogica;
		i++;

		portaLogica = new IT_LogicalPort(gp, 11, 12, IT_LogicalPort.OR, "right", IT_LogicalPort.GRAY, 5);
		gp.iTile[mapNum][i] = portaLogica;
		portaLogica.addPlateIndex(0);
		portaLogica.inputPortIDs.add(3);
		i++;

		portaLogica = new IT_LogicalPort(gp, 17, 13, IT_LogicalPort.NOT, "right", IT_LogicalPort.GRAY, 6);
		portaLogica.inputPortIDs.add(5);
		gp.iTile[mapNum][i] = portaLogica;
		i++;

		portaLogica = new IT_LogicalPort(gp, 15, 12, IT_LogicalPort.AND, "right", IT_LogicalPort.GRAY, 7);
		gp.iTile[mapNum][i] = portaLogica;
		portaLogica.addPlateIndex(2);
		portaLogica.inputPortIDs.add(4);
		i++;

		portaLogica = new IT_LogicalPort(gp, 18, 10, IT_LogicalPort.AND, "up", IT_LogicalPort.GRAY, 8);
		portaLogica.inputPortIDs.add(6);
		portaLogica.inputPortIDs.add(7);
		gp.iTile[mapNum][i] = portaLogica;
		i++;

		// FASE 5
		mapNum = 4;

		
	}

	public void setWires() {
		int mapNum = 0;
		int i = 0;

		// FASE 1
		// entradas
		String cor = IT_Wire.BLACK;
		// Primeiro conjunto de fios (rosa)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 5, "curve_left_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 4, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 3, "curve_right_up2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 3, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 3, "curve_left_up2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 5, "vertical_right", cor);
		i++;

		cor = IT_Wire.BLACK;
		// Primeiro conjunto de fios (vermelho)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 5, "curve_left_up2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 7, "curve_right_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 7, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 7, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 7, "horizontal_down", cor);
		i++;

		// saida
		// Fios da saida da porta AND
		cor = IT_Wire.BLACK;
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 5, "curve_right_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 6, "curve_left_down2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 17, 6, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 6, "curve_right_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 7, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 8, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 9, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 10, "curve_right_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 17, 10, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 10, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 10, "curve_left_down1", cor);
		i++;

		// FASE 2
		cor = IT_Wire.RED;
		mapNum = 1;
		// Primeiro conjunto de fios (rosa)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 9, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 9, "curve_right_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 10, "curve_left_down3", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 17, 10, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 10, "horizontal_up", cor);
		i++;

		// FASE 3
		cor = IT_Wire.BLACK;
		mapNum = 2;
		// Primeiro conjunto de fios (rosa)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 5, "horizontal_down", cor);
		i++;
		cor = IT_Wire.BLACK;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 5, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 5, "horizontal_up", cor);
		i++;
		cor = IT_Wire.WHITE;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 6, "vertical_right", cor);
		i++;

		cor = IT_Wire.BLACK;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 10, "curve_left_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 17, 10, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 10, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 19, 10, "horizontal_down", cor);
		i++;
		cor = IT_Wire.WHITE;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 20, 10, "curve_right_down1", cor);
		i++;

		cor = IT_Wire.BLACK;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 10, "curve_left_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 19, 10, "horizontal_down", cor);
		i++;
		cor = IT_Wire.WHITE;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 20, 10, "curve_right_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 20, 8, "vertical_left", cor);
		i++;

		// FASE 4
		cor = IT_Wire.BLUE;
		mapNum = 3;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 11, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 12, "curve_left_down2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 12, "horizontal_up", cor);
		i++;
		cor = IT_Wire.BLACK;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 12, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 12, "curve_right_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 13, "curve_left_down2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 13, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 13, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 13, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 17, 13, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 13, "curve_right_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 12, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 11, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 10, "vertical_right", cor);
		i++;
		cor = IT_Wire.RED;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 8, 11, "curve_left_down3", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 9, 11, "curve_right_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 9, 12, "curve_left_down3", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 12, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 12, "horizontal_up", cor);
		i++;

		cor = IT_Wire.GREEN;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 11, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 12, "curve_left_down3", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 12, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 12, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 12, "horizontal_up", cor);
		i++;
		cor = IT_Wire.BLUE;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 11, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 12, "curve_left_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 12, "horizontal_down", cor);
		i++;

		cor = IT_Wire.BLACK;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 12, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 17, 12, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 12, "curve_right_down2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 11, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 10, "vertical_left", cor);
		i++;

		cor = IT_Wire.WHITE;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 9, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 8, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 18, 7, "vertical_right", cor);
		i++;
	}

}
