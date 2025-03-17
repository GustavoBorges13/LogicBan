package br.ufcat.logicban.util;

import javax.swing.*;

import br.ufcat.logicban.ui.Main;

import java.awt.*;
import java.awt.event.*;

public class MonitorSelectionWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JComboBox<String> monitorList;
    private JButton startButton;
    private String selectedMonitor;

    public MonitorSelectionWindow() {
        // Inicialização da janela de seleção de monitor
        setTitle("Selecionar Monitor");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Obtendo os monitores disponíveis
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        // Adicionando os monitores ao JComboBox
        monitorList = new JComboBox<>();
        for (int i = 0; i < screens.length; i++) {
            monitorList.addItem("Monitor " + (i + 1));
        }

        // Inicializa o monitor selecionado como o primeiro da lista
        selectedMonitor = "Monitor 1"; // Por padrão, o monitor 1 é selecionado

        monitorList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Atualiza a variável 'selectedMonitor' com o valor atual do JComboBox
                selectedMonitor = (String) monitorList.getSelectedItem();
            }
        });

        // Botão para iniciar o jogo
        startButton = new JButton("Iniciar Jogo");
        // Estilização do botão "Ignorar" (fundo azul e texto branco)
        startButton.setBackground(new Color(0, 120, 215)); // Azul
        startButton.setForeground(Color.WHITE); // Texto branco
        startButton.setFocusPainted(false); // Remove o contorno de foco
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Passando a seleção para o jogo
                Main.startGameOnSelectedMonitor(selectedMonitor);
                dispose(); // Fecha a janela de seleção
            }
        });

        // Adicionando componentes à janela
        add(monitorList);
        add(startButton);

        SwingUtilities.invokeLater(() -> startButton.requestFocusInWindow());

        setLocationRelativeTo(null); // Centraliza a janela
        
    }
}
