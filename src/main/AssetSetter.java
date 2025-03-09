package main;

import entity.NPC_Box;
import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Key;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

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
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 2);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 4);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 17, 6);
		i++;

	}
}
