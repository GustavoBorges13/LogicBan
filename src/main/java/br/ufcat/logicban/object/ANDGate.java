package br.ufcat.logicban.object;

import br.ufcat.logicban.ui.GamePanel;

public class ANDGate extends LogicGate {
    public static final String objName = "AND Gate";
    public ANDGate(GamePanel gp) {
        super(gp, 2); // AND tem 2 entradas
        name = objName;
        down1 = setup("/assets/objects/and_gate", gp.tileSize, gp.tileSize);
    }

    @Override
    public void calculateOutput() {
        if (inputs[0] != null && inputs[1] != null) {
            output = inputs[0].isPowered() && inputs[1].isPowered();
        } else {
            output = false; // Se alguma entrada não estiver conectada, a saída é falsa
        }
    }
}