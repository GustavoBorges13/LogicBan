package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import object.OBJ_Key;
import object.OBJ_Logo;
import object.OBJ_LogoUfcat;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, purisaB;

	BufferedImage keyImage, logoLogicBan, logoUFCAT;

	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int commandNum = 0;
	public int titleScreenState = 0; // the first screen, 1: the second screen
	public int creditScreenState = 0; // the first screen, 1: the second screen
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	int subState = 0;
	int counter = 0;
	public String currentDialogue = "";
	public boolean flagAux = true;

	// Controle da animação dos créditos
	public int creditY; // Começa mais abaixo da tela
	private int speed = 1; // Velocidade da animação (ajustável)
	public boolean animationFinished = false; // Para saber se a animação terminou
	private BufferedImage backgroundCache;
	// Índice da fase selecionada (linha * 5 + coluna)
	private float selectionOpacity = 0.5f; // Opacidade inicial da seleção
	// Variáveis para controle da transição do fundo
	private float timeOfDay = 0; // 0 (manhã) → 1 (noite)
	private boolean isDay = true;
	private float transitionSpeed = 0.00070f; // Ajuste para suavidade
	public boolean creditsAnimating = false; // Flag para controlar a animação dos créditos

	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.down1;

		OBJ_Logo logo = new OBJ_Logo(gp);
		logoLogicBan = logo.down1;

		OBJ_LogoUfcat ufcat = new OBJ_LogoUfcat(gp);
		logoUFCAT = ufcat.down1;

		creditY = gp.screenHeight;

	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			Color corSuperiorDia = new Color(135, 206, 235); // Azul claro (manhã)
			Color corInferiorDia = new Color(173, 216, 230); // Azul médio (tarde)

			renderBackground(corSuperiorDia, corInferiorDia, new Color(20, 24, 82), new Color(12, 14, 55));
			g2.drawImage(backgroundCache, 0, 0, null);

			update(); // Atualiza as variáveis de animação, etc.

			gp.particleManager.desenharParticulas((Graphics2D) g2);

			drawTitleScreen();

		}
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerKeys();
		}

		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}

		// OPTION STATE
		if (gp.gameState == gp.optionState) {
			drawOptionsScreen();
		}

		//

		// DIALOGUE STATE - NAO TEM

		// CHARACTER STATE - NAO TEM

		// GAME OVER STATE - NAO TEM

		// TRANSITION STATE
		if (gp.gameState == gp.transitionState) {
			drawTransition();
		}

	}

	public void drawButtonNewGame(double x, double y, int cmd) {
		BufferedImage btnImage = null;

		btnImage = gp.btnNovoJogo.btnClick;

		int imgX = gp.screenWidth - (int) (gp.tileSize * x);
		int imgY = gp.screenHeight - (int) (gp.tileSize * y);

		// Ajuste proporcional da altura
		double scala = 1.4;
		int imgH = (int) (btnImage.getHeight() / scala); // Mantendo a altura como estava antes

		// int imgW = (int) (imgH * aspectRatio); // Mantendo a proporção entre altura e
		// largura
		int imgW = (int) (btnImage.getWidth() / scala);

		// Define a transparência inicial (meio apagado)
		float opacity = 0.5f;
		if (commandNum == cmd) {
			opacity = 1f; // Torna o botão opaco quando está selecionado
		}

		// Aplica a transparência
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		// Desenha o botão com a opacidade definida
		g2.drawImage(btnImage, imgX, imgY, imgW, imgH, null);

		// Restaura a opacidade normal para os próximos elementos
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Se o botão estiver selecionado, desenha o ">"
		if (commandNum == cmd) {
			gp.btnNovoJogo.botaoSelecionado = true;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.BLACK);
			g2.drawString(">", imgX - 25, (int) (imgY + imgH / 1.6));
		} else {
			gp.btnNovoJogo.botaoSelecionado = false;
		}

		// Desenha o botão de som com animação (se necessário)
		if (gp.btnNovoJogo.botaoSelecionado == true) {
			if (gp.btnNovoJogo.estadoBotao == true) {
				// Se a animação estiver ativada, desenha o botão com a animação
				if (gp.btnNovoJogo.animation) {
					gp.btnNovoJogo.draw(g2, imgX, imgY, imgW, imgH); // Chama o método draw que lida com a animação
				} else {
					gp.btnNovoJogo.draw(g2, imgX, imgY, imgW, imgH);
				}
			}
		}
	}

	public void drawButtonBack(double x, double y, int cmd) {

		BufferedImage btnImage = null;

		btnImage = gp.btnVoltar.btnClick;

		int imgX = gp.screenWidth - (int) (gp.tileSize * x);
		int imgY = gp.screenHeight - (int) (gp.tileSize * y);

		// Ajuste proporcional da altura
		double scala = 1.4;
		int imgH = (int) (btnImage.getHeight() / scala); // Mantendo a altura como estava antes

		// int imgW = (int) (imgH * aspectRatio); // Mantendo a proporção entre altura e
		// largura
		int imgW = (int) (btnImage.getWidth() / scala);

		// Define a transparência inicial (meio apagado)
		float opacity = 0.5f;
		if (commandNum == cmd) {
			opacity = 1f; // Torna o botão opaco quando está selecionado
		}

		// Aplica a transparência
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		// Desenha o botão com a opacidade definida
		g2.drawImage(btnImage, imgX, imgY, imgW, imgH, null);

		// Restaura a opacidade normal para os próximos elementos
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Se o botão estiver selecionado, desenha o ">"
		if (commandNum == cmd) {
			gp.btnVoltar.botaoSelecionado = true;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.BLACK);
			g2.drawString(">", imgX - 25, (int) (imgY + imgH / 1.6));
		} else {
			gp.btnVoltar.botaoSelecionado = false;
		}

		// Desenha o botão de som com animação (se necessário)
		if (gp.btnVoltar.botaoSelecionado == true) {
			if (gp.btnVoltar.estadoBotao == true) {
				// Se a animação estiver ativada, desenha o botão com a animação
				if (gp.btnVoltar.animation) {
					gp.btnVoltar.draw(g2, imgX, imgY, imgW, imgH); // Chama o método draw que lida com a animação
				} else {
					gp.btnVoltar.draw(g2, imgX, imgY, imgW, imgH);
				}
			}
		}
	}

	public void drawButtonCredits(int x, int y) {
		BufferedImage btnImage = null;

		btnImage = gp.btnCreditos.btnClick;

		int imgX = (int) (x + gp.tileSize * 3.3);
		int imgY = (int) (y + gp.tileSize * 6.5);

		// Ajuste proporcional da altura
		double scala = 1.4;
		int imgH = (int) (btnImage.getHeight() / scala); // Mantendo a altura como estava antes

		// int imgW = (int) (imgH * aspectRatio); // Mantendo a proporção entre altura e
		// largura
		int imgW = (int) (btnImage.getWidth() / scala);

		// Define a transparência inicial (meio apagado)
		float opacity = 0.5f;
		if (commandNum == 1) {
			opacity = 1f; // Torna o botão opaco quando está selecionado
		}

		// Aplica a transparência
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		// Desenha o botão com a opacidade definida
		g2.drawImage(btnImage, imgX, imgY, imgW, imgH, null);

		// Restaura a opacidade normal para os próximos elementos
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Se o botão estiver selecionado, desenha o ">"
		if (commandNum == 1) {
			gp.btnCreditos.botaoSelecionado = true;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.BLACK);
			g2.drawString(">", imgX - 30, (int) (imgY + imgH / 1.6));
		} else {
			gp.btnCreditos.botaoSelecionado = false;
		}

		// Desenha o botão de som com animação (se necessário)
		if (gp.btnCreditos.botaoSelecionado == true) {
			if (gp.btnCreditos.estadoBotao == true) {
				// Se a animação estiver ativada, desenha o botão com a animação
				if (gp.btnCreditos.animation) {
					gp.btnCreditos.draw(g2, imgX, imgY, imgW, imgH); // Chama o método draw que lida com a animação
				} else {
					gp.btnCreditos.draw(g2, imgX, imgY, imgW, imgH);
				}
			}
		}
	}

	public void drawButtonStartGame(int x, int y) {

		BufferedImage btnImage = null;

		btnImage = gp.btnStart.btnClick;

		int imgX = (int) (x + gp.tileSize * 3.3);
		int imgY = (int) (y + gp.tileSize * 5);

		// Ajuste proporcional da altura
		double scala = 1.4;
		int imgH = (int) (btnImage.getHeight() / scala); // Mantendo a altura como estava antes

		// int imgW = (int) (imgH * aspectRatio); // Mantendo a proporção entre altura e
		// largura
		int imgW = (int) (btnImage.getWidth() / scala);

		// Define a transparência inicial (meio apagado)
		float opacity = 0.5f;
		if (commandNum == 0) {
			opacity = 1f; // Torna o botão opaco quando está selecionado
		}

		// Aplica a transparência
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		// Desenha o botão com a opacidade definida
		g2.drawImage(btnImage, imgX, imgY, imgW, imgH, null);

		// Restaura a opacidade normal para os próximos elementos
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Se o botão estiver selecionado, desenha o ">"
		if (commandNum == 0) {
			gp.btnStart.botaoSelecionado = true;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.BLACK);
			g2.drawString(">", imgX - 30, (int) (imgY + imgH / 1.6));
		} else {
			gp.btnStart.botaoSelecionado = false;
		}

		// Desenha o botão de som com animação (se necessário)
		if (gp.btnStart.botaoSelecionado == true) {
			if (gp.btnStart.estadoBotao == true) {
				// Se a animação estiver ativada, desenha o botão com a animação
				if (gp.btnStart.animation) {
					gp.btnStart.draw(g2, imgX, imgY, imgW, imgH); // Chama o método draw que lida com a animação
				} else {
					gp.btnStart.draw(g2, imgX, imgY, imgW, imgH);
				}
			}
		}
	}

	public void drawButtonExit(int x, int y) {
		BufferedImage btnImage = null;

		btnImage = gp.btnFechar.btnClick;

		int imgX = (int) (x + gp.tileSize * 3.3);
		int imgY = (int) (y + gp.tileSize * 8.0);

		// Ajuste proporcional da altura
		double scala = 1.4;
		int imgH = (int) (btnImage.getHeight() / scala); // Mantendo a altura como estava antes

		// int imgW = (int) (imgH * aspectRatio); // Mantendo a proporção entre altura e
		// largura
		int imgW = (int) (btnImage.getWidth() / scala);

		// Define a transparência inicial (meio apagado)
		float opacity = 0.5f;
		if (commandNum == 2) {
			opacity = 1f; // Torna o botão opaco quando está selecionado
		}

		// Aplica a transparência
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		// Desenha o botão com a opacidade definida
		g2.drawImage(btnImage, imgX, imgY, imgW, imgH, null);

		// Restaura a opacidade normal para os próximos elementos
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Se o botão estiver selecionado, desenha o ">"
		if (commandNum == 2) {
			gp.btnFechar.botaoSelecionado = true;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.BLACK);
			g2.drawString(">", imgX - 25, (int) (imgY + imgH / 1.6));
		} else {
			gp.btnFechar.botaoSelecionado = false;
		}

		// Desenha o botão de som com animação (se necessário)
		if (gp.btnFechar.botaoSelecionado == true) {
			if (gp.btnFechar.estadoBotao == true) {
				// Se a animação estiver ativada, desenha o botão com a animação
				if (gp.btnFechar.animation) {
					gp.btnFechar.draw(g2, imgX, imgY, imgW, imgH); // Chama o método draw que lida com a animação
				} else {
					gp.btnFechar.draw(g2, imgX, imgY, imgW, imgH);
				}
			}
		}
	}

	public void drawButtonSound(double x, double y, int cmd) {
		BufferedImage btnImage = null;

		if ((gp.sfx.volumeScale > 0 && gp.music.volumeScale > 0)) {
			btnImage = gp.btnSom.btnOn;
		} else if ((gp.sfx.volumeScale > 0 && gp.music.volumeScale == 0)
				|| (gp.sfx.volumeScale == 0 && gp.music.volumeScale > 0)) {
			btnImage = gp.btnSom.btnOn;
		} else if (gp.sfx.volumeScale == 0 && gp.music.volumeScale == 0) {
			btnImage = gp.btnSom.btnOff;
		}

		int imgX = gp.screenWidth - (int) (gp.tileSize * x);
		int imgY = gp.screenHeight - (int) (gp.tileSize * y);

		// Ajuste proporcional da altura
		double scala = 1.4;
		int imgH = (int) (btnImage.getHeight() / scala);
		int imgW = (int) (btnImage.getWidth() / scala);

		// Define a transparência inicial
		float opacity = (commandNum == cmd) ? 1f : 0.5f;

		// Aplica a transparência antes de desenhar
		Graphics2D g2d = (Graphics2D) g2.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		// Desenha o botão com a opacidade definida
		g2d.drawImage(btnImage, imgX, imgY, imgW, imgH, null);
		g2d.dispose(); // Libera os recursos do Graphics2D para evitar problemas

		// Se o botão estiver selecionado, desenha a seta ">"
		if (commandNum == cmd) {
			gp.btnSom.botaoSelecionado = true;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.BLACK);
			g2.drawString(">", imgX - 30, (int) (imgY + imgH / 1.6));
		} else {
			gp.btnSom.botaoSelecionado = false;
		}

		// Agora desenha a animação apenas se o botão estiver selecionado
		if (gp.btnSom.botaoSelecionado && gp.btnSom.animation) {
			gp.btnSom.draw(g2, imgX, imgY, imgW, imgH); // Certifique-se de que este método respeita a opacidade
		}
	}

	public void drawPlayerKeys() {

		if (gameFinished == true) {

			String text;
			int textLengt;
			int x;
			int y;

			// texto 1
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.white);
			text = "Você encontrou o tesouro!";
			textLengt = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // tamanho do texto
			x = gp.screenWidth / 2 - textLengt / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 3);
			g2.drawString(text, x, y);

			// texto 2
			text = "Seu tempo é: " + dFormat.format(playTime) + "!";
			textLengt = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // tamanho do texto
			x = gp.screenWidth / 2 - textLengt / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 4);
			g2.drawString(text, x, y);

			// texto 3
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.yellow);
			text = "Parabéns!";
			textLengt = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // tamanho do texto
			x = gp.screenWidth / 2 - textLengt / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 2);
			g2.drawString(text, x, y);

			gp.gameThread = null; // para o jogo!.

		} else {

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 8, gp.tileSize, gp.tileSize, null);
			g2.drawString("x " + gp.player.hasKey, 74, gp.tileSize);

			// TIME
			playTime += (double) 1 / 60;
			g2.drawString("Tempo: " + dFormat.format(playTime),
					(gp.maxScreenCol * gp.tileSize) - (int) (gp.tileSize * 5.5), gp.tileSize);

			// MESSAGE
			if (messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
				g2.drawString(message, gp.tileSize / 2, (int) (gp.tileSize * 2.4));

				messageCounter++;

				if (messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}

	public void drawTitleScreen() {

		// MENU PRINCIPAL
		if (titleScreenState == 0) {

			// TITLE NAME - LOGO
			int x = gp.screenWidth / 2 - (gp.tileSize * 5);
			int y = gp.tileSize;
			g2.drawImage(logoLogicBan, x + 20, y, logoLogicBan.getWidth(), logoLogicBan.getHeight(), null);

			// Desenha botoes do menu do jogo!
			drawButtonStartGame(x, y);
			drawButtonCredits(x, y);
			drawButtonExit(x, y);
			drawButtonSound(4.8, 1.7, 3);

		}
		// SUBMENU DE INICIAR!
		else if (titleScreenState == 1) {

			// Definir posição e tamanho da caixa de diálogo
			int x = gp.screenWidth / 15;
			int y = gp.screenHeight / 4;
			int width = gp.screenWidth - (gp.tileSize * 4);
			int height = gp.screenHeight - (gp.tileSize * 7);

			// Fundo da caixa de diálogo
			g2.setColor(new Color(0, 0, 0, 200)); // Preto semi-transparente (RGBA)
			g2.fillRect(x, y, width, height);

			// Borda branca
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(x, y, width - 3, height);

			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
			String text = "Seletor de fases";
			int titleX = getXforCenteredText(text);
			int titleY = gp.tileSize * 3;

			// SHADOW
			g2.setColor(Color.black);
			g2.drawString(text, titleX + 5, titleY + 5);

			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, titleX, titleY);

			// CÁLCULO DO GRID (5x2)
			int cellWidth = width / 5;
			int cellHeight = height / 2;

			// Lista de nomes para cada fase (simulado)
			String[] fases = { "Fase 1", "Fase 2", "Fase 3", "Fase 4", "Fase 5", "Fase 6", "Fase 7", "Fase 8", "Fase 9",
					"Fase 10" };

			// Renderizando as imagens e os textos nas células
			for (int row = 0; row < 2; row++) {
				for (int col = 0; col < 5; col++) {
					int index = row * 5 + col; // Índice linear
					int cellX = x + (col * cellWidth);
					int cellY = y + (row * cellHeight);

					if (index <= gp.highestUnlockedFase) {
						// Se a fase está liberada
						g2.setColor(new Color(100, 100, 255, 100)); // Azul normal
					} else {
						// Se a fase está bloqueada
						g2.setColor(new Color(50, 50, 50, 0)); // Cinza escuro (fase bloqueada)
					}
					g2.fillRect(cellX + 5, cellY + 5, cellWidth - 10, cellHeight - 10);

					// Texto centralizado
					g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
					String faseNome = fases[index];
					int textX = cellX + (cellWidth / 2) - (g2.getFontMetrics().stringWidth(faseNome) / 2);
					int textY = cellY + (cellHeight / 2) + 10;

					g2.setColor(Color.BLACK);
					g2.drawString(faseNome, textX + 2, textY + 2); // Sombra preta
					g2.setColor(Color.WHITE);
					g2.drawString(faseNome, textX, textY);

					// Efeito de seleção
					if (index == gp.ui.commandNum) {
						if (index <= gp.highestUnlockedFase) {
							g2.setColor(new Color(0, 255, 0, 80)); // Seleção normal
						} else {
							g2.setColor(new Color(255, 0, 0, 100)); // Vermelho se tentar selecionar fase bloqueada
						}
						g2.fillRect(cellX + 5, cellY + 5, cellWidth - 10, cellHeight - 10);
					}
				}
			}

			// Adicionando opção "Voltar"
			drawButtonBack(20.5, 2.2, 10);
			drawButtonNewGame(14.4, 2.2, 11);

		}
		// CREDITOS
		else if (titleScreenState == 2) {

			// -----------------------------------------------------
			// 1. LOGICBAN LOGO (NO TOPO)
			// -----------------------------------------------------
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));
			int xLogo = gp.screenWidth / 2 - (logoLogicBan.getWidth() / 2);
			int yLogoLogicBan = creditY; // Posição inicial da logo (acompanha a animação)
			g2.drawImage(logoLogicBan, xLogo, yLogoLogicBan, logoLogicBan.getWidth(), logoLogicBan.getHeight(), null);

			// -----------------------------------------------------
			// 2. CRÉDITOS (EMBAIXO DA LOGO)
			// -----------------------------------------------------
			int y = yLogoLogicBan + logoLogicBan.getHeight() + gp.tileSize; // Começa após a logo
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));

			String[] credits = { "Desenvolvido pela Equipe Bug Hunters!", "", "Game Design:", "Gustavo, Davi, Rafael",
					"", "UI Menu:", "Rafael, Michael", "", "Gameplay Programming:", "Davi, Gustavo, Marcos", "",
					"Level Design:", "Davi, Gustavo, Marcos", "", "UI & Icon Design:", "Marcos, Luis, Rafael", "",
					"Credits Implementation:", "Marcos, Gustavo", "", "SFX:", "Rafael, Marcos, Michael", "",
					"Quality Assurance:", "Davi, Gustavo", "", "Art Design:", "Luis", "", "Tutorial & Repository:",
					"Gustavo", "", "Agradecemos por jogar LogicBan!", "Obrigado pelo seu apoio!" };

			int lineSpacing = 80;
			// Desenha as linhas na ordem correta (primeira linha primeiro)
			for (String line : credits) {
				if (!line.isEmpty()) {
					int textWidth = g2.getFontMetrics().stringWidth(line);
					int x = (gp.screenWidth - textWidth) / 2;

					if (line.endsWith(":")) {
						drawTextWithBorder(g2, line, x, y, Color.YELLOW, Color.BLACK);
					} else {
						drawTextWithBorder(g2, line, x, y, Color.WHITE, Color.BLACK);
					}
					y += lineSpacing; // Aumenta Y para descer (não subir)
				}
			}

			// -----------------------------------------------------
			// 3. UFCAT LOGO (NO FINAL DOS CRÉDITOS)
			// -----------------------------------------------------
			int yLogoUFCAT = y + gp.tileSize; // Espaço após o último crédito
			g2.drawImage(logoUFCAT, xLogo, yLogoUFCAT, logoUFCAT.getWidth(), logoUFCAT.getHeight(), null);

			// -----------------------------------------------------
			// 4. BOTÕES (POSIÇÃO FIXA)
			// --------------------------------------------------
			drawButtonBack(28.8, 1.7, 0);
			drawButtonSound(4.8, 1.7, 1);
		}
	}

	// Método para atualizar o efeito de fade-in/fade-out da seleção
	public void updateOpacity() {
		selectionOpacity -= 0.02f; // Diminui a opacidade suavemente
		if (selectionOpacity < 0.3f)
			selectionOpacity = 0.3f; // Mantém um mínimo de opacidade
	}

	public void drawPauseScreen() {
		// Configura o fundo com transparência (opacidade de 0.5)
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Opacidade de 50%
		g2.setColor(new Color(0, 0, 0)); // Cor preta
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight); // Desenha o fundo com transparência

		// Restaura a opacidade para 100% para desenhar o texto em branco
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Opacidade total

		// Configura o texto
		if (gp.FullScreenOn == true) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 150F));
		} else {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
		}

		String text = "PAUSED";
		int x = getXforCenteredText(text); // Calcula a posição centralizada para o texto
		int y = (gp.screenHeight / 2) + 1 * gp.tileSize; // Posição do texto na tela

		// Desenha o texto em branco
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y); // Desenha o texto
	}

	// Método para desenhar o texto com borda
	private void drawTextWithBorder(Graphics2D g2, String text, int x, int y, Color textColor, Color borderColor) {
		g2.setColor(borderColor);
		g2.drawString(text, x - 1, y - 1);
		g2.drawString(text, x + 1, y - 1);
		g2.drawString(text, x - 1, y + 1);
		g2.drawString(text, x + 1, y + 1);

		g2.setColor(textColor);
		g2.drawString(text, x, y);
	}

	public void updateCredits() {

		if (!animationFinished) {
			creditY -= speed;

			// Verifica se o texto saiu completamente da tela
			if (creditY < -2450) { // Ajuste conforme a altura do seu texto
				animationFinished = true;
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState = 0;
			}
		}
	}

	// Método para renderizar o fundo com transição entre dia e noite
	public void renderBackground(Color corSuperiorDia, Color corInferiorDia, Color corSuperiorNoite,
			Color corInferiorNoite) {
		// Verifica se backgroundCache é null e inicializa se necessário
		if (backgroundCache == null) {
			backgroundCache = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		}

		int width = gp.screenWidth;
		int height = gp.screenHeight;

		// Interpolação entre as cores do dia e da noite
		int rSuperior = (int) (corSuperiorDia.getRed() * (1 - timeOfDay) + corSuperiorNoite.getRed() * timeOfDay);
		int gSuperior = (int) (corSuperiorDia.getGreen() * (1 - timeOfDay) + corSuperiorNoite.getGreen() * timeOfDay);
		int bSuperior = (int) (corSuperiorDia.getBlue() * (1 - timeOfDay) + corSuperiorNoite.getBlue() * timeOfDay);

		int rInferior = (int) (corInferiorDia.getRed() * (1 - timeOfDay) + corInferiorNoite.getRed() * timeOfDay);
		int gInferior = (int) (corInferiorDia.getGreen() * (1 - timeOfDay) + corInferiorNoite.getGreen() * timeOfDay);
		int bInferior = (int) (corInferiorDia.getBlue() * (1 - timeOfDay) + corInferiorNoite.getBlue() * timeOfDay);

		Color corSuperiorAtual = new Color(rSuperior, gSuperior, bSuperior);
		Color corInferiorAtual = new Color(rInferior, gInferior, bInferior);

		// Criação do gradiente
		GradientPaint gp = new GradientPaint(0, 0, corSuperiorAtual, 0, height, corInferiorAtual);
		Graphics2D g2 = backgroundCache.createGraphics();
		g2.setPaint(gp);
		g2.fillRect(0, 0, width, height);
		g2.dispose();
	}

	public void drawOptionsScreen() {

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));

		// SUB WINDOW
		int frameX = (int) (gp.tileSize * 10.3 - 35);
		int frameY = (int) (gp.tileSize + 49);
		int frameWidth = (int) (gp.tileSize * 9.2);
		int frameHeight = (int) (gp.tileSize * 12);
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subState) {
		case 0:
			options_top(frameX, frameY);
			break;
		case 1:
			options_fullScreenNotification(frameX, frameY);
			break;
		case 2:
			options_control(frameX, frameY);
			break;
		case 3:
			options_endGameConfirmation(frameX, frameY);
			break;
		}

		gp.keyH.enterPressed = false;
	}

	public void options_top(int frameX, int frameY) {

		int textX;
		int textY;

		// TITLE
		String text = "Options";

		textX = getXforCenteredText(text) - 20;
		textY = frameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		g2.drawString(text, textX, textY);

		// FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString("Full Screen", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				if (gp.FullScreenOn == false) {
					gp.FullScreenOn = true;
				} else if (gp.FullScreenOn == true) {
					gp.FullScreenOn = false;
				}
				subState = 1;
			}
		}

		// MUSIC
		textY += gp.tileSize;
		g2.drawString("Musica", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}

		// SFX
		textY += gp.tileSize;
		g2.drawString("SFX", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
		}

		// CONTROL
		textY += gp.tileSize;
		g2.drawString("Controles", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}

		// Walk Type
		textY += gp.tileSize;
		g2.drawString("Modo", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
		}

		// END GAME
		textY += gp.tileSize;
		g2.drawString("Encerrar jogo", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}

		// BACK
		textY += (int) (gp.tileSize * 2.8);
		g2.drawString("Voltar", textX, textY);
		if (commandNum == 6) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}

		// FULL SCREEN CHECK BOX
		textX = frameX + (int) (gp.tileSize * 5);
		textY = frameY + gp.tileSize * 2 + 26;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if (gp.FullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}

		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		// SFX VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		volumeWidth = 24 * gp.sfx.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		// WALK TYPE
		text = gp.player.walkType;
		textY += gp.tileSize * 2.5;
		g2.drawString(text, textX, textY);

		gp.config.saveConfig();

	}

	public void options_fullScreenNotification(int frameX, int frameY) {

		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		currentDialogue = "The change will take \neffect after restarting \nthe game.";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// BACK
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}

	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;

		// TITLE
		String text = "Control";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		g2.setStroke(new BasicStroke(1));
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Confirm", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Options", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Debug Data", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Debug Map", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Restart level", textX, textY);
		textY += gp.tileSize;

		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 3;
		g2.drawString("WASD", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY);
		textY += gp.tileSize;
		g2.drawString("P", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;
		g2.drawString("M", textX, textY);
		textY += gp.tileSize;
		g2.drawString("CTRL + M", textX, textY);
		textY += gp.tileSize;
		g2.drawString("CTRL + R", textX, textY);
		textY += gp.tileSize;

		// BACK
		textX = frameX + gp.tileSize;
		textY = (int) (frameY + gp.tileSize * 11);
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}

	}

	public void options_endGameConfirmation(int frameX, int frameY) {

		int textX = frameX + gp.tileSize + 12;
		int textY = frameY + gp.tileSize * 3;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		currentDialogue = "         Sair do jogo e \n voltar para a tela inicial?";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// YES
		String text = "Sim";
		textX = getXforCenteredText(text) - gp.tileSize;
		textY += gp.tileSize * 3;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState = 0;
				gp.stopMusic();
				gp.playMusic(5);

				gp.restart();
			}
		}

		// NO
		text = "Não";
		textX = getXforCenteredText(text) - gp.tileSize;
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}

	public void drawTransition2() {
		counter++;
		g2.setColor(new Color(0, 0, 0, counter * 5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (counter == 50) {
			counter = 0;
		}
	}

	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0, 0, 0, counter * 5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;

			// Atualize o currentMap com o novo mapa (índice)
			int newMap = gp.eHandler.tempMap; // índice do novo mapa
			gp.currentMap = newMap;

			// Atualiza as posições do player
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
			gp.playSFX(8);

			System.out.println("new Map: " + newMap + " HighetsUnlockedFase " + gp.highestUnlockedFase);
			// Se atingiu uma nova fase (índice) maior, atualiza highestUnlockedFase
			if (newMap > gp.highestUnlockedFase) {
				gp.highestUnlockedFase = newMap;
				gp.saveLoad.save(); // Salva apenas se desbloqueou nova fase
				gp.ui.currentDialogue = "The progress has been saved";
				System.out.println("Progresso salvo. Fase[" + newMap + "]: " + gp.faseMap[newMap]);
			}

			gp.changeArea();
			// gp.restart();
		}
	}

	public void updateBackground() {
		// Atualiza o tempo do dia (aumenta lentamente)
		if (isDay) {
			timeOfDay += transitionSpeed;
			if (timeOfDay >= 1) {
				timeOfDay = 1;
				isDay = false; // Começa a reverter para o dia
			}
		} else {
			timeOfDay -= transitionSpeed;
			if (timeOfDay <= 0) {
				timeOfDay = 0;
				isDay = true; // Começa a reverter para a noite
			}
		}
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(9, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	// Atualizar a animação do background e das partículas
	public void update() {
		// Atualizar a transição do fundo (dia/noite)
		if (isDay) {
			timeOfDay += transitionSpeed; // Transição para noite
			if (timeOfDay >= 1) {
				isDay = false;
			}
		} else {
			timeOfDay -= transitionSpeed; // Transição para dia
			if (timeOfDay <= 0) {
				isDay = true;
			}
		}
		gp.particleManager.update();

	}

}
