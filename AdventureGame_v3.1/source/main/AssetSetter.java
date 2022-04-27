package main;

import enemy.Bandit;
import entity.HoodedNPC;

import object.Key;
import object.PotionRed;
import object.Boots;
import object.Chest;
import object.Door;

public class AssetSetter {

    Display display;

    AssetSetter(Display display) {

        this.display = display;
    }

    public void setObject() {

        int i = 0;

        display.object[i] = new PotionRed(display);
        display.object[i].mapX = display.tileSize * 47;
        display.object[i].mapY = display.tileSize * 18;
        i++;

        display.object[i] = new PotionRed(display);
        display.object[i].mapX = display.tileSize * 47;
        display.object[i].mapY = display.tileSize * 19;
        i++;

        display.object[i] = new PotionRed(display);
        display.object[i].mapX = display.tileSize * 47;
        display.object[i].mapY = display.tileSize * 20;
        i++;

        display.object[i] = new Key(display);
        display.object[i].mapX = display.tileSize * 44;
        display.object[i].mapY = display.tileSize * 4;
        i++;

        display.object[i] = new Key(display);
        display.object[i].mapX = display.tileSize * 54;
        display.object[i].mapY = display.tileSize * 4;
        i++;

        display.object[i] = new Key(display);
        display.object[i].mapX = display.tileSize * 44;
        display.object[i].mapY = display.tileSize * 12;
        i++;

        display.object[i] = new Boots(display);
        display.object[i].mapX = display.tileSize * 50;
        display.object[i].mapY = display.tileSize * 8;
        i++;

        display.object[i] = new Chest(display);
        display.object[i].mapX = display.tileSize * 57;
        display.object[i].mapY = display.tileSize * 7;
        i++;

        display.object[i] = new Door(display);
        display.object[i].mapX = display.tileSize * 49;
        display.object[i].mapY = display.tileSize * 15;

    }

    public void setNPC() {

        int i = 0;

        display.npc[i] = new HoodedNPC(display);
        display.npc[i].mapX = display.tileSize * 57;
        display.npc[i].mapY = display.tileSize * 5;
    }

    public void setBandit() {

        int i = 0;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 37;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 38;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 39;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 40;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 41;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 42;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 43;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 18;
        display.bandit[i].mapY = display.tileSize * 44;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 50;
        display.bandit[i].mapY = display.tileSize * 7;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 50;
        display.bandit[i].mapY = display.tileSize * 8;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 70;
        display.bandit[i].mapY = display.tileSize * 13;
        i++;

        display.bandit[i] = new Bandit(display);
        display.bandit[i].mapX = display.tileSize * 70;
        display.bandit[i].mapY = display.tileSize * 14;
    }

    // OLD SET OBJECT METHOD
    // public void setObject() {

    // display.object[0] = new Key(display);
    // display.object[0].mapX = 27 * display.tileSize;
    // display.object[0].mapY = 7 * display.tileSize;

    // display.object[1] = new Key(display);
    // display.object[1].mapX = 27 * display.tileSize;
    // display.object[1].mapY = 28 * display.tileSize;

    // display.object[2] = new Door(display);
    // display.object[2].mapX = 25 * display.tileSize;
    // display.object[2].mapY = 15 * display.tileSize;

    // display.object[3] = new Door(display);
    // display.object[3].mapX = 26 * display.tileSize;
    // display.object[3].mapY = 15 * display.tileSize;

    // display.object[4] = new Chest(display);
    // display.object[4].mapX = 20 * display.tileSize;
    // display.object[4].mapY = 20 * display.tileSize;

    // display.object[5] = new Chest(display);
    // display.object[5].mapX = 28 * display.tileSize;
    // display.object[5].mapY = 7 * display.tileSize;

    // display.object[6] = new Boots(display);
    // display.object[6].mapX = 25 * display.tileSize;
    // display.object[6].mapY = 20 * display.tileSize;
    // }
}
