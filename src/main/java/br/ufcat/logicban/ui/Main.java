package br.ufcat.logicban.ui;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;

import br.ufcat.logicban.util.MonitorSelectionWindow;
import br.ufcat.logicban.util.UpdateChecker;

import java.awt.*;

public class Main extends JFrame {

    private GamePanel gamePanel;

    public Main() {
        setTitle("LogicBan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel(this); // Passa 'this' (a janela Main) para o GamePanel
        add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.FullScreenOn) {
            setUndecorated(true);
        }
    }

    public void startGame() {
        pack(); // Ajusta o tamanho da janela

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        // Configurar o Look and Feel FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Verificar atualizações
        UpdateChecker.checkForUpdates();

        // Exibir a janela de seleção de monitor
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MonitorSelectionWindow selectionWindow = new MonitorSelectionWindow();
                selectionWindow.setVisible(true);
            }
        });
    }

    public void startGameOnSelectedMonitor(String monitorChoice) {
        // Obtendo os monitores disponíveis
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        // Movendo a janela para o monitor selecionado
        int monitorIndex = Integer.parseInt(monitorChoice.split(" ")[1]) - 1; // Obtém o índice do monitor escolhido
        if (screens.length > monitorIndex) {
            Rectangle bounds = screens[monitorIndex].getDefaultConfiguration().getBounds();
            setLocation(bounds.x, bounds.y); // Move para o monitor selecionado
            setBounds(bounds); // Ajusta o tamanho da janela ao monitor
        } else {
            setLocationRelativeTo(null); // Caso algo dê errado, centraliza na tela principal
        }

        setVisible(true); // Tornar a janela visível
        startGame(); // Iniciar o jogo
    }
}