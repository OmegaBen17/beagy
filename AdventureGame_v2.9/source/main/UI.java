package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
import object.Life;

//import javax.swing.text.AttributeSet.FontAttribute;

import java.awt.BasicStroke;
//import java.awt.image.BufferedImage;

//import object.Key;

public class UI {

    Display display;
    Graphics2D graphics2D;

    // Font arial_40;
    // Font arial_80B;

    Font maruMonika;

    BufferedImage life_full;
    BufferedImage life_blank;

    // BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public String currentDialogue = "";

    public int menuTargetNumber = 0;

    public UI(Display display) {

        this.display = display;

        try {

            InputStream inputStream = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonika = Font.createFont(Font.TRUETYPE_FONT, inputStream);

        } catch (FontFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        // arial_40 = new Font("Arial", Font.PLAIN, 40);
        // arial_80B = new Font("Arial", Font.BOLD, 80);

        // Key key = new Key(display);
        // keyImage = key.bufferedImage;

        // CREATE HUD OBJECT
        Entity life = new Life(display);
        life_full = life.bufferedImage1;
        life_blank = life.bufferedImage2;

    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {

        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonika);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.white);

        // MENU STATE
        if (display.gameState == display.menuState) {

            drawMenuScreen();
        }

        // PLAY STATE
        if (display.gameState == display.playState) {

            drawHeroLife();
        }

        // PAUSE STATE
        if (display.gameState == display.pauseState) {
            drawHeroLife();

            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (display.gameState == display.dialogueState) {
            drawHeroLife();

            drawDialogueScreen();
        }
    }

    public void drawHeroLife() {

        int x = display.tileSize / 2;
        int y = display.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while (i < display.hero.maxLife) {

            graphics2D.drawImage(life_blank, x, y, null);
            i++;
            x += display.tileSize;
        }

        x = display.tileSize / 2;
        y = display.tileSize / 2;
        i = 0;

        // DRAW CURRENT LIFE
        while (i < display.hero.life) {

            graphics2D.drawImage(life_full, x, y, null);
            i++;
            x += display.tileSize;
        }
    }

    public void drawMenuScreen() {

        // BACKGROUND
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.fillRect(0, 0, display.screenWidth, display.screenHeight);

        // TITLE
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 200F));
        String text = "Adventure Game";
        int x = getXforCenteredText(text);
        int y = display.screenHeight / 4;

        // SHADOW
        graphics2D.setColor(Color.gray);
        graphics2D.drawString(text, x + 7, y + 7);

        // TITLE
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x, y);

        // HERO IMAGE
        x = display.screenWidth / 2 - display.tileSize;
        y += display.tileSize;
        graphics2D.drawImage(display.hero.down1, x, y, display.tileSize * 2, display.tileSize * 2, null);

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 100F));

        text = "New Game";
        x = getXforCenteredText(text);
        y = 2 * display.screenHeight / 3;
        graphics2D.drawString(text, x, y);

        if (menuTargetNumber == 0) {

            graphics2D.drawString(">", x - display.tileSize, y);
        }

        text = "Load Game";
        x = getXforCenteredText(text);
        y += display.tileSize * 1.5;
        graphics2D.drawString(text, x, y);

        if (menuTargetNumber == 1) {

            graphics2D.drawString(">", x - display.tileSize, y);
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += display.tileSize * 1.5;
        graphics2D.drawString(text, x, y);

        if (menuTargetNumber == 2) {

            graphics2D.drawString(">", x - display.tileSize, y);
        }
    }

    public void drawPauseScreen() {

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = display.screenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // DIALOGUE WINDOW
        int x = display.tileSize * 2;
        int y = display.tileSize / 2;
        int width = display.screenWidth - (display.tileSize * 4);
        int height = display.tileSize * 5;

        drawSubWindow(x, y, width, height);

        x += display.tileSize;
        y += display.tileSize;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 40F));

        for (String line : currentDialogue.split("\n")) {

            graphics2D.drawString(line, x, y);

            y += display.tileSize;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(0, 0, 0, 200);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public int getXforCenteredText(String text) {

        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = display.screenWidth / 2 - length / 2;

        return x;
    }

    // OLD DRAW METHOD
    // public void draw(Graphics2D graphics2D) {

    // if (gameFinished) {

    // graphics2D.setFont(arial_40);
    // graphics2D.setColor(Color.white);

    // String text = "The End";
    // int textLength = (int) graphics2D.getFontMetrics().getStringBounds(text,
    // graphics2D).getWidth();
    // int x = display.screenWidth / 2 - textLength / 2;
    // int y = display.screenHeight / 2 - display.tileSize * 3;
    // graphics2D.drawString(text, x, y);

    // graphics2D.setFont(arial_80B);
    // graphics2D.setColor(Color.yellow);

    // text = "Congratulations!";
    // textLength = (int) graphics2D.getFontMetrics().getStringBounds(text,
    // graphics2D).getWidth();
    // x = display.screenWidth / 2 - textLength / 2;
    // y = display.screenHeight / 2 + display.tileSize * 2;
    // graphics2D.drawString(text, x, y);

    // display.gameThread = null;

    // } else {

    // graphics2D.setFont(arial_40);
    // graphics2D.setColor(Color.white);
    // graphics2D.drawImage(
    // keyImage,
    // display.tileSize / 2,
    // display.tileSize / 2,
    // display.tileSize,
    // display.tileSize,
    // null);
    // graphics2D.drawString("x " + display.hero.hasKey, 105, 75);

    // // MESSAGE
    // if (messageOn) {

    // graphics2D.setFont(graphics2D.getFont().deriveFont(30F)); // float
    // graphics2D.drawString(message, display.tileSize / 2, display.tileSize * 5);

    // messageCounter++;

    // if (messageCounter > 120) {

    // messageCounter = 0;
    // messageOn = false;
    // }
    // }
    // }
    // }
}
