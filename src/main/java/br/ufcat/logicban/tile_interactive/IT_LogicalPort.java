package br.ufcat.logicban.tile_interactive;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import br.ufcat.logicban.main.GamePanel;

public class IT_LogicalPort extends InteractiveTile {
	public static final String itName = "Porta Logica";
	public static final String NOT = "NOT";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String NOR = "NOR";
	public static final String XOR = "XOR";
	public static final String NAND = "NAND";
	public static final String XNOR = "XNOR";
	
	public static final String GRAY = "gray";
	public static final String WHITE = "white";
	
	public String cor;
	
	public int id; // ID único
	public ArrayList<Integer> plateIndices = new ArrayList<>(); // Lista dos índices das placas conectadas
	public boolean outputState = false; // Adicionado
	public ArrayList<Integer> inputPortIDs = new ArrayList<>(); // Adicionado
	
	public IT_LogicalPort(GamePanel gp, int col, int row, String tipo, String direction, String cor, int id) {
		super(gp, col, row);
		this.gp = gp;

		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;

		this.tipo = tipo;
		this.direction = direction;
		this.id = id;
		this.cor = cor;
		
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 48;
		solidArea.height = 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		name = itName;
		
		switch (tipo) {
		case NOT:

			color = Color.black;
			break;
		case AND:
			color = Color.black;
			break;
		case OR:
			color = Color.black;
			break;
		case NOR:
			color = Color.black;
			break;
		case XOR:
			color = Color.black;
			break;
		case NAND:
			color = Color.black;
			break;
		case XNOR:
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
		tipo = tipo.toLowerCase();
		switch (direction) {
		case "left":
			left1 = setup("/assets/wires_connections/" + tipo + "_left_"+cor, gp.tileSize, gp.tileSize);
			break;
		case "right":
			right1 = setup("/assets/wires_connections/" + tipo + "_right_"+cor, gp.tileSize, gp.tileSize);
			break;
		case "up":
			up1 = setup("/assets/wires_connections/" + tipo + "_up_"+cor, gp.tileSize, gp.tileSize);
			break;
		case "down":
			down1 = setup("/assets/wires_connections/" + tipo + "_down_"+cor, gp.tileSize, gp.tileSize);
			break;
		}
	}

}
