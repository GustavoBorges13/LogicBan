package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.main.GamePanel;
import br.ufcat.logicban.tile_interactive.IT_LogicalPort;
import br.ufcat.logicban.tile_interactive.IT_MetalPlate;

public class OBJ_Door extends Entity {
	GamePanel gp;
	public int controllingPortID;
	public int controllingPlateID;
	public boolean isOpen = false; // Adicione esta linha
	public boolean originalCollision; // Adicione esta linha para salvar o estado original da colisao
	public String originalImage; // Adicione esta linha para salvar o caminho da imagem original
	public boolean messageShown = false; // Nova flag
	public static final String objName = "Door";

	public OBJ_Door(GamePanel gp, int controllingPortID, String option)  {
		super(gp);
		this.gp = gp;
		switch (option) {
		case IT_LogicalPort.itName:
			this.controllingPortID = controllingPortID;
			break;
		case  IT_MetalPlate.itName:
			this.controllingPlateID = controllingPortID;
			break;
		}
		name = objName;
		down1 = setup("/assets/objects/door", gp.tileSize, gp.tileSize);
		originalImage = "/assets/objects/door"; // Salva o caminho da imagem original
		collision = true;
		originalCollision = true; // Salva o estado original da colisão

		color = Color.WHITE;
	}

	// Método para "abrir" a porta (esconder e desativar a colisão)
	public void openDoor() {
		if (!isOpen) {
			collision = false;
			down1 = null; // Torna a porta invisível
			isOpen = true;
		}
	}

	// Método para "fechar" a porta (reexibir e reativar a colisão)
	public void closeDoor() {
		if (isOpen) {
			collision = originalCollision; // Restaura o estado original da colisão
			down1 = setup(originalImage, gp.tileSize, gp.tileSize); // Restaura a imagem original
			isOpen = false;
		}
	}
}
