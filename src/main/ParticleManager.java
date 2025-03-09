package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ParticleManager {
    private GamePanel gp;
    private List<Particle> particulas;  // Lista para armazenar as partículas
    private Random random;

    // Construtor da classe ParticleManager
    public ParticleManager(GamePanel gp) {
        this.gp = gp;
        this.particulas = new ArrayList<>();
        this.random = new Random();
    }

    // Método para adicionar novas partículas
    public void adicionarParticulas() {
        // Gerar posições aleatórias para as estrelas (partículas)
        int x = random.nextInt(gp.screenWidth);
        int y = random.nextInt(gp.screenHeight);

        // Definir o tamanho aleatório para a partícula (exemplo: entre 2 e 5)
        int size = random.nextInt(4) + 2;  // Gera tamanhos entre 2 e 5 pixels

        // Adicionar uma nova partícula à lista
        particulas.add(new Particle(x, y, size, new Color(1.0f, 1.0f, 1.0f, 1.0f), gp));
    }

    // Método para atualizar todas as partículas
    public void atualizarParticulas() {
        Iterator<Particle> iterator = particulas.iterator();

        while (iterator.hasNext()) {
            Particle p = iterator.next();
            p.update();  // Atualiza o estado da partícula (movimento, cor, etc.)

            // Se a partícula saiu da tela, remova-a
            if (p.isOutOfBounds()) {
                iterator.remove();  // Remove a partícula de forma segura
            }
        }
    }

    // Método para desenhar as partículas (estrelinhas)
    public void desenharParticulas(Graphics2D g2) {
        for (Particle p : particulas) {
            // Garantir que alpha esteja no intervalo de 0 a 255
            int alpha = Math.max(0, Math.min(255, (int) (p.alpha * 255))); // Converte alpha para o intervalo correto

            // Ajusta a cor com base na opacidade
            g2.setColor(new Color(1.0f, 1.0f, 1.0f, alpha / 255f));  // Usa a mesma cor
            g2.fillOval(p.x, p.y, p.size, p.size);  // Desenha a partícula com o tamanho e a cor corretos
        }
    }

    // Método para atualizar a animação do fundo e das partículas
    public void update() {
        // Adicionar novas partículas
    	if(random.nextInt(3) == 0) {  // 33% de chance de gerar partícula a cada frame
            adicionarParticulas();
        }


        // Atualizar todas as partículas
        atualizarParticulas();
    }
}
