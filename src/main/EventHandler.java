package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class EventHandler {
	GamePanel gp;

	EventRect eventRect[][][];
	int mapAux = 0, colAux = 0, rowAux = 0;

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	public int tempMap, tempCol, tempRow;
	public boolean outTile = false;
	public int newWorldX, newWorldY, newSpeed;
	public static boolean debugModeOn = false;
	private ArrayList<TeleportEvent> teleportEvents = new ArrayList<>();

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxMap][gp.maxScreenCol][gp.maxScreenRow];

		for (int map = 0; map < gp.maxMap; map++) {
			for (int col = 0; col < gp.maxScreenCol; col++) {
				for (int row = 0; row < gp.maxScreenRow; row++) {
					eventRect[map][col][row] = new EventRect();
					eventRect[map][col][row].x = 4;
					eventRect[map][col][row].y = 4;
					eventRect[map][col][row].width = 42;
					eventRect[map][col][row].height = 42;
					eventRect[map][col][row].eventRectDefaultX = 4;
					eventRect[map][col][row].eventRectDefaultY = 4;
				}
			}
		}

		carregarEventos();
	}

	private void carregarEventos() {
		teleportEvents.add(new TeleportEvent(0, 29, 6, "right", 1, 2, 2, "right")); // fase1 -> fase2
		teleportEvents.add(new TeleportEvent(0, 29, 7, "right", 1, 2, 2, "right"));
		teleportEvents.add(new TeleportEvent(0, 29, 8, "right", 1, 2, 2, "right"));

		teleportEvents.add(new TeleportEvent(1, 0, 1, "left", 0, 28, 7, "left")); // fase2 -> fase1
		teleportEvents.add(new TeleportEvent(1, 0, 2, "left", 0, 28, 7, "left"));
		teleportEvents.add(new TeleportEvent(1, 0, 3, "left", 0, 28, 7, "left"));

		teleportEvents.add(new TeleportEvent(1, 1, 15, "down", 2, 1, 0, "down")); // fase2 -> fase3
		teleportEvents.add(new TeleportEvent(1, 2, 15, "down", 2, 1, 0, "down"));
		teleportEvents.add(new TeleportEvent(1, 3, 15, "down", 2, 1, 0, "down"));

		teleportEvents.add(new TeleportEvent(2, 0, 0, "up", 1, 2, 15, "up")); // fase3 -> fase2
		teleportEvents.add(new TeleportEvent(2, 1, 0, "up", 1, 2, 15, "up"));
		teleportEvents.add(new TeleportEvent(2, 2, 0, "up", 1, 2, 15, "up"));

		teleportEvents.add(new TeleportEvent(2, 16, 10, "left", 3, 11, 10, "up")); // fase3 -> fase4
		teleportEvents.add(new TeleportEvent(3, 11, 11, "down", 2, 17, 10, "right")); // fase4 -> fase3
	}

	public void playerNewGamePosition() {
		newWorldX = gp.tileSize * 4;
		newWorldY = gp.tileSize * 2;
		newSpeed = gp.player.speedAux;
	}

	public void checkEvent() {
		// Check if the player character is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);

		if (distance > gp.tileSize || (outTile || gp.player.collisionEndWorld)) {
			canTouchEvent = true;
		}

		if (canTouchEvent) {
			for (TeleportEvent event : teleportEvents) {
				if (hit(event.currentMap, event.col, event.row, event.direction)) {
					teleport(event.targetMap, event.targetCol, event.targetRow, event.targetDirection);
					break; // Evita múltiplos teleportes simultâneos
				}
			}
		}
	}

	public boolean hit(int map, int col, int row, String reqDirection) {
		boolean hit = false;

		colAux = col;
		rowAux = row;
		mapAux = map;

		if (map == gp.currentMap) {
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
