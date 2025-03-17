package br.ufcat.logicban.util;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.formdev.flatlaf.FlatDarkLaf;

import org.json.JSONObject;

public class UpdateChecker {

	// URL da API do GitHub para obter a última release
	private static final String GITHUB_API_URL = "https://api.github.com/repos/GustavoBorges13/LogicBan/releases/latest";

	private static String currentVersion;

	static {
	    try {
	        currentVersion = AppVersion.VERSION; // Use a constante da classe
	    } catch (Exception e) {
	        System.err.println("Erro ao ler a versão: " + e.getMessage());
	        currentVersion = "0.0.0"; // Versão padrão em caso de falha
	    }
	}

	public static void checkForUpdates() {
		try {
			String latestVersion = getLatestVersionFromGitHub();
			if (latestVersion != null) {
				latestVersion = latestVersion.startsWith("v") ? latestVersion.substring(1) : latestVersion;

				if (isGitHubVersionNewer(currentVersion, latestVersion)) {
					System.out.println("Nova versão disponível: " + latestVersion);
					showUpdateDialog(latestVersion);
				} else {
					System.out.println("O aplicativo está atualizado.");
				}
			} else {
				System.err.println("Falha ao verificar atualizações.");
			}
		} catch (IOException e) {
			System.err.println("Erro ao verificar atualizações: " + e.getMessage());
		}
	}

	private static boolean isGitHubVersionNewer(String currentVersion, String latestVersion) {
		String[] currentParts = currentVersion.split("\\.");
		String[] latestParts = latestVersion.split("\\.");

		int currentMajor = Integer.parseInt(currentParts[0]);
		int currentMinor = Integer.parseInt(currentParts[1]);
		int currentPatch = Integer.parseInt(currentParts[2]);

		int latestMajor = Integer.parseInt(latestParts[0]);
		int latestMinor = Integer.parseInt(latestParts[1]);
		int latestPatch = Integer.parseInt(latestParts[2]);

		if (latestMajor > currentMajor) {
			return true;
		} else if (latestMajor == currentMajor && latestMinor > currentMinor) {
			return true;
		} else if (latestMajor == currentMajor && latestMinor == currentMinor && latestPatch > currentPatch) {
			return true;
		} else {
			return false;
		}
	}

	private static String getLatestVersionFromGitHub() throws IOException {
		try {
			URL url = new URL(GITHUB_API_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/vnd.github.v3+json"); // Informa que aceita JSON

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = in.readLine()) != null) {
						response.append(line);
					}

					// Faz o parsing da resposta JSON para obter a tag
					JSONObject jsonResponse = new JSONObject(response.toString());
					return jsonResponse.getString("tag_name"); // Assume que a tag é "tag_name"
				}
			} else {
				System.err
						.println("Erro ao obter a última versão da API do GitHub. Código de resposta: " + responseCode);
				return null;
			}
		} catch (Exception e) {
			System.err.println("Erro ao processar a resposta da API do GitHub: " + e.getMessage());
			return null;
		}
	}

	private static String getCurrentVersionFromPom() throws Exception {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("pom.xml"); // Nome do arquivo pom.xml
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("version");
			if (nList.getLength() > 0) {
				Node node = nList.item(0);
				return node.getTextContent();
			} else {
				throw new Exception("Tag 'version' não encontrada no pom.xml");
			}
		} catch (Exception e) {
			System.err.println("Erro ao ler o pom.xml: " + e.getMessage());
			throw e;
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