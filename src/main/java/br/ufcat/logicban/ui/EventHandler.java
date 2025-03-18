package br.ufcat.logicban.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class EventHandler {
	GamePanel gp;

	EventRect eventRect[][][];
	int mapAux = 0, colAux = 0, rowAux = 0;

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	public int tempMap, tempCol, tempRow;
	public boolean outTile = false;
	public int newWorldX, newWorldY, oldSpeed = 3;
	public static boolean debugModeOn = false;
	private ArrayList<TeleportEvent> teleportEvents = new ArrayList<>();
	private TeleportEvent pendingTeleport; // Armazena informações do teleporte pendente

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxMap][gp.maxScreenCol][gp.maxScreenRow];

		for (int map = 0; map < gp.maxMap; map++) {
			for (int col = 0; col < gp.maxScreenCol; col++) {
				for (int row = 0; row < gp.maxScreenRow; row++) {
					eventRect[map][col][row] = new EventRect();
					eventRect[map][col][row].x = 0;
					eventRect[map][col][row].y = 0;
					eventRect[map][col][row].width = 48;
					eventRect[map][col][row].height = 48;
					eventRect[map][col][row].eventRectDefaultX = 0;
					eventRect[map][col][row].eventRectDefaultY = 0;
				}
			}
		}

		carregarEventos();
	}

	// Aqui onde fica eventos de teleporte, TP inicial X TP final
	private void carregarEventos() {
		teleportEvents.add(new TeleportEvent(0, 16, 8, "right", 1, 9, 9, "right")); // fase1 -> fase2

		teleportEvents.add(new TeleportEvent(1, 21, 12, "any", 2, 1, 0, "any")); // fase2 -> fase3

	}

	public void playerNewGamePosition() {

	}

	public void checkEvent() {
		// Check if the player character is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX + gp.tileSize);
		int yDistance = Math.abs(gp.player.worldY + gp.tileSize);
		int distance = Math.max(xDistance, yDistance);

		if (distance > gp.tileSize || (outTile || gp.player.collisionEndWorld)) {
			canTouchEvent = true;
		}

		if (canTouchEvent) {
			for (TeleportEvent event : teleportEvents) {
				if (hit(event.currentMap, event.col, event.row, event.direction)) {
					// Armazena as informações do teleporte pendente
					pendingTeleport = event;

					// Abre a tela de escolha
					gp.gameState = gp.nextPhaseState;
					gp.ui.levelFinished = true;
					gp.stopMusic();
					gp.playSFX(4);
					break; // Importante: Evita múltiplos eventos
				}
			}
		}
	}

	// Novo método para executar o teleporte (chamado pelo KeyHandler)
	public void executePendingTeleport() {
		if (pendingTeleport != null) {
			teleport(pendingTeleport.targetMap, pendingTeleport.targetCol, pendingTeleport.targetRow,
					pendingTeleport.targetDirection);
			pendingTeleport = null; // Limpa o teleporte pendente
			gp.ui.levelFinished = false; // Reseta pra nao bugar a tela e só aparecer 1 vez
		}
	}

	public boolean hit(int map, int col, int row, String reqDirection) {
		boolean hit = false;

		colAux = col;
		rowAux = row;
		mapAux = map;

		if (map == gp.currentMap) {
			if (gp.player.walkType.equals(gp.player.smoothWalk)) {
				gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
				gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
				eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
				eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

				if (gp.player.solidArea.intersects(eventRect[map][col][row])
						&& eventRect[map][col][row].eventDone == false) {
					if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
						hit = true;
						previousEventX = gp.player.worldX;
						previousEventY = gp.player.worldY;
					}
				}

				gp.player.solidArea.x = gp.player.solidAreaDefaultX;
				gp.player.solidArea.y = gp.player.solidAreaDefaultY;
				eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
				eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
			} else if (gp.player.walkType.equals(gp.player.stepWalk)) {
				switch (gp.player.direction) {
				case "up":
					gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
					gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
					eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
					eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
					break;
				case "down":
					gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
					gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
					eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
					eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
					break;
				case "left":
					gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
					gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
					eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
					eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
					break;
				case "right":
					gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
					gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
					eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
					eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
					break;
				}
				
				
				//System.out.println("playersolidarea: " + gp.player.solidArea + "eventRec: " + eventRect[map][col][row]);
				
				if (gp.player.solidArea.intersects(eventRect[map][col][row])
						&& eventRect[map][col][row].eventDone == false) {
					if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
						hit = true;
						previousEventX = gp.player.worldX;
						previousEventY = gp.player.worldY;
					}
				}

				gp.player.solidArea.x = gp.player.solidAreaDefaultX;
				gp.player.solidArea.y = gp.player.solidAreaDefaultY;
				eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
				eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
			}
		}

		return hit;
	}

	public void teleport(int targetMapIndex, int col, int row, String direction) {

		// Garante que o índice está dentro do array
		if (targetMapIndex >= 0 && targetMapIndex < gp.faseMap.length) {
			gp.gameState = gp.transitionState;
			tempMap = targetMapIndex; // Índice correto
			tempCol = col;
			tempRow = row;
			canTouchEvent = false;
			gp.nova_direcao_player = direction;

			// Atualiza imediatamente a fase atual
			// gp.currentMap = targetMapIndex; // Opcional, depende do fluxo
		}
	}

	// Os teleporte agora são desenhado de azul na tela caso use ferramenta debug !
	public void draw(Graphics2D g2) {
		if (debugModeOn && eventRect != null) {
			g2.setColor(Color.CYAN);
			g2.setStroke(new BasicStroke(4));

			for (TeleportEvent event : teleportEvents) {
				if (event.currentMap == gp.currentMap) { // Filtra apenas os eventos do mapa atual
					g2.drawRect(event.col * gp.tileSize, event.row * gp.tileSize, gp.tileSize, gp.tileSize);
				}
			}
		}
	}

}
