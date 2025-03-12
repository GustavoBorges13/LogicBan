package br.ufcat.logicban.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

public class UpdateChecker {

    private static final String VERSION_URL = "https://raw.githubusercontent.com/GustavoBorges13/LogicBan/main/version.json";

    public static void checkForUpdates(String currentVersion) {
        try {
            URL url = new URL(VERSION_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Suponha que o arquivo JSON contenha algo como: {"version": "0.0.2"}
                String latestVersion = response.toString().split("\"version\":\"")[1].split("\"")[0];
                
            
                if (!latestVersion.equals(currentVersion)) {
                    showUpdateDialog(latestVersion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showUpdateDialog(String latestVersion) {
        JOptionPane.showMessageDialog(null,
                "Uma nova versão (" + latestVersion + ") está disponível!\n" +
                        "Visite o repositório para baixar a atualização.",
                "Atualização Disponível",
                JOptionPane.INFORMATION_MESSAGE);
    }
}