package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;

public class SaveLoad {

	GamePanel gp;
	public int saveAntigo;
	public boolean precisaSalvar = true;

	public SaveLoad(GamePanel gp) {

		this.gp = gp;
	}

	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage ds = new DataStorage();
			ds.highestUnlockedFase = gp.highestUnlockedFase; // Salva a fase mais alta
			oos.writeObject(ds);
			System.out.println("HighetsUnlockedFase SAVE: "+gp.highestUnlockedFase);
			oos.close();
		} catch (Exception e) {
			System.out.println("Save Exception!");
		}
	}

	public void load() {
	    try {
	        File saveFile = new File("save.dat");
	        if(saveFile.exists()) {
	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile));
	            DataStorage ds = (DataStorage) ois.readObject();
	            gp.highestUnlockedFase = ds.highestUnlockedFase;
	            ois.close();
	        } else {
	            // Primeira execução - inicializa com fase 0
	            gp.highestUnlockedFase = 0;
	        }
	        System.out.println("HighetsUnlockedFase LOAD: "+gp.highestUnlockedFase);
	    } catch (Exception e) {
	        System.out.println("Load Exception! Initializing new save.");
	        gp.highestUnlockedFase = 0;
	    }
	}

}
