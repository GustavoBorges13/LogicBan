package br.ufcat.logicban.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

    GamePanel gp;
    private static final String CONFIG_PATH = "config/config.txt";
    private static final String CONFIG_DIR = "config"; // Caminho do diretório

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            // Verifica se o diretório existe e cria se não existir
            File dir = new File(CONFIG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Cria o diretório, caso não exista
                System.out.println("Diretório de configuração criado: " + CONFIG_DIR);
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG_PATH))) {

                // Full screen
                bw.write(gp.FullScreenOn ? "On" : "Off");
                bw.newLine();

                // Music volume
                bw.write(String.valueOf(gp.music.volumeScale));
                bw.newLine();

                // SFX volume
                bw.write(String.valueOf(gp.sfx.volumeScale));
                bw.newLine();

                // Walk Type
                bw.write(gp.player.walkType);
                bw.newLine();

                //System.out.println("Configuração salva com sucesso.");

            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar configuração: " + e.getMessage());
        }
    }

    public void loadConfig() {
        File configFile = new File(CONFIG_PATH);

        if (!configFile.exists()) {
            System.out.println("Arquivo de configuração não encontrado. Criando novo com valores padrão.");
            setDefaultValues();
            saveConfig(); // Cria o arquivo com valores padrão
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String s = br.readLine();

            // Full screen
            gp.FullScreenOn = "On".equals(s);

            // Music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // SFX volume
            s = br.readLine();
            gp.sfx.volumeScale = Integer.parseInt(s);

            // Walk Type
            s = br.readLine();
            System.out.println(gp.player.walkType);
            gp.player.walkType = s;

            //System.out.println("Configuração carregada com sucesso.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar configuração: " + e.getMessage());
            setDefaultValues();
        }
    }

    public void setDefaultValues() {
        gp.FullScreenOn = false;
        gp.music.volumeScale = 3; // Volume padrão
        gp.sfx.volumeScale = 3; // Volume padrão
        gp.player.walkType = gp.player.stepWalk; // Caminhada padrão
        gp.update();
    }
}
