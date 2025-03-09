package main;

import java.awt.Color;

public class Particle {
	GamePanel gp;
	public int x, y, size;
    public float alpha;  // Transparência da partícula
    public float lifeTime;  // Tempo de vida da partícula
    public float speedX, speedY;  // Velocidade da partícula para um movimento mais dinâmico
    Color color;  // Cor da partícula

    // Modificado para receber 'size' e 'color' como parâmetros
    public Particle(int x, int y, int size, Color color, GamePanel gp) {
    	this.gp = gp;
        this.x = x;
        this.y = y;
        this.size = size;
        this.alpha = 1.0f;  // Começando com opacidade total
        this.lifeTime = 0;  // Começa com zero tempo de vida
        this.speedX = (float) (Math.random() * 2 - 1);  // Velocidade aleatória no eixo X
        this.speedY = (float) (Math.random() * 2 - 1);  // Velocidade aleatória no eixo Y
        this.color = color;  // Cor fornecida na criação da partícula
    }

    public void update() {
        lifeTime++;
        
        // Aumentar a velocidade do fade
        alpha -= 0.02f;  // De 0.02 para 0.05 (2.5x mais rápido)
        
        // Aumentar a velocidade de movimento
        x += speedX * 2;  // Multiplicador de velocidade
        y += speedY * 2;

        if (alpha <= 0 || isOutOfBounds()) {
            return;
        }
    }



    public boolean isOutOfBounds() {
        // Verifica se a partícula saiu dos limites da tela
        return (x < 0 || x > gp.screenWidth || y < 0 || y > gp.screenHeight);  // Ajuste conforme o tamanho da tela
    }
}
