package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
    // public String message = "";
    // int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;

    public String currentDialogue = "";

    public int menuTargetNumber = 0;

    public int slotCol = 0;
    public int slotRow = 0;

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

    public void addMessage(String text) {

        // message = text;
        // messageOn = true;

        message.add(text);
        messageCounter.add(0);
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
            drawMessage();
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

        // CHARACTER STATE
        else if (display.gameState == display.characterState) {

            drawHeroLife();
            drawInventory();
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

    public void drawMessage() {

        int messageX = display.tileSize;
        int messageY = display.tileSize * 4;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {

            if (message.get(i) != null) {

                // SHADOW
                graphics2D.setColor(Color.black);
                graphics2D.drawString(message.get(i), messageX + 2, messageY + 2);

                // TEXT
                graphics2D.setColor(Color.white);
                graphics2D.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);

                messageY += display.tileSize * 2 / 3;

                if (messageCounter.get(i) > 180) {

                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
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

    public void drawInventory() {

        // FRAME
        int frameX = display.screenWidth - display.tileSize * 10;
        int frameY = display.tileSize * 2;
        int frameWidth = display.tileSize * 8;
        int frameheight = display.tileSize * 7;
        drawSubWindow(frameX, frameY, frameWidth, frameheight);

        // SLOT
        final int slotXStart = frameX + display.tileSize / 2;
        final int slotYStart = frameY + display.tileSize / 2;
        int slotX = slotXStart;
        int slotY = slotYStart;

        // DRAW ITEMS
        for (int i = 0; i < display.hero.inventory.size(); i++) {

            graphics2D.drawImage(display.hero.inventory.get(i).down1, slotX, slotY, null);

            slotX += display.tileSize;

            if (i == 6 ||
                    i == 13 ||
                    i == 20 ||
                    i == 27 ||
                    i == 34) {

                slotX = slotXStart;
                slotY += display.tileSize;
            }
        }

        // CURSOR
        int cursorX = slotXStart + (display.tileSize * slotCol);
        int cursorY = slotYStart + (display.tileSize * slotRow);
        int cursorWidth = display.tileSize;
        int cursorHeight = display.tileSize;

        // DRAW CURSOR
        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(display.tileSize / 20));
        graphics2D.drawRoundRect(
                cursorX,
                cursorY,
                cursorWidth,
                cursorHeight,
                display.tileSize / 4,
                display.tileSize / 4);

        // DESCRIPTION
        int itemIndex = getItemIndexInSlot();
        if (itemIndex < display.hero.inventory.size() &&
                display.hero.inventory.get(itemIndex).description != "") {
            // DESCRIPTION FRAME
            int descriptionFrameX = frameX;
            int descriptionFrameY = frameY + frameheight;
            int descriptionFrameWidth = frameWidth;
            int descriptionFrameHeight = display.tileSize * 3;
            // drawSubWindow(
            // descriptionFrameX,
            // descriptionFrameY,
            // descriptionFrameWidth,
            // descriptionFrameHeight);

            // DRAW DESCRIPTION
            int textX = descriptionFrameX + display.tileSize / 2;
            int textY = descriptionFrameY + display.tileSize * 2 / 3;
            graphics2D.setFont(graphics2D.getFont().deriveFont(28F));

            if (itemIndex < display.hero.inventory.size()) {

                drawSubWindow(
                        descriptionFrameX,
                        descriptionFrameY,
                        descriptionFrameWidth,
                        descriptionFrameHeight);

                for (String line : display.hero.inventory.get(itemIndex).description.split("\n")) {

                    graphics2D.drawString(line, textX, textY);
                    textY += display.tileSize / 2;
                }
            }
        }
    }

    public int getItemIndexInSlot() {

        int itemIndex = slotCol + (slotRow * 7);

        return itemIndex;
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
