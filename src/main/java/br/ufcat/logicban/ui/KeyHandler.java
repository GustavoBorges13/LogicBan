package br.ufcat.logicban.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import br.ufcat.logicban.entity.Entity;
import br.ufcat.logicban.entity.NPC_Box;
import br.ufcat.logicban.tile.TileManager;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, rPressed, back_spacePressed = false;

	int countAux = 0;
	// Cooldown para o Undo
	public int undoCooldown = 0;
	final int undoCooldownMax = 5; // cooldown de 0.5 segundos (120 frames)

	// DEBUG
	boolean showDebug = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code, e);
		}

		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code, e);
		}

		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code, e);
		}

		// PAUSE STATE
		else if (gp.gameState == gp.nextPhaseState) {
			// pauseState(code);

			nextPhaseState(code, e);
		}

		// DIALOGUE STATE - NOTHING

		// CHARACTER STATE - NOTHING

		// OPTION STATE
		else if (gp.gameState == gp.optionState) {
			optionsState(code, e);
		}
	}

	public void titleState(int code, KeyEvent e) {
		// menu principal
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				// System.out.println("cmd " + gp.ui.commandNum + " aux " + countAux);
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_S) {
				// System.out.println("cmd " + gp.ui.commandNum + " aux " + countAux);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_A) {
				// System.out.println("cmd " + gp.ui.commandNum + " aux " + countAux);
				// gp.ui.commandNum = 2;
				switch (gp.ui.commandNum) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					gp.ui.commandNum = 2;
					break;
				}

				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_D) {
				// System.out.println("cmd " + gp.ui.commandNum + " aux " + countAux);
				switch (gp.ui.commandNum) {
				case 0:
					gp.ui.commandNum = 3;
					break;
				case 1:
					gp.ui.commandNum = 3;
					break;
				case 2:
					gp.ui.commandNum = 3;
					break;
				case 3:
					gp.ui.commandNum = 0;
					break;
				}
				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_ENTER) {

				// Botao Iniciar
				if (gp.ui.commandNum == 0) {
					gp.btnStart.state = "enable"; // Troca para 'disable'
					gp.btnStart.animation = true; // Inicia a animação
					gp.btnStart.spriteNum = 0; // Reseta a animação para o início
					gp.btnStart.spriteCounter = 0; // Reseta o contador de frames
					gp.playSFX(6);
					gp.ui.commandNum = 0;
					gp.saveLoad.load(); // carrega aqui o ultimo save pra preencher a grid
				}

				// CREDITS
				if (gp.ui.commandNum == 1) {
					gp.ui.animationFinished = false;
					gp.btnCreditos.state = "enable"; // Troca para 'disable'
					gp.btnCreditos.animation = true; // Inicia a animação
					gp.btnCreditos.spriteNum = 0; // Reseta a animação para o início
					gp.btnCreditos.spriteCounter = 0; // Reseta o contador de frames
					gp.playSFX(6);
				}

				// EXIT - FECHAR
				if (gp.ui.commandNum == 2) {
					gp.btnFechar.state = "enable"; // Troca para 'disable'
					gp.btnFechar.animation = true; // Inicia a animação
					gp.btnFechar.spriteNum = 0; // Reseta a animação para o início
					gp.btnFechar.spriteCounter = 0; // Reseta o contador de frames
					gp.playSFX(6);
				}

				// SOUND
				if (gp.ui.commandNum == 3) {

					if (gp.sfx.volumeScale == 0 && gp.music.volumeScale == 0) {
						gp.config.setDefaultValues();
						gp.music.checkVolume();
						gp.btnSom.estadoBotao = true;
						gp.btnSom.state = "enable"; // Troca para 'enable'
						gp.btnSom.animation = true; // Inicia a animação
						gp.btnSom.spriteNum = 0; // Reseta a animação para o início
						gp.btnSom.spriteCounter = 0; // Reseta o contador de frames
						gp.config.saveConfig();
					} else if (gp.sfx.volumeScale >= 0 || gp.music.volumeScale >= 0) {
						gp.btnSom.volumeSFXTemp = gp.sfx.volumeScale;
						gp.btnSom.volumeMusicTemp = gp.music.volumeScale;
						gp.sfx.volumeScale = 0;
						gp.music.volumeScale = 0;
						gp.music.checkVolume();
						gp.btnSom.estadoBotao = false;
						gp.btnSom.state = "disable"; // Troca para 'enable'
						gp.btnSom.animation = true; // Inicia a animação
						gp.btnSom.spriteNum = 0; // Reseta a animação para o início
						gp.btnSom.spriteCounter = 0; // Reseta o contador de frames
						gp.config.saveConfig();
					}
				}

			}

			// DEBUG MODE
			if (!e.isControlDown() && code == KeyEvent.VK_M) {
				showDebug = !showDebug;
			}

		}
		// SUBMENU
		else if (gp.ui.titleScreenState == 1) {
			// prevCommandNum = gp.ui.commandNum;
			if (code == KeyEvent.VK_A) { // Esquerda
				if (gp.ui.commandNum > 0 && gp.ui.commandNum < 10) {
					gp.ui.commandNum--;
				}

				switch (gp.ui.commandNum) {
				case 11:
					gp.ui.commandNum = 10;
					break;
				case 10:
					gp.ui.commandNum = 9;
					break;
				}
				// System.out.println("cmd: " + gp.ui.commandNum);
				gp.playSFX(6);
			} else if (code == KeyEvent.VK_D) { // Direita
				if (gp.ui.commandNum < 12) { // Máximo de 10 fases (0-9)
					gp.ui.commandNum++;
				}
				switch (gp.ui.commandNum) {
				case 12:
					gp.ui.commandNum = 11;
					break;
				}
				// System.out.println("cmd: " + gp.ui.commandNum);
				gp.playSFX(6);
			} else if (code == KeyEvent.VK_W) { // Cima
				if (gp.ui.commandNum >= 5 && gp.ui.commandNum < 10) {
					gp.ui.commandNum -= 5; // Sobe uma linha
				} else if (gp.ui.commandNum >= 10) {
					gp.ui.commandNum = 5; // Volta para a linha de cima
				}
				// System.out.println("cmd: " + gp.ui.commandNum);
				gp.playSFX(6);

			} else if (code == KeyEvent.VK_S) { // Baixo
				if (gp.ui.commandNum < 5) {
					gp.ui.commandNum += 5; // Desce uma linha
				} else if (gp.ui.commandNum > 7 && gp.ui.commandNum <= 9) {
					gp.ui.commandNum = 11; // Vai para "Voltar"
				} else if (gp.ui.commandNum > 4 && gp.ui.commandNum <= 7) {
					gp.ui.commandNum = 10; // Vai para "Voltar"
				} else if (gp.ui.commandNum == 10) {
					gp.ui.commandNum = 11; // Vai para "Novo Jogo"
				}
				// System.out.println("cmd: " + gp.ui.commandNum);
				gp.playSFX(6);
			}

//			// Impede mover para uma fase bloqueada
//			if (gp.ui.commandNum > gp.currentMap && gp.ui.commandNum < 10) {
//				gp.ui.commandNum = prevCommandNum; // Retorna ao anterior
//			}
			// Após qualquer movimento, verifique se a fase está bloqueada
			// else if (gp.ui.commandNum <= 9 && gp.ui.commandNum > gp.highestUnlockedFase)
			// {
			// gp.ui.commandNum = prevCommandNum;
			// }

			// Atribui a fase selecionada antes da verificação de ENTER

			// ENTER - Ação de seleção
			else if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 10) { // "Voltar"
					gp.ui.titleScreenState = 0;
					gp.ui.commandNum = 0;

				} else if (gp.ui.commandNum == 11) { // "Novo Jogo"
					gp.ui.titleScreenState = 1;
					gp.gameState = gp.playState;
					gp.currentMap = 0;
					gp.highestUnlockedFase = 0; // Reset para fase inicial
					gp.saveLoad.save();
					gp.stopMusic();
					gp.playMusic(0);
				} else {
					for (int i = 0; i <= gp.highestUnlockedFase; i++) {
						if (gp.ui.commandNum < gp.highestUnlockedFase || gp.ui.commandNum == i) {
							// System.out.println("cmd: "+gp.ui.commandNum+" HighUnlockFase: "+
							// gp.highestUnlockedFase);
							gp.currentMap = gp.ui.commandNum;
							gp.gameState = gp.playState;

							// Força atualização imediata
							gp.proxima_fase = gp.faseMap[gp.currentMap];
							gp.fase_atual = gp.currentMap;

							gp.playerPositions();
							gp.changeArea();
						}
					}
				}
			}

			// DEBUG MODE
			else if (!e.isControlDown() && code == KeyEvent.VK_M) {
				showDebug = !showDebug;
			}
		} else if (gp.ui.titleScreenState == 2) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 1;
				}
				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_A) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_D) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
				gp.playSFX(6);
			}
			if (code == KeyEvent.VK_ENTER) {

				// BACK
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 0;
					gp.gameState = gp.titleState;
					gp.ui.commandNum = 0;
					gp.playSFX(6);
					gp.ui.creditY = (int) (gp.screenHeight * 2.5); // Resetando a posição de creditY
				}

				// SOUND
				if (gp.ui.commandNum == 1) {
					if (gp.sfx.volumeScale == 0 && gp.music.volumeScale == 0) {
						gp.config.setDefaultValues();
						gp.music.checkVolume();
						gp.btnSom.estadoBotao = true;
						gp.btnSom.state = "enable"; // Troca para 'enable'
						gp.btnSom.animation = true; // Inicia a animação
						gp.btnSom.spriteNum = 0; // Reseta a animação para o início
						gp.btnSom.spriteCounter = 0; // Reseta o contador de frames
						gp.config.saveConfig();
					} else if (gp.sfx.volumeScale >= 0 || gp.music.volumeScale >= 0) {
						gp.btnSom.volumeSFXTemp = gp.sfx.volumeScale;
						gp.btnSom.volumeMusicTemp = gp.music.volumeScale;
						gp.sfx.volumeScale = 0;
						gp.music.volumeScale = 0;
						gp.music.checkVolume();
						gp.btnSom.estadoBotao = false;
						gp.btnSom.state = "disable"; // Troca para 'enable'
						gp.btnSom.animation = true; // Inicia a animação
						gp.btnSom.spriteNum = 0; // Reseta a animação para o início
						gp.btnSom.spriteCounter = 0; // Reseta o contador de frames
						gp.config.saveConfig();
					}
				}
			}
			// DEBUG MODE
			if (!e.isControlDown() && code == KeyEvent.VK_M) {
				showDebug = !showDebug;
			}
		}
	}

	public void playState(int code, KeyEvent e) {

		if (code == KeyEvent.VK_W && !upPressed) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S && !downPressed) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A && !leftPressed) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D && !rightPressed) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.playSFX(6);
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionState;
			gp.ui.commandNum = 0;
		}

		// DEBUG MODE
		if (!e.isControlDown() && code == KeyEvent.VK_M) {
			showDebug = !showDebug;
			TileManager.debugModeOn = showDebug;
			Entity.debugModeOn = showDebug;
			EventHandler.debugModeOn = showDebug;
		}

		if (e.isControlDown() && code == KeyEvent.VK_M) {
			switch (gp.currentMap) {
			case 0:
				gp.tileM.loadMap("/assets/maps/map01.txt", 0);
				break;
			case 1:
				gp.tileM.loadMap("/assets/maps/map02.txt", 1);
				break;
			case 2:
				gp.tileM.loadMap("/assets/maps/map03.txt", 2);
				break;
			case 3:
				gp.tileM.loadMap("/assets/maps/map04.txt", 3);
				break;
			}

		}

		// RESET MAP
		if (e.isControlDown() && code == KeyEvent.VK_R) {
			gp.gameState = gp.playState;
			gp.restart();
		}
	}

	public void pauseState(int code, KeyEvent e) {

		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			gp.gameState = gp.playState;
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
			gp.playSFX(6);
		}

		// DEBUG MODE
		if (!e.isControlDown() && code == KeyEvent.VK_M) {
			showDebug = !showDebug;
			TileManager.debugModeOn = showDebug;
			Entity.debugModeOn = showDebug;
			EventHandler.debugModeOn = showDebug;
		}

	}

	public void optionsState(int code, KeyEvent e) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
