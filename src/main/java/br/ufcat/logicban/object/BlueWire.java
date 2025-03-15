package br.ufcat.logicban.object;

import java.awt.Color;

import br.ufcat.logicban.ui.GamePanel;

public class BlueWire extends Wire {
    public static final String objName = "Blue Wire";
    public BlueWire(GamePanel gp) {
        super(gp, "blue");
        name = objName;
        color = Color.BLUE;
    }
}