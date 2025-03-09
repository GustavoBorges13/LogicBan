package button;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Button_NovoJogo extends Button {

	GamePanel gp;
	public static final String objName = "Botao novo jogo";

	// Tamanho do botao na UI
	int largura = 336, altura = 105; // tamanho imagem

	public Button_NovoJogo(GamePanel gp) {
		super(gp);
		this.gp = gp;

		String imagePaths[] = { "/buttons/novo_jogo" };

		btnClick = setup(imagePaths[0], largura, altura);

		btnAnimation = null;

	}

	public BufferedImage[] getSoundButtonImage(String nameFile) {
		BufferedImage[] frames = new BufferedImage[12]; // Certifique-se de inicializar o array
		if (btnAnimation != null) {
			for (int i = 0; i < 12; i++) {
				frames[i] = setup(nameFile + (i + 1), largura, altura);
			}

			return frames;
		} return null;
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
	}

	public void draw(Graphics2D g2, int imgX, int imgY, int imgW, int imgH) {
		BufferedImage image = null; // Apenas um único frame por vez

		// Verifica se a animação está ativa
		switch (state) {
		case "enable":
			if (!animation) {
				image = btnClick; // Exibe a imagem do botão 'on' quando não animando
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
