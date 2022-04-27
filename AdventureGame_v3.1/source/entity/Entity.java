package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//import java.awt.Color;
import java.awt.Graphics2D;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Display;
import main.UtilityTool;

public class Entity {

    Display display;

    // STATE
    public int mapX;
    public int mapY;
    public int speed;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int maxLife;
    public int life;
    public int actionCounter = 0;
    public int attackActionCounter = 0;
    public int atackAnimationCounter = 0;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    boolean healthbarOn = false;
    int healthBarCounter = 0;

    // IMAGES
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage right1;
    public BufferedImage right2;

    public BufferedImage attackUp1;
    public BufferedImage attackUp2;
    public BufferedImage attackDown1;
    public BufferedImage attackDown2;
    public BufferedImage attackLeft1;
    public BufferedImage attackLeft2;
    public BufferedImage attackRight1;
    public BufferedImage attackRight2;

    // SOLID AREA
    public Rectangle solidArea = new Rectangle();
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    // INTERACTION AREA
    public Rectangle interactionArea = new Rectangle();
    public int interactionAreaDefaultX;
    public int InteractionAreaDefaultY;
    public boolean InteractionEnabled = false;

    // ATTACK AREA
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    // INVINCIBILITY
    public boolean invincible = false;
    public int invincibleCounter;

    // DIALOGUE
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    // OBJECT
    public BufferedImage bufferedImage1;
    public BufferedImage bufferedImage2;
    public String name;
    public String description = "";

    public boolean collision = false;

    public int type; // 0 = hero, 1 = NPC, 2 = bandit
    public int banditType = 2;
    public int consumableObject = 3;

    public Entity(Display display) {

        this.display = display;

        solidArea.x = 0;
        solidArea.y = display.tileSize / 4;
        solidArea.width = display.tileSize;
        solidArea.height = display.tileSize * 3 / 4;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        interactionArea.x = 0;
        interactionArea.y = 0;
        interactionArea.width = 2 * display.tileSize;
        interactionArea.height = 2 * display.tileSize;
    }

    public void setAction() {
    }

    public void damageReaction() {
    }

    public void speak() {

        if (dialogues[dialogueIndex] == null) {

            dialogueIndex = 0;
        }

        display.userInterface.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }

    public void use(Entity entity) {

    }

