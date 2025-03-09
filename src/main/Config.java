package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

			// Full screen
			if (gp.FullScreenOn == true) {
				bw.write("On");
			}
			if (gp.FullScreenOn == false) {
				bw.write("Off");
			}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadConfig() {

		try {

			BufferedReader br = new BufferedReader(new FileReader("config.txt"));

			String s = br.readLine();

			// Full screen
			if (s.equals("On")) {
				gp.FullScreenOn = true;
			}
			if (s.equals("Off")) {
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
			gp.player.walkType = s.toString(); //npc herda do player

			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de configuração não encontrado! Criando um novo com valores padrão...");

			// Definir configurações padrão antes de salvar
			gp.FullScreenOn = false;
			gp.music.volumeScale = 3;  // Volume padrão
			gp.sfx.volumeScale = 3;    // Volume padrão
			gp.player.walkType = "Smooth-Walk"; // Tipo de caminhada padrão
			gp.update();
			saveConfig(); // Criar o arquivo com as configurações padrão
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
