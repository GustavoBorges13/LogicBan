package br.ufcat.logicban.tile_interactive;

import java.awt.Color;
import java.awt.image.BufferedImage;

import br.ufcat.logicban.main.GamePanel;

public class IT_RedWire extends InteractiveTile {
	GamePanel gp;
//	public static final String itName = "wire";
	public String direction = "horizontal_down";
	public BufferedImage vertical, horizontal, curve_left_up, curve_right_up, curve_left_down, curve_right_down;
	public boolean destructible = false;
	public int estadoLogico;

	public IT_RedWire(GamePanel gp, int col, int row, String direction, String wireName) {
		super(gp, col, row);
		this.gp = gp;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		this.direction = direction;

		name = wireName;

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

	}

	public void carregarTextura() {
		switch (direction) {
		case "horizontal_up":
			down1 = setup("/assets/wires_connections/" + name + "_horizontal_up", gp.tileSize, gp.tileSize);
			break;
		case "horizontal_down":
			down1 = setup("/assets/wires_connections/" + name + "_horizontal_down", gp.tileSize, gp.tileSize);
			break;
		case "vertical_left":
			down1 = setup("/assets/wires_connections/" + name + "_vertical_left", gp.tileSize, gp.tileSize);
			break;
		case "vertical_right":
			down1 = setup("/assets/wires_connections/" + name + "_vertical_right", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_up1":
			down1 = setup("/assets/wires_connections/" + name + "_curve_left_up1", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_up2":
			down1 = setup("/assets/wires_connections/" + name + "_curve_left_up2", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_up3":
			down1 = setup("/assets/wires_connections/" + name + "_curve_left_up3", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_up1":
			down1 = setup("/assets/wires_connections/" + name + "_curve_right_up1", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_up2":
			down1 = setup("/assets/wires_connections/" + name + "_curve_right_up2", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_up3":
			down1 = setup("/assets/wires_connections/" + name + "_curve_right_up3", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_down1":
			down1 = setup("/assets/wires_connections/" + name + "_curve_left_down1", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_down2":
			down1 = setup("/assets/wires_connections/" + name + "_curve_left_down2", gp.tileSize, gp.tileSize);
			break;
		case "curve_left_down3":
			down1 = setup("/assets/wires_connections/" + name + "_curve_left_down3", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_down1":
			down1 = setup("/assets/wires_connections/" + name + "_curve_right_down1", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_down2":
			down1 = setup("/assets/wires_connections/" + name + "_curve_right_down2", gp.tileSize, gp.tileSize);
			break;
		case "curve_right_down3":
			down1 = setup("/assets/wires_connections/" + name + "_curve_right_down3", gp.tileSize, gp.tileSize);
			break;
		}
	}
}