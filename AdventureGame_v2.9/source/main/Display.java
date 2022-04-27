package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Hero;
import tile.TileManager;

public class Display extends JPanel implements Runnable {

    // SCREEN SETTINGS
//mivan github köcsög?!?!?!?!
    // Tile = originalTileSize x originalTileSize pixels
    final int originalTileSize = 16;
    final int scale = 4;

    // Real tileSize = scale*originalTileSize x scale*originalTileSize pixels
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 24;
    public final int maxScreenRow = 14;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // MAP SETTINGS
    // public final int maxMapCol = 50;
    public final int maxMapCol = 100;
    public final int maxMapRow = 50;
    public final int mapWidth = tileSize * maxMapCol;
    public final int mapHeight = tileSize * maxMapRow;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this);

    public KeyInput keyInput = new KeyInput(this);

    public EventHandler eventHandler = new EventHandler(this);

    Thread gameThread;

    public CollisionDetector collisionDetector = new CollisionDetector(this);

    public AssetSetter assetSetter = new AssetSetter(this);

    public UI userInterface = new UI(this);

    // ENTITY AND OBJECT
    public Hero hero = new Hero(this, keyInput);
    public Entity object[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity bandit[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public Display() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
    }

    public void setupGame() {

        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setBandit();

        gameState = menuState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    // CALLED WHEN startGameThread();
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime = 0;

        // FPS counter
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            // System.out.println("Game loop is running.");

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            // FPS counter
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {

                // UPDATE
                update();

                // DRAW
                repaint();

                delta--;
                // FPS counter
                drawCount++;
            }

            // FPS counter
            if (timer >= 1000000000) {

                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if (gameState == playState) {
            // HERO
            hero.update();

            // NPC
            // for (int i = 0; i < npc.length; i++) {
            // if (npc[i] != null) {

            // npc[i].update();
            // }
            // }

            // Bandit
            for (int i = 0; i < bandit.length; i++) {
                if (bandit[i] != null) {

                    if (bandit[i].alive && !bandit[i].dying) {

                        bandit[i].update();
                    }
                    if (!bandit[i].alive) {

                        bandit[i] = null;
                    }
                }
            }
        }

        if (gameState == pauseState) {

            // nothing
        }

    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        // DEBUG
        long drawStart = 0;
        if (keyInput.checkDrawTime) {

            drawStart = System.nanoTime();
        }

        // MENU SCREEN
        if (gameState == menuState) {

            userInterface.draw(graphics2D);
        } else {

            // TILE
            tileManager.draw(graphics2D);

            // ADD ENTITIES TO ENTITY LIST
            entityList.add(hero);

            for (int i = 0; i < npc.length; i++) {

                if (npc[i] != null) {

                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < object.length; i++) {

                if (object[i] != null) {

                    entityList.add(object[i]);
                }
            }

            for (int i = 0; i < bandit.length; i++) {

                if (bandit[i] != null) {

                    entityList.add(bandit[i]);
                }
            }

            // SORT ENTITIES
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity entity1, Entity entity2) {

                    int result = Integer.compare(entity1.mapY, entity2.mapY);

                    return result;
                }
            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {

                entityList.get(i).draw(graphics2D);
            }

            // DEBUG DRAW HERO SOLID AREA
            // graphics2D.setColor(Color.red);
            // graphics2D.drawRect(
            // hero.screenX + hero.solidArea.x,
            // hero.screenY + hero.solidArea.y,
            // hero.solidArea.width,
            // hero.solidArea.height);

            // RESET ENTITY LIST
            entityList.clear();

            // for (int i = 0; i < entityList.size(); i++) {

            // entityList.remove(i);
            // }

            // // OBJECT
            // for (int i = 0; i < object.length; i++) {
            // if (object[i] != null) {

            // object[i].draw(graphics2D, this);
            // }
            // }

            // // NPC
            // for (int i = 0; i < npc.length; i++) {
            // if (npc[i] != null) {

            // npc[i].draw(graphics2D);
            // }
            // }

            // // HERO
            // hero.draw(graphics2D);

            // UI
            userInterface.draw(graphics2D);
        }

        // DEBUG
        if (keyInput.checkDrawTime) {

            long drawEnd = System.nanoTime();
            long drawTime = drawEnd - drawStart;
            graphics2D.setColor(Color.white);
            graphics2D.drawString("Draw time: " + drawTime, 10, 400);
            System.out.println("Draw time: " + drawTime);
        }

        graphics2D.dispose();
    }
}