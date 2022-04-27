package tile;

import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Display;
import main.UtilityTool;

public class TileManager {

    Display display;
    public Tile[] tile;

    public int mapTileNumber[][];

    public TileManager(Display display) {

        this.display = display;

        tile = new Tile[50];
        mapTileNumber = new int[display.maxMapCol][display.maxMapRow];

        getTileImage();

        loadMap("/maps/worldmap_template2.txt");
    }

    public void getTileImage() {

        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);

        setup(10, "grass00", false);
        setup(11, "grass01", false);

        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);

        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);

        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
        setup(42, "tombstone", true);
    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool utilityTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].bufferedImage = ImageIO.read(
                    getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].bufferedImage = utilityTool.scaleImage(
                    tile[index].bufferedImage,
                    display.tileSize,
                    display.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFilePath) {

        try {

            InputStream inputStream = getClass().getResourceAsStream(mapFilePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < display.maxMapCol &&
                    row < display.maxMapRow) {

                String line = bufferedReader.readLine();

                while (col < display.maxMapCol) {

                    String numbers[] = line.split(" ");

                    int number = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = number;
                    col++;
                }

                if (col == display.maxMapCol) {

                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {

        int mapCol = 0;
        int mapRow = 0;

        while (mapCol < display.maxMapCol &&
                mapRow < display.maxMapRow) {

            int tileNumber = mapTileNumber[mapCol][mapRow];

            int mapX = mapCol * display.tileSize;
            int mapY = mapRow * display.tileSize;
            int screenX = mapX - display.hero.mapX + display.hero.screenX;
            int screenY = mapY - display.hero.mapY + display.hero.screenY;

            // STOP CAMERA MOVING AT EDGE
            if (display.hero.screenX > display.hero.mapX) {

                screenX = mapX;
            }
            if (display.hero.screenY > display.hero.mapY) {

                screenY = mapY;
            }

            int rightEdgeDistance = display.screenWidth - display.hero.screenX;
            if (rightEdgeDistance > display.mapWidth - display.hero.mapX) {
                screenX = display.screenWidth - (display.mapWidth - mapX);
            }

            int bottomEdgeDistance = display.screenHeight - display.hero.screenY;
            if (bottomEdgeDistance > display.mapHeight - display.hero.mapY) {
                screenY = display.screenHeight - (display.mapHeight - mapY);
            }

            if (mapX + display.tileSize > display.hero.mapX - display.hero.screenX &&
                    mapX - display.tileSize < display.hero.mapX + display.hero.screenX &&
                    mapY + display.tileSize > display.hero.mapY - display.hero.screenY &&
                    mapY - display.tileSize < display.hero.mapY + display.hero.screenY) {

                graphics2D.drawImage(
                        tile[tileNumber].bufferedImage,
                        screenX,
                        screenY,
                        null);
            } else if (display.hero.screenX > display.hero.mapX ||
                    display.hero.screenY > display.hero.mapY ||
                    rightEdgeDistance > display.mapWidth - display.hero.mapX ||
                    bottomEdgeDistance > display.mapHeight - display.hero.mapY) {
                        
                        graphics2D.drawImage(
                            tile[tileNumber].bufferedImage,
                            screenX,
                            screenY,
                            null);
            }

            mapCol++;

            if (mapCol == display.maxMapCol) {
                mapCol = 0;
                mapRow++;
            }
        }
    }
}
