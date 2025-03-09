package button;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Button {

	GamePanel gp;

	// Variáveis
	public int worldX, worldY;

	// Estados botao de som
	public boolean estadoBotao = true;
	public boolean botaoSelecionado = false;
	public boolean animation = false;

	// Imagens
	public BufferedImage frames[] = new BufferedImage[12];;
	public BufferedImage btnOn, btnOff, btnOnOff[], btnOffOn[], btnClick, btnAnimation[];

	// Sprites animations
	public int totalFrames = 12;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public String state = "enable";

	public Button(GamePanel gp) {
		this.gp = gp;
	}

	public void update() {

	}

	public BufferedImage setup(String imagePath, int largura, int altura) {
	    BufferedImage image = null;
	    try {
	        image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
	        BufferedImage imageARGB = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = imageARGB.createGraphics();
	        g2d.drawImage(image, 0, 0, null);
	        g2d.dispose();
	        return imageARGB;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return image;
	}


}