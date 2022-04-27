package main;

import entity.Entity;

public class CollisionDetector {

    Display display;

    public CollisionDetector(Display display) {

        this.display = display;
    }

    public void checkTile(Entity entity) {

        int entityLeftMapX = entity.mapX +
                entity.solidArea.x;

        int entityRightMapX = entity.mapX +
                entity.solidArea.x +
                entity.solidArea.width;

        int entityTopMapY = entity.mapY +
                entity.solidArea.y;

        int entityBottomMapY = entity.mapY +
                entity.solidArea.y +
                entity.solidArea.height;

        int entityLeftCol = entityLeftMapX / display.tileSize;
        int entityRightCol = entityRightMapX / display.tileSize;
        int entityTopRow = entityTopMapY / display.tileSize;
        int entityBottomRow = entityBottomMapY / display.tileSize;

        int tileNumber1;
        int tileNumber2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopMapY - entity.speed) / display.tileSize;

                tileNumber1 = display.tileManager.mapTileNumber[entityLeftCol][entityTopRow];

                tileNumber2 = display.tileManager.mapTileNumber[entityRightCol][entityTopRow];

                if (display.tileManager.tile[tileNumber1].collision ||
                        display.tileManager.tile[tileNumber2].collision) {

                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomMapY + entity.speed) / display.tileSize;

                tileNumber1 = display.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];

                tileNumber2 = display.tileManager.mapTileNumber[entityRightCol][entityBottomRow];

                if (display.tileManager.tile[tileNumber1].collision ||
                        display.tileManager.tile[tileNumber2].collision) {

                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftMapX - entity.speed) / display.tileSize;

                tileNumber1 = display.tileManager.mapTileNumber[entityLeftCol][entityTopRow];

                tileNumber2 = display.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];

                if (display.tileManager.tile[tileNumber1].collision ||
                        display.tileManager.tile[tileNumber2].collision) {

                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightMapX + entity.speed) / display.tileSize;

                tileNumber1 = display.tileManager.mapTileNumber[entityRightCol][entityTopRow];

                tileNumber2 = display.tileManager.mapTileNumber[entityRightCol][entityBottomRow];

                if (display.tileManager.tile[tileNumber1].collision ||
                        display.tileManager.tile[tileNumber2].collision) {

                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int detectObject(Entity entity, boolean ifHero) {

        int index = 999;

        for (int i = 0; i < display.object.length; i++) {

            if (display.object[i] != null) {

                // GET ENTITY'S SOLID AREA POSITION
                entity.solidArea.x = entity.mapX + entity.solidArea.x;
                entity.solidArea.y = entity.mapY + entity.solidArea.y;

                // GET OBJECT'S SOLID AREA POSITION
                display.object[i].solidArea.x = display.object[i].mapX + display.object[i].solidArea.x;
                display.object[i].solidArea.y = display.object[i].mapY + display.object[i].solidArea.y;

                switch (entity.direction) {
                    case "up":

                        entity.solidArea.y -= entity.speed;
                        break;

                    case "down":

                        entity.solidArea.y += entity.speed;
                        break;

                    case "left":

                        entity.solidArea.x -= entity.speed;
                        break;

                    case "right":

                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(display.object[i].solidArea)) {

                    if (display.object[i].collision) {
                        entity.collisionOn = true;
                    }

                    if (ifHero) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                display.object[i].solidArea.x = display.object[i].solidAreaDefaultX;
                display.object[i].solidArea.y = display.object[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // NPC OR ENEMY COLLISION
    public int detectEntity(Entity entity, Entity[] target) {

        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                // GET ENTITY'S SOLID AREA POSITION
                entity.solidArea.x = entity.mapX + entity.solidArea.x;
                entity.solidArea.y = entity.mapY + entity.solidArea.y;

                // GET TARGET'S SOLID AREA POSITION
                target[i].solidArea.x = target[i].mapX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].mapY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":

                        entity.solidArea.y -= entity.speed;
                        break;

                    case "down":

                        entity.solidArea.y += entity.speed;
                        break;

                    case "left":

                        entity.solidArea.x -= entity.speed;
                        break;

                    case "right":

                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(target[i].solidArea)) {

                    if (target[i] != entity) {

                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }

        }

        return index;
    }

    // NPC OR ENEMY COLLISION
    public int detectEntityInteraction(Entity entity, Entity[] target) {

        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                // GET ENTITY'S SOLID AREA POSITION
                entity.solidArea.x = entity.mapX + entity.solidArea.x;
                entity.solidArea.y = entity.mapY + entity.solidArea.y;

                // GET TARGET'S INTERACTION AREA POSITION
                target[i].interactionArea.x = target[i].mapX + target[i].interactionArea.x - display.tileSize / 2;
                target[i].interactionArea.y = target[i].mapY + target[i].interactionArea.y - display.tileSize / 2;

                if (entity.solidArea.intersects(target[i].interactionArea)) {

                    entity.InteractionEnabled = true;
                    index = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                target[i].interactionArea.x = target[i].interactionAreaDefaultX;
                target[i].interactionArea.y = target[i].InteractionAreaDefaultY;
            }

        }

        return index;
    }

    public boolean detectHero(Entity entity) {

        boolean contactHero = false;

        // GET ENTITY'S SOLID AREA POSITION
        entity.solidArea.x = entity.mapX + entity.solidArea.x;
        entity.solidArea.y = entity.mapY + entity.solidArea.y;

        // GET OBJECT'S SOLID AREA POSITION
        display.hero.solidArea.x = display.hero.mapX + display.hero.solidArea.x;
        display.hero.solidArea.y = display.hero.mapY + display.hero.solidArea.y;

        switch (entity.direction) {
            case "up":

                entity.solidArea.y -= entity.speed;
                break;

            case "down":

                entity.solidArea.y += entity.speed;
                break;

            case "left":

                entity.solidArea.x -= entity.speed;
                break;

            case "right":

                entity.solidArea.x += entity.speed;
                break;
        }

        if (entity.solidArea.intersects(display.hero.solidArea)) {

            entity.collisionOn = true;
            contactHero = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        display.hero.solidArea.x = display.hero.solidAreaDefaultX;
        display.hero.solidArea.y = display.hero.solidAreaDefaultY;

        return contactHero;
    }
}
