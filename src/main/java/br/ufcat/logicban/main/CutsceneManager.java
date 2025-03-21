package br.ufcat.logicban.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.ufcat.logicban.entity.NPC_Box;
import br.ufcat.logicban.entity.NPC_OldMan;
import br.ufcat.logicban.entity.NPC_OldPlayer;
import br.ufcat.logicban.entity.Player;
import br.ufcat.logicban.object.OBJ_Boots;
import br.ufcat.logicban.object.OBJ_Door;
import br.ufcat.logicban.object.OBJ_Door_Iron;
import br.ufcat.logicban.object.OBJ_Flag;
import br.ufcat.logicban.tile_interactive.IT_LogicalPort;
import br.ufcat.logicban.tile_interactive.IT_MetalPlate;
import br.ufcat.logicban.tile_interactive.IT_Wire;

public class CutsceneManager {
	GamePanel gp;
	Graphics2D g2;
	public int sceneNum = -1;
	public int scenePhase;
	int counter = 0;
	float alpha = 0f;

	// scene number
	public final int init = 0;
	public final int loading = 1;

	public boolean transitionNeed = false;
	BufferedImage NOTImage, ANDImage, ORImage, NORImage, XORImage, NANDImage, XNORImage, Image, wireBlueImage,
			wireGreenImage, wireRedImage, plateImage, doorImage, ironDoorImage, bootImage, flagImage, boxImage,
			npcOldManImage, npcOldPlayerImage, playerImage;

	public CutsceneManager(GamePanel gp) {
		this.gp = gp;

		OBJ_Door door = new OBJ_Door(gp);
		doorImage = door.down1;

		OBJ_Door_Iron ironDoor = new OBJ_Door_Iron(gp);
		ironDoorImage = ironDoor.down1;

		OBJ_Boots boot = new OBJ_Boots(gp);
		bootImage = boot.down1;

		OBJ_Flag flag = new OBJ_Flag(gp, OBJ_Flag.YELLOW);
		flagImage = flag.down1;

		IT_Wire wire = new IT_Wire(gp, "horizontal_up", IT_Wire.BLUE);
		wireBlueImage = wire.down1;
		wire = new IT_Wire(gp, "horizontal_up", IT_Wire.RED);
		wireRedImage = wire.down1;
		wire = new IT_Wire(gp, "horizontal_up", IT_Wire.GREEN);
		wireGreenImage = wire.down1;

		IT_LogicalPort portaLogica = new IT_LogicalPort(gp, IT_LogicalPort.NOT, "right", IT_LogicalPort.WHITE);
		NOTImage = portaLogica.right1;
		portaLogica = new IT_LogicalPort(gp, IT_LogicalPort.AND, "right", IT_LogicalPort.WHITE);
		ANDImage = portaLogica.right1;
		portaLogica = new IT_LogicalPort(gp, IT_LogicalPort.OR, "right", IT_LogicalPort.WHITE);
		ORImage = portaLogica.right1;
		portaLogica = new IT_LogicalPort(gp, IT_LogicalPort.NOR, "right", IT_LogicalPort.WHITE);
		NORImage = portaLogica.right1;
		portaLogica = new IT_LogicalPort(gp, IT_LogicalPort.XOR, "right", IT_LogicalPort.WHITE);
		XORImage = portaLogica.right1;
		portaLogica = new IT_LogicalPort(gp, IT_LogicalPort.NAND, "right", IT_LogicalPort.WHITE);
		NANDImage = portaLogica.right1;
		portaLogica = new IT_LogicalPort(gp, IT_LogicalPort.XNOR, "right", IT_LogicalPort.WHITE);
		XNORImage = portaLogica.right1;

		IT_MetalPlate metalPlate = new IT_MetalPlate(gp);
		plateImage = metalPlate.down1;

		NPC_Box box = new NPC_Box(gp);
		boxImage = box.down1;

		NPC_OldMan npcOldman = new NPC_OldMan(gp);
		npcOldManImage = npcOldman.down1;

		NPC_OldPlayer npcOldPlayer = new NPC_OldPlayer(gp);
		npcOldPlayerImage = npcOldPlayer.down1;

		Player player = new Player(gp);
		playerImage = player.down1;

	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		switch (sceneNum) {
		case init:
			scene_opening();
			gp.stopMusic();
			break;
		case loading:
			scene_loading();
		}
	}

