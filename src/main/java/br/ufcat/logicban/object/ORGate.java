package br.ufcat.logicban.object;

import br.ufcat.logicban.ui.GamePanel;

public class ORGate extends LogicGate {
    public static final String objName = "OR Gate";
    public ORGate(GamePanel gp) {
        super(gp, 2); // OR tem 2 entradas
        name = objName;
        down1 = setup("/assets/objects/or_gate", gp.tileSize, gp.tileSize);
    }

    @Override
    public void calculateOutput() {
        if (inputs[0] != null && inputs[1] != null) {
            output = inputs[0].isPowered() || inputs[1].isPowered();
        } else {
            output = false; // Se alguma entrada não estiver conectada, a saída é falsa
        }
    }
}