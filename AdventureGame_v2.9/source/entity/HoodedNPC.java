package entity;

import main.Display;

public class HoodedNPC extends Entity {

    public HoodedNPC(Display display) {

        super(display);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {

        down1 = setup("/npc/hooded_npc/hooded_npc_left", display.tileSize, display.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Greetings Traveller!";
        dialogues[1] = "You fought well.";
        dialogues[2] = "You're a talented warrior.";
        dialogues[3] = "I have a quest for you.";
    }

    public void speak() {

        super.speak();
    }
}