//			gp.restart();
		}

		int maxCommandNum = 0;

		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 6;
			break;
		case 3:
			maxCommandNum = 1;
			break;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;

			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}

			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;

			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
				}
				if (gp.ui.commandNum == 2 && gp.sfx.volumeScale > 0) {
					gp.sfx.volumeScale--;
				}
				if (gp.ui.commandNum == 4) {
					handleWalkTypeChange();
				}
			}
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
				}
				if (gp.ui.commandNum == 2 && gp.sfx.volumeScale < 5) {
					gp.sfx.volumeScale++;
				}
				if (gp.ui.commandNum == 4) {
					handleWalkTypeChange();
				}
			}
			gp.playSFX(6);

		}

		// DEBUG MODE
		if (!e.isControlDown() && code == KeyEvent.VK_M) {
			showDebug = !showDebug;
			TileManager.debugModeOn = showDebug;
			Entity.debugModeOn = showDebug;
			EventHandler.debugModeOn = showDebug;
		}

	}

	public void nextPhaseState(int code, KeyEvent e) {

		if (code == KeyEvent.VK_SPACE) {
			gp.ui.levelFinished = false;
			gp.ui.gameFinished = false;
			gp.gameState = gp.playState;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_A) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_D) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSFX(6);
		}
		if (code == KeyEvent.VK_ENTER) {

			// PROXIMA FASE
			if (gp.ui.commandNum == 0) {
				// Executa o teleporte pendente
				gp.eHandler.executePendingTeleport(); // Chama o método para teleportar
				gp.ui.playTime = 0;
			}

			// MENU INICIAL
			if (gp.ui.commandNum == 1) {
				gp.ui.levelFinished = false;
				gp.ui.gameFinished = false;
				gp.ui.commandNum = 0;
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState = 0;
				System.out.println("current map: " + gp.currentMap + " highestUnlockedFase: " + gp.highestUnlockedFase);
				if (gp.currentMap == gp.highestUnlockedFase) {
					gp.highestUnlockedFase += 1;
				}
				gp.saveLoad.save();
				gp.stopSFX();
				gp.stopMusic();
				gp.playMusic(5);
				gp.restart();
			}

			gp.playSFX(6);
		}

		// DEBUG MODE
		if (!e.isControlDown() && code == KeyEvent.VK_M) {
			showDebug = !showDebug;
			TileManager.debugModeOn = showDebug;
			Entity.debugModeOn = showDebug;
			EventHandler.debugModeOn = showDebug;
		}
	}

	public void resetJustPressed() {
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		rPressed = false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_R) {
			rPressed = false; // Resetar quando a tecla R for solta
		}
	}

	// Método para atualizar o cooldown do UNDO
	public void update() {
		if (undoCooldown > 0) {
			undoCooldown--; // Reduz o cooldown a cada frame
		}
	}

	private void centralizeBoxes(int playerTileCol, int playerTileRow) {
		for (int i = 0; i < gp.npc[1].length; i++) {
			if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name != null
					&& gp.npc[gp.currentMap][i].name.equals(NPC_Box.npcName)) {

				// 3. Calcular as coordenadas do mundo da caixa
				int boxLeftWorldX = gp.npc[gp.currentMap][i].worldX + gp.npc[gp.currentMap][i].solidArea.x;
				int boxRightWorldX = gp.npc[gp.currentMap][i].worldX + gp.npc[gp.currentMap][i].solidArea.x
						+ gp.npc[gp.currentMap][i].solidArea.width;
				int boxTopWorldY = gp.npc[gp.currentMap][i].worldY + gp.npc[gp.currentMap][i].solidArea.y;
				int boxBottomWorldY = gp.npc[gp.currentMap][i].worldY + gp.npc[gp.currentMap][i].solidArea.y
						+ gp.npc[gp.currentMap][i].solidArea.height;

				// 4. Calcular as colunas e linhas dos tiles da caixa
				int boxLeftCol = boxLeftWorldX / gp.tileSize;
				int boxRightCol = boxRightWorldX / gp.tileSize;
				int boxTopRow = boxTopWorldY / gp.tileSize;
				int boxBottomRow = boxBottomWorldY / gp.tileSize;

				// 5. Determinar qual tile a caixa está "pendendo" mais
				int boxTileCol = (boxLeftCol + boxRightCol) / 2; // Média das colunas
				int boxTileRow = (boxTopRow + boxBottomRow) / 2; // Média das linhas

				// 6. Ajustar a posição da caixa para o tile correto
				int newBoxWorldX = boxTileCol * gp.tileSize;
				int newBoxWorldY = boxTileRow * gp.tileSize;

				// 7. Verificar se o novo tile é o mesmo do jogador
				if (boxTileCol == playerTileCol && boxTileRow == playerTileRow) {
//					System.out.println("Box " + i + " está no mesmo tile do jogador, movendo para um tile adjacente.");
					adjustBoxPosition(i, newBoxWorldX, newBoxWorldY);

				} else {
					// 8. Mover a caixa para o tile mais próximo
//					System.out.println("Box " + i + " - Antes - WorldX: " + gp.npc[gp.currentMap][i].worldX
//							+ ", WorldY: " + gp.npc[gp.currentMap][i].worldY);
					gp.npc[gp.currentMap][i].worldX = newBoxWorldX;
					gp.npc[gp.currentMap][i].worldY = newBoxWorldY;
//					System.out.println("Box " + i + " - Depois - WorldX: " + gp.npc[gp.currentMap][i].worldX
//							+ ", WorldY: " + gp.npc[gp.currentMap][i].worldY);
				}
			}
		}
	}

	private void adjustBoxPosition(int boxIndex, int newBoxWorldX, int newBoxWorldY) {
		switch (gp.player.direction) {
		case "up":
			gp.npc[gp.currentMap][boxIndex].worldY = newBoxWorldY - gp.tileSize;
			break;
		case "down":
			gp.npc[gp.currentMap][boxIndex].worldY = newBoxWorldY + gp.tileSize;
			break;
		case "left":
			gp.npc[gp.currentMap][boxIndex].worldX = newBoxWorldX - gp.tileSize;
			break;
		case "right":
			gp.npc[gp.currentMap][boxIndex].worldX = newBoxWorldX + gp.tileSize;
			break;
		}
	}

	private void handleWalkTypeChange() {
		if (!gp.player.walkType.equals(gp.player.smoothWalk)) {
			gp.player.walkType = gp.player.smoothWalk;
			gp.player.speed = gp.player.speedAux;
		} else {
			gp.player.walkType = gp.player.stepWalk;


			// 1. Calcular a posição do jogador em tiles
			int playerTileCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int playerTileRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
//			System.out.println("Player - Inicial - WorldX: " + gp.player.worldX + ", WorldY: " + gp.player.worldY);
//			System.out.println("Player - Tile - Col: " + playerTileCol + ", Row: " + playerTileRow);

			// 2. Centralizar as caixas no tile
			centralizeBoxes(playerTileCol, playerTileRow);

			// 3. Centralizar o jogador no tile DEPOIS de tratar das caixas
			gp.player.worldX = playerTileCol * gp.tileSize;
			gp.player.worldY = playerTileRow * gp.tileSize;
//			Syste.out.println("Player - Final - WorldX: " + gp.player.worldX + ", WorldY: " + gp.player.worldY);
		}
	}
}
