package br.ufcat.logicban.entity;

import java.awt.Color;
import java.util.Random;

import br.ufcat.logicban.main.GamePanel;

public class NPC_OldMan extends Entity {

	private int moveInterval = 80; // Intervalo de tempo entre movimentos (em frames)
	private int moveTimer = 0; // Contador de frames para controlar o intervalo
	private boolean isMoving = false; // Controla se o NPC está se movend
	private int movimento = 1;
	public static final String npcName = "Old Man";

	public NPC_OldMan(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;
		
		if (gp.player != null && gp.player.walkType != null) {
		    walkType = gp.player.walkType;
		}
		
		name = npcName;

		getImage();
		color = Color.magenta;
	}

	public void getImage() {

		up1 = setup("/assets/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/assets/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/assets/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/assets/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/assets/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/assets/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/assets/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/assets/npc/oldman_right_2", gp.tileSize, gp.tileSize);

	}

	@Override
	public void setAction() {
		if (movimento == 1) {
			walkType = gp.player.walkType;
			if (walkType.equals(gp.player.smoothWalk)) {
				speed = 1;
				actionLockCounter++;

				if (actionLockCounter == 120) {
					Random random = new Random();
					int i = random.nextInt(100) + 1;

					if (i <= 25) {
						direction = "up";
					}
					if (i < 25 && i <= 50) {
						direction = "down";
					}
					if (i > 50 && i <= 75) {
						direction = "left";
					}
					if (i > 75 && i <= 100) {
						direction = "right";
					}
					actionLockCounter = 0;
				}
			} else if (walkType.equals(gp.player.stepWalk)) {
				speed = gp.tileSize;
				moveTimer++;

				// Só decide uma nova direção após o intervalo de tempo
				if (moveTimer >= moveInterval && !isMoving) {
					Random random = new Random();
					int i = random.nextInt(100) + 1;

					if (i <= 25) {
						direction = "up";
					} else if (i > 25 && i <= 50) {
						direction = "down";
					} else if (i > 50 && i <= 75) {
						direction = "left";
					} else if (i > 75 && i <= 100) {
						direction = "right";
					}


					if (!collisionOn) {
						isMoving = true; // Inicia o movimento
					}

					moveTimer = 0; // Reseta o contador de tempo
				}
			}
		} else if (movimento == 2) {
			playersGoal = true;
			if (walkType.equals(gp.player.smoothWalk)) {
				if (onPath == true) {

					int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
					int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

					searchPath(goalCol, goalRow);
				} else {
					System.out.println("OUTRO MOVIMENTO");
					speed = 1;
					actionLockCounter++;

					if (actionLockCounter == 120) {
						Random random = new Random();
						int i = random.nextInt(100) + 1;

						if (i <= 25) {
							direction = "up";
						}
						if (i < 25 && i <= 50) {
							direction = "down";
						}
						if (i > 50 && i <= 75) {
							direction = "left";
						}
						if (i > 75 && i <= 100) {
							direction = "right";
						}
						actionLockCounter = 0;
					}
				}
			} else if (walkType.equals(gp.player.stepWalk)) {
				if (onPath == true) {
					speed = gp.tileSize;
					moveTimer++;

					// Só decide uma nova direção após o intervalo de tempo
					if (moveTimer >= moveInterval && !isMoving) {

						int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
						int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

						searchPath(goalCol, goalRow);

						if (!collisionOn) {
							isMoving = true; // Inicia o movimento
						}
						moveTimer = 0; // Reseta o timer após chamar searchPath
					}
				} else {

					speed = gp.tileSize;
					moveTimer++;

					// Só decide uma nova direção após o intervalo de tempo
					if (moveTimer >= moveInterval && !isMoving) {
						Random random = new Random();
						int i = random.nextInt(100) + 1;

						if (i <= 25) {
							direction = "up";
						} else if (i > 25 && i <= 50) {
							direction = "down";
						} else if (i > 50 && i <= 75) {
							direction = "left";
						} else if (i > 75 && i <= 100) {
							direction = "right";
						}


						if (!collisionOn) {
							isMoving = true; // Inicia o movimento
						}

						moveTimer = 0; // Reseta o contador de tempo
					}
				}
			}
		} else if (movimento == 3) {
			if (walkType.equals(gp.player.smoothWalk)) {
				if (onPath == true) {

					int goalCol = 15;
					int goalRow = 4;

					searchPath(goalCol, goalRow);
				} else {
					System.out.println("OUTRO MOVIMENTO");
					speed = 1;
					actionLockCounter++;

					if (actionLockCounter == 120) {
						Random random = new Random();
						int i = random.nextInt(100) + 1;

						if (i <= 25) {
							direction = "up";
						}
						if (i < 25 && i <= 50) {
							direction = "down";
						}
						if (i > 50 && i <= 75) {
							direction = "left";
						}
						if (i > 75 && i <= 100) {
							direction = "right";
						}
						actionLockCounter = 0;
					}
				}
			} else if (walkType.equals(gp.player.stepWalk)) {
				if (onPath == true) {
					speed = gp.tileSize;
					moveTimer++;

					// Só decide uma nova direção após o intervalo de tempo
					if (moveTimer >= moveInterval && !isMoving) {

						int goalCol = 15;
						int goalRow = 4;

						searchPath(goalCol, goalRow);

						if (!collisionOn) {
							isMoving = true; // Inicia o movimento
						}
						moveTimer = 0; // Reseta o timer após chamar searchPath
					}
				} else {

					speed = gp.tileSize;
					moveTimer++;

					// Só decide uma nova direção após o intervalo de tempo
					if (moveTimer >= moveInterval && !isMoving) {
						Random random = new Random();
						int i = random.nextInt(100) + 1;

						if (i <= 25) {
							direction = "up";
						} else if (i > 25 && i <= 50) {
							direction = "down";
						} else if (i > 50 && i <= 75) {
							direction = "left";
						} else if (i > 75 && i <= 100) {
							direction = "right";
						}


						if (!collisionOn) {
							isMoving = true; // Inicia o movimento
						}

						moveTimer = 0; // Reseta o contador de tempo
					}
				}
			}
		}
	}

