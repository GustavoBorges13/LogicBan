package button;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Button_Creditos extends Button {

	GamePanel gp;
	public static final String objName = "Botao Creditos";

	// Tamanho do botao na UI
	int largura = 240, altura = 105; // tamanho imagem

	public Button_Creditos(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		String imagePaths[] = { "/buttons/creditos" };

		btnClick = setup(imagePaths[0], largura, altura);

		btnAnimation = getSoundButtonImage("/buttons/creditos");

	}

	public BufferedImage[] getSoundButtonImage(String nameFile) {
		BufferedImage[] frames = new BufferedImage[12]; // Certifique-se de inicializar o array

		for (int i = 0; i < 12; i++) {
			frames[i] = setup(nameFile + (i + 1), largura, altura);
		}

		return frames;
	}

	public void update() {
		if (animation) {
			// Chama a animação a cada atualização
			animation();
		}
	}

	public void animation() {
		if (!animation) {
			return; // Se a animação não estiver ativada, não faz nada
		}

		spriteCounter++; // Incrementa o contador

		// Começa a animação a cada 5 atualizações
		if (spriteCounter >= 5) {
			spriteNum++; // Avança para o próximo quadro
			spriteCounter = 0; // Reseta o contador

			if (spriteNum >= totalFrames) {
				spriteNum = 0; // Reinicia a animação
				animation = false; // Para a animação após completar
				gp.gameState = gp.titleState;
				gp.ui.commandNum = 0;
				gp.ui.creditY = (int)(gp.screenHeight);
				gp.ui.creditsAnimating = true;
				gp.ui.titleScreenState = 2;
			}
		}
	}

	public void draw(Graphics2D g2, int imgX, int imgY, int imgW, int imgH) {
		BufferedImage image = null; // Apenas um único frame por vez

		// Verifica se a animação está ativa
		switch (state) {
		case "enable":
			if (!animation) {
				image = btnClick; // Exibe a imagem do botão 'on' quando não animando
			} else {
				image = btnAnimation[spriteNum]; // Exibe o quadro atual da animação "enable"
			}
			break;

		case "disable":
		}

		// Desenha a imagem no botão
		if (image != null) {
			g2.drawImage(image, imgX, imgY, imgW, imgH, null);
		}
	}
}
