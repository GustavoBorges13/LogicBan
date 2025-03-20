package br.ufcat.logicban.ui;

public class TeleportEvent {
	int currentMap, col, row;
	String direction;
	int targetMap, targetCol, targetRow;
	String targetDirection;
	boolean ultima_fase;
	
	public TeleportEvent(int currentMap, int col, int row, String direction, int targetMap, int targetCol,
			int targetRow, String targetDirection, boolean ultima_fase) {
		this.currentMap = currentMap;
		this.col = col;
		this.row = row;
		this.direction = direction;
		this.targetMap = targetMap;
		this.targetCol = targetCol;
		this.targetRow = targetRow;
		this.targetDirection = targetDirection;
		this.ultima_fase = ultima_fase;
	}
}