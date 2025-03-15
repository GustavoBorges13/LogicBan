package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.ui.GamePanel;

public class GreenWire extends Wire {
    public static final String objName = "Green Wire";
    public GreenWire(GamePanel gp) {
        super(gp, "green");
        name = objName;
        color = Color.GREEN;
    }
}