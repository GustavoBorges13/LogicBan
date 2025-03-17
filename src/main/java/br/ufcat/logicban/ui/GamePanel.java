package br.ufcat.logicban.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.ufcat.logicban.button.Button_Continuar;
import br.ufcat.logicban.button.Button_Creditos;
import br.ufcat.logicban.button.Button_Fechar;
import br.ufcat.logicban.button.Button_Menu;
import br.ufcat.logicban.button.Button_NovoJogo;
import br.ufcat.logicban.button.Button_ProximaFase;
import br.ufcat.logicban.button.Button_Som;
import br.ufcat.logicban.button.Button_Start;
import br.ufcat.logicban.button.Button_Voltar;
import br.ufcat.logicban.data.SaveLoad;
import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.entity.NPC_Box;
import br.ufcat.logicban.entity.Player;
import br.ufcat.logicban.object.OBJ_Door;
import br.ufcat.logicban.object.OBJ_Door_Iron;
import br.ufcat.logicban.tile.TileManager;
import br.ufcat.logicban.tile_interactive.IT_LogicalPort;
import br.ufcat.logicban.tile_interactive.IT_MetalPlate;
import br.ufcat.logicban.tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = -2905871651319130202L;

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	final double proportion = 1.375; // Razão entre 22 e 16

	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = (int) (22 * proportion);
	public final int maxScreenRow = (int) (12 * proportion);
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	// WORLD SETTINGS
	public int maxWorldCol;
	public int maxWorldRow;
	public final int worldWidth = tileSize * maxScreenCol;
	public final int worldHeigth = tileSize * maxScreenRow;
	public final int maxMap = 10;
	public int currentMap;
	// FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean FullScreenOn = false;

	// FPS
	int FPS = 60;
	int drawCount = 0;

	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public Sound music = new Sound();
	public Sound sfx = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	public SaveLoad saveLoad = new SaveLoad(this);
	Button_Som btnSom = new Button_Som(this);
	Button_Start btnStart = new Button_Start(this);
	Button_Voltar btnVoltar = new Button_Voltar(this);
	Button_Creditos btnCreditos = new Button_Creditos(this);
	Button_Fechar btnFechar = new Button_Fechar(this);
	Button_NovoJogo btnNovoJogo = new Button_NovoJogo(this);
	Button_Menu btnMenu = new Button_Menu(this);
	Button_ProximaFase btnProximaFase = new Button_ProximaFase(this);
	Button_Continuar btnContinuar = new Button_Continuar(this);
	Thread gameThread;

	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][50];
	public Entity npc[][] = new Entity[maxMap][50];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	ArrayList<Entity> entityList = new ArrayList<>();
	ParticleManager particleManager = new ParticleManager(this);; // Instância do ParticleManager
	public Entity wire[][] = new Entity[maxMap][70];
	public int doorIndex = -1; // Índice da porta no array gp.obj (inicializado como -1)
	public int doorWorldX; // Posição X da porta no mundo
	public int doorWorldY; // Posição Y da porta no mundo
	public ArrayList<Entity> boxList = new ArrayList<Entity>();

	// public ArrayList<Box> boxes = new ArrayList<>();

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int optionState = 4;
	public final int transitionState = 5;
	public final int nextPhaseState = 6;

	// AREA Change
	public int fase_atual;
	public int proxima_fase;
	public int highestUnlockedFase; // Adicione esta linha
	public String nova_direcao_player = "down";
	public int[] faseMap = { 50, 51, 52, 53 }; // Exemplo para 10 fases

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setIgnoreRepaint(true); // Importante para fullscreen
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

		// Habilite aceleração de hardware:
	}

	public void setupGame() {

		player.speed = player.speedAux;

		aSetter.setWires();
		aSetter.setObject();
		aSetter.setInteractiveTile();
		aSetter.setNPC();

		// fase_atual = fase1;
		gameState = titleState;
		System.out.println(gameState);
		playerPositions();

		playMusic(5); // tocar musica em loop
		// stopMusic(); // debug - teste sem musica

		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();

		if (FullScreenOn == true) {
			setFullScreen();
		}

	}

	public void limpeza() {
		eHandler = new EventHandler(this);
		// Limpa o array iTile antes de redefinir os tiles interativos
		for (int i = 0; i < iTile[currentMap].length; i++) {
			iTile[currentMap][i] = null;
		}

		// Limpa o array npcs antes de redefinir os npcs
		for (int i = 0; i < npc[currentMap].length; i++) {
			npc[currentMap][i] = null;
		}

		// Limpa o array objetos antes de redefinir os objetos
		for (int i = 0; i < obj[currentMap].length; i++) {
			obj[currentMap][i] = null;
		}

		// Limpa o array wires antes de redefinir os wires
		for (int i = 0; i < wire[currentMap].length; i++) {
			wire[currentMap][i] = null;
		}
	}

	public void restart() {
		ui.playTime = 0;

		proxima_fase = faseMap[currentMap];
		fase_atual = currentMap;

		player.speed = player.speedAux;
		limpeza();
		aSetter.setWires();
		aSetter.setObject(); // Garante que os objetos sejam redefinido
		aSetter.setInteractiveTile();
		aSetter.setNPC();

		playerPositions();
		// Restaura o estado inicial da porta
		doorIndex = -1;
	}

	public void playerPositions() {
		switch (currentMap) {
		case 0:
			player.worldX = tileSize * 11;
			player.worldY = tileSize * 9;
			break;
		case 1:
			player.worldX = tileSize * 9;
			player.worldY = tileSize * 9;
			break;
		case 2:
			player.worldX = tileSize * 1;
			player.worldY = tileSize * 0;
			break;
		case 3:
			player.worldX = tileSize * 11;
			player.worldY = tileSize * 10;
			break;
		}
		eHandler.oldSpeed = player.speed - (2 * player.speedMultiplicator);
		player.speedMultiplicator = 0;
	}

	public void setFullScreen() {
		/*
		 * GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 * GraphicsDevice gd = ge.getDefaultScreenDevice();
		 * gd.setFullScreenWindow(Main.window);
		 * 
		 * // GET FULL SCREEN WIDTH AND HEIGTH screenWidth2 = Main.window.getWidth();
		 * screenHeight2 = Main.window.getHeight();
		 */

		// Garantir que estamos pegando o mesmo monitor onde a janela foi posicionada
		Rectangle bounds = Main.window.getGraphicsConfiguration().getBounds();

		Main.window.setBounds(bounds); // Ajusta a janela ao monitor correto
		Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);

		screenWidth2 = bounds.width;
		screenHeight2 = bounds.height;
		// fator de deslocamento a ser usado pelo ouvinte do mouse ou ouvinte de
		// movimento do mouse se você estiver usando o cursor no seu jogo. Multiplique
		// seu e.getX()e.getY() por isso. fullScreenOffsetFactor = (float) screenWidth /
		// (float) screenWidth2; }
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		int timer = 0;
		int drawCount = this.drawCount;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				// repaint();
				drawToTempScreen(); // draw everythin to the buffered image
				drawToScreen(); // draw the buffered image to the screen
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				// System.out.println("FPS: " + drawCount);
				this.drawCount = drawCount;
				drawCount = 0;
				timer = 0;
			}

		}
	}

	public void update() {

		if (gameState == titleState) {
			btnSom.update(); // Atualiza a animação do botão
			btnStart.update();
			btnFechar.update();
			btnCreditos.update();
			ui.updateBackground();
			if (ui.titleScreenState == 2) {
				ui.updateCredits();
			}
		}
		if (gameState == playState) {

			// PLAYER
			player.update();
			// NPC
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}

			// interactive tiles
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}

			NPC_Box box = null;
			for (int i = 0; i < npc[currentMap].length; i++) {
				if (npc[currentMap][i] instanceof NPC_Box && npc[currentMap][i] != null) {
					box = (NPC_Box) npc[currentMap][i];
					break;
				}
			}

			if (box != null) {
				for (int i = 0; i < obj[currentMap].length; i++) {
					if (obj[currentMap][i] instanceof OBJ_Door_Iron && obj[currentMap][i] != null) {
						OBJ_Door_Iron door = (OBJ_Door_Iron) obj[currentMap][i];
						for (IT_LogicalPort port : box.logicalPortList) {
							if (port.id == door.controllingPortID) {
								if (port.outputState) {
									// Se a porta está fechada, abra-a
									door.openDoor(); // Chama o método para "abrir" a porta
									ui.showMessage("Você abriu a porta!"); // debug em UI
									// System.out.println("Porta de ferro " + door.controllingPortID + " aberta!");
								} else {
									// Se a porta está aberta, feche-a
									door.closeDoor(); // Chama o método para "fechar" a porta
									// System.out.println("Porta de ferro " + door.controllingPortID + " fechada.");
								}
								break;
							}
						}

					} else if (obj[currentMap][i] instanceof OBJ_Door && obj[currentMap][i] != null) {
						OBJ_Door door = (OBJ_Door) obj[currentMap][i];

						for (IT_LogicalPort port : box.logicalPortList) {
							if (port.id == door.controllingPortID) {
								if (port.outputState) {
									// Se a porta está fechada, abra-a
									door.openDoor(); // Chama o método para "abrir" a porta
									ui.showMessage("Você abriu a porta!"); // debug em UI
									// System.out.println("Porta de ferro " + door.controllingPortID + " aberta!");
								} else {
									// Se a porta está aberta, feche-a
									door.closeDoor(); // Chama o método para "fechar" a porta
									// System.out.println("Porta de ferro " + door.controllingPortID + " fechada.");
								}
								break;
							}
						}
					}
				}

				
				for (int i = 0; i < obj[currentMap].length; i++) {
					if (obj[currentMap][i] instanceof OBJ_Door_Iron && obj[currentMap][i] != null) {
						OBJ_Door_Iron door = (OBJ_Door_Iron) obj[currentMap][i];
						int plateIndex = door.controllingPlateID;
						if (plateIndex >= 0 && plateIndex < iTile[currentMap].length
								&& iTile[currentMap][plateIndex] instanceof IT_MetalPlate) {
							// Obtém a placa de pressão pelo ID
							IT_MetalPlate plate = (IT_MetalPlate) iTile[currentMap][plateIndex];
							if (plate.isActivated()) {
								ui.showMessage("Você abriu a porta!"); // debug em UI
								door.openDoor();
							} else {
								door.closeDoor();
							}
						}

					}
				}
			} else {
				// System.out.println("Sem caixas no mapa!");
			}

		}
		if (gameState == pauseState) {
			// nothing
		}

		if (gameState == optionState) {

		}
	}

	public void drawToTempScreen() {

		// DEBUG
		long drawStart = 0;
		if (keyH.showDebug == true) {
			drawStart = System.nanoTime();
		}

		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}
		// OTHERS
		else {

			// TILE
			tileM.draw(g2);

			// Wires fios
			for (int i = 0; i < Math.max(iTile[currentMap].length, wire[currentMap].length); i++) {

				if (i < wire[currentMap].length && wire[currentMap][i] != null) {
					entityList.add(wire[currentMap][i]);
				}
			}

			// ADD LIST
			for (int i = 0; i < Math.max(iTile[currentMap].length, wire[currentMap].length); i++) {
				if (i < iTile[currentMap].length && iTile[currentMap][i] != null) {
					entityList.add(iTile[currentMap][i]);
				}
			}

			// ADD ENTITIES TO THE LIST
			entityList.add(player);

			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}
			for (int i = 0; i < obj[1].length; i++) {
				if (obj[currentMap][i] != null) {
					entityList.add(obj[currentMap][i]);
				}
			}

			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					int e1BottomY = e1.worldY + e1.solidArea.height; // Supondo que tileSize seja a altura da entidade
					int e2BottomY = e2.worldY + e2.solidArea.height;

					return Integer.compare(e1BottomY, e2BottomY);
				}
			});

			// DRAW ENTITIES
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}

			// EMPTY ENTITY LIST
			entityList.clear();

			eHandler.draw(g2); // debug

			ui.draw(g2);

		}

		// Se o debug estiver ativo, desenha o fundo com opacidade
		if (keyH.showDebug == true) {
			AlphaComposite originalComposite = (AlphaComposite) g2.getComposite();
			float alpha = (gameState == optionState) ? 0.5f : 1.0f; // Opacidade, ajuste conforme necessário
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2.setColor(new Color(0, 0, 0, 50)); // Cor de fundo com opacidade (preto semi-transparente)
			g2.fillRect(0, 0, screenWidth, screenHeight);
			g2.setComposite(originalComposite); // Restaura a opacidade original
		}

		// DEBUG
		if (keyH.showDebug == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
			g2.setColor(Color.white);
			int x = 10;
			int y = screenHeight - (tileSize * 5);
			int lineHeigth = 40;

			// lado esquerdo
			drawStringWithOpacity(g2, "WorldX: " + player.worldX, x, y, 1.0f);
			y += lineHeigth;
			drawStringWithOpacity(g2, "WorldY: " + player.worldY, x, y, 1.0f);
			y += lineHeigth;
			drawStringWithOpacity(g2, "Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y, 1.0f);
			y += lineHeigth;
			drawStringWithOpacity(g2, "Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y, 1.0f);
			y += lineHeigth;
			drawStringWithOpacity(g2, "Draw Time: " + passed, x, y, 1.0f);
			y += lineHeigth;
			drawStringWithOpacity(g2, "FPS: " + drawCount, x, y, 1.0f);

			// lado direito
			x = screenWidth - tileSize * 7;
			y = screenHeight - (tileSize * 10);
			lineHeigth = 40;

			// Create a plate list
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null && iTile[currentMap][i].name != null
						&& iTile[currentMap][i].name.equals(IT_MetalPlate.itName)) {
					drawStringWithOpacity(g2, i + "", iTile[currentMap][i].worldX + 15, iTile[currentMap][i].worldY,
							1.0f);

					// outros debugs da placa
					drawStringWithOpacity(g2, "Placa [" + i + "]: " + iTile[currentMap][i].estadoLogico + " caixa",
							x - 50, y, 1.0f);
					y += lineHeigth;
				}
			}

			// Mostrar o estado das portas lógicas
			NPC_Box npcBox = null;
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] instanceof NPC_Box) {
					npcBox = (NPC_Box) npc[currentMap][i];
					break;
				}
			}

			if (npcBox != null) {
				ArrayList<String> debugInfo = npcBox.getLogicalPortDebugInfo();

				// Aqui também precisa ter um loop para os iTiles
				for (String debugString : debugInfo) {

					drawStringWithOpacity(g2, debugString, x - 50, y, 1.0f);
					y += lineHeigth;
				}

				for (int i = 0; i < iTile[currentMap].length; i++) {

					if (iTile[currentMap][i] instanceof IT_LogicalPort) {
						IT_LogicalPort port = (IT_LogicalPort) iTile[currentMap][i];

						for (String debugString : debugInfo) {

							drawStringWithOpacity(g2, port.id + "", iTile[currentMap][i].worldX + 15,
									iTile[currentMap][i].worldY, 1.0f);
							break;
						}
					}
				}
			}
		}
	}

	private void drawStringWithOpacity(Graphics2D g2, String text, int x, int y, float alpha) {
		AlphaComposite originalComposite = (AlphaComposite) g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.drawString(text, x, y);
		g2.setComposite(originalComposite);
	}

	public void drawToScreen() {

		Graphics g = getGraphics();
		if (g != null && tempScreen != null) {
			g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);

			g.dispose();
		}

	}

	public void playMusic(int i) {

		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void stopSFX() {
		sfx.stop();
	}

	public void playSFX(int i) {

		sfx.setFile(i);
		sfx.play();
	}

	public void changeArea() {
		// Garante que proxima_fase está atualizado
		player.speedMultiplicator = 0;
		proxima_fase = faseMap[currentMap];

		if (proxima_fase != fase_atual) {
			stopMusic();

			// Switch para música baseado no currentMap
			switch (currentMap) { // Mais eficiente que vários if-else
			case 0:
				playMusic(0);
				break;
			case 1:
				playMusic(9);
				break;
			case 2:
				playMusic(10);
				break;
			case 3:
				playMusic(9);
				break;
			}

			eHandler.newWorldX = player.worldX;
			eHandler.newWorldY = player.worldY;
		}

		fase_atual = proxima_fase;
	}
}
