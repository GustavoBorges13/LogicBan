package main;

import com.formdev.flatlaf.FlatDarkLaf; // Ou FlatLightLaf para o tema claro

import javax.swing.*;
import java.awt.*;

public class Main {

    public static JFrame window;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        // Configurar o Look and Feel FlatLaf
        try {
            // Usando o tema escuro
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Ou FlatLightLaf para o tema claro
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Exibir a janela de seleção de monitor
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MonitorSelectionWindow selectionWindow = new MonitorSelectionWindow();
                selectionWindow.setVisible(true);
                
            }
        });
    }

    public static void startGameOnSelectedMonitor(String monitorChoice) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("LogicBan");

        gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.FullScreenOn == true) {
            window.setUndecorated(true);
        }

        window.pack();

        // Obtendo os monitores disponíveis
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        // Movendo a janela para o monitor selecionado
        int monitorIndex = Integer.parseInt(monitorChoice.split(" ")[1]) - 1; // Obtém o índice do monitor escolhido
        if (screens.length > monitorIndex) {
            Rectangle bounds = screens[monitorIndex].getDefaultConfiguration().getBounds();
            window.setLocation(bounds.x, bounds.y); // Move para o monitor selecionado
        } else {
            window.setLocationRelativeTo(null); // Caso algo dê errado, centraliza na tela principal
        }

        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
        
    }
}
