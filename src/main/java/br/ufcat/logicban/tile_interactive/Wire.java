package br.ufcat.logicban.tile_interactive;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.ui.GamePanel;

public class Wire extends Entity {

	GamePanel gp;
	public boolean destructible = false;
	public int estadoLogico;
	public BufferedImage vertical, horizontal, curve_left_up, curve_right_up, curve_left_down, curve_right_down;


	public Wire(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}

	public void playSFX() {

	}

	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}

	public void update() {
		
	}

	
}
