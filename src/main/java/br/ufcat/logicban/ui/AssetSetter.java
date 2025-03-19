package br.ufcat.logicban.ui;

import br.ufcat.logicban.entity.NPC_Box;
import br.ufcat.logicban.entity.NPC_OldMan;
import br.ufcat.logicban.entity.NPC_OldPlayer;
import br.ufcat.logicban.object.OBJ_Boots;
import br.ufcat.logicban.object.OBJ_Chest;
import br.ufcat.logicban.object.OBJ_Door;
import br.ufcat.logicban.object.OBJ_Door_Iron;
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
		gp.obj[mapNum][i] = new OBJ_Door(gp, 0, IT_LogicalPort.itName); // Porta de ferro controlada pela porta logica ID 0
		gp.obj[mapNum][i].worldX = 15 * gp.tileSize;
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
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp, 3, IT_MetalPlate.itName); // Porta de ferro 1 controlada pela placa ID 2
		gp.obj[mapNum][i].worldX = 19 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 11 * gp.tileSize;
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

	}

	public void setInteractiveTile() {

		int mapNum = 0;
		int i = 0;
		
		// FASE 1
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 12, 5);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 11, 7);
		i++;
		
		IT_LogicalPort andPort = new IT_LogicalPort(gp, 15, 5, IT_LogicalPort.AND, "right", 0); // placa AND id = 0
		andPort.addPlateIndex(0);
		andPort.addPlateIndex(1);
		gp.iTile[mapNum][i] = andPort;
		i++;
		
		// FASE 2
		// gp.iTile[mapNum][i] = new IT_DryTree(gp, 9, 10);i++;
		mapNum = 1;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 12, 9);
		i++;
		// Gp, coluna, linha, tipo de porta, tile sheet, ID


		
//		IT_LogicalPort notPort = new IT_LogicalPort(gp, 14, 2, "not", "left", 1);
//		notPort.addPlateIndex(2); // Conectado à placa 2
//		gp.iTile[mapNum][i] = notPort;
//		i++;
//
//		IT_LogicalPort xorPort = new IT_LogicalPort(gp, 10, 4, "and", "down", 2);
//		xorPort.addPlateIndex(0);
//		xorPort.addPlateIndex(1);
//		xorPort.inputPortIDs.add(0);
//		gp.iTile[mapNum][i] = xorPort;
//		i++;

//		IT_LogicalPort andPort = new IT_LogicalPort(gp, 10, 4, "and", "down", 2);
//		andPort.addPlateIndex(0); // Conecta a placa diretamente
//		andPort.inputPortIDs.add(0); // Recebe a saída da porta NOT (ID 0)
//		andPort.inputPortIDs.add(1); // Recebe a saida da porta OR (ID 1)
//		gp.iTile[mapNum][i] = andPort;
//		i++;

//		IT_LogicalPort orPort = new IT_LogicalPort(gp, 10, 4, "and", "down", 2);
//		orPort.addPlateIndex(0); // Conecta a placa diretamente
//		orPort.inputPortIDs.add(0); // Recebe a saída da porta NOT (ID 0)
//		orPort.inputPortIDs.add(1); // Recebe a saida da porta OR (ID 1)
//		gp.iTile[mapNum][i] = orPort;
//		i++;
	}

	public void setWires() {
		int mapNum = 0;
		int i = 0;
		
		// FASE 1
		// entradas
		String cor = IT_Wire.RED;
		// Primeiro conjunto de fios (rosa)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 5, "curve_left_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 4, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 3, "curve_right_up3", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 3, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 3, "curve_left_up3", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 5, "vertical_right", cor);
		i++;

		cor = IT_Wire.GREEN;
		// Primeiro conjunto de fios (vermelho)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 5, "curve_left_up3", cor);
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
	}

}