	public void scene_opening() {
		int pressEnterY = 650;
		String text;
		if (scenePhase == 0) {
			drawBlackBackground(1.0F);
			alpha += 0.005F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}

			text = "Em um futuro distante, em uma colônia isolada, \n"
					+ "um robô prisioneiro conhecido como L-0G1C foi aprisionado \n"
					+ "por desafiar as leis da programação.\n\n" + "Preso em um complexo de segurança máxima, \n"
					+ "ele descobre que a única forma de escapar \n"
					+ "é manipulando circuitos lógicos espalhados pelo local.\n\n"
					+ "Utilizando suas habilidades com operações binárias, \n"
					+ "L-0G1C deve resolver desafios cada vez mais difíceis \n"
					+ "para desbloquear portas e fugir deste lugar.\n\n";

			drawString(alpha, 35.0F, 100, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Pressione Enter para continuar)", 40);
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

			text = "Em um futuro distante, em uma colônia isolada, \n"
					+ "um robô prisioneiro conhecido como L-0G1C foi aprisionado \n"
					+ "por desafiar as leis da programação.\n\n" + "Preso em um complexo de segurança máxima, \n"
					+ "ele descobre que a única forma de escapar \n"
					+ "é manipulando circuitos lógicos espalhados pelo local.\n\n"
					+ "Utilizando suas habilidades com operações binárias, \n"
					+ "L-0G1C deve resolver desafios cada vez mais difíceis \n"
					+ "para desbloquear portas e fugir deste lugar.\n\n";

			drawString(alpha, 35.0F, 100, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Pressione Enter para continuar)", 40);
		}

		if (scenePhase == 2) {
			drawBlackBackground(1.0F);
			alpha += 0.01F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}

			text = "Será que ele conseguirá decifrar todos os enigmas \n" + "e finalmente alcançar a liberdade?\n\n"
					+ "Tudo depende de você.\n\n\n\n\n\n";

			drawString(alpha, 35.0F, 200, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Pressione Enter para continuar)", 40);
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

			text = "Será que ele conseguirá decifrar todos os enigmas \n" + "e finalmente alcançar a liberdade?\n\n"
					+ "Tudo depende de você.\n\n\n\n\n\n";

