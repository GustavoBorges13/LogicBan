package br.ufcat.logicban.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.ui.GamePanel;

public abstract class LogicGate extends Entity {
    protected Wire[] inputs;
    protected boolean output;
    protected int numInputs; // Numero maximo de entradas
    public BufferedImage image;
    GamePanel gp;

    public LogicGate(GamePanel gp, int numInputs) {
        super(gp);
        this.gp = gp;
        
        collision = true;
        this.numInputs = numInputs;
        inputs = new Wire[numInputs]; // Inicializa o array de entradas
    }

    public boolean getOutput() {
        return output;
    }

    // Método abstrato para calcular a saída. Cada porta lógica implementará isso.
    public abstract void calculateOutput();

    // Método para conectar um fio a uma entrada. Retorna true se a conexão for bem-sucedida, false caso contrário.
    public boolean connectInput(Wire wire, int inputIndex) {
        if (inputIndex >= 0 && inputIndex < numInputs) {
            inputs[inputIndex] = wire;
            return true;
        }
        return false;
    }

    // Método para verificar se todas as entradas estão conectadas
    public boolean areAllInputsConnected() {
        for (int i = 0; i < numInputs; i++) {
            if (inputs[i] == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        if(areAllInputsConnected()){
            calculateOutput();
        }
    }

        // Método para desenhar o fio com tiles
    public void draw(Graphics2D g2) {
         int screenX = worldX - gp.player.worldX;
         int screenY = worldY - gp.player.worldY;

         if (worldX + gp.tileSize > gp.player.worldX  &&
                 worldX - gp.tileSize < gp.player.worldX &&
                 worldY + gp.tileSize > gp.player.worldY &&
                 worldY - gp.tileSize < gp.player.worldY) {

             g2.drawImage(down1, screenX, screenY, gp.tileSize, gp.tileSize, null);
         }
    }
}