    public void update() {

        if (attacking) {

            attacking();

        } else {

            setAction();

            collisionOn = false;
            display.collisionDetector.checkTile(this);
            display.collisionDetector.detectObject(this, false);
            display.collisionDetector.detectEntity(this, display.npc);
            display.collisionDetector.detectEntity(this, display.bandit);
            display.collisionDetector.detectHero(this);
            // boolean contactHero = display.collisionDetector.detectHero(this);

            // if (this.type == 2 && contactHero) {

            // if (!display.hero.invincible) {

            // display.hero.life--;
            // display.hero.invincible = true;
            // }
            // }

            // COLLISION
            if (collisionOn == false) {

                switch (direction) {
                    case "up":
                        mapY -= speed;
                        break;
                    case "down":
                        mapY += speed;
                        break;
                    case "left":
                        mapX -= speed;
                        break;
                    case "right":
                        mapX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }

        }

        if (invincible) {

            invincibleCounter++;

            if (invincibleCounter > 40) {

                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking() {

    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = utilityTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void dyingAnimation(Graphics2D graphics2D) {

        dyingCounter++;

        int frequency = 5;

        if (dyingCounter <= frequency) {

            changeAlpha(graphics2D, 0f);
        }
        if (dyingCounter > frequency && dyingCounter <= frequency * 2) {

            changeAlpha(graphics2D, 1f);
        }
        if (dyingCounter > frequency * 2 && dyingCounter <= frequency * 3) {

            changeAlpha(graphics2D, 0f);
        }
        if (dyingCounter > frequency * 3 && dyingCounter <= frequency * 4) {

            changeAlpha(graphics2D, 1f);
        }
        if (dyingCounter > frequency * 4 && dyingCounter <= frequency * 5) {

            changeAlpha(graphics2D, 0f);
        }
        if (dyingCounter > frequency * 5 && dyingCounter <= frequency * 6) {

            changeAlpha(graphics2D, 1f);
        }
        if (dyingCounter > frequency * 6 && dyingCounter <= frequency * 7) {

            changeAlpha(graphics2D, 0f);
        }
        if (dyingCounter > frequency * 7 && dyingCounter <= frequency * 8) {

            changeAlpha(graphics2D, 1f);
        }
        if (dyingCounter > frequency * 8) {

            dying = false;
            alive = false;
        }
    }

    // TRANSPARENCY
    public void changeAlpha(Graphics2D graphics2D, float alphaValue) {

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = down1;

        int screenX = mapX - display.hero.mapX + display.hero.screenX;
        int screenY = mapY - display.hero.mapY + display.hero.screenY;

        int x = screenX;
        int y = screenY;

        int rightEdgeDistance = 0;
        int bottomEdgeDistance = 0;

        // STOP CAMERA MOVING AT EDGE
        if (display.hero.screenX > display.hero.mapX) {

            x = mapX;
        }
        if (display.hero.screenY > display.hero.mapY) {

            y = mapY;
        }

        rightEdgeDistance = display.screenWidth - display.hero.screenX;
        if (rightEdgeDistance > display.mapWidth - display.hero.mapX) {

            x = display.screenWidth - (display.mapWidth - mapX);
        }

        bottomEdgeDistance = display.screenHeight - display.hero.screenY;
        if (bottomEdgeDistance > display.mapHeight - display.hero.mapY) {

            y = display.screenHeight - (display.mapHeight - mapY);
        }

        // CAMERA DIDN'T SOP AT THE EDGE, AND ENTITY IS VISIBLE
        if (mapX + display.tileSize > display.hero.mapX - display.hero.screenX &&
                mapX - display.tileSize < display.hero.mapX + display.hero.screenX &&
                mapY + display.tileSize > display.hero.mapY - display.hero.screenY &&
                mapY - display.tileSize < display.hero.mapY + display.hero.screenY) {

            switch (direction) {
                case "up":
                    if (!attacking) {

                        if (spriteNumber == 1) {

                            image = up1;
                        }
                        if (spriteNumber == 2) {

                            image = up2;
                        }
                    }
                    if (attacking) {

                        y = y - display.tileSize;

                        if (spriteNumber == 1) {

                            image = attackUp1;
                        }
                        if (spriteNumber == 2) {

                            image = attackUp2;
                        }
                    }

                    break;
                case "down":
                    if (!attacking) {

                        if (spriteNumber == 1) {

                            image = down1;
                        }
                        if (spriteNumber == 2) {

                            image = down2;
                        }
                    }
                    if (attacking) {

                        if (spriteNumber == 1) {

                            image = attackDown1;
                        }
                        if (spriteNumber == 2) {

                            image = attackDown2;
                        }
                    }
                    break;
                case "left":
                    if (!attacking) {

                        if (spriteNumber == 1) {

                            image = left1;
                        }
                        if (spriteNumber == 2) {

                            image = left2;
                        }
                    }
                    if (attacking) {

                        x = x - display.tileSize;

                        if (spriteNumber == 1) {

                            image = attackLeft1;
                        }
                        if (spriteNumber == 2) {

                            image = attackLeft2;
                        }
                    }
                    break;
                case "right":
                    if (!attacking) {

                        if (spriteNumber == 1) {

                            image = right1;
                        }
                        if (spriteNumber == 2) {

                            image = right2;
                        }
                    }
                    if (attacking) {

                        if (spriteNumber == 1) {

                            image = attackRight1;
                        }
                        if (spriteNumber == 2) {

                            image = attackRight2;
                        }
                    }
                    break;
            }

            // BANDIT HEALTH BAR
            if (type == 2 && healthbarOn) {

                double oneScale = (double) display.tileSize / maxLife;
                double healthBarValue = oneScale * life;

                graphics2D.setColor(new Color(35, 35, 35));
                graphics2D.fillRect(
                        x - display.tileSize / 24,
                        y - display.tileSize / 24 - display.tileSize / 4,
                        display.tileSize + display.tileSize / 12,
                        display.tileSize / 8 + display.tileSize / 12);

                graphics2D.setColor(new Color(255, 0, 30));
                graphics2D.fillRect(
                        x,
                        y - display.tileSize / 4,
                        (int) healthBarValue,
                        display.tileSize / 8);

                healthBarCounter++;

                if (healthBarCounter > 600) {

                    healthBarCounter = 0;
                    healthbarOn = false;
                }
            }

            if (invincible) {
                healthbarOn = true;
                healthBarCounter = 0;
                // TRANSPARENCY
                changeAlpha(graphics2D, 0.4f);
            }
            if (dying) {

                dyingAnimation(graphics2D);
            }

            graphics2D.drawImage(
                    image,
                    x,
                    y,
                    null);

            changeAlpha(graphics2D, 1f);

            // graphics2D.setColor(Color.red);
            // graphics2D.drawRect(
            // screenX + interactionArea.x,
            // screenY + interactionArea.y,
            // interactionArea.width,
            // interactionArea.height);

            // CAMERA STOOPED AT EDGE, BUT ENTITY IS STILL VISIBLE
        } else if (display.hero.screenX > display.hero.mapX ||
                display.hero.screenY > display.hero.mapY ||
                rightEdgeDistance > display.mapWidth - display.hero.mapX ||
                bottomEdgeDistance > display.mapHeight - display.hero.mapY) {

            if (x >= -display.tileSize &&
                    x <= display.screenWidth &&
                    y >= -display.tileSize &&
                    y <= display.screenHeight) {

                switch (direction) {
                    case "up":
                        if (!attacking) {

                            if (spriteNumber == 1) {

                                image = up1;
                            }
                            if (spriteNumber == 2) {

                                image = up2;
                            }
                        }
                        if (attacking) {

                            y = y - display.tileSize;

                            if (spriteNumber == 1) {

                                image = attackUp1;
                            }
                            if (spriteNumber == 2) {

                                image = attackUp2;
                            }
                        }

                        break;
                    case "down":
                        if (!attacking) {

                            if (spriteNumber == 1) {

                                image = down1;
                            }
                            if (spriteNumber == 2) {

                                image = down2;
                            }
                        }
                        if (attacking) {

                            if (spriteNumber == 1) {

                                image = attackDown1;
                            }
                            if (spriteNumber == 2) {

                                image = attackDown2;
                            }
                        }
                        break;
                    case "left":
                        if (!attacking) {

                            if (spriteNumber == 1) {

                                image = left1;
                            }
                            if (spriteNumber == 2) {

                                image = left2;
                            }
                        }
                        if (attacking) {

                            x = x - display.tileSize;

                            if (spriteNumber == 1) {

                                image = attackLeft1;
                            }
                            if (spriteNumber == 2) {

                                image = attackLeft2;
                            }
                        }
                        break;
                    case "right":
                        if (!attacking) {

                            if (spriteNumber == 1) {

                                image = right1;
                            }
                            if (spriteNumber == 2) {

                                image = right2;
                            }
                        }
                        if (attacking) {

                            if (spriteNumber == 1) {

                                image = attackRight1;
                            }
                            if (spriteNumber == 2) {

                                image = attackRight2;
                            }
                        }
                        break;
                }

                // BANDIT HEALTH BAR
                if (type == 2 && healthbarOn) {

                    double oneScale = (double) display.tileSize / maxLife;
                    double healthBarValue = oneScale * life;

                    graphics2D.setColor(new Color(35, 35, 35));
                    graphics2D.fillRect(
                            x - display.tileSize / 24,
                            y - display.tileSize / 24 - display.tileSize / 4,
                            display.tileSize + display.tileSize / 12,
                            display.tileSize / 8 + display.tileSize / 12);

                    graphics2D.setColor(new Color(255, 0, 30));
                    graphics2D.fillRect(
                            x,
                            y - display.tileSize / 4,
                            (int) healthBarValue,
                            display.tileSize / 8);

                    healthBarCounter++;

                    if (healthBarCounter > 600) {

                        healthBarCounter = 0;
                        healthbarOn = false;
                    }
                }

                if (invincible) {
                    healthbarOn = true;
                    healthBarCounter = 0;
                    // TRANSPARENCY
                    changeAlpha(graphics2D, 0.4f);
                }
                if (dying) {

                    dyingAnimation(graphics2D);
                }

                graphics2D.drawImage(
                        image,
                        x,
                        y,
                        null);

                changeAlpha(graphics2D, 1f);

                // graphics2D.setColor(Color.red);
                // graphics2D.drawRect(
                // screenX + interactionArea.x,
                // screenY + interactionArea.y,
                // interactionArea.width,
                // interactionArea.height);
            }
        }
    }
}