	public void update() {
		walkType = gp.player.walkType;
		if (walkType.equals(gp.player.smoothWalk)) {

			solidArea.x = 8;
			solidArea.y = 12;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			solidArea.width = 32;
			solidArea.height = 32;

			speed = 1;

			switch (gp.currentMap) {
			case 0:
				movimento = 1;
				break;
			case 1:
				movimento = 2;
				break;
			case 2:
				movimento = 2;
				break;
			case 3:
				movimento = 2;
				break;
			case 4:
				movimento = 2;
				break;
			}

			//// CHECK TILE COLLISION
			collisionOn = false;
			collisionEndWorld = false;
			gp.cChecker.checkTile(this);

			// Verifique a colisão e interaja ANTES de mover
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			int iTileIndex = gp.cChecker.checkObject(this, true);
			interactInteractiveTile(iTileIndex);
			
			boolean playerIndex = gp.cChecker.checkPlayer(this);
			interactPlayer(playerIndex);
			
			
			// CHECK END WORLD COLLISION
			gp.cChecker.checkEnd(this);

			gp.cChecker.checkPlayer(this);

			gp.cChecker.checkObject(this, false);

			// CHECK INTERACTIVE TILE COLISION
			gp.cChecker.checkEntity(this, gp.iTile);
			// checkCollision();

			setAction();

			// Se colisão for falsa, permite o movimento
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			// Trocador de imagens/sprites
			spriteCounter++;
			if (spriteCounter > 12) {
				spriteNum = (spriteNum == 1) ? 2 : 1;
				spriteCounter = 0;
			}

		} else if (walkType.equals(gp.player.stepWalk)) {
			solidArea.x = 2;
			solidArea.y = 2;
			solidAreaDefaultX = solidArea.x;
			solidAreaDefaultY = solidArea.y;
			solidArea.width = 46;
			solidArea.height = 46;

			speed = gp.tileSize;

			switch (gp.currentMap) {
			case 0:
				movimento = 1;
				break;
			case 1:
				movimento = 2;
				break;
			case 2:
				movimento = 2;
				break;
			case 3:
				movimento = 2;
				break;
			case 4:
				movimento = 2;
				break;
			}
			//// CHECK TILE COLLISION
			collisionOn = false;
			collisionEndWorld = false;
			gp.cChecker.checkTile(this);

			// CHECK END WORLD COLLISION
			gp.cChecker.checkEnd(this);
			
			gp.cChecker.checkObject(this, false);

			int iTileIndex = gp.cChecker.checkObject(this, true);
			interactInteractiveTile(iTileIndex);
			
			gp.cChecker.checkObject(this, false);

			// CHECK INTERACTIVE TILE COLISION
			gp.cChecker.checkEntity(this, gp.iTile);
			// checkCollision();

			setAction();


			if (isMoving) {
				// Verifique a colisão e interaja ANTES de mover
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);
				
				boolean playerIndex = gp.cChecker.checkPlayer(this);
				interactPlayer(playerIndex);
				
				// Se não houver colisão após a interação, mova o NPC
				if (!collisionOn) {
					switch (direction) {
					case "up":
						worldY -= speed;
						break;
					case "down":
						worldY += speed;
						break;
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
						break;
					}
				}

				isMoving = false; // Finaliza o movimento
				spriteNum = (spriteNum == 1) ? 2 : 1; // Atualiza animação
			}
		}
		detectPlate();
	}

	public void interactNPC(int i) {
		if (walkType.equals(gp.player.smoothWalk)) {
			if (i != 999) {
				// Obtém a entidade que o player está interagindo
				gp.npc[gp.currentMap][i].move(direction);
			}
		} else if (walkType.equals(gp.player.stepWalk)) {
			if (i != 999) {
				Entity targetNPC = gp.npc[gp.currentMap][i];

				// Verifica se o NPC é outra caixa
				if (targetNPC instanceof NPC_Box) {
					NPC_Box box = (NPC_Box) targetNPC;

					// Salvar a posição original do player e da caixa
					int boxOldWorldX = box.worldX;
					int boxOldWorldY = box.worldY;

					// Tenta mover a outra caixa na mesma direção
					box.move(direction);

					// Se a outra caixa não puder ser movida (colisão)m, marca a colisão
					if (box.collisionOn) {
						box.worldX = boxOldWorldX;
						box.worldY = boxOldWorldY;
						collisionOn = true;
					} else {
						// Troca a posição do player com a posição original da caixa
						worldX = boxOldWorldX;
						worldY = boxOldWorldY;
					}

				} else {
					// Lógica para interagir com outros tipos de NPCs
					collisionOn = true; // Impede o movimento se for outro tipo de NPC
				}
			}
		}

	}

	public void interactInteractiveTile(int i) {

		if (i != 999) {
			// System.out.println("colisao com iteratictive tile");
		}
	}
	
	public void interactPlayer(boolean i) {
		
	}
}
