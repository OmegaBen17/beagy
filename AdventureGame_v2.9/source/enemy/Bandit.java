package enemy;

import java.util.Random;

import entity.Entity;
import main.Display;

public class Bandit extends Entity {

    Display display;

    public Bandit(Display display) {
        super(display);

        this.display = display;

        name = "Bandit";
        type = 2;

        speed = 2;
        maxLife = 3;
        life = maxLife;

        solidArea.x = 0;
        solidArea.y = display.tileSize / 4;
        solidArea.width = display.tileSize;
        solidArea.height = display.tileSize * 3 / 4;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = (display.tileSize * 3) / 4;
        attackArea.height = (display.tileSize * 3) / 4;

        getBanditImage();
        getBanditAttackImage();
    }

    public void getBanditImage() {

        up1 = setup("/bandit/bandit_up_1", display.tileSize, display.tileSize);
        up2 = setup("/bandit/bandit_up_2", display.tileSize, display.tileSize);
        down1 = setup("/bandit/bandit_down_1", display.tileSize, display.tileSize);
        down2 = setup("/bandit/bandit_down_2", display.tileSize, display.tileSize);
        left1 = setup("/bandit/bandit_left_1", display.tileSize, display.tileSize);
        left2 = setup("/bandit/bandit_left_2", display.tileSize, display.tileSize);
        right1 = setup("/bandit/bandit_right_1", display.tileSize, display.tileSize);
        right2 = setup("/bandit/bandit_right_2", display.tileSize, display.tileSize);
    }

    public void getBanditAttackImage() {

        attackUp1 = setup("/bandit/bandit_attack_up_1", display.tileSize, display.tileSize * 2);
        attackUp2 = setup("/bandit/bandit_attack_up_2", display.tileSize, display.tileSize * 2);
        attackDown1 = setup("/bandit/bandit_attack_down_1", display.tileSize, display.tileSize * 2);
        attackDown2 = setup("/bandit/bandit_attack_down_2", display.tileSize, display.tileSize * 2);
        attackLeft1 = setup("/bandit/bandit_attack_left_1", display.tileSize * 2, display.tileSize);
        attackLeft2 = setup("/bandit/bandit_attack_left_2", display.tileSize * 2, display.tileSize);
        attackRight1 = setup("/bandit/bandit_attack_right_1", display.tileSize * 2, display.tileSize);
        attackRight2 = setup("/bandit/bandit_attack_right_2", display.tileSize * 2, display.tileSize);
    }

    public void setAction() {

        int i = 0;
        Random random = new Random();

        actionCounter++;

        if (actionCounter == 120) {

            i = random.nextInt(100) + 1;

            if (i <= 25) {

                direction = "up";
            }
            if (i > 25 && i <= 50) {

                direction = "down";
            }
            if (i > 50 && i <= 75) {

                direction = "left";
            }
            if (i > 75 && i <= 100) {

                direction = "right";
            }

            actionCounter = 0;
        }

        attackActionCounter++;

        if (attackActionCounter == 60) {

            // DEBUG
            // attacking = true;

            i = random.nextInt(100) + 1;

            if (i <= 75) {

                attacking = false;
            }
            if (i > 75 && i <= 100) {

                attacking = true;
            }

            attackActionCounter = 0;
        }

    }

    public void damageReaction() {

        actionCounter = 0;

        switch (display.hero.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

        attacking = true;

    }

    public void attacking() {

        atackAnimationCounter++;

        int spriteNumberTime1 = 40;
        int spriteNumberTime2 = 60;

        // System.out.println(spriteCounter);

        if (atackAnimationCounter <= spriteNumberTime1) {

            spriteNumber = 1;
        }
        if (atackAnimationCounter > spriteNumberTime1 && atackAnimationCounter <= spriteNumberTime2) {

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
            boolean contactHero = display.collisionDetector.detectHero(this);

            if (this.type == 2 && contactHero) {

                if (!display.hero.invincible) {

                    display.hero.life--;
                    display.hero.invincible = true;
                }
            }

            mapX = currentMapX;
            mapY = currentMapY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeigth;
        }

        if (atackAnimationCounter > spriteNumberTime2) {

            spriteNumber = 1;
            atackAnimationCounter = 0;
            attacking = false;
        }
    }

}
