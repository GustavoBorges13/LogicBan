package br.ufcat.logicban.ui;

import java.awt.Color;

import br.ufcat.logicban.entity.NPC_Box;
import br.ufcat.logicban.entity.NPC_OldMan;
import br.ufcat.logicban.entity.NPC_OldPlayer;
import br.ufcat.logicban.object.OBJ_Boots;
import br.ufcat.logicban.object.OBJ_Chest;
import br.ufcat.logicban.object.OBJ_Door;
import br.ufcat.logicban.object.OBJ_Door_Iron;
import br.ufcat.logicban.object.OBJ_Key;
import br.ufcat.logicban.tile_interactive.IT_DryTree;
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
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.obj[mapNum][i].worldX = 11 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 8 * gp.tileSize;
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
		gp.npc[mapNum][i].worldX = 6 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 3 * gp.tileSize;
		i++;
		gp.npc[mapNum][i] = new NPC_OldPlayer(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 10 * gp.tileSize;
		i++;
		
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 2 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 3 * gp.tileSize;
		i++;
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 8 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 3 * gp.tileSize;
		i++;
		gp.npc[mapNum][i] = new NPC_Box(gp);
		gp.npc[mapNum][i].worldX = 7 * gp.tileSize;
		gp.npc[mapNum][i].worldY = 3 * gp.tileSize;
		i++;
	}

	public void setInteractiveTile() {

		int mapNum = 1;
		int i = 0;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 8, 10);
		i++;
		// gp.iTile[mapNum][i] = new IT_DryTree(gp, 9, 10);i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 7, 5);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 4);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 6);
		i++;

	}
	
	public void setObjectsAndConnections() {
	    int mapNum = 1;
	    int i = 0;
	    int wireSet = 0; // Identificador do primeiro conjunto de fios

	    // Primeiro conjunto de fios
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 8, 5, "horizontal_down", wireSet); // Cabeça
	    gp.wire[mapNum][i].solidArea.x = -10;
	    gp.wire[mapNum][i].solidArea.y = 16;
	    gp.wire[mapNum][i].solidArea.width = 20;
	    gp.wire[mapNum][i].solidArea.height = 20;
	    gp.wire[mapNum][i].solidAreaDefaultX =  gp.wire[mapNum][i].solidArea.x;
	    gp.wire[mapNum][i].solidAreaDefaultY =  gp.wire[mapNum][i].solidArea.y;
	    gp.wire[mapNum][i].color = Color.YELLOW;
	    gp.wire[mapNum][i].isHead = true;
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 9, 5, "horizontal_down", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 5, "horizontal_down", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 5, "curve_right_down1", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 4, "vertical_right", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 3, "curve_left_up2", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 3, "horizontal_down", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 3, "curve_right_down1", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 13, 2, "curve_right_up1", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 12, 2, "horizontal_up", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 11, 2, "horizontal_up", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 2, "curve_left_up1", wireSet);
	    i++;
	    gp.wire[mapNum][i] = new IT_RedWire(gp, 10, 3, "vertical_left", wireSet); // Cauda
	    gp.wire[mapNum][i].solidArea.x = 12;
	    gp.wire[mapNum][i].solidArea.y = 36;
	    gp.wire[mapNum][i].solidArea.width = 20;
	    gp.wire[mapNum][i].solidArea.height = 20;
	    gp.wire[mapNum][i].solidAreaDefaultX =  gp.wire[mapNum][i].solidArea.x;
	    gp.wire[mapNum][i].solidAreaDefaultY =  gp.wire[mapNum][i].solidArea.y;
	    gp.wire[mapNum][i].color = Color.black;
	    gp.wire[mapNum][i].isTail = true;
	    i++;

	    // Se você tiver mais conjuntos de fios, incremente 'wireSet' e continue instanciando
	}
}
