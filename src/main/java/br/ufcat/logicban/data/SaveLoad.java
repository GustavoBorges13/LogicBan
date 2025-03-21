package br.ufcat.logicban.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.ufcat.logicban.main.GamePanel;

public class SaveLoad {

    GamePanel gp;
    public int saveAntigo;
    public boolean precisaSalvar = true;
    private static final String SAVE_PATH = "savefile/save.dat";
    private static final String SAVE_DIR = "savefile"; // Caminho do diretório

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {
            // Verifica se o diretório existe e cria se não existir
            File dir = new File(SAVE_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Cria o diretório, caso não exista
                System.out.println("Diretório de save criado: " + SAVE_DIR);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH))) {
                DataStorage ds = new DataStorage();
                ds.highestUnlockedFase = gp.highestUnlockedFase; // Salva a fase mais alta
                oos.writeObject(ds);
                System.out.println("HighestUnlockedFase SAVE: " + gp.highestUnlockedFase);
            }
        } catch (Exception e) {
            System.out.println("Save Exception!");
        }
    }

    public void load() {
        File saveFile = new File(SAVE_PATH);

        if (saveFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                DataStorage ds = (DataStorage) ois.readObject();
                gp.highestUnlockedFase = ds.highestUnlockedFase;
                System.out.println("HighestUnlockedFase LOAD: " + gp.highestUnlockedFase);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar o jogo: " + e.getMessage());
                gp.highestUnlockedFase = 0; // Inicia do zero se falhar
            }
        } else {
            System.out.println("Nenhum save encontrado. Criando novo.");
            gp.highestUnlockedFase = 0;
            save(); // Cria um novo arquivo de save com valores padrão
        }
    }
}
