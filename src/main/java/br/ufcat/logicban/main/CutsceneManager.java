package br.ufcat.logicban.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class CutsceneManager {
	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	int counter = 0;
	float alpha = 0f;

	// scene number
	public final int init = 0;

	public boolean transitionNeed = false;

	public CutsceneManager(GamePanel gp) {
		this.gp = gp;
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		System.out.println("debug1");
		switch (sceneNum) {
		case init:
			System.out.println("debug2");
			scene_opening();
			break;
		}
	}

	public void scene_opening() {
		int pressEnterY = 520;
		String text;
		if (scenePhase == 0) {
			drawBlackBackground(1.0F);
			alpha += 0.005F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}

			text = "This is an island somewhere far away.\n\nBlue Boy, an aspiring adventurer, \ncomes to this island because he hears \nthat it holds a legendary treasure.\n\n\n\n\n";
			drawString(alpha, 35.0F, 170, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Press Enter to continue)", 40);
			if (gp.keyH.enterPressed) {
				gp.keyH.enterPressed = false;
				++scenePhase;
			}
		}

		if (scenePhase == 1) {
			drawBlackBackground(1.0F);
			alpha -= 0.02F;
			if (alpha < 0.0F) {
				alpha = 0.0F;
				++scenePhase;
			}

			text = "This is an island somewhere far away.\n\nBlue Boy, an aspiring adventurer, \ncomes to this island because he hears \nthat it holds a legendary treasure.\n\n\n\n\n";
			drawString(alpha, 35.0F, 170, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Press Enter to continue)", 40);
		}

		if (scenePhase == 2) {
			drawBlackBackground(1.0F);
			alpha += 0.01F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}

			text = "Can he safely find the treasure on this island,\nwhere dangerous monsters roam?\n\nIt all depends on you.\n\n\n\n\n\n";
			drawString(alpha, 35.0F, 200, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Press Enter to continue)", 40);
			if (gp.keyH.enterPressed) {
				gp.keyH.enterPressed = false;
				++scenePhase;
			}
		}

		if (scenePhase == 3) {
			drawBlackBackground(1.0F);
			alpha -= 0.02F;
			if (alpha < 0.0F) {
				alpha = 0.0F;
				++scenePhase;
			}

			text = "Can he safely find the treasure on this island,\nwhere dangerous monsters roam?\n\nIt all depends on you.\n\n\n\n\n";
			drawString(alpha, 35.0F, 200, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Press Enter to continue)", 40);
		}

		if (scenePhase == 4) {
			drawBlackBackground(1.0F);
			alpha += 0.005F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}

			drawString(alpha, 35.0F, 50, "<How to Play>", 40);
			text = "Move: [W/A/S/D]\nAttack/Interact/Confirm: [ENTER]\nMagic: [F]\nGuard/Parry: [SPACE]\nInventory/Status: [C]\nMap: [M]   Mini Map: [X]\nPause: [P]\nOptions: [ESC]\n\n";
			drawString(alpha, 35.0F, 120, text, 45);
			drawString(alpha, 35.0F, pressEnterY, "(Press Enter to start the adventure)", 40);
			if (gp.keyH.enterPressed) {
				gp.keyH.enterPressed = false;
				++scenePhase;
			}
		}

		if (scenePhase == 5) {
			gp.keyH.enterPressed = false;
			sceneNum--;
			scenePhase = 0;
			gp.ui.titleScreenState = 0;
			gp.gameState = gp.playState;
			gp.currentMap = 0;
			gp.playerPositions();
			gp.highestUnlockedFase = 0; // Reset para fase inicial
			gp.saveLoad.save();
			gp.stopMusic();
			gp.playMusic(0);
		}
	}

	public boolean counterReached(int target) {
		boolean counterReached = false;
		++counter;
		if (counter > target) {
			counterReached = true;
			counter = 0;
		}

		return counterReached;
	}

	public void drawBlackBackground(float alpha) {
		g2.setComposite(AlphaComposite.getInstance(3, alpha));
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
	}

	public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
		g2.setComposite(AlphaComposite.getInstance(3, alpha));
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(fontSize));
		String[] var9;
		int var8 = (var9 = text.split("\n")).length;

		for (int var7 = 0; var7 < var8; ++var7) {
			String line = var9[var7];
			int x = gp.ui.getXforCenteredText(line);
			g2.drawString(line, x, y);
			y += lineHeight;
		}

		g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
	}
}
