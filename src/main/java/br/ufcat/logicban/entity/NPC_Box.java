package br.ufcat.logicban.entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import br.ufcat.logicban.tile_interactive.IT_LogicalPort;
import br.ufcat.logicban.tile_interactive.IT_MetalPlate;
import br.ufcat.logicban.tile_interactive.InteractiveTile;
import br.ufcat.logicban.ui.GamePanel;

public class NPC_Box extends Entity {

	public static final String npcName = "Box";
	public String logicalOperation = "";
	public ArrayList<InteractiveTile> plateList = new ArrayList<InteractiveTile>();
	public ArrayList<IT_LogicalPort> logicalPortList = new ArrayList<>(); // Lista de Portas Lógicas
	public static ArrayList<Entity> boxList = new ArrayList<Entity>();

	// Dentro da classe NPC_Box
	public boolean isMoving = false; // Flag para evitar recursão

	public NPC_Box(GamePanel gp) {
		super(gp);

		name = npcName;
		direction = "down";
		speed = 3;
		walkType = gp.player.walkType;

		color = Color.ORANGE;
		solidArea.x = 6;
		solidArea.y = 9;
		solidArea.width = 38;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
	}

	public void getImage() {

		up1 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);
		up2 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);
		down1 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);
		down2 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);
		left1 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);
		left2 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);
		right1 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);
		right2 = setup("/assets/npc/box01", gp.tileSize, gp.tileSize);

	}

	public void setAction() {
	}

	public void update() {
		if (gp.player.walkType.equals(gp.player.smoothWalk)) {
			solidArea.x = 6;
			solidArea.y = 9;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			solidArea.width = 38;
			solidArea.height = 32;
		} else if (gp.player.walkType.equals(gp.player.stepWalk)) {
			solidArea.x = 3;
			solidArea.y = 3;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			solidArea.width = 42;
			solidArea.height = 42;

		}
		detectPlate();
		updateLogicalPorts(); // Adicione esta linha
	}

	@Override
	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkPlayer(this);
		// gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.iTile);
		// gp.cChecker.checkEntity(this, gp.wire);
		// boolean contactPlayer = gp.cChecker.checkPlayer(this);
		collisionEndWorld = false;
		gp.cChecker.checkEnd(this);
		// gp.cChecker.checkEntity(this, gp.monster);
	}

	public void move(String d) {
		if (gp.player.walkType.equals(gp.player.smoothWalk)) {
			if (isMoving)
				return;

			isMoving = true;
			this.direction = d;

			// Salva a posição antiga para poder voltar se houver colisão
			int oldWorldX = worldX;
			int oldWorldY = worldY;

			// Atualiza a posição com base na direção
			switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}

			checkCollision();
			
			if (collisionOn) {
				worldX = oldWorldX;
				worldY = oldWorldY;
				isMoving = false;
				return; // Interrompe o movimento se houver colisão com tile
			}

			// Verifica colisão com outros NPCs (incluindo caixas)
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			if (npcIndex != 999) {
				interactNPC(npcIndex); // Tenta interagir com o NPC
				// Se a interação (empurrar) não for possível, volta à posição antiga
				if (collisionOn) {
					worldX = oldWorldX;
					worldY = oldWorldY;
				}
			}
			isMoving = false; // Garante que isMoving seja false após a tentativa de movimento
		} else if (gp.player.walkType.equals(gp.player.stepWalk)) {

			if (isMoving)
				return;
			if (isMoving)
				return;

			isMoving = true;
			this.direction = d;

			// Salva a posição antiga para poder voltar se houver colisão
			int oldWorldX = worldX;
			int oldWorldY = worldY;

			// Atualiza a posição com base na direção
			switch (direction) {
			case "up":
				worldY -= gp.tileSize;
				break;
			case "down":
				worldY += gp.tileSize;
				break;
			case "left":
				worldX -= gp.tileSize;
				break;
			case "right":
				worldX += gp.tileSize;
				break;
			}

			checkCollision();
			
			if (collisionOn) {
				worldX = oldWorldX;
				worldY = oldWorldY;
				isMoving = false;
				return; // Interrompe o movimento se houver colisão com tile
			}

			// Verifica colisão com outros NPCs (incluindo caixas)
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			if (npcIndex != 999) {
				interactNPC(npcIndex); // Tenta interagir com o NPC
				// Se a interação (empurrar) não for possível, volta à posição antiga
				if (collisionOn) {

					worldX = oldWorldX;
					worldY = oldWorldY;
				}
			}
			isMoving = false; // Garante que isMoving seja false após a tentativa de movimento
		}
	}

	public void interactNPC(int i) {
		if (i != 999) {
			Entity targetNPC = gp.npc[gp.currentMap][i];

			// Verifica se o NPC é outra caixa
			if (targetNPC instanceof NPC_Box) {
				NPC_Box box = (NPC_Box) targetNPC;

				// Salvar a posição original do player e da caixa
				int playerOldWorldX = worldX;
				int playerOldWorldY = worldY;
				int boxOldWorldX = box.worldX;
				int boxOldWorldY = box.worldY;

				// Tenta mover a outra caixa na mesma direção
				// box.move(direction);

				// Se a outra caixa não puder ser movida (colisão), marca a colisão
				if (box.collisionOn) {
					box.worldX = boxOldWorldX;
					box.worldY = boxOldWorldY;
					collisionOn = true;
				} else {
					// Troca a posição do player com a posição original da caixa
//					worldX = 200;
//					worldY = 150;
				}

			} else {
				// Lógica para interagir com outros tipos de NPCs
				collisionOn = true; // Impede o movimento se for outro tipo de NPC
			}
		}
	}

	public void detectPlate() {

		// Limpa a lista de placas e de portas lógicas
		plateList.clear();
		logicalPortList.clear();

		// Cria uma lista de placas
		for (int i = 0; i < gp.iTile[1].length; i++) {
			if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null
					&& gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
				plateList.add(gp.iTile[gp.currentMap][i]);
			}
		}

		// Cria uma lista de portas lógicas
		for (int i = 0; i < gp.iTile[1].length; i++) {
			if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null
					&& gp.iTile[gp.currentMap][i].name.equals(IT_LogicalPort.itName)) {
				logicalPortList.add((IT_LogicalPort) gp.iTile[gp.currentMap][i]);
			}
		}

		// Cria uma lista de caixas
		boxList.clear();
		for (int i = 0; i < gp.npc[1].length; i++) {
			if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name != null
					&& gp.npc[gp.currentMap][i].name.equals(NPC_Box.npcName)) {
				boxList.add(gp.npc[gp.currentMap][i]);
			}
		}

		// Scanea sempre as placas (detectar caixas)
		for (InteractiveTile plate : plateList) {
			int xDistance = Math.abs(worldX - plate.worldX);
			int yDistance = Math.abs(worldY - plate.worldY);
			int distance = Math.max(xDistance, yDistance);

			// altera 15 se quiser mexer na hitbox das placas
			if (distance < 20) {
				if (linkedEntity == null) {
					linkedEntity = plate;
					plate.estadoLogico = 1;
					gp.playSFX(3);
				}
			} else {
				if (linkedEntity == plate) {
					linkedEntity = null;
					plate.estadoLogico = 0;
				}
			}
		}

		// Conta quantas caixas estão em cima de uma placa
		int count = 0;
		for (Entity box : boxList) {
			if (((NPC_Box) box).linkedEntity != null) {
				count++;
			}
			// System.out.println("Caixas em cima de placa: "+count);
		}

	}

	// Novo método para atualizar as portas lógicas
	public void updateLogicalPorts() {
		for (IT_LogicalPort port : logicalPortList) {
			if (port != null) {
				port.outputState = verificarCondicaoLogica(port.id);
			}
		}
	}

	// Método para verificar a condição lógica de uma porta
	public boolean verificarCondicaoLogica(int portID) {
		IT_LogicalPort port = null;
		for (IT_LogicalPort p : logicalPortList) {
			if (p.id == portID) {
				port = p;
				break;
			}
		}

		if (port == null) {
			System.out.println("Porta lógica com ID " + portID + " não encontrada.");
			return false;
		}

		switch (port.tipo) {
		case IT_LogicalPort.NOT:
			// A porta NOT inverte a entrada, que pode vir de uma placa ou de outra porta
			if (port.plateIndices.size() > 0) {
				int plateIndex = port.plateIndices.get(0);
				if (plateIndex >= 0 && plateIndex < plateList.size()) {
					return plateList.get(plateIndex).estadoLogico == 0; // Inverte o estado da placa
				} else {
					System.out.println("Índice de placa inválido para a porta NOT.");
					return false;
				}
			} else if (port.inputPortIDs.size() > 0) {
				int inputPortID = port.inputPortIDs.get(0);
				return !verificarCondicaoLogica(inputPortID); // Inverte o estado da porta de entrada
			} else {
				System.out.println("Porta NOT sem placa ou porta lógica conectada.");
				return false;
			}

		case IT_LogicalPort.AND:
			// A porta AND requer que todas as entradas sejam verdadeiras
			boolean andResult = true;
			// Verifica as placas conectadas
			for (int plateIndex : port.plateIndices) {
				if (plateIndex >= 0 && plateIndex < plateList.size()) {
					andResult = andResult && (plateList.get(plateIndex).estadoLogico == 1);
				} else {
					System.out.println("Índice de placa inválido para a porta AND.");
					return false;
				}
			}

			// Verifica as portas lógicas de entrada
			for (int inputPortID : port.inputPortIDs) {
				andResult = andResult && verificarCondicaoLogica(inputPortID); // Recursivamente verifica o estado da
																				// porta
			}
			return andResult;

		case IT_LogicalPort.OR:
			// A porta OR requer que pelo menos uma entrada seja verdadeira
			boolean orResult = false;
			
			// Verifica as placas conectadas
			for (int plateIndex : port.plateIndices) {
				
				if (plateIndex >= 0 && plateIndex < plateList.size()) {
					orResult = orResult || (plateList.get(plateIndex).estadoLogico == 1);
				} else {
					System.out.println("Índice de placa inválido para a porta OR.");
					return false;
				}
			}
			// Verifica as portas lógicas de entrada
			for (int inputPortID : port.inputPortIDs) {
				orResult = orResult || verificarCondicaoLogica(inputPortID); // Recursivamente verifica o estado da
																				// porta
			}

			return orResult;

		case IT_LogicalPort.XOR:
			// A porta XOR requer que exatamente uma entrada seja verdadeira
			int trueCount = 0;

			// Verifica as placas conectadas
			for (int plateIndex : port.plateIndices) {
				if (plateIndex >= 0 && plateIndex < plateList.size()) {
					if (plateList.get(plateIndex).estadoLogico == 1) {
						trueCount++;
					}
				} else {
					System.out.println("Índice de placa inválido para a porta XOR.");
					return false;
				}
			}

			// Verifica as portas lógicas de entrada
			for (int inputPortID : port.inputPortIDs) {
				if (verificarCondicaoLogica(inputPortID)) {
					trueCount++;
				}
			}

			return trueCount == 1; // Retorna verdadeiro se exatamente uma entrada for verdadeira

		case IT_LogicalPort.NAND:
			// A porta NAND é o inverso da porta AND
			boolean nandResult = true;
			for (int plateIndex : port.plateIndices) {
				if (plateIndex >= 0 && plateIndex < plateList.size()) {
					nandResult = nandResult && (plateList.get(plateIndex).estadoLogico == 1);
				} else {
					System.out.println("Índice de placa inválido para a porta NAND.");
					return false;
				}
			}
			for (int inputPortID : port.inputPortIDs) {
				nandResult = nandResult && verificarCondicaoLogica(inputPortID);
			}
			return !nandResult;

		case IT_LogicalPort.XNOR:
			// A porta XNOR é o inverso da porta XOR
			int xnorTrueCount = 0;

			// Verifica as placas conectadas
			for (int plateIndex : port.plateIndices) {
				if (plateIndex >= 0 && plateIndex < plateList.size()) {
					if (plateList.get(plateIndex).estadoLogico == 1) {
						xnorTrueCount++;
					}
				} else {
					System.out.println("Índice de placa inválido para a porta XNOR.");
					return false;
				}
			}

			// Verifica as portas lógicas de entrada
			for (int inputPortID : port.inputPortIDs) {
				if (verificarCondicaoLogica(inputPortID)) {
					xnorTrueCount++;
				}
			}

			return xnorTrueCount != 1; // Inverte o resultado da porta XOR

		// Ou, de forma mais direta:
		// boolean xorResult = (entrada1 ^ entrada2); // ^ é o operador XOR bit a bit
		// return !xorResult;

		default:
			System.out.println("Tipo de porta lógica desconhecido: " + port.tipo);
			return false;
		}

	}

	public ArrayList<String> getLogicalPortDebugInfo() {
		ArrayList<String> debugInfo = new ArrayList<>();
		for (IT_LogicalPort port : logicalPortList) {
			if (port != null) {
				boolean portStatus = verificarCondicaoLogica(port.id);
				String debugString = "Porta " + port.tipo.toUpperCase() + " id[" + port.id + "]: " + portStatus; // +
																													// portStatus;
				debugInfo.add(debugString);
			}
		}
		return debugInfo;
	}
}