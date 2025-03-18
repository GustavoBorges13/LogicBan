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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Mudança importante aqui
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
        selectedMonitor = (screens.length > 0) ? "Monitor 1" : null; // Garante que não seja nulo se não houver monitores
        if (selectedMonitor != null) {
            monitorList.setSelectedItem(selectedMonitor);
        }

        monitorList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedMonitor = (String) monitorList.getSelectedItem();
            }
        });

        // Botão para iniciar o jogo
        startButton = new JButton("Iniciar Jogo");
        startButton.setBackground(new Color(0, 120, 215)); // Azul
        startButton.setForeground(Color.WHITE); // Texto branco
        startButton.setFocusPainted(false); // Remove o contorno de foco
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedMonitor != null) {
                    Main gameWindow = new Main(); // Cria uma nova instância de Main
                    gameWindow.startGameOnSelectedMonitor(selectedMonitor); // Inicia o jogo no monitor selecionado
                    dispose(); // Fecha esta janela de seleção
                } else {
                    JOptionPane.showMessageDialog(MonitorSelectionWindow.this, "Nenhum monitor detectado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionando componentes à janela
        add(monitorList);
        add(startButton);

        SwingUtilities.invokeLater(() -> startButton.requestFocusInWindow());

        setLocationRelativeTo(null); // Centraliza a janela
    }
}