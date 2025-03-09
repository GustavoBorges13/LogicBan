package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

public class MonitorSelectionWindow extends JFrame {

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
