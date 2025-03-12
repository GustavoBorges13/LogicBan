package br.ufcat.logicban.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	GamePanel gp;

	public Config(GamePanel gp) {
		this.gp = gp;
	}

	public void saveConfig() {
	    try {
	        // Caminho relativo baseado no diretório atual de execução
	        String path = System.getProperty("user.dir");
	        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path, "config.txt")));

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
	        bw.write(String.valueOf(gp.player.walkType));
	        bw.newLine();

	        bw.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void loadConfig() {
	    try {
	        String path = System.getProperty("user.dir");
	        BufferedReader br = new BufferedReader(new FileReader(new File(path, "config.txt")));
	        
	        String s = br.readLine();

	        // Full screen
	        if (s.equals("On")) {
	            gp.FullScreenOn = true;
	        } else if (s.equals("Off")) {
	            gp.FullScreenOn = false;
	        }

	        // Music volume
	        s = br.readLine();
	        gp.music.volumeScale = Integer.parseInt(s);

	        // SFX volume
	        s = br.readLine();
	        gp.sfx.volumeScale = Integer.parseInt(s);

	        // Walk Type
	        s = br.readLine();
	        gp.player.walkType = s;

	        br.close();

	    } catch (FileNotFoundException e) {
	        System.out.println("Arquivo de configuração não encontrado! Criando um novo com valores padrão...");
	        gp.FullScreenOn = false;
	        defaultSoundValues();
	        gp.player.walkType = "Smooth-Walk"; // Tipo de caminhada padrão
	        gp.update();
	        saveConfig(); // Criar o arquivo com as configurações padrão

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	public void defaultSoundValues() {
		gp.music.volumeScale = 3; // Volume padrão
		gp.sfx.volumeScale = 3; // Volume padrão
	}

}