			drawString(alpha, 35.0F, 200, text, 40);
			drawString(alpha, 35.0F, pressEnterY, "(Pressione Enter para continuar)", 40);
		}

		if (scenePhase == 4) {
			drawBlackBackground(1.0F);
			alpha += 0.01F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}
			
			// Posição base para exibir as imagens
			int x = 150;
			int y = 80;
			int spacing = 50; // Espaçamento entre os itens

			// lado esquerdo
			g2.setComposite(AlphaComposite.getInstance(3, alpha));
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(35.0F));
			g2.drawString("<Simbologias / Portas Lógicas>", x + 90, y);
			y = 110;
			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(plateImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- Botão - Estado 0 quando não há caixa sobre ele.", x, y);

			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(plateImage, x - 60, y - 36, 50, 50, null);
			g2.drawImage(boxImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- Botão - Estado 1 quando há caixa, jogador ou NPC sobre ele.", x, y);

			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(NOTImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- NOT - Inverte o valor da condição.", x, y);

			y += spacing;
			g2.drawImage(ANDImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- AND - Verdadeiro se ambas as entradas forem verdadeiras.", x, y);

			y += spacing;
			g2.drawImage(ORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- OR - Verdadeiro se pelo menos uma entrada for verdadeira.", x, y);

			y += spacing;
			g2.drawImage(NORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- NOR - Verdadeiro apenas se ambas as entradas forem falsas.", x, y);

			y += spacing;
			g2.drawImage(XORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- XOR - Verdadeiro se apenas uma entrada for verdadeira.", x, y);

			y += spacing;
			g2.drawImage(NANDImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- NAND - Falso apenas se ambas as entradas forem verdadeiras.", x, y);

			y += spacing;
			g2.drawImage(XNORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- XNOR - Verdadeiro se ambas as entradas forem iguais.", x, y);

			y += spacing;
			g2.drawImage(wireBlueImage, x - 60, y - 36, 50, 50, null);
			g2.drawImage(wireRedImage, x - 60, y - 36, 50, 50, null);
			g2.drawImage(wireGreenImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- Fios: Indicam visualmente as conexões lógicas.", x, y);

			// lado direito cima
			x = (int) (gp.screenWidth / 1.3);
			y = 80;
			spacing = 50; // Espaçamento entre os itens
			g2.setFont(g2.getFont().deriveFont(35.0F));
			g2.drawString("<Objetos>", x, y);
			y = 110;
			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(doorImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Porta de Madeira", x, y);

			y += spacing;
			g2.drawImage(ironDoorImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Porta de Ferro", x, y);

			y += spacing;
			g2.drawImage(bootImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Bota (+ Velocidade)", x, y);

			y += spacing;
			g2.drawImage(flagImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Bandeira (Vitoria)", x, y);

			// lado direito baixo
			x = (int) (gp.screenWidth / 1.33);
			y = gp.screenHeight / 2 + 15;
			spacing = 50; // Espaçamento entre os itens
			g2.setFont(g2.getFont().deriveFont(35.0F));
			g2.drawString("<Personagens>", x, y);

			y = gp.screenHeight / 2 + 50;
			x = (int) (gp.screenWidth / 1.3);

			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(npcOldManImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Senhor", x, y);

			y += spacing;
			g2.drawImage(npcOldPlayerImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Outro Prisioneiro", x, y);

			y += spacing;
			g2.drawImage(playerImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Player", x, y);

			drawString(alpha, 35.0F, pressEnterY + 50, "(Pressione Enter para continuar)", 40);

			if (gp.keyH.enterPressed) {
				gp.keyH.enterPressed = false;
				++scenePhase;
			}
		}
		if (scenePhase == 5) {
			drawBlackBackground(1.0F);
			alpha -= 0.02F;
			if (alpha < 0.0F) {
				alpha = 0.0F;
				++scenePhase;
			}

			// Posição base para exibir as imagens
			int x = 150;
			int y = 80;
			int spacing = 50; // Espaçamento entre os itens

			// lado esquerdo
			g2.setComposite(AlphaComposite.getInstance(3, alpha));
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(35.0F));
			g2.drawString("<Simbologias / Portas Lógicas>", x + 90, y);
			y = 110;
			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(plateImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- Botão - Estado 0 quando não há caixa sobre ele.", x, y);

			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(plateImage, x - 60, y - 36, 50, 50, null);
			g2.drawImage(boxImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- Botão - Estado 1 quando há caixa, jogador ou NPC sobre ele.", x, y);

			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(NOTImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- NOT - Inverte o valor da condição.", x, y);

			y += spacing;
			g2.drawImage(ANDImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- AND - Verdadeiro se ambas as entradas forem verdadeiras.", x, y);

			y += spacing;
			g2.drawImage(ORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- OR - Verdadeiro se pelo menos uma entrada for verdadeira.", x, y);

			y += spacing;
			g2.drawImage(NORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- NOR - Verdadeiro apenas se ambas as entradas forem falsas.", x, y);

			y += spacing;
			g2.drawImage(XORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- XOR - Verdadeiro se apenas uma entrada for verdadeira.", x, y);

			y += spacing;
			g2.drawImage(NANDImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- NAND - Falso apenas se ambas as entradas forem verdadeiras.", x, y);

			y += spacing;
			g2.drawImage(XNORImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- XNOR - Verdadeiro se ambas as entradas forem iguais.", x, y);

			y += spacing;
			g2.drawImage(wireBlueImage, x - 60, y - 36, 50, 50, null);
			g2.drawImage(wireRedImage, x - 60, y - 36, 50, 50, null);
			g2.drawImage(wireGreenImage, x - 60, y - 36, 50, 50, null);
			g2.drawString("- Fios: Indicam visualmente as conexões lógicas.", x, y);

			// lado direito cima
			x = (int) (gp.screenWidth / 1.3);
			y = 80;
			spacing = 50; // Espaçamento entre os itens
			g2.setFont(g2.getFont().deriveFont(35.0F));
			g2.drawString("<Objetos>", x, y);
			y = 110;
			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(doorImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Porta de Madeira", x, y);

			y += spacing;
			g2.drawImage(ironDoorImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Porta de Ferro", x, y);

			y += spacing;
			g2.drawImage(bootImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Bota (+ Velocidade)", x, y);

			y += spacing;
			g2.drawImage(flagImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Bandeira (Vitoria)", x, y);

			// lado direito baixo
			x = (int) (gp.screenWidth / 1.33);
			y = gp.screenHeight / 2 + 15;
			spacing = 50; // Espaçamento entre os itens
			g2.setFont(g2.getFont().deriveFont(35.0F));
			g2.drawString("<Personagens>", x, y);

			y = gp.screenHeight / 2 + 50;
			x = (int) (gp.screenWidth / 1.3);

			y += spacing;
			g2.setFont(g2.getFont().deriveFont(30.0F));
			g2.drawImage(npcOldManImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Senhor", x, y);

			y += spacing;
			g2.drawImage(npcOldPlayerImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Outro Prisioneiro", x, y);

			y += spacing;
			g2.drawImage(playerImage, x - 70, y - 36, 50, 50, null);
			g2.drawString("- Player", x, y);

			drawString(alpha, 35.0F, pressEnterY + 50, "(Pressione Enter para continuar)", 40);

			if (gp.keyH.enterPressed) {
				gp.keyH.enterPressed = false;
				++scenePhase;
			}
		}

		if (scenePhase == 6) {
			drawBlackBackground(1.0F);
			alpha += 0.005F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}

			drawString(alpha, 35.0F, 150, "<Como Jogar>", 40);
			text = "Mover: [W/A/S/D]\nConfirmar: [ENTER]\nReiniciar Fase: [CTRL+R]\nPausar: [P]\nOpções: [ESC]\n\n";
			drawString(alpha, 35.0F, 210, text, 45);
			drawString(alpha, 35.0F, pressEnterY, "(Pressione Enter para começar a aventura)", 40);
			if (gp.keyH.enterPressed) {
				gp.keyH.enterPressed = false;
				++scenePhase;
			}
		}

		if (scenePhase == 7) {
			gp.keyH.enterPressed = false;
			sceneNum--;
			scenePhase = 0;
			gp.ui.titleScreenState = 0;
			gp.gameState = gp.playState;
			gp.currentMap = 0;
			gp.playerPositions();
			gp.highestUnlockedFase = 0; // Reset para fase inicial
			gp.saveLoad.save();
			gp.changeArea();
		}
	}

	public void scene_loading() {
		int pressEnterY = 400;
		int barWidth = 200; // Largura da barra de carregamento
		int barHeight = 20; // Altura da barra de carregamento
		int barX = gp.screenWidth / 2 - barWidth / 2; // Centralizar a barra
		int barY = pressEnterY + 50; // Posicionar a barra abaixo do texto
		String text;

		if (scenePhase == 0) {
			drawBlackBackground(1.0F);
			alpha += 0.005F;
			if (alpha > 1.0F) {
				alpha = 1.0F;
			}

			// Set the font before drawing
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35.0F));

			drawString(alpha, 35.0F, pressEnterY, "Carregando Fase...", 40); // Changed text here

			// Calcula o target para 3 segundos (assumindo 60 FPS)
			int targetCounter = 60 * 3;

			// Calcula a largura da barra preenchida com base no contador
			double progress = (double) counter / targetCounter;
			int filledBarWidth = (int) (barWidth * progress);

			// Desenha a borda da barra
			g2.setColor(Color.WHITE); // Cor da borda
			g2.drawRect(barX, barY, barWidth, barHeight);

			// Desenha a barra preenchida
			g2.setColor(Color.GREEN); // Cor do preenchimento
			g2.fillRect(barX, barY, filledBarWidth, barHeight);

			// Verifica se o contador chegou ao target
			if (counterReached(targetCounter)) {
				gp.keyH.enterPressed = false; // Mantive esta linha, mas ela não é mais usada para verificação
				scenePhase = 2; // Avança para a fase 2 para sumir com a barra e o fundo
				counter = 0; // Reseta o contador para a próxima fase
				// tenta fechar os clips
				gp.music.closeAllClips(); // Fecha todos os Clips de música
				gp.sfx.closeAllClips(); // Fecha todos os Clips de efeitos sonoros
			}

		}

		else if (scenePhase == 1) {
			drawBlackBackground(1.0F); // Garante que o fundo preto continue presente
			alpha -= 0.01F;
			if (alpha < 0.0F) {
				alpha = 0.0F;
				System.out.println("db1");
				gp.gameState = gp.playState; // Só muda para o jogo quando a transição some completamente
			}

			// Set the font before drawing
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35.0F));

			drawString(alpha, 35.0F, pressEnterY, "Carregando Fase...", 40); // Changed text here

			// Desenha a barra preenchida (completa)
			g2.setColor(Color.GREEN); // Cor do preenchimento
			g2.fillRect(barX, barY, barWidth, barHeight);

		} else if (scenePhase == 2) {
			drawBlackBackground(alpha);
			alpha -= 0.01F;
			if (alpha <= 0.0F) {
				alpha = 0.0F;

				// Garante que proxima_fase está atualizado
				gp.player.speedMultiplicator = 0;
				gp.proxima_fase = gp.faseMap[gp.currentMap];

				if (gp.proxima_fase != gp.fase_atual) {
					// Switch para música baseado no currentMap
					switch (gp.currentMap) { // Mais eficiente que vários if-else
					case 0:
						gp.playMusic(0);
						break;
					case 1:
						gp.playMusic(9);
						break;
					case 2:
						gp.playMusic(10);
						break;
					case 3:
						gp.playMusic(9);
						break;
					case 4:
						gp.playMusic(10);
						break;
					}

					gp.eHandler.newWorldX = gp.player.worldX;
					gp.eHandler.newWorldY = gp.player.worldY;
				} else {
					switch (gp.currentMap) { // Mais eficiente que vários if-else
					case 0:
						gp.playMusic(0);
						break;
					case 1:
						gp.playMusic(9);
						break;
					case 2:
						gp.playMusic(10);
						break;
					case 3:
						gp.playMusic(9);
						break;
					case 4:
						gp.playMusic(10);
						break;
					}
				}

				gp.fase_atual = gp.proxima_fase;

				gp.gameState = gp.playState; // Move gameState change to phase 2
				scenePhase = 0;
				sceneNum = -1;

			}
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
		String[] textSplit = text.split("\n");
		int textLength = textSplit.length;

		for (int i = 0; i < textLength; i++) {
			String line = textSplit[i];
			int x = gp.ui.getXforCenteredText(line);
			g2.drawString(line, x, y);
			y += lineHeight;
		}

		g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
	}
}
