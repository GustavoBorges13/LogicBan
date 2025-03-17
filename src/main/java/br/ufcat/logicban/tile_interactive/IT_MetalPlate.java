package br.ufcat.logicban.tile_interactive;

import java.awt.Color;
import java.awt.image.BufferedImage;

import br.ufcat.logicban.ui.GamePanel;

public class IT_MetalPlate extends InteractiveTile {

    GamePanel gp;
    public static final String itName = "Metal Plate";
    public boolean soundPlayed = false;
    private boolean activated = false; // Adiciona um estado para controlar a ativação
    private BufferedImage originalImage; // Para restaurar a imagem original

    public IT_MetalPlate(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        name = itName;
        down1 = setup("/assets/tiles_interactive/metalplate", gp.tileSize, gp.tileSize);
        originalImage = down1; // Salva a imagem original para poder restaurar depois

        solidArea.x = 25;
        solidArea.y = 25;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        color = Color.red;
        estadoLogico = 0;
        activated = false; // Inicializa como não ativada
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    //Chamado no update para poder atualizar o estado da placa, nao apenas setar
    public void update() {
        if(estadoLogico == 1 && !activated) {
            activated = true;
        } else if (estadoLogico == 0 && activated) {
            activated = false;
        }
    }

}