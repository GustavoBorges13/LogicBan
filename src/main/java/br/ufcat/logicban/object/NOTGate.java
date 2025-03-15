package br.ufcat.logicban.object;

import br.ufcat.logicban.ui.GamePanel;

public class NOTGate extends LogicGate {
    public static final String objName = "NOT Gate";
    public NOTGate(GamePanel gp) {
        super(gp, 1); // NOT tem 1 entrada
        name = objName;
        down1 = setup("/assets/objects/not_gate", gp.tileSize, gp.tileSize);
    }

    @Override
    public void calculateOutput() {
        if (inputs[0] != null) {
            output = !inputs[0].isPowered();
        } else {
            output = false; // Se a entrada não estiver conectada, a saída é falsa
        }
    }
}