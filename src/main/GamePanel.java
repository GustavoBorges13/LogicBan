package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import button.Button_Creditos;
import button.Button_Fechar;
import button.Button_NovoJogo;
import button.Button_Som;
import button.Button_Start;
import button.Button_Voltar;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

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
	KeyHandler keyH = new KeyHandler(this);
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
	Thread gameThread;

	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][10];
	public Entity npc[][] = new Entity[maxMap][10];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][10];
	ArrayList<Entity> entityList = new ArrayList<>();
	ParticleManager particleManager = new ParticleManager(this);; // Instância do ParticleManager

	// public ArrayList<Box> boxes = new ArrayList<>();

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int optionState = 4;
	public final int transitionState = 5;

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

		eHandler.playerNewGamePosition();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setInteractiveTile();

		// fase_atual = fase1;
		gameState = titleState;

		playMusic(5); // tocar musica em loop
		// stopMusic(); // debug - teste sem musica

		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();

		if (FullScreenOn == true) {
			setFullScreen();
		}

	}

	public void restart() {
		ui.playTime = 0;

		proxima_fase = faseMap[currentMap];
		fase_atual = currentMap;

		eHandler.playerNewGamePosition();
		player.setDefaultValues();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setInteractiveTile();
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

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

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

			// INTERACTIVE TILES
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].draw(g2);
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
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
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

		// DEBUG
		if (keyH.showDebug == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
			g2.setColor(Color.white);
			int x = 10;
			int y = screenHeight - (tileSize * 5);
			int lineHeigth = 40;

			// lado esquerdo
			g2.drawString("WorldX: " + player.worldX, x, y);
			y += lineHeigth;
			g2.drawString("WorldY: " + player.worldY, x, y);
			y += lineHeigth;
			g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
			y += lineHeigth;
			g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
			y += lineHeigth;
			g2.drawString("Draw Time: " + passed, x, y);
			y += lineHeigth;
			g2.drawString("FPS: " + drawCount, x, y);

			// lado direito
			x = screenWidth - tileSize * 6;
			y = screenHeight - (tileSize * 10);
			lineHeigth = 40;

			// Create a plate list
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null && iTile[currentMap][i].name != null
						&& iTile[currentMap][i].name.equals(IT_MetalPlate.itName)) {
					g2.drawString("Placa [" + i + "]: " + iTile[currentMap][i].estadoLogico, x, y);
					y += lineHeigth;
				}

			}
		}
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

	public void playSFX(int i) {

		sfx.setFile(i);
		sfx.play();
	}

	public void changeArea() {
		// Garante que proxima_fase está atualizado
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
