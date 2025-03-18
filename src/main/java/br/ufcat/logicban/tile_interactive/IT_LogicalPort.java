package br.ufcat.logicban.tile_interactive;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import br.ufcat.logicban.ui.GamePanel;

public class IT_LogicalPort extends InteractiveTile {
	public static final String itName = "Porta Logica";
	public static final String NOT = "NOT";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String NOR = "NOR";
	public static final String XOR = "XOR";
	public static final String NAND = "NAND";
	public static final String XNOR = "XNOR";
	public int id; // ID único
	public ArrayList<Integer> plateIndices = new ArrayList<>(); // Lista dos índices das placas conectadas
	public boolean outputState = false; // Adicionado
	public ArrayList<Integer> inputPortIDs = new ArrayList<>(); // Adicionado
	
	public IT_LogicalPort(GamePanel gp, int col, int row, String tipo, String direction, int id) {
		super(gp, col, row);
		this.gp = gp;

		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;

		this.tipo = tipo;
		this.direction = direction;
		this.id = id;

		name = itName;
		
		switch (tipo) {
		case NOT:
			solidArea = new Rectangle();
			solidArea.x = 2;
			solidArea.y = 8;
			solidArea.width = 36;
			solidArea.height = 30;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			color = Color.black;
			break;
		case AND:
			solidArea = new Rectangle();
			solidArea.x = 6;
			solidArea.y = 10;
			solidArea.width = 36;
			solidArea.height = 38;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			color = Color.black;
			break;
		case OR:
			solidArea = new Rectangle();
			solidArea.x = 6;
			solidArea.y = 8;
			solidArea.width = 36;
			solidArea.height = 38;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			color = Color.black;
			break;
		case NOR:
			solidArea = new Rectangle();
			solidArea.x = 6;
			solidArea.y = 8;
			solidArea.width = 36;
			solidArea.height = 38;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			color = Color.black;
			break;
		case XOR:
			solidArea = new Rectangle();
			solidArea.x = 6;
			solidArea.y = 8;
			solidArea.width = 36;
			solidArea.height = 38;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			color = Color.black;
			break;
		case NAND:
			solidArea = new Rectangle();
			solidArea.x = 6;
			solidArea.y = 8;
			solidArea.width = 36;
			solidArea.height = 38;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			color = Color.black;
			break;
		case XNOR:
			solidArea = new Rectangle();
			solidArea.x = 6;
			solidArea.y = 8;
			solidArea.width = 36;
			solidArea.height = 38;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			color = Color.black;
			break;
		}

		getImage(tipo);
	}

	// Método para adicionar um índice de placa à lista
	public void addPlateIndex(int index) {
		plateIndices.add(index);
	}

	public void getImage(String tipo) {

		switch (direction) {
		case "left":
			left1 = setup("/assets/wires_connections/" + tipo + "_left", gp.tileSize, gp.tileSize);
			break;
		case "right":
			right1 = setup("/assets/wires_connections/" + tipo + "_right", gp.tileSize, gp.tileSize);
			break;
		case "up":
			up1 = setup("/assets/wires_connections/" + tipo + "_up", gp.tileSize, gp.tileSize);
			break;
		case "down":
			down1 = setup("/assets/wires_connections/" + tipo + "_down", gp.tileSize, gp.tileSize);
			break;
		}
	}

}
