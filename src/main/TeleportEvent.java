package main;

public class TeleportEvent {
	int currentMap, col, row;
	String direction;
	int targetMap, targetCol, targetRow;
	String targetDirection;

	public TeleportEvent(int currentMap, int col, int row, String direction, int targetMap, int targetCol,
			int targetRow, String targetDirection) {
		this.currentMap = currentMap;
		this.col = col;
		this.row = row;
		this.direction = direction;
		this.targetMap = targetMap;
		this.targetCol = targetCol;
		this.targetRow = targetRow;
		this.targetDirection = targetDirection;
	}
}