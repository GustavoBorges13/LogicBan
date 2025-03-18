package br.ufcat.logicban.tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import br.ufcat.logicban.ui.GamePanel;
import br.ufcat.logicban.util.UtilityTool;

public class TileManager {

    private GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    private ArrayList<String> fileNames = new ArrayList<>();
    private ArrayList<String> collisionStatus = new ArrayList<>();

    public static boolean debugModeOn = false;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Ler dados do arquivo de tiles
        loadTileData("/assets/maps/tiledata.txt");

        // Inicializar o array de tiles baseado no tamanho de fileNames
        tile = new Tile[fileNames.size()];
        getTileImage();

        // Obter as dimensões do mapa
        loadMapDimensions("/assets/maps/map01.txt");

        // Carregar mapas
        loadMap("/assets/maps/map01.txt", 0);
        loadMap("/assets/maps/map02.txt", 1);
        loadMap("/assets/maps/map03.txt", 2);
        loadMap("/assets/maps/map04.txt", 3);
    }

    private void loadTileData(String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMapDimensions(String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line = br.readLine();
            String[] maxTile = line.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;

            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        } catch (IOException e) {
            System.out.println("Erro ao carregar as dimensões do mapa!");
        }
    }

    public void getTileImage() {
        UtilityTool uTool = new UtilityTool();
        
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            boolean collision = collisionStatus.get(i).equals("true");

            setup(i, fileName, collision, uTool);
        }
    }

    private void setup(int index, String imageName, boolean collision, UtilityTool uTool) {
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/assets/tiles/" + imageName));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
    	InputStream is = getClass().getResourceAsStream(filePath);
    	if (is == null) {
    	    System.out.println("Arquivo não encontrado: " + filePath);
    	}
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))){

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }

                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int x = 0;
        int y = 0;

        while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
            g2.drawImage(tile[tileNum].image, x, y, null);

            worldCol++;
            x += gp.tileSize;

            if (debugModeOn) {
                // Área de debug de colisão
            	g2.setColor(new Color(0, 100, 0)); // Verde Escuro (RGB)
                g2.setStroke(new BasicStroke(1));
                g2.drawRect(x - gp.tileSize, y, gp.tileSize, gp.tileSize);
            }

            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                x = 0;
                worldRow++;
                y += gp.tileSize;
            }
        }
    }
}
