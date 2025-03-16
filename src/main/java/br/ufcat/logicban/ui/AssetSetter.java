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

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {

		int mapNum = 0;
		int i = 0;

		gp.obj[mapNum][i] = new OBJ_Key(gp);
		gp.obj[mapNum][i].worldX = 17 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 9 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Door(gp);
		gp.obj[mapNum][i].worldX = 14 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].worldX = 7 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Boots(gp);
		gp.obj[mapNum][i].worldX = 15 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 9 * gp.tileSize;
		i++;

		mapNum = 1;
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp, 2); // Porta de ferro 1 controlada pela porta AND com ID 2
		gp.obj[mapNum][i].worldX = 11 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
		i++;

		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp, 1); // Porta de ferro 2 controlada pela porta AND com ID 5 (exemplo)
		gp.obj[mapNum][i].worldX = 15 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 10 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Boots(gp);
		gp.obj[mapNum][i].worldX = 7 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 9 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Boots(gp);
		gp.obj[mapNum][i].worldX = 8 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 9 * gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].worldX = 18 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 10 * gp.tileSize;
	}

	public void setNPC() {

		int mapNum = 0;
		int i = 0;
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = 3 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 1 * gp.tileSize;
		i++;

		mapNum = 1;
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = 3 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 3 * gp.tileSize;
		i++;
		gp.npc[mapNum][i] = new NPC_OldPlayer(gp);
		gp.npc[mapNum][i].worldX = 8 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 2 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 8 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 7 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 6 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 5 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 4 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 3 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 4 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 8 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 4 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 7 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 4 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 6 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 4 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 5 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 4 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 4 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 4 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 3 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 3 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 2 * gp.tileSize;
		i++;

		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 3 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 9 * gp.tileSize;
		i++;

	}

	public void setInteractiveTile() {

		int mapNum = 1;
		int i = 0;
		// gp.iTile[mapNum][i] = new IT_DryTree(gp, 9, 10);i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 7, 5);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 2);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 6);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 3, 6);
		i++;
		// Gp, coluna, linha, tipo de porta, tile sheet, ID

		IT_LogicalPort notPort = new IT_LogicalPort(gp, 14, 2, "not", "left", 0);
		notPort.addPlateIndex(2); // Conectado à placa 2
		gp.iTile[mapNum][i] = notPort;
		i++;

		IT_LogicalPort xorPort = new IT_LogicalPort(gp, 10, 4, "and", "down", 1);
		xorPort.addPlateIndex(0);
		xorPort.addPlateIndex(1);
		xorPort.inputPortIDs.add(0);
		gp.iTile[mapNum][i] = xorPort;
		i++;

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
		int mapNum = 1;
		int i = 0;
		String cor = "pink";
		// Primeiro conjunto de fios (rosa)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 6, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 6, "curve_left_down2", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 5, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 4, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 3, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 2, "curve_right_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 2, "curve_left_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 3, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 4, "vertical_left", cor); // debaixo da porta logica
		i++;

		cor = "red";
		// Primeiro conjunto de fios (vermelho)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 8, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 9, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 5, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 5, "curve_right_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 4, "vertical_right", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 3, "curve_left_up3", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 3, "horizontal_down", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 3, "curve_right_down1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 2, "curve_right_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 2, "curve_left_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 3, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 4, "vertical_left", cor); // debaixo da porta logica
		i++;

		cor = "green";
		// Primeiro conjunto de fios (vermelho)
		// gp | Coluna X | Linha Y | Imagem | Cor
		gp.wire[mapNum][i] = new IT_RedWire(gp, 16, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 15, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 14, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 2, "horizontal_up", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 2, "curve_left_up1", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 3, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 4, "vertical_left", cor); // debaixo da porta logica

		// Fios da saida da porta AND
		cor = "black";
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 5, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 6, "vertical_left", cor);
		i++;
		gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 7, "vertical_left", cor);
	}

}
