package main;

import enemy.Bandit;
import entity.HoodedNPC;

// import object.Key;
// import object.Boots;
// import object.Chest;
// import object.Door;

public class AssetSetter {

    Display display;

    AssetSetter(Display display) {

        this.display = display;
    }

    public void setObject() {

    }

    public void setNPC() {

        display.npc[0] = new HoodedNPC(display);
        display.npc[0].mapX = display.tileSize * 21;
        display.npc[0].mapY = display.tileSize * 21;
    }

    public void setBandit() {

        display.bandit[0] = new Bandit(display);
        display.bandit[0].mapX = display.tileSize * 23;
        display.bandit[0].mapY = display.tileSize * 36;

        display.bandit[1] = new Bandit(display);
        display.bandit[1].mapX = display.tileSize * 24;
        display.bandit[1].mapY = display.tileSize * 37;   

        
        display.bandit[2] = new Bandit(display);
        display.bandit[2].mapX = display.tileSize * 10;
        display.bandit[2].mapY = display.tileSize * 10;

        display.bandit[3] = new Bandit(display);
        display.bandit[3].mapX = display.tileSize * 11;
        display.bandit[3].mapY = display.tileSize * 11;   

        
        display.bandit[4] = new Bandit(display);
        display.bandit[4].mapX = display.tileSize * 41;
        display.bandit[4].mapY = display.tileSize * 9;

        display.bandit[5] = new Bandit(display);
        display.bandit[5].mapX = display.tileSize * 40;
        display.bandit[5].mapY = display.tileSize * 9;   

        display.bandit[6] = new Bandit(display);
        display.bandit[6].mapX = display.tileSize * 39;
        display.bandit[6].mapY = display.tileSize * 42;

        display.bandit[7] = new Bandit(display);
        display.bandit[7].mapX = display.tileSize * 38;
        display.bandit[7].mapY = display.tileSize * 42;   
    }

    // OLD SET OBJECT METHOD
    // public void setObject() {

    //     display.object[0] = new Key(display);
    //     display.object[0].mapX = 27 * display.tileSize;
    //     display.object[0].mapY = 7 * display.tileSize;
        
    //     display.object[1] = new Key(display);
    //     display.object[1].mapX = 27 * display.tileSize;
    //     display.object[1].mapY = 28 * display.tileSize;
        
    //     display.object[2] = new Door(display);
    //     display.object[2].mapX = 25 * display.tileSize;
    //     display.object[2].mapY = 15 * display.tileSize;
        
    //     display.object[3] = new Door(display);
    //     display.object[3].mapX = 26 * display.tileSize;
    //     display.object[3].mapY = 15 * display.tileSize;
        
    //     display.object[4] = new Chest(display);
    //     display.object[4].mapX = 20 * display.tileSize;
    //     display.object[4].mapY = 20 * display.tileSize;
        
    //     display.object[5] = new Chest(display);
    //     display.object[5].mapX = 28 * display.tileSize;
    //     display.object[5].mapY = 7 * display.tileSize;

    //     display.object[6] = new Boots(display);
    //     display.object[6].mapX = 25 * display.tileSize;
    //     display.object[6].mapY = 20 * display.tileSize;
    // }
}
