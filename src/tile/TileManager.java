package tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collsionStatus = new ArrayList<>();

	public static boolean debugModeOn = false;

	public TileManager(GamePanel gp) {

		this.gp = gp;

		// READ TILE DATA FILE
		InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// GETTING TILE NAMES AND COLLSION INFO FROM THE FILE
		String line;
		try {
			while ((line = br.readLine()) != null) {

				fileNames.add(line);
				collsionStatus.add(br.readLine());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// INITIALIZE THE TILE ARRAY BASED ON THE fileNames size
		tile = new Tile[fileNames.size()];
		getTileImage();
		
		//GET THE maxWorldCol & Row
		is = getClass().getResourceAsStream("/maps/map01.txt");
		br = new BufferedReader(new InputStreamReader(is));
		
		try {
			
			String line2 = br.readLine();
			String maxTile[] = line2.split(" ");
			
			gp.maxWorldCol = maxTile.length;
			gp.maxWorldRow = maxTile.length;
			
			mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
			
			br.close();
			
		}catch(IOException e) {
			System.out.println("Execption!");
		}
		
		loadMap("/maps/map01.txt", 0);
		loadMap("/maps/map02.txt", 1);
		loadMap("/maps/map03.txt", 2);
		loadMap("/maps/map04.txt", 3);
		
//		loadMap("/maps/map01.txt", 0);
//		loadMap("/maps/map02.txt", 1);
//		loadMap("/maps/map03.txt", 2);
	}

	public void getTileImage() {
		
		for(int i=0; i < fileNames.size(); i++) {
			
			String fileName;
			boolean collision;
			
			// Get a file name
			fileName = fileNames.get(i);
			
			// Get a collision status
			if(collsionStatus.get(i).equals("true")) {
				collision = true;
			}else {
				collision = false;
			}
			
			setup(i, fileName, collision);
			
		}
	}

	public void setup(int index, String imageName, boolean collision) {

		UtilityTool uTool = new UtilityTool();

		try {

			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String filePath, int map) {
		try {

			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;
			
			// maxScreenCol = 20;
			// maxScreenRow = 12;
			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
			
				while (col < gp.maxScreenCol) {

					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[map][col][row] = num;
					col++;
				}
				if (col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}

			br.close();

		} catch (Exception e) {
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

			if (debugModeOn == true) {
				// DEBUG AREA COLISAO!!
				g2.setColor(Color.green);
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
