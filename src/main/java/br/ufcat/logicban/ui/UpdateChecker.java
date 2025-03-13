package br.ufcat.logicban.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;

public class UpdateChecker {

	// URL do arquivo VERSION no GitHub
	private static final String REMOTE_VERSION_URL = "https://raw.githubusercontent.com/GustavoBorges13/LogicBan/main/VERSION";
	// Caminho do arquivo VERSION local
	private static final String LOCAL_VERSION_PATH = "./VERSION";
	// Versão atual do aplicativo
	private static final String CURRENT_VERSION = "1.0.0";

	public static void checkForUpdates() {
		try {
			// Lê a versão local
			String localVersion = readLocalVersion();
			// Cria o arquivo VERSION com a versão atual
			createLocalVersionFile();
			localVersion = CURRENT_VERSION; // Usa a versão atual como fallback

			// Lê a versão remota
			String remoteVersion = readRemoteVersion();
			if (remoteVersion == null) {
				System.err.println("Não foi possível ler a versão remota.");
				return;
			}
			System.out.println("versao atual: " + localVersion + " Versao remota: " + remoteVersion);

			// Compara as versões
			if (!remoteVersion.equals(localVersion)) {
				// Se a versão remota for diferente, atualiza a versão local
				updateLocalVersion();
				showUpdateDialog(remoteVersion); // Mostra o diálogo de atualização
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readLocalVersion() throws IOException {
		// Lê a versão do arquivo VERSION local
		try (BufferedReader reader = new BufferedReader(new FileReader(LOCAL_VERSION_PATH))) {
			return reader.readLine(); // Lê a primeira linha do arquivo
		} catch (IOException e) {
			// Se ocorrer um erro ao ler o arquivo, retorna null
			return null;
		}
	}

	private static void createLocalVersionFile() throws IOException {
		// Cria o arquivo VERSION local com a versão atual
		try (FileWriter writer = new FileWriter(LOCAL_VERSION_PATH)) {
			writer.write(CURRENT_VERSION); // Escreve a versão atual no arquivo
		}
	}

	private static String readRemoteVersion() throws IOException {
		// Faz uma requisição HTTP para buscar a versão remota
		URL url = new URL(REMOTE_VERSION_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		// Verifica se a resposta foi bem-sucedida (código 200)
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				return in.readLine(); // Lê a primeira linha do arquivo remoto
			}
		} else {
			System.err.println("Erro ao buscar a versão remota. Código de resposta: " + responseCode);
			return null;
		}
	}

	private static void updateLocalVersion() throws IOException {
		// Atualiza o arquivo local com a versão atual
		try (FileWriter writer = new FileWriter(LOCAL_VERSION_PATH)) {
			writer.write(CURRENT_VERSION); // Atualiza o arquivo com a versão atual
			System.out.println("Versão local atualizada para: " + CURRENT_VERSION);
		}
	}

	private static void showUpdateDialog(String remoteVersion) {
		// Configura o tema escuro
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf()); // Ou FlatLightLaf para o tema claro
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Criação da mensagem da caixa de diálogo
		String message = "Uma nova versão (" + remoteVersion + ") está disponível!\n"
				+ "Clique em 'Prosseguir' para ir baixar a atualização ou 'Ignorar'\npara continuar com o jogo.\n";

		// Criação do JDialog personalizado
		JDialog dialog = createDialog(message);

		// Exibe o diálogo
		dialog.setVisible(true);
	}

	private static JDialog createDialog(String message) {
		// Criação do JDialog
		JDialog dialog = new JDialog((Frame) null, "Atualização Disponível", true);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setLayout(new BorderLayout());

		// Adiciona o painel de mensagem ao diálogo
		dialog.add(createMessagePanel(message), BorderLayout.CENTER);

		// Adiciona o painel de botões ao diálogo
		dialog.add(createButtonPanel(dialog), BorderLayout.SOUTH);

		// Configura o tamanho da janela (largura, altura)
		dialog.setSize(460, 170); // Ajuste o tamanho conforme necessário
		dialog.setLocationRelativeTo(null); // Centraliza a janela

		// Adiciona um WindowListener para tratar o fechamento da janela
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0); // Encerra o programa ao clicar no X
			}
		});

		return dialog;
	}

	private static JPanel createMessagePanel(String message) {
	    // Criação do JTextArea para exibir a mensagem com quebra de linha
	    JTextArea textArea = new JTextArea(message);
	    textArea.setEditable(false); // Impede a edição do texto
	    textArea.setLineWrap(true); // Ativa a quebra de linha
	    textArea.setWrapStyleWord(true); // Quebra por palavras inteiras
	    textArea.setBackground(UIManager.getColor("Panel.background")); // Usa o fundo do tema

	    // Obtém a fonte padrão do sistema e ajusta o estilo e o tamanho
	    Font defaultFont = UIManager.getFont("Label.font");
	    Font customFont = defaultFont.deriveFont(Font.PLAIN, 15);
	    textArea.setFont(customFont); // Aplica a fonte personalizada

	    textArea.setMargin(new Insets(10, 10, 10, 10)); // Adiciona margens internas
	    textArea.setEnabled(false); // Impede a seleção de texto
	    textArea.setDisabledTextColor(UIManager.getColor("Label.foreground")); // Usa a cor de texto padrão
	    
		// Criação do painel de mensagem
		JPanel messagePanel = new JPanel(new BorderLayout());
		messagePanel.add(textArea, BorderLayout.CENTER);

		return messagePanel;
	}

	private static JPanel createButtonPanel(JDialog dialog) {
		// Criação dos botões
		JButton ignoreButton = new JButton("Ignorar");
		JButton proceedButton = new JButton("Prosseguir");

		// Estilização do botão "Ignorar" (fundo azul e texto branco)
		ignoreButton.setBackground(new Color(0, 120, 215)); // Azul
		ignoreButton.setForeground(Color.WHITE); // Texto branco
		ignoreButton.setFocusPainted(false); // Remove o contorno de foco

		// Criação do painel de botões
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(proceedButton); // Adiciona "Prosseguir" primeiro
		buttonPanel.add(ignoreButton); // Adiciona "Ignorar" depois

		// Ação para o botão "Ignorar"
		ignoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose(); // Fecha o diálogo
			}
		});

		// Ação para o botão "Prosseguir"
		proceedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Abrir o link do GitHub no navegador
					Desktop.getDesktop().browse(new URI("https://github.com/GustavoBorges13/LogicBan/releases/latest"));
				} catch (Exception ex) {
					System.err.println("Erro ao abrir o navegador: " + ex.getMessage());
				}
				// Encerra o programa após redirecionar o usuário
				System.exit(0); // Finaliza a aplicação
			}
		});
		proceedButton.setSelected(false);
		ignoreButton.setSelected(true);
		// Define o foco inicial no botão "Ignorar"
		ignoreButton.requestFocusInWindow();

		return buttonPanel;
	}
}
