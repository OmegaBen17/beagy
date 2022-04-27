package entity;

import java.awt.AlphaComposite;
// import java.awt.Color;
// import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Display;
import main.KeyInput;

public class Hero extends Entity {

    // Display display;
    KeyInput keyInput;

    public final int screenX;
    public final int screenY;

    // public int hasKey = 0;

    int standCounter = 0;

    public Hero(Display display, KeyInput keyInput) {

        super(display);

        // this.display = display;
        this.keyInput = keyInput;

        screenX = display.screenWidth / 2 - (display.tileSize / 2);
        screenY = display.screenHeight / 2 - (display.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = display.tileSize / 6;
        solidArea.y = display.tileSize / 3;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = display.tileSize - display.tileSize / 3;
        solidArea.height = display.tileSize - display.tileSize / 3;

        attackArea.width = (display.tileSize * 3) / 4;
        attackArea.height = (display.tileSize * 3) / 4;

        setDefaultValues();
        getHeroImage();
        getHeroAttackImage();
    }

    public void setDefaultValues() {

        // mapX = display.tileSize * 23;
        // mapY = display.tileSize * 21;
        mapX = display.tileSize * 50 - display.tileSize / 2;
        mapY = display.tileSize * 23 - display.tileSize / 2;
        speed = 4;
        direction = "down";

        // HERO STATUS
        maxLife = 4;
        life = maxLife;
    }

    public void getHeroImage() {

        up1 = setup("/blue_knight/blue_knight_up_1", display.tileSize, display.tileSize);
        up2 = setup("/blue_knight/blue_knight_up_2", display.tileSize, display.tileSize);
        down1 = setup("/blue_knight/blue_knight_down_1", display.tileSize, display.tileSize);
        down2 = setup("/blue_knight/blue_knight_down_2", display.tileSize, display.tileSize);
        left1 = setup("/blue_knight/blue_knight_left_1", display.tileSize, display.tileSize);
        left2 = setup("/blue_knight/blue_knight_left_2", display.tileSize, display.tileSize);
        right1 = setup("/blue_knight/blue_knight_right_1", display.tileSize, display.tileSize);
        right2 = setup("/blue_knight/blue_knight_right_2", display.tileSize, display.tileSize);
    }

    public void getHeroAttackImage() {

        attackUp1 = setup("/blue_knight/blue_knight_attack_up_1", display.tileSize, display.tileSize * 2);
        attackUp2 = setup("/blue_knight/blue_knight_attack_up_2", display.tileSize, display.tileSize * 2);
        attackDown1 = setup("/blue_knight/blue_knight_attack_down_1", display.tileSize, display.tileSize * 2);
        attackDown2 = setup("/blue_knight/blue_knight_attack_down_2", display.tileSize, display.tileSize * 2);
        attackLeft1 = setup("/blue_knight/blue_knight_attack_left_1", display.tileSize * 2, display.tileSize);
        attackLeft2 = setup("/blue_knight/blue_knight_attack_left_2", display.tileSize * 2, display.tileSize);
        attackRight1 = setup("/blue_knight/blue_knight_attack_right_1", display.tileSize * 2, display.tileSize);
        attackRight2 = setup("/blue_knight/blue_knight_attack_right_2", display.tileSize * 2, display.tileSize);
    }

    public void update() {

        int npcIndex;

        if (attacking) {

            attacking();
            display.keyInput.spacePressed = false;

        } else {

            if (keyInput.spacePressed) {

                // ATTACKING
                attacking = true;
            }

            if (keyInput.ePressed) {

                // DETECT NPC INTERACTION
                InteractionEnabled = false;
                npcIndex = display.collisionDetector.detectEntityInteraction(this, display.npc);
                interactNPC(npcIndex);

                keyInput.ePressed = false;
            }

            if (keyInput.upPressed ||
                    keyInput.downPressed ||
                    keyInput.leftPressed ||
                    keyInput.rightPressed) {

                if (keyInput.upPressed) {
                    direction = "up";
                }
                if (keyInput.downPressed) {
                    direction = "down";
                }
                if (keyInput.leftPressed) {
                    direction = "left";
                }
                if (keyInput.rightPressed) {
                    direction = "right";
                }

                // System.out.println(direction);

                // COLLISION DETECTION
                collisionOn = false;
                display.collisionDetector.checkTile(this);

                // DETECT OBJECT COLLISION
                int objectIndex = display.collisionDetector.detectObject(this, true);
                pickUpObject(objectIndex);

                // DETECT NPC COLLISION
                npcIndex = display.collisionDetector.detectEntity(this, display.npc);
                // interactNPC(npcIndex);

                // DETECT BANDIT COLLISION
                display.collisionDetector.detectEntity(this, display.bandit);
                // int banditIndex = display.collisionDetector.detectEntity(this, display.bandit);
                // contactBandit(banditIndex);

                // CHECK EVENT
                display.eventHandler.checkEvent();

                // display.keyInput.ePressed = false;

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

        }

        if (!(keyInput.upPressed ||
                keyInput.downPressed ||
                keyInput.leftPressed ||
                keyInput.rightPressed) && !attacking) {

            standCounter++;

            if (standCounter >= 20) {

                spriteNumber = 1;
                standCounter = 0;
            }
        }

        if (invincible) {

            invincibleCounter++;

            if (invincibleCounter > 60) {

                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking() {

        spriteCounter++;

        // System.out.println(spriteCounter);

        if (spriteCounter <= 5) {

            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {

            spriteNumber = 2;

            // STORE CURRENT STATUS
            int currentMapX = mapX;
            int currentMapY = mapY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeigth = solidArea.height;

            // DEFINE ATTACK AREA ON MAP
            switch (direction) {
                case "up":

                    mapY -= attackArea.height;
                    break;

                case "down":

                    mapY += attackArea.height;
                    break;

                case "left":

                    mapX -= attackArea.width;
                    break;

                case "right":

                    mapX += attackArea.width;
                    break;
            }

            // MAKE SOLID AREA TO ATTACK AREA TO DETECT COLLISION
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // DEBUG
            // System.out.println("mapX: " + currentMapX + " " + mapX +
            // " mapY: " + currentMapY + " " + mapY +
            // " solidArea.width: " + solidAreaWidth + " " + solidArea.width +
            // " solidArea.heigth: " + solidAreaWidth + " " + solidArea.height);

            // DETECT COLLISION WITH BANDIT
            int banditIndex = display.collisionDetector.detectEntity(this, display.bandit);
            damageBandit(banditIndex);

            mapX = currentMapX;
            mapY = currentMapY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeigth;
        }

        if (spriteCounter > 25) {

            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int objectIndex) {

        if (objectIndex != 999) {

        }
    }

    public void interactNPC(int npcIndex) {

        if (npcIndex != 999) {
            // System.out.println("Collision with NPC!");

            if (display.keyInput.ePressed) {

                display.gameState = display.dialogueState;
                display.npc[npcIndex].speak();
            }
        }
    }

    public void contactBandit(int banditIndex) {

        if (banditIndex != 999) {
            // System.out.println("Collision with Bandit!");

            if (!invincible) {

                life--;
                invincible = true;
            }
        }
    }

    public void damageBandit(int i) {

        if (i != 999) {

            if (!display.bandit[i].invincible) {

                // System.out.println("HIT!");

                display.bandit[i].life--;
                display.bandit[i].invincible = true;
                display.bandit[i].damageReaction();

                if (display.bandit[i].life <= 0) {

                    display.bandit[i].dying = true;
                }
            }
        }
        // else {
        // System.out.println("MISS!");
        // }
    }

    // OLD PICK UP OBJECT METHOD
    // public void pickUpObject(int objectIndex) {

    // if (objectIndex != 999) {

    // // display.object[objectIndex] = null;

    // String objectName = display.object[objectIndex].name;

    // switch (objectName) {
    // case "Key":

    // hasKey++;
    // display.object[objectIndex] = null;

    // // System.out.println("Key: " + hasKey);
    // display.userInterface.showMessage("+1 Key");

    // break;

    // case "Door":

    // if (hasKey > 0) {

    // display.object[objectIndex] = null;
    // hasKey--;

    // display.userInterface.showMessage("Door opened!");
    // } else {

    // display.userInterface.showMessage("Key required!");
    // }

    // break;

    // case "Boots":

    // speed += 1;
    // display.object[objectIndex] = null;

    // display.userInterface.showMessage("Speed up!");

    // break;

    // case "Chest":

    // display.userInterface.gameFinished = true;

    // display.userInterface.showMessage("Speed up!");

    // break;
    // }
    // }
    // }

    public void draw(Graphics2D graphics2D) {

        // graphics2D.setColor(Color.white);
        // graphics2D.fillRect(x, y, display.tileSize, display.tileSize);

        BufferedImage image = null;

        int x = screenX;
        int y = screenY;

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

                    y = screenY - display.tileSize;

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

                    x = screenX - display.tileSize;

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

        if (screenX > mapX) {

            if (!attacking || (attacking && !(direction == "left"))) {

                x = mapX;
            }
            if (attacking && direction == "left") {

                x = mapX - display.tileSize;
            }
        }
        if (screenY > mapY) {

            if (!attacking || (attacking && !(direction == "up"))) {

                y = mapY;
            }
            if (attacking && direction == "up") {

                y = mapY - display.tileSize;
            }
        }

        int rightEdgeDistance = display.screenWidth - screenX;
        if (rightEdgeDistance > display.mapWidth - mapX) {

            if (!attacking || (attacking && !(direction == "left"))) {

                x = display.screenWidth - (display.mapWidth - mapX);

            }
            if (attacking && direction == "left") {

                x = display.screenWidth - (display.mapWidth - mapX) - display.tileSize;
            }
        }

        int bottomEdgeDistance = display.screenHeight - screenY;
        if (bottomEdgeDistance > display.mapHeight - mapY) {

            if (!attacking || (attacking && !(direction == "up"))) {

                y = display.screenHeight - (display.mapHeight - mapY);
            }
            if (attacking && direction == "up") {

                y = display.screenHeight - (display.mapHeight - mapY) - display.tileSize;
            }
        }

        if (invincible) {
            // TRANSPARENCY
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        graphics2D.drawImage(image, x, y, null);

        // RESET TRANSPARENCY
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG
        // graphics2D.setFont(new Font("Arial", Font.PLAIN, 26));
        // graphics2D.setColor(Color.white);
        // graphics2D.drawString("invincible: " + invincibleCounter, display.tileSize, 5
        // * display.tileSize);
    }
}
